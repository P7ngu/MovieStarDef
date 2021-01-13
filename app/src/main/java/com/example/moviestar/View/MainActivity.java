package com.example.moviestar.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.moviestar.Controllers.CognitoSettings;
import com.example.moviestar.Controllers.Connessione;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.RegistrazioneController;
import com.example.moviestar.Controllers.UtenteDAO1;
import com.example.moviestar.R;
import com.example.moviestar.View.login.RegistrazioneActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Connection;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {
    ResultSet UsersRS;
    boolean isUserLogged=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context mContext=this;
        SharedPreferences prefs;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            CurrentUser.setUserId(bundle.getString("userId"));
            CurrentUser.setUsername(bundle.getString("username"));
        }


        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


        prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");
        Log.i("Test", "saved data: "+username+password);

        if(!isUserLogged){
            Intent intent = new Intent(this, RegistrazioneActivity.class);
            startActivity(intent);
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
      R.id.navigation_home, R.id.navigation_social, R.id.navigation_profilo)
          .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

}