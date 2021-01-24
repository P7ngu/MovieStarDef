package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.home.AggiungiCommentoActivity;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.MostraDettagliFilmCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmDaVedereCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoPreferitoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MostraDettagliFilmController {
    static boolean flag=false;


    public static void openFilm(Film filmCliccato, Context context){
        Intent intent;
        LoginController.loadCurrentUserDetails();
       if (CurrentUser.getInstance().daVedereContainsFilm(filmCliccato)) {
           Log.d("Activity123", "Film cliccato da vedere");
           intent = new Intent(context, MostraDettagliFilmDaVedereCliccatoActivity.class);
       }
       else if(checkFilmPresenteListaDB(filmCliccato.getId(), "FilmPreferiti", context) || CurrentUser.preferitiContainsFilm(filmCliccato)) {
           Log.d("Activity123", "Film preferito");
           intent = new Intent(context, MostraDettagliFilmVistoCliccatoPreferitoActivity.class);
       }
       else if(CurrentUser.getInstance().vistiContainsFilm(filmCliccato)) {
           Log.d("Activity123", "Film visto");
           intent = new Intent(context, MostraDettagliFilmVistoCliccatoActivity.class);
           LoginController.loadCurrentUserDetails();
       }
       else
           intent=new Intent(context, MostraDettagliFilmCliccatoActivity.class);
        LoginController.loadCurrentUserDetails();

        intent.putExtra("FilmPicPath", filmCliccato.getImg());
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmId", filmCliccato.getId());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        context.startActivity(intent);
    }

    public static boolean checkFilmPresenteListaDB (String filmId, String path, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String idFilm, title, overview, fotoPath, voto;
        flag=false;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);
        ArrayList<Film> filmList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("userID", userId).whereEqualTo("filmID", filmId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              flag=true;
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());

                    }
                });
        return flag;
    }




}

