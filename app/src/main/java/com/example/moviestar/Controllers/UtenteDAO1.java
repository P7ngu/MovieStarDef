package com.example.moviestar.Controllers;

import java.sql.ResultSet;

public class UtenteDAO1 {
   static ResultSet UtentiFromDB;

    public static ResultSet getUtentiFromDB() {
        return UtentiFromDB;
    }

    public static void setUtentiFromDB(ResultSet utentiFromDB) {
        UtentiFromDB = utentiFromDB;
    }
}
