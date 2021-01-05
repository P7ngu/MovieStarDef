package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.View.LoginActivity;
import com.example.moviestar.View.MainActivity;

import static com.example.moviestar.View.LoginActivity.*;

public class LoginController {
    private static Utente myUtente;
    public static SharedPreferences prefs;
    private static String mpassword;
    private static String midutente;
    private static  Context myContext;

    final static AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            prefs = myContext.getSharedPreferences("myPrefsKeys", Context.MODE_PRIVATE);
            //SharedPreferences sharedPref = SharedPreferences.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", midutente);
            editor.putString("password", mpassword);
            editor.apply();
            Intent intent=new Intent(myContext, MainActivity.class);
            myContext.startActivity(intent);


        }



        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            AuthenticationDetails authenticationDetails=new AuthenticationDetails(midutente, mpassword, null);
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
            Log.d("Test", "not successful"+exception.getLocalizedMessage());

        }
    };


    public static void login(final String idUtente, final String password, final Context mContext){
        myContext=mContext;
        if(checkCampiNonVuoti(idUtente, password)) {
            Log.d("Test", "username" + idUtente+password);
            mpassword=password;
            midutente=idUtente;
            CognitoSettings cognitoSettings=new CognitoSettings(mContext);
            CognitoUser thisUser=cognitoSettings.getUserPool().getUser(idUtente);
            thisUser.getSessionInBackground(authenticationHandler);
           setMyUtente(new Utente(idUtente, password));

        }
        else {
            String errorMessage="Errore, compilare tutti i campi!";
            PopupController.mostraPopup("Errore", errorMessage, myContext);

        }

    }


    static boolean  checkCampiValidi(String idUtente, String password) {
        checkId();
        checkPassword();
        return true;
    }

    private static void checkPassword() {
        // TODO Auto-generated method stub

    }

    private static void checkId() {
        // TODO Auto-generated method stub

    }

    static boolean checkCampiNonVuoti(String idUtente, String password) {
        return idUtente.length() > 0 && password.length() > 0;
    }


    public static Utente getMyUtente() {
        return myUtente;
    }

    public static void setMyUtente(Utente myUtente) {
        myUtente = myUtente;
    }


}
