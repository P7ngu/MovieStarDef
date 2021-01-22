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

public class MostraDettagliFilmVistoCliccatoPreferitoActivity extends AppCompatActivity {
    ImageView star_1, star_2, star_3, star_4, star_5;
    int number_star;
    String filmId;
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
        Log.d("Activity", "Film preferito");
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
        filmId = intent.getStringExtra("FilmId");
        String filmFotoPath=intent.getStringExtra("FilmPicPath");

        int starNumber = getNumberOfStarsFromDB();
        riempiStelle(starNumber);
        riempiStelle(number_star);

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

    private int getNumberOfStarsFromDB() {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path="VotoStar";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);

        db.collection(path)
                .whereEqualTo("userID", userId).whereEqualTo("filmID", filmId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                number_star= Integer.parseInt(String.valueOf(document.get("voto")));
                                riempiStelle(number_star);
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());

                    }
                });

        return number_star;
    }


    private void riempiStelle(int numeroStelleDaRiempire) {
        switch(numeroStelleDaRiempire) {
            case 1:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                riempiStelleDB(1, filmId);

                break;
            case 2:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                riempiStelleDB(2, filmId);

                break;
            case 3:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                riempiStelleDB(3, filmId);

                break;
            case 4:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/9YgQ3YN/star-vuota.png").into(star_5);
                riempiStelleDB(4, filmId);

                break;
            case 5:
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_1);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_2);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_3);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_4);
                Glide.with(mContext).load("https://i.ibb.co/hDRdQTD/star-piena.png").into(star_5);
                riempiStelleDB(5, filmId);

                break;

            default:
                // code block
        }
    }

    private void riempiStelleDB(int numeroStelleDaRiempire, String filmId) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection("VotoStar");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("userID", userId);
        data4.put("voto", numeroStelleDaRiempire );
        filmPreferiti.document(userId+filmId).set(data4);

    }

}
