package com.example.moviestar.Controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.FilmDAO;
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
        FilmDAO.rimuoviFilmDaLista_Firebase(mContext, pathListaDaCuiRimuovere, filmId);

    }

   public static void onClickPositiveButton(Context mContext, String pathListaDaCuiRimuovere, String filmId){
        rimozioneFilmDaLista(mContext, pathListaDaCuiRimuovere, filmId);
        if(pathListaDaCuiRimuovere.equals("FilmDaVedere"))
            MostraDettagliFilmDaVedereCliccatoActivity.removeFromDaVedereSuccess(mContext);
        else if (pathListaDaCuiRimuovere.equals("FilmVisti")) {
            MostraDettagliFilmVistoCliccatoActivity.removeFromVistiSuccess(mContext);
            //MostraDettagliFilmVistoCliccatoPreferitoActivity.removePreferitoFromVistiSuccess(mContext);
        }
       if(pathListaDaCuiRimuovere.equals("FilmPreferiti"))
           MostraDettagliFilmVistoCliccatoPreferitoActivity.removeFromPreferitiSuccess(mContext);
    }

    public static void onClickNegativeButton(Context mContext){


    }


}
