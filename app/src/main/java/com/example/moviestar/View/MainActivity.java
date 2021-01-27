package com.example.moviestar.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.InviaRichiesteAmicoController;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.LogoutController;
import com.example.moviestar.Controllers.VerificaController;
import com.example.moviestar.R;
import com.example.moviestar.View.login.RegistrazioneActivity;
import com.example.moviestar.View.login.VerificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
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
    static boolean isUserVerified=false;
    static ProgressBar progBar;
    Context myContext;

    public void refreshActivity(){
        finish();
        startActivity(getIntent());
    }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_menu_button:
                LogoutController.logoutCurrentUser_Firebase(myContext);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context mContext=this;
        SharedPreferences prefs;
        myContext=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progBar=findViewById(R.id.progressBar2);
        progBar.setVisibility(0);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        isUserVerified= VerificaController.IsEmailVerified();


        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            CurrentUser currentUser= CurrentUser.getInstance();
           if(bundle.getString("userID")!=null && bundle.getString("userID").length()>0
                   && bundle.get("username")!= null &&bundle.getString("username").length()>0) {
                currentUser.setUserId(bundle.getString("userID"));
                currentUser.setUsername(bundle.getString("username"));
                if(isUserVerified) isUserLogged = true;
                else startActivity(new Intent(this, VerificationActivity.class));
            }
        }

        prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
        final String email = prefs.getString("email", "");
        final String password = prefs.getString("password", "");
        Log.d("Stored Data", email+password);
        if(!isUserLogged && isUserVerified && email.length()>3 && password.length()>5) LoginController.Firebase_loginEmailPasswordUser(email, password, mContext);
        else if(!isUserVerified) startActivity(new Intent(this, VerificationActivity.class));
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