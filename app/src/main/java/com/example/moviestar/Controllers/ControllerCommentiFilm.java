package com.example.moviestar.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Film;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.View.home.RecyclerCommenti.AdapteryCommenti;
import com.example.moviestar.View.social.SocialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerCommentiFilm {

    public static void inserisciCommentoFilm(String idFilmCommentato, String commentoDaInserire, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        String username= currentUser.getUsername();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference richiesteamico = db.collection("Commenti");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("userID", userId);
        data4.put("commento", commentoDaInserire);
        data4.put("filmID", idFilmCommentato);
        data4.put("username", username);
        richiesteamico.document(userId+idFilmCommentato+ Timestamp.now().getSeconds()).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("Commento", "aggiunto alla lista", mContext);
    }

    public static void getListaCommentiFilm(String idFilm, Context mContext, RecyclerView recyclerView){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path = "Commenti";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference commentiCollection = db.collection(path);
        ArrayList<Commento> commentiList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("filmID", idFilm)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String commentoText = document.getData().get("commento").toString();
                                String userid = document.getData().get("userID").toString();
                                String username = document.getData().get("username").toString();
                                Commento commentoTemp = new Commento(username, userId, commentoText);

                                if (commentoTemp != null) commentiList.add(commentoTemp);
                            }
                            PutDataIntoRecyclerView(commentiList, recyclerView, mContext);
                        } else
                            Log.d("testFirebase", "Error getting documents: ", task.getException());
                    }
                });

    }
    private static void PutDataIntoRecyclerView(List<Commento> commentoList, RecyclerView commentiRecycler, Context mContext){
        AdapteryCommenti adaptery=new AdapteryCommenti(mContext, commentoList);
        commentiRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        commentiRecycler.setAdapter(adaptery);

    }
}
