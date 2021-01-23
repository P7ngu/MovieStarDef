package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.login.LoginActivity;
import com.example.moviestar.View.login.RegistrazioneActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogoutController {
    private static FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser user;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();

    public static void logoutCurrentUser_Firebase(Context mContext){
        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        if(user != null && firebaseAuth!=null){
            firebaseAuth.signOut();

            SharedPreferences prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            editor.apply();

            CurrentUser.getInstance().setUserId(null);

            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }

    }
}
