package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.R;

public class EditProfiloActivity extends AppCompatActivity {
    final Context mContext=this;
    Button cambiaFotoButton, cambiaNomeButton, cambiaPasswordButton;
    TextView nomeVisualizzatoTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilo);
        nomeVisualizzatoTV=findViewById(R.id.nomeutente_ep);
        nomeVisualizzatoTV.setText("TODO metodo");

        cambiaFotoButton=findViewById(R.id.button_ep_cambiafoto);
        cambiaNomeButton=findViewById(R.id.button_changename_ep);
        cambiaPasswordButton=findViewById(R.id.button_changepassword_ep);

        cambiaFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfiloFotoActivity.class);
                startActivity(intent);
            }
        });

        cambiaNomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfiloNomeActivity.class);
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
