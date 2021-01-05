package com.example.moviestar.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.R;

public class LoginActivity extends AppCompatActivity  {
    Context mContext = this;
    EditText usernameEditText, passwordEditText;
    TextView forgotPassword;
    Button accediButton, accedi2Button, registratiButton;
    ImageView logo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent = getIntent();

        usernameEditText = findViewById(R.id.editText_username_login);
        passwordEditText = findViewById(R.id.editText_password_login);
        forgotPassword = findViewById(R.id.textView_forgotpassword_login);

        accediButton = findViewById(R.id.button_login_login);
        accedi2Button = findViewById(R.id.button3);
        registratiButton = findViewById(R.id.button_registrati_login);

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "bottone");

                LoginController.login(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim(), mContext);
            }
        });

        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegistrazioneActivity.class);
                startActivity(intent);
            }
        });

    }


}
