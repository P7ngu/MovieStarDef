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
import com.example.moviestar.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean isUserLogged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context mContext=this;
        SharedPreferences prefs;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
        final String username = prefs.getString("username", "");
        final String password = prefs.getString("password", "");
        Log.i("Test", "saved data: "+username+password);

        final AuthenticationHandler authenticationHandler=new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                isUserLogged=true;
                Log.i("Test", "auto login: "+username+password);
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                AuthenticationDetails authenticationDetails=new AuthenticationDetails(userId, password, null);

                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

            }

            @Override
            public void onFailure(Exception exception) {
            Log.d("Test", "login fallito");
            }
        };

        CognitoSettings cognitoSettings=new CognitoSettings(MainActivity.this);

        CognitoUser thisUser=cognitoSettings.getUserPool().getUser(username);
        thisUser.getSessionInBackground(authenticationHandler);

        if (!isUserLogged) {
            Log.d("Test", "Main");

            Intent intent = new Intent(this, RegistrazioneActivity.class);
            //startActivity(intent);

        }


        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //   R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
        //    .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);

    }

}