/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main;


import component.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;



/**
 *
 * @author DELL
 */
public class Menu extends javax.swing.JPanel {

    /**
     * Creates new form Menu
     */
    public static LinkedHashMap<String,Object[]> menuInfo;//same
    public static HashMap<String,Integer> menuNow  = new HashMap<>();
    public static  DefaultListModel<String> model = new DefaultListModel<>();

    public Menu() {
        initComponents();
        addMenuGrid();
    }
    

    public static void addMenuGrid() {
        //set layout
        jCourseContainer.setLayout(new GridBagLayout());
        jDesssertContainer.setLayout(new GridBagLayout());
        jDrinkContainer.setLayout(new GridBagLayout());
        //config layout 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int column = 3;
        int[] carSize = new int[3];
        int[] colCounter = new int[3];
        int[] rowCounter = new int[3];

        // query menu;
        menuInfo= Index.db.geteMenu();//same
        for(String name:menuInfo.keySet()) {
            int price =  (int)menuInfo.get(name)[0];
            String car = (String)menuInfo.get(name)[1];
            String imagePath = System.getProperty("user.dir") + (String)menuInfo.get(name)[2];

            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(imagePath));
                image.getScaledInstance(280, 230, Image.SCALE_DEFAULT);
            } catch (IOException e) {
                try {
                    image = ImageIO.read(new File(System.getProperty("user.dir")+"\\DB\\Pic\\default_image.png"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            //add data MenuGrid
            MenuGrid grid = new MenuGrid();
            grid.price = price;
            grid.jLabelName.setText(name);
            grid.jLabelImage.setIcon(new ImageIcon(image));
            //set Grid and 
            int containerIndex = 0;
            javax.swing.JPanel targetContainer = null;
            if (car.equals(Index.MAIN_DISH)) {
                carSize[0]++;
                targetContainer = jCourseContainer;
                containerIndex = 0;
            } else if (car.equals(Index.DESSERT)) {
                carSize[1]++;
                targetContainer = jDesssertContainer;
                containerIndex = 1;
            } else {
                carSize[2]++;
                targetContainer = jDrinkContainer;
                containerIndex = 2;
            }

            gbc.gridx = colCounter[containerIndex] % column;
            gbc.gridy = rowCounter[containerIndex];

            if (colCounter[containerIndex] % column == column - 1) {
                rowCounter[containerIndex]++;
            }

            colCounter[containerIndex]++;

            targetContainer.add(grid, gbc);
        }
        jCourseContainer.revalidate();
        jDesssertContainer.revalidate();
        jDrinkContainer.revalidate();
    }
    public static void clearMenuGrid(){
        jCourseContainer.removeAll();
        jDesssertContainer.removeAll();
        jDrinkContainer.removeAll();
    }

    public static  void setTotalPrice(){
        int total=0;
        for (String name :menuNow.keySet()) {
            int amount = menuNow.get(name);
            int price = (int)menuInfo.get(name)[0];
            total+=(amount* price);
        }
        jButtonCheckBill.setText("PayBill "+ total);
    }
    public static String getTotalPrice(){
        return jButtonCheckBill.getText().split(" ")[1];
    }
    

    private void createReceipt(javax.swing.JTextArea TA){
        String receipt = "         CS Brew Coffee & Restaurant \n\n";
        for (String name : menuNow.keySet()) {
            int amount = menuNow.get(name);
            int price = (int)menuInfo.get(name)[0];
            receipt+= String.format("  %d  %s\t\t%d\n"
            ,amount, name,(amount*price));    
        }
        receipt+="  -------------------------------------------------- \n\n";
        receipt+="  Total\t\t\t"+ getTotalPrice();
        TA.setText(receipt);
    }

    public  static void endOrder(){
        //clear receipt
        model.clear();
        jListMenu.setModel(model);

        menuNow.clear();
        //clear pay with cash  page
        BillCash.jLabelChange.setText("0 bath");
        BillCash.jTextFieldCash.setText("");
        setTotalPrice();
    }
    public static void makeOrderTransition(){
        int transitionId = Index.db.addAndGetIdTranstion();
        //for loop insert in order table
        for (String name : menuNow.keySet()) {
            int product_id = (int)menuInfo.get(name)[3];
            int amount = menuNow.get(name);
            Index.db.addTransition(transitionId,product_id,amount);
        }
        Index.haveOrder = true;
        endOrder();
        Index.jTabbedPane1.setSelectedIndex(0);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonDeleteMenu = new javax.swing.JButton();
        jButtonCheckBill = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButtonClearMenu = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButtonMain = new javax.swing.JButton();
        jButtonDessert = new javax.swing.JButton();
        jButtonDrink = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jCourseContainer = new javax.swing.JPanel();
        jDesssertContainer = new javax.swing.JPanel();
        jDrinkContainer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListMenu = new javax.swing.JList<>();

        setBackground(new java.awt.Color(235, 235, 235));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonDeleteMenu.setBackground(new java.awt.Color(244, 103, 0));
        jButtonDeleteMenu.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        jButtonDeleteMenu.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeleteMenu.setText("Delete Menu");
        jButtonDeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteMenuActionPerformed(evt);
            }
        });
        add(jButtonDeleteMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 590, 180, 60));

        jButtonCheckBill.setBackground(new java.awt.Color(244, 103, 0));
        jButtonCheckBill.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        jButtonCheckBill.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCheckBill.setText("PayBill 0");
        jButtonCheckBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCheckBillActionPerformed(evt);
            }
        });
        add(jButtonCheckBill, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 660, 350, 60));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel18.setText("Queue ");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel11.setText(":  Status");
        jLabel11.setToolTipText("");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 90, 30));

        jButtonClearMenu.setBackground(new java.awt.Color(244, 103, 0));
        jButtonClearMenu.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jButtonClearMenu.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClearMenu.setText("Clear Bill");
        jButtonClearMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearMenuActionPerformed(evt);
            }
        });
        jPanel6.add(jButtonClearMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 140, 70));

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, -1, 70));

        jButton6.setBackground(new java.awt.Color(244, 103, 0));
        jButton6.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Promotion");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 590, 160, 60));

        jButtonMain.setBackground(new java.awt.Color(244, 103, 0));
        jButtonMain.setFont(new java.awt.Font("Comic Sans MS", 0, 22)); // NOI18N
        jButtonMain.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMain.setText("Main course");
        jButtonMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMainActionPerformed(evt);
            }
        });
        add(jButtonMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 660, 180, 60));

        jButtonDessert.setBackground(new java.awt.Color(244, 103, 0));
        jButtonDessert.setFont(new java.awt.Font("Comic Sans MS", 0, 22)); // NOI18N
        jButtonDessert.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDessert.setText("Dessert");
        jButtonDessert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDessertActionPerformed(evt);
            }
        });
        add(jButtonDessert, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 660, 180, 60));

        jButtonDrink.setBackground(new java.awt.Color(244, 103, 0));
        jButtonDrink.setFont(new java.awt.Font("Comic Sans MS", 0, 22)); // NOI18N
        jButtonDrink.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDrink.setText("Drink");
        jButtonDrink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDrinkActionPerformed(evt);
            }
        });
        add(jButtonDrink, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 660, 180, 60));

        jCourseContainer.setLayout(new java.awt.GridBagLayout());
        jTabbedPane1.addTab("tab1", jCourseContainer);

        jDesssertContainer.setLayout(new java.awt.GridBagLayout());
        jTabbedPane1.addTab("tab2", jDesssertContainer);

        jDrinkContainer.setLayout(new java.awt.GridBagLayout());
        jTabbedPane1.addTab("tab3", jDrinkContainer);

        jScrollPane1.setViewportView(jTabbedPane1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, -20, 970, 650));

        jScrollPane2.setToolTipText("");

        jListMenu.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jScrollPane2.setViewportView(jListMenu);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 100, 350, 470));
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButtonDeleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteMenuActionPerformed
        //copy model jListMenu and remove  
        model = (DefaultListModel<String>)jListMenu.getModel();
        menuNow.remove(jListMenu.getSelectedValue().split("X")[0].trim());
        model.removeElementAt(jListMenu.getSelectedIndex());
        jListMenu.setModel(model);

        setTotalPrice();
    }//GEN-LAST:event_jButtonDeleteMenuActionPerformed

    private void jButtonCheckBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCheckBillActionPerformed
        if(getTotalPrice().equals("0")){
            return;
        }
        createReceipt(CheckBill.jReceipt);
        Index.jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButtonCheckBillActionPerformed

    private void jButtonClearMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearMenuActionPerformed
        endOrder();
    }//GEN-LAST:event_jButtonClearMenuActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButtonDessertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDessertActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jButtonDessertActionPerformed

    private void jButtonMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMainActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButtonMainActionPerformed

    private void jButtonDrinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDrinkActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButtonDrinkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton6;
    public static javax.swing.JButton jButtonCheckBill;
    private javax.swing.JButton jButtonClearMenu;
    private javax.swing.JButton jButtonDeleteMenu;
    private javax.swing.JButton jButtonDessert;
    private javax.swing.JButton jButtonDrink;
    private javax.swing.JButton jButtonMain;
    public static javax.swing.JPanel jCourseContainer;
    public static javax.swing.JPanel jDesssertContainer;
    public static javax.swing.JPanel jDrinkContainer;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel18;
    public static javax.swing.JList<String> jListMenu;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
