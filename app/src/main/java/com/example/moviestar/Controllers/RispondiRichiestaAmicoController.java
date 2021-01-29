package com.example.moviestar.Controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.UtenteDAO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RispondiRichiestaAmicoController {
    //TODO
    public static void accettaRichiestaAmico(String idUtenteDaAggiungere, Context mContext){
        UtenteDAO.accettaRichiestaAmico_Firebase(idUtenteDaAggiungere, mContext);
    }

    private static void removeRichiestaAmico(String idUtenteDaAggiungere, Context mContext) {
        UtenteDAO.removeRichiestaAmico_Firebase(idUtenteDaAggiungere, mContext);

    }


    public static void respingiRichiestaAmico(String idUtenteDaRespingere, Context mContext){
    UtenteDAO.respingiRichiestaAmico(idUtenteDaRespingere, mContext);

    }

}
