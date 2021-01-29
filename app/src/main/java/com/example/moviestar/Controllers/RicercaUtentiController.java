package com.example.moviestar.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.social.SocialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RicercaUtentiController {

    public static void cercaAmicoByNome(String nome){
     UtenteDAO.cercaUtenteByNome(nome);
    }

    public static void getListaUtentiFromDB(){
        UtenteDAO.getListaUtenti_Firebase();

    }
}
