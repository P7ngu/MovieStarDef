package com.example.moviestar.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Film;

public class PopupController {

    public static void mostraPopup(String titolo, String messaggio, Context myContext){
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(myContext).create(); //Read Update
            alertDialog.setTitle(titolo);
            alertDialog.setMessage(messaggio);

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // here you can add functions
                }
            });

            alertDialog.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void mostraPopupDiConfermaOAnnulla(String titolo, String messaggio, Context myContext, String classe, String path, String idObject){
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        boolean risultato;

        builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("rimuovifilm"))
                RimuoviFilmDaListaController.onClickPositiveButton(myContext, path, idObject);
                else if(classe.equals("rimuoviamico"))
                    RimuoviAmicoController.onClickPositiveButton(myContext, path, idObject);
//                else if(classe.equals("spoiler"))
//                    RecensioniFilmController.onClickPositiveButton(idObject, myContext);


            }
        });
        builder.setNegativeButton("Annulla azione", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("rimuovifilm"))
                RimuoviFilmDaListaController.onClickNegativeButton(myContext);
                else if(classe.equals("rimuoviamico"))
                    RimuoviAmicoController.onClickNegativeButton(myContext);
                else if(classe.equals("spoiler"))
                    RecensioniFilmController.onClickNegativeButton(myContext);
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle(titolo);
        dialog.setMessage(messaggio);
        dialog.show();
    }

    public static void mostraPopupDiConfermaOAnnulla(String titolo, String messaggio, Context myContext, String classe, String path, String idObject, Film filmCliccato){
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        boolean risultato;

        builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("rimuovifilm"))
                    RimuoviFilmDaListaController.onClickPositiveButton(myContext, path, idObject);
                else if(classe.equals("rimuoviamico"))
                    RimuoviAmicoController.onClickPositiveButton(myContext, path, idObject);
                else if(classe.equals("spoiler"))
                    RecensioniFilmController.onClickPositiveButton(filmCliccato, myContext);


            }
        });
        builder.setNegativeButton("Annulla azione", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("rimuovifilm"))
                    RimuoviFilmDaListaController.onClickNegativeButton(myContext);
                else if(classe.equals("rimuoviamico"))
                    RimuoviAmicoController.onClickNegativeButton(myContext);
                else if(classe.equals("spoiler"))
                    RecensioniFilmController.onClickNegativeButton(myContext);
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle(titolo);
        dialog.setMessage(messaggio);
        dialog.show();
    }


//    public static void mostraPopupDiConfermaOAnnulla(String titolo, String messaggio, Context myContext, String classe, String path, Object idObject){
//        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
//        boolean risultato;
//
//        builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                if(classe.equals("commento"))
//                    ModificaRecensioneController.onClickPositiveButton((Commento) idObject, myContext);
//
//            }
//        });
//        builder.setNegativeButton("Annulla azione", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                if(classe.equals("commento"))
//                    ModificaRecensioneController.onClickNegativeButton(myContext);
//
//                // User cancelled the dialog
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.setTitle(titolo);
//        dialog.setMessage(messaggio);
//        dialog.show();
//    }

    public static void mostraPopupDiConfermaOAnnulla(Film filmCliccato, String titolo, String messaggio, Context myContext, String classe, String path, Object idObject){
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        boolean risultato;

        builder.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("commento"))
                    ModificaRecensioneController.onClickPositiveButton(filmCliccato,(Commento) idObject, myContext);

            }
        });
        builder.setNegativeButton("Annulla azione", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(classe.equals("commento"))
                    ModificaRecensioneController.onClickNegativeButton(myContext);

                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle(titolo);
        dialog.setMessage(messaggio);
        dialog.show();
    }


}
