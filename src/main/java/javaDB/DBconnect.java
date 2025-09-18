package javaDB;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class DBconnect {
    private Connection conn;//ถ้า class Connection > 1 methon ทำแบบนี่
    private PreparedStatement stmt;

    final private String url = "jdbc:sqlite:";//+dbname = +null
    private String dbname;

    
    public DBconnect(){
        String dbpath = "\\DB\\posDB.db"; 
        this.dbname= url+System.getProperty("user.dir")+dbpath;
        System.out.println(this.dbname);
        connect();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(this.dbname);
        } catch (SQLException e) {
             String message = "The program cannot processd because folder DB was not found!";
             JOptionPane.showMessageDialog(null,message ,"",JOptionPane.ERROR_MESSAGE);
             System.out.println(message);
        }
    }

    public void disconnect(){
        try {
          if (stmt != null) stmt.close();
          if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String command,Object... anys) throws SQLException{
        stmt = conn.prepareStatement(command);
        for (int i = 0; i < anys.length; i++) {
            stmt.setObject(i+1, anys[i]);
        }
        return  stmt.executeQuery();
    }

    public ResultSet query(String command) throws SQLException{
        stmt = conn.prepareStatement(command);
        return stmt.executeQuery();
    }
    // find = use ?
    public DBconnect update(String command,Object... anys)throws SQLException{
        stmt = conn.prepareStatement(command);

        for (int i = 0; i < anys.length; i++) {
            stmt.setObject(i+1, anys[i]);
        }
        stmt.executeUpdate();
        return this;
    }

    public DBconnect update(String command) throws SQLException{
        stmt = conn.prepareStatement(command);
        stmt.executeUpdate();
        return this;
    }
    //object usefull == genaric;
    public DBconnect insert(String table, HashMap<String, Object> columnValues) throws SQLException {
        StringBuffer columnPart = new StringBuffer();
        StringBuffer valuePart = new StringBuffer();
        for (String columnName : columnValues.keySet()) {
            columnPart.append(columnName +",");
            valuePart.append("?,");
        }
        columnPart.deleteCharAt(columnPart.length()-1);
        valuePart.deleteCharAt(valuePart.length()-1);

        String command = String.format("INSERT INTO %s(%s) VALUES(%s)"
        , table,columnPart,valuePart);
        //columnValues.values() return เป็น  collection ทำควยไรไม่ได้ Error ไม่บอกด้วย 
        update(command,columnValues.values().toArray());
        return this;
    }

    public DBconnect mutiInsert(String table,String[] columns,Object[][] values)throws SQLException {
        StringBuffer columnPart=new StringBuffer();
        StringBuffer valuePart=new StringBuffer();
        int valueRowLen=values.length;
        int valueColLen= values[0].length; // error if row =0
        Object[] flatValues = new Object[valueRowLen*valueColLen];

        for (String c : columns) {
            columnPart.append(c +",");
        }
        columnPart.deleteCharAt(columnPart.length()-1);

        int j = 0;
        for (int i = 0; i < valueRowLen; i++) {
            valuePart.append("(");
            for (Object value : values[i]) {
                valuePart.append("?,");
                flatValues[j++] = value;
            }
            valuePart.deleteCharAt(valuePart.length()-1); 
            valuePart.append("),");
        }
        valuePart.deleteCharAt(valuePart.length()-1);
 
        String command = String.format("INSERT INTO %s(%s) VALUES(%s)"
        , table,columnPart,valuePart);
        System.out.println(command);
        System.out.println(Arrays.toString(flatValues));
        update(command,flatValues);
        return this;
    }

}