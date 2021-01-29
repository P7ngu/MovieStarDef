package com.example.moviestar.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Model.Film;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.View.profilo.ListaAmiciActivity;
import com.example.moviestar.View.social.SocialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UtenteDAO {
    String imageURI;
    public static SharedPreferences prefs;
    static Uri ImageUri;
    static String currentID;
    static boolean flag;


    public static void eliminaAmicoDaListaAmici_Firebase(String idUtenteDaEliminare, Context mContext){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId=currentUser.getUserId();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection("ListaAmici");

        db.collection("ListaAmici").document(idUtenteDaEliminare+userId)
                .delete();

        db.collection("ListaAmici").document(userId+idUtenteDaEliminare)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                LoginController.loadListaAmiciFromDB();
                PopupController.mostraPopup("Utente eliminato con successo",
                        "Utente eliminato dalla lista amici con successo.", mContext);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                PopupController.mostraPopup("Errore", " non rimosso.", mContext);
            }
        });
    }

    public static void loadListaFilmFromDB(String path) {
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
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());
                        if(path.equals("FilmVisti"))currentUser.setListaFilmVisti(filmList);
                        if(path.equals("FilmPreferiti"))currentUser.setListaFilmPreferiti(filmList);
                        if(path.equals("FilmDaVedere"))currentUser.setListaFilmDaVedere(filmList);
                    }
                });

    }

    public static void loadListaAmiciFromDB(){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path="ListaAmici";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listaAmici = db.collection(path);
        ArrayList<Utente> amiciList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("userID_mandante", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idUtente = document.getData().get("userID_ricevente").toString();
                                Utente utenteTemp = new Utente(idUtente);
                                if (utenteTemp != null) amiciList.add(utenteTemp);
                            }
                            //ListaAmiciActivity.PutDataIntoRecyclerView(amiciList);
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());
                        currentUser.setListaAmici(amiciList);
                        try{
                            ListaAmiciActivity.PutDataIntoRecyclerView(amiciList);} catch(Exception e){}
                    }
                });

    }


    public static void getUtentiByID(String idDaCercare){
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String path="Users";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userList = db.collection(path);
        ArrayList<Utente> usersList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("userID", idDaCercare)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //data = document.getData();
                                String idUtente = document.getData().get("userID").toString();
                                String nomeUtente = document.getData().get("username").toString();
                                Utente utenteTemp = new Utente(idUtente, nomeUtente);
                                if (utenteTemp != null){
                                    usersList.add(utenteTemp);
                                    currentUser.setListaUtenti(usersList);
                                }
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());
                    }
                });
        try {
         //   currentUser.setListaUtenti(usersList);
           // SocialFragment.PutDataIntoRecyclerView(usersList, "richieste");
        }catch (Exception e){}
    }
    public static void getImageFromDatabase(String currentID, Context mContext){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        StorageReference storageRef;
        storageRef = FirebaseStorage.getInstance().getReference();

        storageRef.child("profile_pictures/"+currentID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = prefs.edit();
                editor1.putString("uri", uri.toString());
                editor1.apply();

                CurrentUser.getInstance().setImageUri(uri);

                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    public static boolean checkFilmPresenteLista_Firebase(String filmId, String path, Context mContext) {
        CurrentUser currentUser = CurrentUser.getInstance();
        String userId = currentUser.getUserId();
        String idFilm, title, overview, fotoPath, voto;
        flag=false;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference filmPreferiti = db.collection(path);
        ArrayList<Film> filmList = new ArrayList<>();

        db.collection(path)
                .whereEqualTo("userID", userId).whereEqualTo("filmID", filmId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                flag=true;
                            }
                        } else Log.d("testFirebase", "Error getting documents: ", task.getException());

                    }
                });
        return flag;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(String currentID) {
        this.currentID = currentID;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    String currentUsername;

    public UtenteDAO(String imageURI, String currentID, String currentUsername) {
        this.imageURI = imageURI;
        this.currentID = currentID;
        this.currentUsername = currentUsername;
    }

    public UtenteDAO(){}

    public static boolean checkEmailNonPresente(String email) {
        return false;
    }

    public static boolean checkIdUtenteNonPresente(String idUtente) {
        return false;
    }
}
