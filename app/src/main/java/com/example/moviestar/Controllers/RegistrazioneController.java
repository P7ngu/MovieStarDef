package com.example.moviestar.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.ui.RegistrazioneActivity;

public class RegistrazioneController {


    public static void registraUtente(String email, String password1, String password2, String idUtente, Context myContext) {
        if(checkCampiNonVuoti(idUtente, password1, password2, email)) {
            if (checkCampiValidi(idUtente, password1, password2, email)) {
                Utente myUtente = new Utente (idUtente, password1, email);
                VerificaController.sendCodice(idUtente);
            }
        }
        if(!checkCampiNonVuoti(idUtente, password1, password2, email)){
                String errorMessage="Errore, compilare tutti i campi!";
            Toast.makeText(myContext, errorMessage, Toast.LENGTH_SHORT).show();
            AlertDialog alertDialog = new AlertDialog.Builder(myContext).create(); //Read Update
            alertDialog.setTitle("hi");
            alertDialog.setMessage("this is my app");

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });

            alertDialog.show();  //<-- See This!
            //new SchermataPopupErrore(errorMessage);
        }

    }

    private static boolean checkCampiNonVuoti(String idUtente, String password1, String password2, String email) {
        if(idUtente.length() > 0 && password1.length() > 0 && password2.length() > 0 && email.length() > 0) return true;
        else
        return false;
    }

    static boolean checkCampiValidi(String idUtente, String password, String password2, String email) {
        if( checkId(idUtente) && checkPassword(password, password2) && checkEmail(email) )
        return true;
        else return false;
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



