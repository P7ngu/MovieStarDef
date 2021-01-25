package com.example.moviestar.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.moviestar.R;

public class PopupController {

    public static void mostraPopup(String titolo, String messaggio, Context myContext){
        AlertDialog alertDialog = new AlertDialog.Builder(myContext).create(); //Read Update
        alertDialog.setTitle(titolo);
        alertDialog.setMessage(messaggio);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });

        alertDialog.show();

    }

    public static void mostraPopupDiConfermaOAnnulla(String titolo, String messaggio, Context myContext, String classe, String path, String idObject){
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        boolean risultato;

        builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("rimuovifilm"))
                RimuoviFilmDaListaController.gestionePositiveButton(myContext, path, idObject);
                else if(classe.equals("rimuoviamico"))
                    RimuoviAmicoController.gestionePositiveButton(myContext, path, idObject);
                else if(classe.equals("spoiler"))
                    MostraDettagliFilmController.gestionePositiveButton(myContext, path, idObject);

            }
        });
        builder.setNegativeButton("Annulla azione", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("rimuovifilm"))
                RimuoviFilmDaListaController.gestioneNegativeButton(myContext);
                else if(classe.equals("rimuoviamico"))
                    RimuoviAmicoController.gestioneNegativeButton(myContext);
                else if(classe.equals("spoiler"))
                    MostraDettagliFilmController.gestioneNegativeButton(myContext);
                // User cancelled the dialog
            }
        });


        AlertDialog dialog = builder.create();
        dialog.setTitle(titolo);
        dialog.setMessage(messaggio);
        dialog.show();
    }

}
