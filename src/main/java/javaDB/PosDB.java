package javaDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PosDB {
    DBconnect db; 
    public PosDB(){
       db  = new DBconnect();
    }
    
    public void addMenu(String name,String categories ,int price,String imagePath) throws SQLException{
        HashMap<String,Object> in = new HashMap<>();
        in.put("name",name);
        in.put("price",price);
        in.put("categories",categories);
        in.put("image",imagePath);
        db.insert("product", in);

    }

    public void deleteMenu(String menuName){
        String command = "DELETE FROM product where name = ?";
        try {
            db.update(command,menuName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public LinkedHashMap<String,Object[]> geteMenu(){
        String command ="SELECT * FROM product ORDER BY categories ";
            try {
                LinkedHashMap<String,Object[]> MenuInfo = new LinkedHashMap<>();
                ResultSet result =  db.query(command);
                while(result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int price = result.getInt("price");
                    String car = result.getString("categories");
                    String image = result.getString("image");
                    MenuInfo.put(name, new Object[]{price,car,image,id});
                }
                return MenuInfo;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    public ResultSet getTransition(){
        String command = "SELECT transition.id, product.name, orders.amount, transition.timestamp "+
        "FROM orders INNER JOIN product ON product.id=orders.product_id "+ 
        "INNER JOIN transition ON transition.id=orders.transition_id "+
        "ORDER BY transition.timestamp DESC";
        try {
            return db.query(command);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addTransition(int transition_id,int product_id,int amount){
        HashMap<String,Object> in = new HashMap<>();
        in.put("transition_id",transition_id);
        in.put("product_id",product_id);
        in.put("amount",amount);
        try {
            db.insert("orders",in) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  int addAndGetIdTranstion(){
        try {
            db.update("INSERT INTO transition DEFAULT VALUES");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String command ="SELECT id FROM transition ORDER BY id DESC  LIMIT 1";
        try {
            ResultSet result =db.query(command);
            return result.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

