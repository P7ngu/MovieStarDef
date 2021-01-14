package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.Recycler.Adaptery;
import com.example.moviestar.View.home.RecyclerCommenti.AdapteryCommenti;

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
    Context mContext=this;
    public List<Film> filmList;

    public static void setFilm(Film filmCliccato1) {
        filmCliccato=filmCliccato1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        tipologiaLista=intent.getStringExtra("tipologia");
        setContentView(R.layout.listafilm);
        filmRecycler=findViewById(R.id.listafilm_recyclerview);
        filmList=new ArrayList<>();

        URL = "https://api.themoviedb.org/3/movie/123?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT";
        try {
            GetData getData1 = new GetData(URL);
            getData1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public class GetData extends AsyncTask<String, String, String> {
        String URL1;

        public GetData(String url_forSearching) throws UnsupportedEncodingException {
            this.URL1=url_forSearching;
        }

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                java.net.URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(URL1);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            Film model;
            try{
                for (int i=0; i<15; i++) {
                    if(tipologiaLista.equals("filmpreferiti")){
                    model = new Film("Lorem prefe", "Autore di prova");
                        if (model != null) filmList.add(model);
                    }
                    else {
                        model = new Film("Lorem visti ", "Autore di prova");
                        if (model != null) filmList.add(model);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(filmList);
        }
    }

    private void PutDataIntoRecyclerView(List<Film> filmList){
        Adaptery adaptery=new Adaptery(mContext, filmList);
        filmRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        filmRecycler.setAdapter(adaptery);

    }
}


