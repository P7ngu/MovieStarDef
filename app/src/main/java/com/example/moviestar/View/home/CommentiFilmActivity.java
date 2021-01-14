package com.example.moviestar.View.home;

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

public class CommentiFilmActivity extends AppCompatActivity {
    RecyclerView commentiRecycler;
    static Film filmCliccato;
    static String URL;
     Context mContext=this;
    public List<Commento> commentoList;

    public static void setFilm(Film filmCliccato1) {
        filmCliccato=filmCliccato1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentifilm);
        commentiRecycler=findViewById(R.id.commentifilm_recyclerview);
        Intent intent = getIntent();
        String filmID = intent.getStringExtra("filmCliccatoId");
        commentoList=new ArrayList<>();

        URL = "https://api.themoviedb.org/3/movie/"+filmID+"?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT";
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
            String Lorem="Lorem ipsum dolor sit amet, consectetur adipiscing elit. In dapibus augue a ex lobortis vehicula. Proin et convallis justo, et eleifend orci. Aliquam sodales dignissim ipsum a commodo. Phasellus scelerisque aliquam leo quis lobortis. Sed auctor, enim in auctor aliquam, tellus ligula convallis risus, sit amet rutrum mauris ante id augue. Pellentesque porttitor est sit amet luctus molestie. Nam nulla justo, pulvinar finibus elementum id, euismod venenatis velit.";

            try{
                for (int i=0; i<15; i++) {
                    Commento model = new Commento(Lorem, "Autore di prova");
                    commentoList.add(model);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(commentoList);
        }
    }

    private void PutDataIntoRecyclerView(List<Commento> commentoList){
        AdapteryCommenti adaptery=new AdapteryCommenti(mContext, commentoList);
        commentiRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        commentiRecycler.setAdapter(adaptery);

    }
}
