package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.moviestar.View.home.Recycler.Adaptery;
import com.example.moviestar.View.profilo.ListaAmiciActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RimuoviAmicoController {

    public static void accettaRichiestaAmico(String idUtenteDaAggiungere, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference richiesteamico = db.collection("ListaAmici");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("userID_ricevente", idUtenteDaAggiungere);
        data4.put("userID_mandante", userId);
        richiesteamico.document(userId+idUtenteDaAggiungere).set(data4);

        Map<String, Object> data5 = new HashMap<>();
        data5.put("userID_ricevente", userId);
        data5.put("userID_mandante", idUtenteDaAggiungere);
        richiesteamico.document(idUtenteDaAggiungere+userId).set(data5);

        removeRichiestaAmico(idUtenteDaAggiungere, mContext);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("Utente", "aggiunto alla lista", mContext);
    }

    private static void removeRichiestaAmico(String idUtenteDaAggiungere, Context mContext) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("RichiesteAmico");

        db.collection("RichiesteAmico").document(idUtenteDaAggiungere+userId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //PopupController.mostraPopup("Richiesta respinta", "rimossa con successo.", mContext);
                        
                        //REFRESH SCHERMATA
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", idUtenteDaAggiungere+" non rimosso.", mContext);
                    }
                });

    }


    public static void respingiRichiestaAmico(String idUtenteDaRespingere, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("RichiesteAmico");

        db.collection("RichiesteAmico").document(idUtenteDaRespingere+userId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupController.mostraPopup("Richiesta respinta", "rimossa con successo.", mContext);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", idUtenteDaRespingere+" non rimosso.", mContext);
                    }
                });

    }

    public static void eliminaAmicoDaListaAmici(String idUtenteDaEliminare, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("ListaAmici");

        db.collection("ListaAmici").document(idUtenteDaEliminare+userId)
                .delete();

        db.collection("ListaAmici").document(userId+idUtenteDaEliminare)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        LoginController.loadListaAmiciFromDB();
                        PopupController.mostraPopup("Utente eliminato con successo",
                                "Utente eliminato dalla lista amici con successo.", mContext);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", " non rimosso.", mContext);
                    }
                });

    }

    public static void gestionePositiveButton(Context myContext, String path, String idObject) {

    }

    public static void gestioneNegativeButton(Context myContext) {

    }
}



