package com.example.moviestar.DAO;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Model.Film;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

public class FilmDAO {

    public static Film currentModel;
    public static ArrayList<Film> movieList;

    public static Film queryDB_FindListByPath(String path, Context mContext) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);

        db.collection(path)
                .whereEqualTo("userID", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idFilm=document.getData().get("filmID").toString();
                                String title=document.getData().get("filmName").toString();
                                String overview=document.getData().get("filmOverview").toString();
                                String fotoPath=document.getData().get("filmFotoPath").toString();
                                String voto=document.getData().get("filmVoto").toString();
                                Film model = new Film(idFilm, title, fotoPath,voto, overview);
                                currentModel=model;
                                //findFilmDataById(idFilm, mContext);
                                PopupController.mostraPopup(model.getName(), model.getOverview(), mContext);
                                //PopupController.mostraPopup("FIlm id", document.getData().get("filmID").toString(), mContext);
                            }
                        } else {
                            Log.d("testFirebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return currentModel;
    }



//    private static void findFilmDataById(String idFilm, Context mContext) {
//        String APIkey="89d40cd46523243c6d553bb54b2ca47e";
//        String myURL = "https://api.themoviedb.org/3/movie/"+idFilm+"?api_key="+APIkey+"&append_to_response=videos";
//        try {
//            GetData getData = new GetData(myURL, mContext);
//            getData.execute();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    public static class GetData extends AsyncTask<String, String, String> {
//        Context mContext;
//        String URL1;
//        Film model_returned=new Film();
//
//        public GetData(String url, Context mContext) throws UnsupportedEncodingException {
//            this.URL1=url;
//            this.mContext=mContext;
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String current = "";
//            try {
//                URL url;
//                HttpURLConnection urlConnection = null;
//                try {
//                    url = new URL(URL1);
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    InputStream is = urlConnection.getInputStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//
//                    int data = isr.read();
//                    while (data != -1) {
//                        current += (char) data;
//                        data = isr.read();
//                    }
//
//                    return current;
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (urlConnection != null) {
//                        urlConnection.disconnect();
//                    }
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            return current;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            try{
//                JSONArray jsonArray= new JSONArray(s);
//                for(int i=0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    Film model = new Film();
//                    model.setVote(jsonObject1.getString("vote_average"));
//                    model.setId(jsonObject1.getString("id"));
//                    model.setName(jsonObject1.getString("title"));
//                    model.setImg(jsonObject1.getString("poster_path"));
//                    model.setOverview(jsonObject1.getString("overview"));
//                    PopupController.mostraPopup(model.getName(), "dijejfd", mContext);
//                    movieList.add(model);
//
//                }
//
//
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//    }



}
