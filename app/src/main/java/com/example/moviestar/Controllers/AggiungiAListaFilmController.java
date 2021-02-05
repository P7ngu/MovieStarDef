package com.example.moviestar.Controllers;

import android.content.Context;

import com.example.moviestar.DAO.FilmDAO;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.moviestar.Controllers.RimuoviFilmDaListaController.onClickRemoveToDaVedere;

public class AggiungiAListaFilmController {

    public static void onClickAddToPreferiti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        FilmDAO.rimuoviFilmDaLista_Firebase(mContext,"FilmDaVedere", filmId);
        onClickRemoveToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
        onClickAddToVisti(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
        FilmDAO.addToPreferitiDAO(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);


    }

    public static void onClickAddToVisti(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){ onClickRemoveToDaVedere(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
       FilmDAO.addToVistiDAO(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);

    }

    public static void onClickAddToDaVedere(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
       FilmDAO.addToDaVedereDAO(filmId, filmName, filmOverview, filmFotoPath, filmVoto, mContext);
    }

}
