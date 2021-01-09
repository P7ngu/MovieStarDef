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
    static String dbName = System.getenv("db");
    static String userName = System.getenv("admin");
    static String hostname = System.getenv("db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com");
    static String port = System.getenv("3306");

    static String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;

    public static Connection getConnection() {
        if (dbConnection == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                dbConnection = DriverManager.getConnection(jdbcUrl);
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
        String url = "jdbc:postgresql://database-4.cnwn7xaokcqa.us-east-2.rds.amazonaws.com";
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
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("ok");
        Connection connection = DriverManager.getConnection(jdbcUrl);
        Log.d("connessione", "connesso.");
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from utente");
        return rs;
    }




}

