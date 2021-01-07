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
import com.example.moviestar.R;
import com.example.moviestar.View.LoginActivity;
import com.example.moviestar.View.MainActivity;

import static android.provider.Settings.System.getString;
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
        if(checkCampiValidi(idUtente, password) == true) {
            Log.d("Test", "username" + idUtente+password);
            mpassword=password;
            midutente=idUtente;
            CognitoSettings cognitoSettings=new CognitoSettings(mContext);
            CognitoUser thisUser=cognitoSettings.getUserPool().getUser(idUtente);
            thisUser.getSessionInBackground(authenticationHandler);
            setMyUtente(new Utente(idUtente, password));

        }
        else {
            if(checkCampiVuoti(idUtente,password) == true){
                String errorMessage1 = mContext.getString(R.string.error_missing_credentials);
                PopupController.mostraPopup("Errore", errorMessage1, myContext);
            }else if(checkId(idUtente) == false){
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
