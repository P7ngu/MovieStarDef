package com.example.moviestar.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.example.moviestar.Controllers.ForgotPasswordController;
import com.example.moviestar.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    Context mContext=this;
    Button resetPasswordButton, sendNewCodeButton;
    EditText idUtenteET, codeET, newPasswordET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
        getIntent();

        resetPasswordButton=findViewById(R.id.button_resetpassword_fp);
        sendNewCodeButton=findViewById(R.id.button_sendcode_fp);
        idUtenteET=findViewById(R.id.editText_username_fp);
        codeET=findViewById(R.id.code_fp);
        newPasswordET=findViewById(R.id.newpass_fp);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordController.resetPassword(mContext, newPasswordET.getText().toString().trim(), codeET.getText().toString().trim());
            }
        });

        sendNewCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ForgotPasswordController.sendNewCode(mContext, idUtenteET.getText().toString().trim());
            }
        });

    }
}
