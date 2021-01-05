package com.example.moviestar.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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

}
