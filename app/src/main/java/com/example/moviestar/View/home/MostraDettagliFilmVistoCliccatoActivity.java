package com.example.moviestar.View.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;

import static com.example.moviestar.Controllers.AggiungiAListaFilmController.onClickAddToPreferiti;
import static com.example.moviestar.Controllers.ModificaRecensioneController.getNumberOfStarsFromDB;
import static com.example.moviestar.Controllers.ModificaRecensioneController.riempiStelleDB;
import static com.example.moviestar.Controllers.RecensioniFilmController.onClickAggiungiCommento;
import static com.example.moviestar.Controllers.RecensioniFilmController.onClickLeggiCommenti;
import static com.example.moviestar.Controllers.RimuoviFilmDaListaController.onClickRemoveFromVisti;

public class MostraDettagliFilmVistoCliccatoActivity extends AppCompatActivity {
    static ImageView star_1, star_2, star_3, star_4, star_5;
    static Context mContext;
    static int number_star;
    static String filmId;
    static String activity_type;
    static String filmName;
    static String filmVoto;
    static String filmOverview;
    static String filmFotoPath;
    Film filmCliccato;
    TextView filmNameTextView, tramaTextView, votoTextView;
    Button addToPreferitiButton;
    Button removeFromVistiButton;
    Button buttonLeggiCommenti, aggiungiCommentoButton;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filmvistocliccato);
        mContext=this;

        star_1 = findViewById(R.id.star_1);
        star_2 = findViewById(R.id.star_2);
        star_3 = findViewById(R.id.star_3);
        star_4 = findViewById(R.id.star_4);
        star_5 = findViewById(R.id.star_5);

        star_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(1, true);
                Toast.makeText(mContext, filmName + " votato 1/5", Toast.LENGTH_SHORT).show();
            }
        });

        star_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(2, true);
                Toast.makeText(mContext, filmName + " votato 2/5", Toast.LENGTH_SHORT).show();
            }
        });

        star_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(3, true);
                Toast.makeText(mContext, filmName + " votato 3/5", Toast.LENGTH_SHORT).show();
            }
        });

        star_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(4, true);
                Toast.makeText(mContext, filmName + " votato 4/5", Toast.LENGTH_SHORT).show();
            }
        });

        star_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(5, true);
                Toast.makeText(mContext, filmName + " votato 5/5", Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = getIntent();
        activity_type = intent.getStringExtra("tipologia");
        filmName = intent.getStringExtra("FilmName");
        filmVoto = intent.getStringExtra("FilmVoto");
        filmOverview = intent.getStringExtra("FilmOverview");
        filmId = intent.getStringExtra("FilmId");
        filmFotoPath=intent.getStringExtra("FilmPicPath");

        filmCliccato = new Film(filmId, filmName, filmFotoPath, filmVoto, filmOverview);


        int starNumber = getNumberOfStarsFromDB(filmId, number_star);
       // riempiStelle(starNumber, false);
        //riempiStelle(number_star, false);


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
                onClickLeggiCommenti(filmCliccato, mContext);
            }
        });

        aggiungiCommentoButton = findViewById(R.id.button_addcomment);
        aggiungiCommentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAggiungiCommento(filmCliccato, mContext);
            }
        });

       addToPreferitiButton = findViewById(R.id.button_list_preferiti);
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


        removeFromVistiButton=findViewById(R.id.button_remove_list_visti);
        removeFromVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRemoveFromVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);

            }
        });


    }

    public static void removeFromVistiSuccess(Context mContext){
        //setContentView(R.layout.loadinglayout);
        Intent intent = new Intent(mContext, MostraDettagliFilmCliccatoActivity.class);
        intent.putExtra("FilmName", filmName);
        intent.putExtra("FilmVoto", filmVoto);
        intent.putExtra("FilmOverview", filmOverview);
        intent.putExtra("FilmId", filmId);
        intent.putExtra("FilmPicPath", filmFotoPath);
        mContext.startActivity(intent);
    }




    public static void riempiStelle(int numeroStelleDaRiempire, boolean isUserEditing) {
        switch(numeroStelleDaRiempire) {
            case 1:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                if(isUserEditing)riempiStelleDB(1, filmId);

                break;
            case 2:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                if(isUserEditing)riempiStelleDB(2, filmId);

                break;
            case 3:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                if(isUserEditing)riempiStelleDB(3, filmId);

                break;
            case 4:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                if(isUserEditing)riempiStelleDB(4, filmId);

                break;
            case 5:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_5);
                if(isUserEditing)riempiStelleDB(5, filmId);

                break;

            default:
                // code block
        }
    }




}
