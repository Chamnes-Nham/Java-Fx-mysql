package com.mycompany.mysqlproject.dbtool;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconfig {
    public static Connection connection = null;

    /**
     * Establishes a connection to the MySQL database.
     * 
     * @return Connection object if the connection is successful, null otherwise.
     */
    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String user = "root";
        String password = "";
        try {
            if (connection == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection OK");
            }
        } catch (Exception e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main (String[] args){
        DBconfig.getConnection();
    }
}
