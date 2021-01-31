package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviestar.DAO.UtenteDAO;
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

import java.io.CharArrayWriter;
import java.util.ArrayList;

import static com.example.moviestar.DAO.UtenteDAO.loadListaAmiciFromDB;
import static com.example.moviestar.DAO.UtenteDAO.loadListaFilmFromDB;
import static com.google.firebase.FirebaseError.ERROR_EMAIL_ALREADY_IN_USE;

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
        if(login(email,password,mContext)==true) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //assert user != null;
                            if (user != null) {
                                final String currentuserid = user.getUid();

                                collectionReference
                                        .whereEqualTo("userID", currentuserid)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                if (error != null) {
                                                    String errore = error.getMessage();
                                                    Log.d("LOGIN123",error.getMessage());
                                                    System.out.println(errore);
                                                    System.out.println(error.getMessage());
                                                    PopupController.mostraPopup("Errore durante il login", error.getMessage(), mContext);
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
                                                    if(VerificaController.IsEmailVerified()) {
                                                        mContext.startActivity(new Intent(mContext, MainActivity.class));
                                                    } else {
                                                        mContext.startActivity(new Intent(mContext, VerificationActivity.class));
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            PopupController.mostraPopup("Errore durante il login", e.toString(), mContext);
                        }
                    });
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


    public static void loadListaFilmFromDB(String path){
        try {
            UtenteDAO.loadListaFilmFromDB(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void loadListaAmiciFromDB(){
        try {
            UtenteDAO.loadListaAmiciFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }
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

//    public String excMessage(String message){
//        String result = result.replaceFirst(String regex, String "Error:");
//        return result;
//    }


}
