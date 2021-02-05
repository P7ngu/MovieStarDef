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

import com.example.moviestar.Controllers.RecensioniFilmController;
import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;

import static com.example.moviestar.Controllers.AggiungiAListaFilmController.onClickAddToDaVedere;
import static com.example.moviestar.Controllers.AggiungiAListaFilmController.onClickAddToPreferiti;
import static com.example.moviestar.Controllers.AggiungiAListaFilmController.onClickAddToVisti;

public class MostraDettagliFilmCliccatoActivity extends AppCompatActivity {
    Context mContext;
    TextView filmNameTextView, tramaTextView, votoTextView;
    Button addToPreferitiButton, addToVistiButton, addToDaVedereButton;
    Button buttonLeggiCommenti;
    Film filmCliccato;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostradettaglifilmcliccato);
        mContext=this;
        Log.d("Activity", "Film cliccato");

        Intent intent = getIntent();
        String activity_type=intent.getStringExtra("tipologia");
        String filmName = intent.getStringExtra("FilmName");
        String filmVoto = intent.getStringExtra("FilmVoto");
        String filmOverview = intent.getStringExtra("FilmOverview");
        String filmId = intent.getStringExtra("FilmId");
        String filmFotoPath=intent.getStringExtra("FilmPicPath");

        filmCliccato = new Film(filmId, filmName, filmFotoPath, filmVoto, filmOverview);

        filmNameTextView = findViewById(R.id.title_text);
        filmNameTextView.setText(filmName);

        tramaTextView = findViewById(R.id.trama_text);
        tramaTextView.setText(filmOverview);

        votoTextView = findViewById(R.id.voto_text);
        votoTextView.setText(filmVoto);

        buttonLeggiCommenti = findViewById(R.id.button_leggicommenti);
        buttonLeggiCommenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecensioniFilmController.onClickLeggiCommenti(filmCliccato, mContext, true);
            }
        });


        addToPreferitiButton = findViewById(R.id.button_list_preferiti);
        addToPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddToPreferiti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                setContentView(R.layout.loadinglayout);
                Intent intent = new Intent(mContext, MostraDettagliFilmVistoCliccatoPreferitoActivity.class);
                setContentView(R.layout.loadinglayout);
                intent.putExtra("FilmName", filmName);
                intent.putExtra("FilmVoto", filmVoto);
                intent.putExtra("FilmOverview", filmOverview);
                intent.putExtra("FilmId", filmId);
                intent.putExtra("FilmPicPath", filmFotoPath);
                startActivity(intent);
            }
        });

        addToDaVedereButton = findViewById(R.id.button_list_davedere);
        addToDaVedereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                setContentView(R.layout.loadinglayout);
                Intent intent = new Intent(mContext, MostraDettagliFilmDaVedereCliccatoActivity.class);
                setContentView(R.layout.loadinglayout);
                intent.putExtra("FilmName", filmName);
                intent.putExtra("FilmVoto", filmVoto);
                intent.putExtra("FilmOverview", filmOverview);
                intent.putExtra("FilmId", filmId);
                intent.putExtra("FilmPicPath", filmFotoPath);
                startActivity(intent);
            }
        });
        addToVistiButton = findViewById(R.id.button_list_visti);
        addToVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddToVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                setContentView(R.layout.loadinglayout);
                Intent intent = new Intent(mContext, MostraDettagliFilmVistoCliccatoActivity.class);
                setContentView(R.layout.loadinglayout);
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
