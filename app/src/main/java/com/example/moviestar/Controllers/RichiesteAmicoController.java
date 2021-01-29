package com.example.moviestar.Controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.View.social.SocialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RichiesteAmicoController {

    public static void sendRichiestaAmico(String idUtenteDaAggiungere, Context mContext) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        if(!userId.equals(idUtenteDaAggiungere)) {
            UtenteDAO.sendRichiestaAmico_Firebase(idUtenteDaAggiungere, mContext);
        } else{
            PopupController.mostraPopup("Errore", "non puoi aggiungere te stesso!", mContext);

        }
    }

    public static void getRichiesteAmico() {
       UtenteDAO.getRichiesteAmico_Firebase();
    }
}

