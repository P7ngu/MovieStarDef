package com.example.moviestar.View.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.ForgotPasswordController;
import com.example.moviestar.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    Context mContext=this;
    Button resetPasswordButton, sendNewCodeButton;
    EditText emailET, codeET, newPasswordET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        getIntent();

        ImageView img = findViewById(R.id.imageView4);
        Glide.with(mContext).load("https://i.ibb.co/zVhNTdy/logo.png").into(img);

        sendNewCodeButton=findViewById(R.id.button_sendcode_fp);
        emailET=findViewById(R.id.email_forgotpassword_ET);

        sendNewCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ForgotPasswordController.sendNewCode(mContext, emailET.getText().toString().trim());
            }
        });

        Button loginButton = findViewById(R.id.button_login_fp);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            }
        });

    }
}
