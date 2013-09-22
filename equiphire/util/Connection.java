/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equiphire.util;

import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * 
 */

public class Connection {

    private static Connection conn = null;
    //String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    //String ConnectionURL = "jdbc:derby:equip;create=false";
    String driver="com.mysql.jdbc.Driver";
    String ConnectionURL="jdbc:mysql://localhost:3306/ucscexam";
    java.sql.Connection dbconnection;
    java.sql.Statement statement;

    private Connection() throws Exception {
        Class.forName(driver);
        dbconnection = DriverManager.getConnection(ConnectionURL,"root","123");
        statement = dbconnection.createStatement();
    }
    public static java.sql.Connection getConnectionObject(){

   try{
        return getConnectionInstance().dbconnection;
   }catch(Exception e){
   e.printStackTrace();
   return null;
   }

    }
    public static Connection getConnectionInstance() throws Exception {
        if (conn == null) {
            conn = new Connection();
        }
        return conn;
    }

    public synchronized  void runQuery(String query) throws Exception {
        statement.execute(query);
    }

    public synchronized  ResultSet getResult(String query) throws Exception {
        ResultSet res = statement.executeQuery(query);
        return res;
    }
}
