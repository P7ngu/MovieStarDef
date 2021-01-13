package com.example.moviestar.Controllers;

public class CurrentUser {
    public static String getUsername() {
        return username;
    }

    public static String getUserId() {
        return userId;
    }

    static String username;
    static String userId;

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static void setUserId(String userId) {
        CurrentUser.userId = userId;
    }
}
