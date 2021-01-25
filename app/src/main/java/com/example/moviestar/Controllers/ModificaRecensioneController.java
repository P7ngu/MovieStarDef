package com.example.moviestar.Controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    public static boolean eliminaRecensione(Commento commentoDaRimuovere, Context mContext){
        PopupController.mostraPopupDiConfermaOAnnulla("Elimina commento", "Vuoi davvero eliminare questo commento?",
                mContext, "commento", "Commenti", commentoDaRimuovere);
        return risultato;

    }


    public static void eliminaRecensione_DB(Commento commentoDaRimuovere, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("RichiesteAmico");
        String filmId=commentoDaRimuovere.getFilmId();
        String seconds=commentoDaRimuovere.getTimeStamp();
        db.collection("Commenti").document(userId+filmId+seconds)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupController.mostraPopup("Commento rimosso", "commento rimosso con successo", mContext);
                        //REFRESH SCHERMATA
                        risultato=true;
                        RecensioniFilmController.onClickLeggiCommenti(filmId, mContext);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", " non rimosso.", mContext);
                    }
                });

    }


    public static boolean eliminaRecensione(Commento commentoDaRimuovere, Context mContext, RecyclerView recyclerView){
        PopupController.mostraPopupDiConfermaOAnnulla("Elimina commento", "Vuoi davvero eliminare questo commento?",
                mContext, "commento", "Commenti", commentoDaRimuovere);
        return risultato;

    }


    public static void eliminaRecensione_DB(Commento commentoDaRimuovere, Context mContext, RecyclerView recyclerView){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("RichiesteAmico");
        String filmId=commentoDaRimuovere.getFilmId();
        String seconds=commentoDaRimuovere.getTimeStamp();
        db.collection("Commenti").document(userId+filmId+seconds)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupController.mostraPopup("Commento rimosso", "commento rimosso con successo", mContext);
                        //REFRESH SCHERMATA
                        risultato=true;
                        RecensioniFilmController.onClickLeggiCommenti(filmId, mContext, recyclerView);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", " non rimosso.", mContext);
                    }
                });

    }



    public static void riempiStelleDB(int numeroStelleDaRiempire, String filmId) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection("VotoStar");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("userID", userId);
        data4.put("voto", numeroStelleDaRiempire );
        filmPreferiti.document(userId+filmId).set(data4);

    }

    public static int getNumberOfStarsFromDB(String filmId) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path="VotoStar";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);

        db.collection(path)
                .whereEqualTo("userID", userId).whereEqualTo("filmID", filmId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                number_star= Integer.parseInt(String.valueOf(document.get("voto")));
                               try {
                                   MostraDettagliFilmVistoCliccatoPreferitoActivity.riempiStelle(number_star, false);
                                   MostraDettagliFilmVistoCliccatoActivity.riempiStelle(number_star, false);
                                   Log.d("DebugStelle", number_star+"");
                               } catch (Exception e){
                                   try{
                                   MostraDettagliFilmVistoCliccatoActivity.riempiStelle(number_star, false);
                                   Log.d("DebugStelle", number_star+"");}
                                   catch (Exception e1){

                                   }
                               }
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());

                    }
                });

        return number_star;
    }

    public static void onClickPositiveButton(Commento idObject, Context myContext) {
        eliminaRecensione_DB(idObject, myContext);
    }

    public static void onClickNegativeButton(Context myContext) {

    }
}
