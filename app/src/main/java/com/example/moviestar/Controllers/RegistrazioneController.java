package com.example.moviestar.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.VerificationActivity;

public class RegistrazioneController extends AppCompatActivity {
    static Utente myUtente;
    Button registratiButton=findViewById(R.id.button_registrati);
    final static CognitoUserAttributes userAttributes = new CognitoUserAttributes();
    static SignUpHandler signupCallback;


    private static void  registerUser(String idUtente, String email, String password, final Context mContext) {

    }





    public static void registraUtente(String email, String password1, String password2, String idUtente, final Context myContext) {
        if(checkCampiNonVuoti(idUtente, password1, password2, email)) {
            if (checkCampiValidi(idUtente, password1, password2, email)) {
                myUtente = new Utente (idUtente, password1, email);
                userAttributes.addAttribute("given_name", idUtente);
                userAttributes.addAttribute("email", email);
                userAttributes.addAttribute("nickname", idUtente);

                CognitoSettings cognitoSettings=new CognitoSettings(myContext);



                signupCallback = new SignUpHandler() {
                    @Override
                    public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                        Log.i("login", "sign up confirmed " + signUpConfirmationState);
                        if (!signUpConfirmationState) {
                            Log.i("login", "not verified"+cognitoUserCodeDeliveryDetails.getDestination());
                            Intent intent = new Intent(myContext, VerificationActivity.class);
                            myContext.startActivity(intent);
                        } else {
                            Log.i("login", "verified");
                        }
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Log.i("login", "failed"+exception.getLocalizedMessage());
                    }
                };
                cognitoSettings.getUserPool().signUpInBackground(idUtente, password1,
                        userAttributes, null, signupCallback);
                //VerificaController.sendCodice(idUtente);
            }
        }
        if(!checkCampiNonVuoti(idUtente, password1, password2, email)){
                String errorMessage="Errore, compilare tutti i campi!";
                PopupController.mostraPopup("Errore", errorMessage, myContext);

        }

    }

    private static boolean checkCampiNonVuoti(String idUtente, String password1, String password2, String email) {
        if(idUtente.length() > 0 && password1.length() > 0 && password2.length() > 0 && email.length() > 0) return true;
        else
        return false;
    }

    static boolean checkCampiValidi(String idUtente, String password, String password2, String email) {
        //if( checkId(idUtente) && checkPassword(password, password2) && checkEmail(email) )
        return true;
        //else return false;
    }

    private static boolean checkEmail(String email) {
        return UtenteDAO.checkEmailNonPresente(email);

    }

    private static boolean checkPassword(String password1, String password2) {
        if(password1.equals(password2) && password1.length()>= 6) return true;
        else return false;

    }

    private static boolean checkId(String idUtente) {
        return UtenteDAO.checkIdUtenteNonPresente(idUtente);

    }


}



