package com.example.moviestar.Controllers;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connessione {
    static Connection Connessione = null;
    // Controllo connessione
    final static String url = "db-5.cnwn7xaokcqa.us-east-2.rds.amazonaws.com" + "/" + "db";

    public static final String username = "admin";
    public static final String password = "password";


    public static Connection getDBConnection() throws SQLException {
        try {
           // if(Connessione == null) {
            Class.forName("com.mysql.jdbc.Driver");
            Connessione = DriverManager.getConnection(url,username, password);
                Log.d("connessione", "connesso");
            //}
        }
        catch(Exception e) {
            Log.d("connessione", "connessione fallita "+Connessione);
            e.printStackTrace();
        }
        try (ResultSet rs = crea()) {
        }


        return Connessione;
    }

    public static ResultSet crea() throws SQLException {
            Statement stmt = Connessione.createStatement();
            ResultSet rs = stmt.executeQuery("select * from utente");
            return rs;
        }



}

