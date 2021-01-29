package com.example.moviestar.Controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.profilo.ListaFilmActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.moviestar.View.profilo.ListaFilmActivity.PutDataIntoRecyclerView;

public class VisualizzaListaController {

    public static void getFilmData(String tipologiaLista){
        try {
            if (tipologiaLista.equals("filmpreferiti")) {
                String path = "FilmPreferiti";
                //getListaFilmPreferitiResults(path);
                LoginController.loadCurrentUserDetails();
                PutDataIntoRecyclerView(CurrentUser.getInstance().getListaFilmPreferiti());
            }
            else if(tipologiaLista.equals("filmvisti")) {
                String path = "FilmVisti";
                //getListaFilmPreferitiResults(path);
                LoginController.loadCurrentUserDetails();
                PutDataIntoRecyclerView(CurrentUser.getInstance().getListaFilmVisti());
            } else{
                String path = "FilmDaVedere";
                LoginController.loadCurrentUserDetails();
                PutDataIntoRecyclerView(CurrentUser.getInstance().getListaFilmDaVedere());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void getSpecifiedListaFilmResults(String path, Context mContext){

        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String idFilm, title, overview, fotoPath, voto;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);
        ArrayList<Film> filmList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("userID", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idFilm = document.getData().get("filmID").toString();
                                String title = document.getData().get("filmName").toString();
                                String overview = document.getData().get("filmOverview").toString();
                                String fotoPath = document.getData().get("filmFotoPath").toString();
                                String voto = document.getData().get("filmVoto").toString();
                                Film filmTemp = new Film(idFilm, title, fotoPath, voto, overview);

                                if (filmTemp != null) filmList.add(filmTemp);
                                else PopupController.mostraPopup("errore", "errore", mContext);
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());
                        PutDataIntoRecyclerView(filmList);
                        PopupController.mostraPopup("Lista Da Copiare", filmList.get(0).toString(), mContext);
                    }
                });

    }
}
