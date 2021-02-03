package com.example.moviestar.View.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.VerificaController;
import com.example.moviestar.R;
import com.example.moviestar.View.login.ForgotPasswordActivity;
import com.example.moviestar.View.login.LoginActivity;

public class VerificationActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    Context mContext=this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifica);
        Intent intent = getIntent();

        Button accediButton, verificaButton, newCodeButton;
        TextView forgotPasswordTV;
        final EditText idText, codiceText;

        accediButton=findViewById(R.id.button_accedi_verification);
        newCodeButton=findViewById(R.id.button_sendnewcode_verification);
        forgotPasswordTV=findViewById(R.id.textView_forgotpassword_verification);

        ImageView img = findViewById(R.id.imageView3);
        Glide.with(mContext).load("https://i.ibb.co/zVhNTdy/logo.png").into(img);


        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });


        newCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificaController.sendEmailConLinkDiVerifica(CurrentUser.getInstance().getUserId(), mContext);

            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
