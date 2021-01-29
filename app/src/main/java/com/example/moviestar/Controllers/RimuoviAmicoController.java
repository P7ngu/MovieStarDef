package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.View.home.Recycler.Adaptery;
import com.example.moviestar.View.profilo.ListaAmiciActivity;
import com.example.moviestar.View.social.recycler.AdapteryUtente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RimuoviAmicoController {

    public static void eliminaAmicoDaListaAmici(String idUtenteDaEliminare, Context mContext){
   PopupController.mostraPopupDiConfermaOAnnulla("Eliminare amico da lista", "Sei sicuro di voler eliminare questa persona dalla tua lista amici?",
           mContext, "rimuoviamico", "ListaAmici", idUtenteDaEliminare);

    }

    public static void eliminaAmicoDaListaAmici_DB(String idUtenteDaEliminare, Context mContext){
        UtenteDAO.eliminaAmicoDaListaAmici_Firebase(idUtenteDaEliminare, mContext);
    }

    public static void onClickPositiveButton(Context myContext, String path, String idObject) {
        eliminaAmicoDaListaAmici_DB(idObject, myContext);

    }

    public static void onClickNegativeButton(Context myContext) {

    }
}



