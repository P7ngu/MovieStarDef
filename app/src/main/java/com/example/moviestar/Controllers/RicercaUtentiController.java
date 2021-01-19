package com.example.moviestar.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.Model.Utente;
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
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path="Users";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection(path);
        ArrayList<Utente> userList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("username", nome)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idUtente = document.getData().get("userID").toString();
                                String username = document.getData().get("username").toString();
                                Utente utenteTemp = new Utente(idUtente, username);

                                if (utenteTemp != null) userList.add(utenteTemp);
                            }
                            SocialFragment.PutDataIntoRecyclerView(userList);
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());
                        //currentUser.setListaUtenti(userList);

                    }
                });
    }

    public static void getListaUtentiFromDB(){
        CurrentUser currentUser = CurrentUser.getInstance();
        String path="Users";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection(path);
        ArrayList<Utente> userList = new ArrayList<>();

        db.collection(path)
                //.whereEqualTo("username", nome)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idUtente = document.getData().get("userID").toString();
                                String username = document.getData().get("username").toString();
                                Utente utenteTemp = new Utente(idUtente, username);

                                if (utenteTemp != null) userList.add(utenteTemp);
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());
                        currentUser.setListaUtenti(userList);
                        //SocialFragment.PutDataIntoRecyclerView(userList);

                    }
                });

    }
}
