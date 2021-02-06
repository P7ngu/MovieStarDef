package com.example.moviestar.View.profilo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.ForgotPasswordController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.R;

public class ChangePasswordActivity extends AppCompatActivity {
    Context mContext=this;
    static EditText editTextNewPass;
    static EditText editTextNewPass2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        getIntent();

        editTextNewPass = findViewById(R.id.cp_newpass);
        editTextNewPass2 = findViewById(R.id.cp_newpass2);
        ImageView img = findViewById(R.id.imageView4);
        Glide.with(mContext).load("https://i.ibb.co/zVhNTdy/logo.png").into(img);

        Button changePasswordButton=findViewById(R.id.cp_change_password_button);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNewPass.getText().toString().trim().equals(editTextNewPass2.getText().toString().trim())){
                    ForgotPasswordController.changePassword(editTextNewPass,  mContext);
                }else{
                    PopupController.mostraPopup("Errore", "Le due password devono corrispondere!",mContext);
                }
            }
        });
    }

    public static void emptyFields(){
        editTextNewPass.setText("");
        editTextNewPass2.setText("");
    }
}
