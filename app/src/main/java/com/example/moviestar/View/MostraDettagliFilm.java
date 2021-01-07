package com.example.moviestar.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviestar.R;

public class MostraDettagliFilm extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostradettaglifilm);

        Intent intent = getIntent();
        String filmName = intent.getStringExtra("FilmName");
        String filmVoto = intent.getStringExtra("FilmVoto");
        String filmOverview = intent.getStringExtra("FilmOverview");

        TextView filmNameTextView = findViewById(R.id.title_text);
        filmNameTextView.setText(filmName);

        TextView tramaTextView = findViewById(R.id.trama_text);
        tramaTextView.setText(filmOverview);

        TextView votoTextView = findViewById(R.id.voto_text);
        votoTextView.setText(filmVoto);

    }
}
