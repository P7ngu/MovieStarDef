package com.example.moviestar.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.ChangePasswordController;
import com.example.moviestar.R;

public class ChangePasswordActivity extends AppCompatActivity {
    Context mContext=this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        getIntent();

        final EditText editTextUsername=findViewById(R.id.cp_username_text);
        final EditText editTextOldPass=findViewById(R.id.cp_oldpass);
        final EditText editTextNewPass=findViewById(R.id.cp_newpass);

        Button changePasswordButton=findViewById(R.id.cp_change_password_button);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordController.changePassword(editTextNewPass, editTextUsername, editTextOldPass, mContext);

            }
        });
    }
}
