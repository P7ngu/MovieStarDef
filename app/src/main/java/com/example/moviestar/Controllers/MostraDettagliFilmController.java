package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.home.AggiungiCommentoActivity;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.MostraDettagliFilmActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MostraDettagliFilmController {
    public static void openFilm(Film filmCliccato, Context context){
        Intent intent=new Intent(context, MostraDettagliFilmActivity.class);
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


    public static void onClickAggiungiCommento(String filmName, Context mContext) {
        Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
        intent.putExtra("film", filmName);
        mContext.startActivity(intent);
    }

    public static void onClickAddToPreferiti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
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

    public static void onClickRimuoviDaListaFilm(String pathListaDaCuiRimuovere, String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
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
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", filmName+" ", mContext);
                    }
                });

    }
}

