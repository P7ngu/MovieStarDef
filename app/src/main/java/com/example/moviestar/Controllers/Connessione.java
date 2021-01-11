package com.example.moviestar.Controllers;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Connessione {
    static Connection Connessione = null;
    final static String url1 = "db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com" + "/" + "db";
    private static Connection dbConnection = null;
    public static final String username = "admin";
    public static final String password = "password";
    static String dbName = "db";
    static String userName = "admin";
    static String hostname = "db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com";
    static String port = "3306";

    public static Connection getConnection() {
        if (dbConnection == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String URL1= "jdbc:mysql://db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com:3306/db?user=admin&password=password&useUnicode=true"+
                "&characterEncoding=UTF"+
                        "-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
               dbConnection = DriverManager.getConnection(URL1);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return dbConnection;
    }





    public static ResultSet getUtentiDB() throws SQLException, ClassNotFoundException {
        Statement stmt = dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from utente");
        return rs;
    }




}

