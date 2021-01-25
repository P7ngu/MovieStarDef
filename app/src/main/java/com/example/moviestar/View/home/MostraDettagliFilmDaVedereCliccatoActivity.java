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

import static com.example.moviestar.Controllers.AggiungiAListaController.onClickAddToPreferiti;
import static com.example.moviestar.Controllers.AggiungiAListaController.onClickAddToVisti;
import static com.example.moviestar.Controllers.RecensioniFilmController.onClickLeggiCommenti;
import static com.example.moviestar.Controllers.RimuoviFilmDaListaController.onClickRemoveToDaVedere;

public class MostraDettagliFilmDaVedereCliccatoActivity extends AppCompatActivity {
    static String activity_type;
    static String filmName;
    static String filmVoto;
    static String filmOverview;
    static String filmId;
    static String filmFotoPath;

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
        activity_type=intent.getStringExtra("tipologia");
        filmName = intent.getStringExtra("FilmName");
        filmVoto = intent.getStringExtra("FilmVoto");
        filmOverview = intent.getStringExtra("FilmOverview");
        filmId = intent.getStringExtra("FilmId");
        filmFotoPath=intent.getStringExtra("FilmPicPath");

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
                onClickLeggiCommenti(filmId, mContext);
            }
        });


        Button addToPreferitiButton = findViewById(R.id.button_list_preferiti);
        addToPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddToPreferiti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
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
                onClickRemoveToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
            }
        });


        Button addToVistiButton = findViewById(R.id.button_list_visti);
        addToVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddToVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
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

    public static void removeFromDaVedereSuccess(Context mContext){
        Intent intent1 = new Intent(mContext, MostraDettagliFilmCliccatoActivity.class);
       // mContext.setContentView(R.layout.loadinglayout);
        intent1.putExtra("FilmName", filmName);
        intent1.putExtra("FilmVoto", filmVoto);
        intent1.putExtra("FilmOverview", filmOverview);
        intent1.putExtra("FilmId", filmId);
        intent1.putExtra("FilmPicPath", filmFotoPath);
        mContext.startActivity(intent1);
    }


}
