package com.example.moviestar.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.RegistrazioneController;
import com.example.moviestar.R;

public class RegistrazioneActivity extends AppCompatActivity {

EditText nomeUtenteEditText, emailEditText, password1EditText, password2EditText;
    Button registratiButton, accediButton;
    ImageView logoIMG;
    final Context context= this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.registrazione);
        Intent intent = getIntent();

        Log.d("Test", "  Prova");

        emailEditText= findViewById(R.id.editTextTextEmailAddress);
        nomeUtenteEditText=findViewById(R.id.editText_NomeUtente);
        password1EditText=findViewById(R.id.editTextTextPassword);
        password2EditText=findViewById(R.id.editTextTextPassword2);

        registratiButton=findViewById(R.id.button_registrati);
        accediButton=findViewById(R.id.button_login);
        logoIMG=findViewById(R.id.imageView2);

        Glide.with(this).load(R.drawable.logo).into(logoIMG);




        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            String email = emailEditText.getText().toString().trim();
                String password1=password1EditText.getText().toString().trim();
                String password2= password2EditText.getText().toString().trim();
                String nomeUtente=nomeUtenteEditText.getText().toString().trim();

                RegistrazioneController.registraUtente(email, password1, password2, nomeUtente, context);

            }
        });


    }
}
