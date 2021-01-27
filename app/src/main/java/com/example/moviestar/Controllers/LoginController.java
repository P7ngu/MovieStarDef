package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviestar.Model.Film;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.login.RegistrazioneActivity;
import com.example.moviestar.View.login.VerificationActivity;
import com.example.moviestar.View.profilo.ListaAmiciActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoginController {
    private static Utente myUtente;
    public static SharedPreferences prefs;
    private static String mpassword;
    private static String memail;
    private static  Context myContext;

    static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();;
    static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser currentUser;

    static FirebaseFirestore db=FirebaseFirestore.getInstance();
    static CollectionReference collectionReference = db.collection("Users");



    public static void Firebase_loginEmailPasswordUser(String email, String password, Context mContext) {
        PopupController.mostraPopup("Debug", email+password, mContext);

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            final String currentuserid = user.getUid();

                            collectionReference
                                    .whereEqualTo("userID", currentuserid)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                PopupController.mostraPopup("Errore durante il login", error.toString(), mContext);
                                            }
                                            assert value != null;
                                            if (!value.isEmpty()) {
                                                for (QueryDocumentSnapshot snapshot : value) {
                                                    CurrentUser currentUser = CurrentUser.getInstance();
                                                    currentUser.setUsername(snapshot.getString("username"));
                                                    currentUser.setUserId(currentuserid);
                                                    PopupController.mostraPopup("Dentro query", currentuserid + snapshot.getString("username"), mContext);
                                                    prefs = mContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = prefs.edit();
                                                    editor.putString("email", email);
                                                    editor.putString("password", password);
                                                    editor.apply();
                                                    loadCurrentUserDetails();
                                                    MainActivity.setUserLogged(true);
                                                    Intent intent = new Intent(mContext, MainActivity.class);
                                                    mContext.startActivity(intent);

                                                }
                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            PopupController.mostraPopup("Errore durante il login", e.toString(), mContext);

                        }
                    });
        if(VerificaController.IsEmailVerified()) {
            mContext.startActivity(new Intent(mContext, MainActivity.class));
        } else {
                mContext.startActivity(new Intent(mContext, VerificationActivity.class));
        }



    }

    public static void loadCurrentUserDetails() {
        try {
            loadListaFilmFromDB("FilmVisti");
            loadListaFilmFromDB("FilmPreferiti");
            loadListaFilmFromDB("FilmDaVedere");
            loadListaAmiciFromDB();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void loadListaFilmFromDB(String path) {
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
                       try{ListaAmiciActivity.PutDataIntoRecyclerView(amiciList);} catch(Exception e){}
                    }
                });

    }


    public static boolean login(final String email, final String password, final Context mContext){
        myContext=mContext;
        if(checkCampiValidi(email, password) == true) {
            Log.d("Test", "username" + email+password);
            mpassword=password;
            memail =email;
            setMyUtente(new Utente(email, password));
            return true;
        }
        else {
            if(checkCampiVuoti(email,password) == true){
                String errorMessage1 = mContext.getString(R.string.error_missing_credentials);
                PopupController.mostraPopup("Errore", errorMessage1, myContext);
            }else if(checkId(email) == false){
                    String errorMessage2 = mContext.getString(R.string.error_missing_username);
                    PopupController.mostraPopup("Errore", errorMessage2, myContext);
                }else if(checkPassword(password) == false){
                    String errorMessage3 = mContext.getString(R.string.error_missing_password);
                    PopupController.mostraPopup("Errore", errorMessage3, myContext);
                }
        }
        return false;

    }


    static boolean checkCampiValidi(String idUtente, String password) {
        if ((checkId(idUtente) == true) && (checkPassword(password) == true)) {
            return true;
        } else return false;
    }

    private static boolean checkPassword(String password) {
        if(password.length() > 0) {
            return true;
        } else{
            return false;
        }
    }

    private static boolean checkId(String idUtente) {
        if(idUtente.length() > 0) {
            return true;
        } else{
            return false;
        }
    }

    static boolean checkCampiVuoti(String idUtente, String password) {
        if((checkId(idUtente) == false) && (checkPassword(password) == false)) {
            return true;                //vuoti => TRUE
        } else return false;
    }


    public static Utente getMyUtente() {
        return myUtente;
    }

    public static void setMyUtente(Utente myUtente) {
        myUtente = myUtente;
    }


}
