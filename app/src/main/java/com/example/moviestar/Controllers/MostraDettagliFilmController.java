package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.home.AggiungiCommentoActivity;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.MostraDettagliFilmCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmDaVedereCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoPreferitoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MostraDettagliFilmController {
    public static void openFilm(Film filmCliccato, Context context){
        Intent intent;
        LoginController.loadCurrentUserDetails();
       if (CurrentUser.getInstance().daVedereContainsFilm(filmCliccato))
           intent=new Intent(context, MostraDettagliFilmDaVedereCliccatoActivity.class);
       if(CurrentUser.getInstance().preferitiContainsFilm(filmCliccato))
           intent = new Intent(context, MostraDettagliFilmVistoCliccatoPreferitoActivity.class);
       if(CurrentUser.getInstance().vistiContainsFilm(filmCliccato))
           intent = new Intent(context, MostraDettagliFilmVistoCliccatoActivity.class);
       else
           intent=new Intent(context, MostraDettagliFilmCliccatoActivity.class);

        intent.putExtra("FilmPicPath", filmCliccato.getImg());
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


    public static void onClickAggiungiCommento(String idFilm, String filmName, String overview, Context mContext) {
        Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
        intent.putExtra("film", filmName);
        intent.putExtra("FilmName", filmName);
        intent.putExtra("FilmId", idFilm);
        intent.putExtra("FilmOverview", overview);
        mContext.startActivity(intent);
    }

    public static void onClickAddToPreferiti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        onClickAddToVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
        onClickRemoveToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);

        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection("FilmPreferiti");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("filmName", filmName);
        data4.put("filmOverview", filmOverview);
        data4.put("filmFotoPath", filmFotoPath);
        data4.put("filmVoto", filmVoto);
        data4.put("userID", userId);
        filmPreferiti.document(userId+filmId).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);
    }

    public static void onClickAddToVisti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        onClickRemoveToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmVisti = db.collection("FilmVisti");


        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("filmName", filmName);
        data4.put("filmOverview", filmOverview);
        data4.put("filmFotoPath", filmFotoPath);
        data4.put("filmVoto", filmVoto);
        data4.put("userID", userId);
        filmVisti.document(userId+filmId).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);

    }

    public static void onClickAddToDaVedere(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmDaVedere = db.collection("FilmDaVedere");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("filmName", filmName);
        data4.put("filmOverview", filmOverview);
        data4.put("filmFotoPath", filmFotoPath);
        data4.put("filmVoto", filmVoto);
        data4.put("userID", userId);
        filmDaVedere.document(userId+filmId).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);
    }

    public static void onClickRimuoviDaListaFilm(String pathListaDaCuiRimuovere, String filmId, String filmName, Context mContext){
        LoginController.loadCurrentUserDetails();
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(pathListaDaCuiRimuovere);

        db.collection(pathListaDaCuiRimuovere).document(userId+filmId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupController.mostraPopup("Film rimosso!", filmName+"rimosso con successo.", mContext);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", filmName+" ", mContext);
                    }
                });

    }

    public static void onClickRemoveToDaVedere(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext) {
        onClickRimuoviDaListaFilm("FilmDaVedere",filmId, filmName, mContext );
    }

    public static void onClickRemoveFromPreferiti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext) {
        onClickRimuoviDaListaFilm("FilmPreferiti",filmId, filmName, mContext );
    }

    public static void onClickRemoveFromVisti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext) {
        onClickRimuoviDaListaFilm("FilmVisti",filmId, filmName, mContext );
        onClickRimuoviDaListaFilm("FilmPreferiti", filmId, filmName, mContext);
    }


}

