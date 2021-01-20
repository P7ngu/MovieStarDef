package com.example.moviestar.View.home;

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
import com.example.moviestar.Controllers.MostraDettagliFilmController;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;

public class MostraDettagliFilmVistoCliccatoPreferitoActivity extends AppCompatActivity {
    ImageView star_1, star_2, star_3, star_4, star_5;
    Context mContext;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmvistopreferitocliccato);
        mContext=this;
        star_1 = findViewById(R.id.star_1);
        star_2 = findViewById(R.id.star_2);
        star_3 = findViewById(R.id.star_3);
        star_4 = findViewById(R.id.star_4);
        star_5 = findViewById(R.id.star_5);

        star_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(1);
            }
        });

        star_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(2);
            }
        });

        star_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(3);
            }
        });

        star_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(4);
            }
        });

        star_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(5);
            }
        });



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
                MostraDettagliFilmController.onClickAggiungiCommento(filmId, filmName, filmOverview, mContext);
            }
        });

        Button removeFromPreferitiButton = findViewById(R.id.button_remove_list_preferiti);
        removeFromPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickRemoveFromPreferiti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
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

        Button addToDaVedereButton = findViewById(R.id.button_list_davedere);
        addToDaVedereButton.setVisibility(0);
        addToDaVedereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //
            }
        });
        Button removeFromVistiButton=findViewById(R.id.button_remove_list_visti);
        removeFromVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraDettagliFilmController.onClickRemoveFromVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
                setContentView(R.layout.loadinglayout);
                Intent intent = new Intent(mContext, MostraDettagliFilmCliccatoActivity.class);
                intent.putExtra("FilmName", filmName);
                intent.putExtra("FilmVoto", filmVoto);
                intent.putExtra("FilmOverview", filmOverview);
                intent.putExtra("FilmId", filmId);
                intent.putExtra("FilmPicPath", filmFotoPath);
                startActivity(intent);

            }
        });

    }


    private void riempiStelle(int numeroStelleDaRiempire) {
        switch(numeroStelleDaRiempire) {
            case 1:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                break;
            case 2:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                break;
            case 3:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);

                break;
            case 4:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);

                break;
            case 5:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_5);

                break;

            default:
                // code block
        }
    }

}
