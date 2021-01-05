package com.example.moviestar.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.moviestar.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean isUserLogged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isUserLogged) {
            Log.d("Test", "Main");
            Intent intent = new Intent(this, RegistrazioneActivity.class);
            startActivity(intent);

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