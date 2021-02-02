package com.example.moviestar.View.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.RecensioniFilmController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.R;

public class AggiungiCommentoActivity extends AppCompatActivity {
String titoloFilm, idfilm;
TextView titoloTextview;
EditText commentoText;
Context mContext;
Button inviaCommentoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungicommento);
        mContext=this;
        idfilm=getIntent().getStringExtra("FilmId");
        titoloFilm = getIntent().getStringExtra("film");
        titoloTextview=findViewById(R.id.textView5_filmtitle);
        titoloTextview.setText(titoloFilm);

        commentoText=findViewById(R.id.editTextTextMultiLine);


        inviaCommentoButton=findViewById(R.id.button_addcomment_add);
        inviaCommentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecensioniFilmController.inserisciCommentoFilm(idfilm, commentoText.getText().toString(), mContext);
            }
        });




    }
}
