package com.example.moviestar.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.VerificaController;
import com.example.moviestar.R;

public class VerificationActivity extends AppCompatActivity {

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
        verificaButton=findViewById(R.id.button_verifica_verification);
        newCodeButton=findViewById(R.id.button_sendnewcode_verification);

        forgotPasswordTV=findViewById(R.id.textView_forgotpassword_verification);

        idText=findViewById(R.id.editText_id_verification);
        codiceText=findViewById(R.id.editText_codice_verification);

        accediButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });

        verificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificaController.verificaCodice(codiceText.getText().toString().trim(), idText.getText().toString().trim(), mContext);

            }
        });

        newCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       // VerificaController.
                //TODO resend codice di verifica

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
