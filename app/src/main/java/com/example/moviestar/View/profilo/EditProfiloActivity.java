package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.R;

public class EditProfiloActivity extends AppCompatActivity {
    final Context mContext=this;
    Button cambiaNomeButton, cambiaPasswordButton;
    TextView nomeVisualizzatoTV;
    static String currentUserID, currentUsername;
    ImageView propicImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilo);

        currentUserID= CurrentUser.getInstance().getUserId();
        currentUsername=CurrentUser.getInstance().getUsername();

        nomeVisualizzatoTV=findViewById(R.id.nomeutente_ep);
        nomeVisualizzatoTV.setText(currentUsername);

        cambiaNomeButton=findViewById(R.id.button_visuallizzaid_ep);
        cambiaPasswordButton=findViewById(R.id.button_changepassword_ep);
        propicImg = findViewById(R.id.propic_ep_imageview);
        Glide.with(mContext).load("https://i.ibb.co/QmmFv5T/batman-hero-avatar-comics-512.png").into(propicImg);

        cambiaNomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VisualizzaIdActivity.class);
                startActivity(intent);
            }
        });

        cambiaPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

    }

}
