package com.example.moviestar.Controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.moviestar.View.home.MostraDettagliFilmDaVedereCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoPreferitoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RimuoviFilmDaListaController {
    public static void onClickRemoveToDaVedere(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext) {
        onClickRimuoviDaListaFilm("FilmDaVedere",filmId, filmName, mContext );
    }

    public static void onClickRemoveFromPreferiti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext) {
        onClickRimuoviDaListaFilm("FilmPreferiti",filmId, filmName, mContext );
    }

    public static void onClickRemoveFromVisti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext) {
       onClickRimuoviDaListaFilm("FilmVisti",filmId, filmName, mContext );
       // onClickRimuoviDaListaFilm("FilmPreferiti", filmId, filmName, mContext);
    }

    public static void onClickRimuoviDaListaFilm(String pathListaDaCuiRimuovere, String filmId, String filmName, Context mContext){
        PopupController.mostraPopupDiConfermaOAnnulla("Rimuovere film?", "vuoi rimuoverlo?", mContext, "rimuovifilm", pathListaDaCuiRimuovere, filmId);

    }

    public static void rimozioneFilmDaLista(Context mContext, String pathListaDaCuiRimuovere, String filmId){
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
                        if(pathListaDaCuiRimuovere.equals("FilmVisti"))
                            db.collection("FilmPreferiti").document(userId+filmId)
                            .delete();
                        //PopupController.mostraPopup("Film rimosso!", filmName+"rimosso con successo.", mContext);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", " ", mContext);
                    }
                });

    }

   public static void gestionePositiveButton(Context mContext, String pathListaDaCuiRimuovere, String filmId){
        rimozioneFilmDaLista(mContext, pathListaDaCuiRimuovere, filmId);
        if(pathListaDaCuiRimuovere.equals("FilmDaVedere"))
            MostraDettagliFilmDaVedereCliccatoActivity.removeFromDaVedereSuccess(mContext);
        else if (pathListaDaCuiRimuovere.equals("FilmVisti")) {
            MostraDettagliFilmVistoCliccatoActivity.removeFromVistiSuccess(mContext);
            MostraDettagliFilmVistoCliccatoPreferitoActivity.removePreferitoFromVistiSuccess(mContext);
        }
        else if(pathListaDaCuiRimuovere.equals("FilmPreferiti"))
           MostraDettagliFilmVistoCliccatoPreferitoActivity.removeFromPreferitiSuccess(mContext);
    }

    public static void gestioneNegativeButton(Context mContext){


    }


}
