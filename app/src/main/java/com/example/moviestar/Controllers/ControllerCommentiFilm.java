package com.example.moviestar.Controllers;

import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Film;
import com.example.moviestar.View.home.RecyclerCommenti.AdapteryCommenti;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ControllerCommentiFilm {

    public static class GetData extends AsyncTask<String, String, String> {
        String URL1;
        Context mContext;
        public RecyclerView commentiRecycler;
        static Film filmCliccato;
        static String URL;
        public List<Commento> commentoList;

        public GetData(String url_forSearching, Context context, List<Commento> commentolist, RecyclerView commentirecycler) throws UnsupportedEncodingException {
            this.URL1=url_forSearching;
            this.mContext=context;
            this.commentoList=commentolist;
            this.commentiRecycler=commentirecycler;

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
            PutDataIntoRecyclerView(commentoList, commentiRecycler, mContext);
        }
    }

    private static void PutDataIntoRecyclerView(List<Commento> commentoList, RecyclerView commentiRecycler, Context mContext){
        AdapteryCommenti adaptery=new AdapteryCommenti(mContext, commentoList);
        commentiRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        commentiRecycler.setAdapter(adaptery);

    }
}
