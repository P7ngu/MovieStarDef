package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.home.AggiungiCommentoActivity;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.MostraDettagliFilmActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MostraDettagliFilmController {
    public static void openFilm(Film filmCliccato, Context context){
        Intent intent=new Intent(context, MostraDettagliFilmActivity.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmId", filmCliccato.getId());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        context.startActivity(intent);
    }

    public static void onClickLeggiCommenti(String filmId, Context context){
        Intent intent=new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("filmCliccatoId", filmId);
        context.startActivity(intent);

    }


    public static void onClickAggiungiCommento(String filmName, Context mContext) {
        Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
        intent.putExtra("film", filmName);
        mContext.startActivity(intent);
    }

    public static void onClickAddToPreferiti(String filmId, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection("FilmPreferiti");

        PopupController.mostraPopup(filmId, "id", mContext);

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("userID", userId);
        filmPreferiti.document("film" + Timestamp.now().getSeconds()).set(data4);

        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);
    }
}
