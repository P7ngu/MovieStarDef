package com.example.moviestar.View.login;

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

import com.example.moviestar.Controllers.RegistrazioneController;
import com.example.moviestar.R;
import com.example.moviestar.View.login.LoginActivity;

public class RegistrazioneActivity extends AppCompatActivity {

EditText nomeUtenteEditText, emailEditText, password1EditText, password2EditText;
TextView disclaimer;
    Button registratiButton, accediButton;
    ImageView logoIMG;
    final Context context= this;

    @Override
    protected void onStart() {
        super.onStart();
        RegistrazioneController.registerOnStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.registrazione);
        Intent intent = getIntent();
        RegistrazioneController.registerOnCreate();

        emailEditText= findViewById(R.id.editTextTextEmailAddress);
        nomeUtenteEditText=findViewById(R.id.editText_NomeUtente);
        password1EditText=findViewById(R.id.editTextTextPassword);
        password2EditText=findViewById(R.id.editTextTextPassword2);
        disclaimer=findViewById(R.id.textView_disclaimer);
        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "Cliccato");
            }
        });

        registratiButton=findViewById(R.id.button_registrati);
        accediButton=findViewById(R.id.button_login);
        logoIMG=findViewById(R.id.imageView2);

        //Glide.with(this).load(R.drawable.logo).into(logoIMG);




        registratiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            String email = emailEditText.getText().toString().trim();
                String password1=password1EditText.getText().toString().trim();
                String password2= password2EditText.getText().toString().trim();
                String nomeUtente=nomeUtenteEditText.getText().toString().trim();
                //check vuoto etc TODO
                Log.d("Firebase", email+password1+nomeUtente+context);
                RegistrazioneController.createUserEmailAccount(email, password1, nomeUtente, context);
                RegistrazioneController.registraUtente(email, password1, password2, nomeUtente, context);




            }
        });

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}