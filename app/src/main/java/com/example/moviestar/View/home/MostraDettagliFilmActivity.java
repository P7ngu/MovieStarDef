package com.example.moviestar.View.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.MostraDettagliFilmController;
import com.example.moviestar.R;

public class MostraDettagliFilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostradettaglifilm);
        Context mContext=this;

        Intent intent = getIntent();
        String activity_type=intent.getStringExtra("tipologia");
        String filmName = intent.getStringExtra("FilmName");
        String filmVoto = intent.getStringExtra("FilmVoto");
        String filmOverview = intent.getStringExtra("FilmOverview");
        String filmId = intent.getStringExtra("FilmId");
        String filmFotoPath=intent.getStringExtra("FilmPicPath");

        TextView filmNameTextView = findViewById(R.id.title_text);
        filmNameTextView.setText(filmName);

        TextView tramaTextView = findViewById(R.id.trama_text);
        tramaTextView.setText(filmOverview);

        TextView votoTextView = findViewById(R.id.voto_text);
        votoTextView.setText(filmVoto);

        Button buttonLeggiCommenti = findViewById(R.id.button_leggicommenti);
        buttonLeggiCommenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickLeggiCommenti(filmId, mContext);
            }
        });

        Button aggiungiCommentoButton = findViewById(R.id.button_addcomment);
        aggiungiCommentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickAggiungiCommento(filmName, mContext);
            }
        });

        Button addToPreferitiButton = findViewById(R.id.button_list_preferiti);
        addToPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickAddToPreferiti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
            }
        });

        Button addToDaVedereButton = findViewById(R.id.button_list_davedere);
        addToDaVedereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickAddToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
            }
        });
        Button removeFromVistiButton=findViewById(R.id.button_list_visti);
        Button addToVistiButton = findViewById(R.id.button_list_visti);
        addToVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickAddToVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                addToVistiButton.setVisibility(0);
                removeFromVistiButton.setText("Rimuovi dai visti");
                removeFromVistiButton.setVisibility(1);
                removeFromVistiButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MostraDettagliFilmController.onClickRimuoviDaListaFilm("FilmVisti",
                                filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                    }
                });
            }
        });

    }
}
