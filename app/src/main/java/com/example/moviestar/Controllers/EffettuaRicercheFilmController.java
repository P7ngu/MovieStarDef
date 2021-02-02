package com.example.moviestar.Controllers;

import android.os.AsyncTask;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EffettuaRicercheFilmController {
    //TODO
    ArrayList<Film> movieList;

    public static class GetData extends AsyncTask<String, String, String> {
        String URL1;
        List<Film> movieList;

        public GetData(String url_forSearching, List<Film> movieListPar) throws UnsupportedEncodingException {
            MainActivity.showProgressBar();
            String ulr_updated = formattaStringaPerQuery(url_forSearching);
            this.movieList=movieListPar;
            this.URL1=ulr_updated;
        }

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
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

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Film model = new Film();
                    model.setVote(jsonObject1.getString("vote_average"));
                    model.setId(jsonObject1.getString("id"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setOverview(jsonObject1.getString("overview"));
                    movieList.add(model);
                }

                MainActivity.hideProgressBar();
            } catch (Exception e){
                e.printStackTrace();
            }
            HomeFragment.PutDataIntoRecyclerView(movieList);
        }
    }

    private static String formattaStringaPerQuery(String url_forSearching) throws UnsupportedEncodingException {
        String result = url_forSearching.replaceAll(" ","%20");
        return result;
    }


}
