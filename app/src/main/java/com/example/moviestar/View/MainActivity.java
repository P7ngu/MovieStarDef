package com.example.moviestar.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.R;
import com.example.moviestar.View.login.RegistrazioneActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.moviestar.Controllers.CurrentUser;

import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {
    ResultSet UsersRS;
    static boolean isUserLogged=false;
    static ProgressBar progBar;

    public static void setUserLogged(boolean b) {
        isUserLogged=b;
    }

    public static void hideProgressBar() {
       progBar.setVisibility(View.GONE);
    }

    public static void showProgressBar(){
        progBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context mContext=this;
        SharedPreferences prefs;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progBar=findViewById(R.id.progressBar2);
        progBar.setVisibility(0);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            CurrentUser currentUser= CurrentUser.getInstance();
            currentUser.setUserId(bundle.getString("userID"));
            currentUser.setUsername(bundle.getString("username"));
            isUserLogged=true;
        }

        prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
        final String email = prefs.getString("email", "");
        final String password = prefs.getString("password", "");
        Log.d("Stored Data", email+password);
        if(!isUserLogged && email.length()>3 && password.length()>5) LoginController.Firebase_loginEmailPasswordUser(email, password, mContext);
        //Log.i("Test", "saved data: "+email+password);

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