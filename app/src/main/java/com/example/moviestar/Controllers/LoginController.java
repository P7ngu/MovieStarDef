package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginController {
    private static Utente myUtente;
    public static SharedPreferences prefs;
    private static String mpassword;
    private static String memail;
    private static  Context myContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Firestore connection
    private FirebaseFirestore db=FirebaseFirestore.getInstance();






    public static void login(final String email, final String password, final Context mContext){
        myContext=mContext;
        if(checkCampiValidi(email, password) == true) {
            Log.d("Test", "username" + email+password);
            mpassword=password;
            memail =email;

            setMyUtente(new Utente(email, password));

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

    }


    static boolean checkCampiValidi(String idUtente, String password) {
        if ((checkId(idUtente) == true) && (checkPassword(password) == true)) {
            return true;
        } else return false;
    }

    private static boolean checkPassword(String password) {
        // TODO Auto-generated method stub
        if(password.length() > 0) {
            return true;
        } else{
            return false;
        }
    }

    private static boolean checkId(String idUtente) {
        // TODO Auto-generated method stub
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
