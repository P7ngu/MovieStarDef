package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.login.LoginActivity;
import com.example.moviestar.View.login.RegistrazioneActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogoutController {


    public static void logoutCurrentUser(Context mContext) {
        UtenteDAO.logout_Firebase();

        SharedPreferences prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        editor.apply();

        CurrentUser.getInstance().setUserId(null);

        mContext.startActivity(new Intent(mContext, LoginActivity.class));

    }

}
