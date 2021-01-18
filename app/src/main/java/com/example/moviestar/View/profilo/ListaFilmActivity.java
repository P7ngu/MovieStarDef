package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.home.Recycler.Adaptery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaFilmActivity extends AppCompatActivity {
    RecyclerView filmRecycler;
    static String tipologiaLista;
    static Film filmCliccato;
    static String URL;
    Context mContext = this;


    public static void setFilm(Film filmCliccato1) {
        filmCliccato = filmCliccato1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        LoginController.loadCurrentUserDetails();
        tipologiaLista = intent.getStringExtra("tipologia");
        setContentView(R.layout.listafilm);
        filmRecycler = findViewById(R.id.listafilm_recyclerview);



        URL = "https://api.themoviedb.org/3/movie/123?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT";

        Film model = new Film();

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


    private void PutDataIntoRecyclerView (List <Film > filmList1) {
        Adaptery adaptery = new Adaptery(mContext, filmList1);
        filmRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        filmRecycler.setAdapter(adaptery);
    }

    public void getSpecifiedListaFilmResults(String path){

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




