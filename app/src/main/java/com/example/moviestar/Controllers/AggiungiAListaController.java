package com.example.moviestar.Controllers;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.moviestar.Controllers.RimuoviFilmDaListaController.onClickRemoveToDaVedere;

public class AggiungiAListaController {

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

}
