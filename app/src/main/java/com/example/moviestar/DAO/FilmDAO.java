package com.example.moviestar.DAO;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Model.Film;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.HashMap;
import java.util.Map;

import static com.example.moviestar.View.profilo.ListaFilmActivity.PutDataIntoRecyclerView;

public class FilmDAO {

    public static Film currentModel;
    public static ArrayList<Film> movieList;

    public static void addToPreferitiDAO(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection("FilmPreferiti");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("filmName", filmName);
        data4.put("filmOverview", filmOverview);
        data4.put("filmFotoPath", filmFotoPath);
        data4.put("filmVoto", filmVoto);
        data4.put("userID", userId);
        filmPreferiti.document(userId+filmId).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);
    }

    public static void addToVistiDAO(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmVisti = db.collection("FilmVisti");


        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("filmName", filmName);
        data4.put("filmOverview", filmOverview);
        data4.put("filmFotoPath", filmFotoPath);
        data4.put("filmVoto", filmVoto);
        data4.put("userID", userId);
        filmVisti.document(userId+filmId).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);
    }

    public static void addToDaVedereDAO(String filmId, String filmName, String filmOverview, String filmFotoPath, String filmVoto, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmDaVedere = db.collection("FilmDaVedere");

        Map<String, Object> data4 = new HashMap<>();
        data4.put("filmID", filmId);
        data4.put("filmName", filmName);
        data4.put("filmOverview", filmOverview);
        data4.put("filmFotoPath", filmFotoPath);
        data4.put("filmVoto", filmVoto);
        data4.put("userID", userId);
        filmDaVedere.document(userId+filmId).set(data4);

        LoginController.loadCurrentUserDetails();
        PopupController.mostraPopup("filmId", "aggiunto alla lista", mContext);
    }

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


    public static void getSpecifiedListaFilmResults_Firebase(String path, Context mContext) {
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

    public static void rimuoviFilmDaLista_Firebase(Context mContext, String pathListaDaCuiRimuovere, String filmId) {
        LoginController.loadCurrentUserDetails();
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(pathListaDaCuiRimuovere);

        db.collection(pathListaDaCuiRimuovere).document(userId+filmId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(pathListaDaCuiRimuovere.equals("FilmVisti"))
                            db.collection("FilmPreferiti").document(userId+filmId)
                                    .delete();
                        //PopupController.mostraPopup("Film rimosso!", filmName+"rimosso con successo.", mContext);
                        LoginController.loadCurrentUserDetails();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupController.mostraPopup("Errore", " ", mContext);
                    }
                });
    }
}
