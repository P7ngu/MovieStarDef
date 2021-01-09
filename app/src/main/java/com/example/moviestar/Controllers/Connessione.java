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
    // Controllo connessione
    final static String url1 = "db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com" + "/" + "db";
    private static Connection dbConnection = null;
    public static final String username = "admin";
    public static final String password = "password";
    static String dbName = "db";
    static String userName = "admin";
    static String hostname = "db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com";
    static String port = "3306";

    static String jdbcUrl = System.getenv().get("jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password);

    public static Connection getConnection() {
        if (dbConnection == null){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String Url2="jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
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
        }
        return dbConnection;
    }


    public static Connection getDBConnection2()throws SQLException{
        String url = "jdbc:postgresql://database-4.cnwn7xaokcqa.us-east-2.rds.amazonaws.com:5432/";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","password");
        props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(url, props);

        return conn;
    }

    public static Connection getDBConnection1() throws SQLException {
        Connection con=null;
        try {
                    // if(Connessione == null) {
            //Connessione = DriverManager.getConnection(url,username, password);
            con = DriverManager.getConnection(jdbcUrl);
                Log.d("connessione", "connesso");
            //}
        }
        catch(Exception e) {
            Log.d("connessione", "connessione fallita "+Connessione);
            e.printStackTrace();
        }
        try (ResultSet rs = crea()) {
        } catch(Exception e){
            e.printStackTrace();
        }

        return con;
    }

    public static ResultSet crea() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://database-4.cnwn7xaokcqa.us-east-2.rds.amazonaws.com:3306/";
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("ok");
        Connection connection = DriverManager.getConnection(url);
        Log.d("connessione", "connesso.");
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from utente");
        return rs;
    }




}

