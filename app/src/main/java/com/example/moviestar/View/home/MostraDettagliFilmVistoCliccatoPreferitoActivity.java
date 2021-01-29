package com.example.moviestar.View.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.MostraDettagliFilmController;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.moviestar.Controllers.ModificaRecensioneController.getNumberOfStarsFromDB;
import static com.example.moviestar.Controllers.ModificaRecensioneController.riempiStelleDB;
import static com.example.moviestar.Controllers.RecensioniFilmController.onClickAggiungiCommento;
import static com.example.moviestar.Controllers.RecensioniFilmController.onClickLeggiCommenti;
import static com.example.moviestar.Controllers.RimuoviFilmDaListaController.onClickRemoveFromPreferiti;
import static com.example.moviestar.Controllers.RimuoviFilmDaListaController.onClickRemoveFromVisti;

public class MostraDettagliFilmVistoCliccatoPreferitoActivity extends AppCompatActivity {
    static String filmId;
    static String activity_type;
    static String filmName;
    static String filmVoto;
    static String filmOverview;
    static String filmFotoPath;

    static ImageView star_1, star_2, star_3, star_4, star_5;
    static int number_star;
    static Context mContext;

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
        Log.d("Activity", "Film preferito");
        star_1 = findViewById(R.id.star_1);
        star_2 = findViewById(R.id.star_2);
        star_3 = findViewById(R.id.star_3);
        star_4 = findViewById(R.id.star_4);
        star_5 = findViewById(R.id.star_5);



        star_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(1, true);
            }
        });

        star_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(2, true);
            }
        });

        star_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(3, true);
            }
        });

        star_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(4, true);
            }
        });

        star_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riempiStelle(5, true);
            }
        });



        Intent intent = getIntent();
         activity_type=intent.getStringExtra("tipologia");
        filmName = intent.getStringExtra("FilmName");
        filmVoto = intent.getStringExtra("FilmVoto");
         filmOverview = intent.getStringExtra("FilmOverview");
        filmId = intent.getStringExtra("FilmId");
         filmFotoPath=intent.getStringExtra("FilmPicPath");

        int starNumber = getNumberOfStarsFromDB(filmId, number_star);
        riempiStelle(starNumber, false);
        //riempiStelle(number_star, false);

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

        Button aggiungiCommentoButton = findViewById(R.id.button_addcomment);
        aggiungiCommentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAggiungiCommento(filmId, filmName, filmOverview, mContext);
            }
        });

        Button removeFromPreferitiButton = findViewById(R.id.button_remove_list_preferiti);
        removeFromPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRemoveFromPreferiti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);

            }
        });


        Button removeFromVistiButton=findViewById(R.id.button_remove_list_visti);
        removeFromVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRemoveFromVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);

            }
        });

    }

    public static void removePreferitoFromVistiSuccess(Context mContext){
        Intent intent = new Intent(mContext, MostraDettagliFilmCliccatoActivity.class);
        intent.putExtra("FilmName", filmName);
        intent.putExtra("FilmVoto", filmVoto);
        intent.putExtra("FilmOverview", filmOverview);
        intent.putExtra("FilmId", filmId);
        intent.putExtra("FilmPicPath", filmFotoPath);
        mContext.startActivity(intent);

    }

    public static void removeFromPreferitiSuccess(Context mContext){
       // setContentView(R.layout.loadinglayout);
        Intent intent = new Intent(mContext, MostraDettagliFilmVistoCliccatoActivity.class);
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
                if(isUserEditing) riempiStelleDB(4, filmId);

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
