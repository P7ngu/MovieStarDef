package com.example.moviestar.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoActivity;
import com.example.moviestar.View.home.MostraDettagliFilmVistoCliccatoPreferitoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ModificaRecensioneController {
    static int number_star=0;
    //todo
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
                               } catch (Exception e){

                               }
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());

                    }
                });

        return number_star;
    }
}
