package com.example.moviestar.Controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.DAO.RecensioneDAO;
import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Commento;
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

import java.util.HashMap;
import java.util.Map;

public class ModificaRecensioneController {
    static int number_star=0;
    static boolean risultato;

    public static void setRisultato(boolean risultato) {
        ModificaRecensioneController.risultato = risultato;
    }

    public static boolean eliminaRecensione(Commento commentoDaRimuovere, Context mContext){
        PopupController.mostraPopupDiConfermaOAnnulla("Elimina commento", "Vuoi davvero eliminare questo commento?",
                mContext, "commento", "Commenti", commentoDaRimuovere);
        return risultato;

    }


    public static void eliminaRecensione_DB(Commento commentoDaRimuovere, Context mContext){
        RecensioneDAO.eliminaRecensione_Firebase(commentoDaRimuovere, mContext);


    }


    public static boolean eliminaRecensione(Commento commentoDaRimuovere, Context mContext, RecyclerView recyclerView){
        PopupController.mostraPopupDiConfermaOAnnulla("Elimina commento", "Vuoi davvero eliminare questo commento?",
                mContext, "commento", "Commenti", commentoDaRimuovere);
        return risultato;

    }


    public static void eliminaRecensione_DB(Commento commentoDaRimuovere, Context mContext, RecyclerView recyclerView){
        RecensioneDAO.eliminaRecensione_Firebase(commentoDaRimuovere, mContext, recyclerView);
    }



    public static void riempiStelleDB(int numeroStelleDaRiempire, String filmId) {
      RecensioneDAO.riempiStelle_Firebase(numeroStelleDaRiempire, filmId);

    }

    public static int getNumberOfStarsFromDB(String filmId, int number_star) {
       int result = RecensioneDAO.getNumberOfStars_Firebase(filmId, number_star);
       return result;
    }

    public static void onClickPositiveButton(Commento idObject, Context myContext) {
        eliminaRecensione_DB(idObject, myContext);
    }

    public static void onClickNegativeButton(Context myContext) {

    }
}
