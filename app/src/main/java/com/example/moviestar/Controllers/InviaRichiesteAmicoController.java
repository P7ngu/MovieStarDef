package com.example.moviestar.Controllers;

import android.content.Context;
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
import java.util.HashMap;
import java.util.Map;

public class InviaRichiesteAmicoController {

    public static void sendRichiestaAmico(String idUtenteDaAggiungere, Context mContext) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        if(!userId.equals(idUtenteDaAggiungere)) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference richiesteamico = db.collection("RichiesteAmico");


            Map<String, Object> data4 = new HashMap<>();
            data4.put("userID_ricevente", idUtenteDaAggiungere);
            data4.put("userID_mandante", userId);
            richiesteamico.document(userId + idUtenteDaAggiungere).set(data4);

            LoginController.loadCurrentUserDetails();
            PopupController.mostraPopup("Utente", "aggiunto alla lista", mContext);
        } else{
            PopupController.mostraPopup("Errore", "non puoi aggiungere te stesso!", mContext);

        }
    }

    public static void getRichiesteAmico() {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path = "RichiesteAmico";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection(path);
        ArrayList<Utente> userList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("userID_ricevente", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idUtente = document.getData().get("userID_mandante").toString();
                                //String username = document.getData().get("username").toString();
                                Utente utenteTemp = new Utente(idUtente);

                                if (utenteTemp != null) userList.add(utenteTemp);
                            }
                           //SocialFragment.PutDataIntoRecyclerView(userList, "richieste");
                            CurrentUser.getInstance().setListaRichiesteAmico(userList);
                        } else
                            Log.d("testFirebase", "Error getting documents: ", task.getException());
                    }
                });
    }
}

