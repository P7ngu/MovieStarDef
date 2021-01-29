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
import com.example.moviestar.Controllers.VisualizzaListaController;
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
    static RecyclerView filmRecycler;
    static String tipologiaLista;
    static Film filmCliccato;
    static String URL;
    static Context mContext;


    public static void setFilm(Film filmCliccato1) {
        filmCliccato = filmCliccato1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mContext=this;
        LoginController.loadCurrentUserDetails();
        tipologiaLista = intent.getStringExtra("tipologia");
        setContentView(R.layout.listafilm);
        filmRecycler = findViewById(R.id.listafilm_recyclerview);


        URL = "https://api.themoviedb.org/3/movie/123?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT";

        Film model = new Film();
        VisualizzaListaController.getFilmData(tipologiaLista);


    }

    public static void PutDataIntoRecyclerView (List <Film > filmList1) {
        Adaptery adaptery = new Adaptery(mContext, filmList1);
        filmRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        filmRecycler.setAdapter(adaptery);
    }


}




