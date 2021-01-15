package com.example.moviestar.Controllers;

import android.app.Application;
import android.net.Uri;

public class CurrentUser extends Application {
    private static String username;
    private static String userId;
    private static Uri imageUri;

    public static Uri getImageUri() {
        return imageUri;
    }

    public static void setImageUri(Uri imageUri) {
        CurrentUser.imageUri = imageUri;
    }

    private static CurrentUser instance;

    public static CurrentUser getInstance() {
        if (instance == null)
            instance = new CurrentUser();
        return instance;

    }

    public CurrentUser(){}


    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
