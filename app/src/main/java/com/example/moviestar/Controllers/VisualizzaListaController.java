package com.example.moviestar.Controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.DAO.FilmDAO;
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
        FilmDAO.getSpecifiedListaFilmResults_Firebase(path, mContext);

    }
}
