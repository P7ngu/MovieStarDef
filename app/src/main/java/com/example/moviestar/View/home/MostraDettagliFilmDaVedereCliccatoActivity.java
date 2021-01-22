package com.example.moviestar.View.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.Controllers.MostraDettagliFilmController;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;

public class MostraDettagliFilmDaVedereCliccatoActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmdavederecliccato);
        Context mContext=this;
        Log.d("Activity", "Film da vedere");
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


        Button addToPreferitiButton = findViewById(R.id.button_list_preferiti);
        addToPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickAddToPreferiti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                setContentView(R.layout.loadinglayout);
                Intent intent = new Intent(mContext, MostraDettagliFilmVistoCliccatoPreferitoActivity.class);
                intent.putExtra("FilmName", filmName);
                intent.putExtra("FilmVoto", filmVoto);
                intent.putExtra("FilmOverview", filmOverview);
                intent.putExtra("FilmId", filmId);
                intent.putExtra("FilmPicPath", filmFotoPath);
                startActivity(intent);
            }
        });

        Button removeFromDaVedereButton = findViewById(R.id.button_remove_list_davedere);
        removeFromDaVedereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickRemoveToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                Intent intent = new Intent(mContext, MostraDettagliFilmCliccatoActivity.class);
                setContentView(R.layout.loadinglayout);
                intent.putExtra("FilmName", filmName);
                intent.putExtra("FilmVoto", filmVoto);
                intent.putExtra("FilmOverview", filmOverview);
                intent.putExtra("FilmId", filmId);
                intent.putExtra("FilmPicPath", filmFotoPath);
                startActivity(intent);
            }
        });
        Button addToVistiButton = findViewById(R.id.button_list_visti);
        addToVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickAddToVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                addToVistiButton.setVisibility(0);
                setContentView(R.layout.loadinglayout);
                Intent intent = new Intent(mContext, MostraDettagliFilmVistoCliccatoActivity.class);
                intent.putExtra("FilmName", filmName);
                intent.putExtra("FilmVoto", filmVoto);
                intent.putExtra("FilmOverview", filmOverview);
                intent.putExtra("FilmId", filmId);
                intent.putExtra("FilmPicPath", filmFotoPath);
                startActivity(intent);


            }
        });

    }
}
