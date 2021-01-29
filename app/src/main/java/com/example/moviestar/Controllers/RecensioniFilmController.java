package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.DAO.RecensioneDAO;
import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Film;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.View.home.AggiungiCommentoActivity;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.RecyclerCommenti.AdapteryCommenti;
import com.example.moviestar.View.social.SocialFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecensioniFilmController {

    public static void onClickLeggiCommenti(String filmId, Context context, boolean isSpoiler){
        PopupController.mostraPopupDiConfermaOAnnulla("SPOILER ALERT", "I commenti potrebbero contenere spoiler, sei sicuro di voler procedere?"
        , context, "spoiler", "Commenti", filmId);
    }

    public static void onClickPositiveButton(String objectId, Context context){
        onClickLeggiCommenti(objectId, context);
    }

    public static void onClickNegativeButton(Context mContext) {

    }

    public static void onClickLeggiCommenti(String filmId, Context context){
        Intent intent=new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("filmCliccatoId", filmId);
        context.startActivity(intent);

    }

    public static void onClickLeggiCommenti(String filmId, Context context, RecyclerView recyclerView){
        Intent intent=new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("filmCliccatoId", filmId);
        context.startActivity(intent);

        getListaCommentiFilm(filmId, context, recyclerView);

    }

    public static void openListaCommentiDopoInserimentoCommento(String filmId, Context context){
        Intent intent = new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("filmCliccatoId", filmId);
        context.startActivity(intent);


    }

    public static void onClickAggiungiCommento(String idFilm, String filmName, String overview, Context mContext) {
        Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
        intent.putExtra("film", filmName);
        intent.putExtra("FilmName", filmName);
        intent.putExtra("FilmId", idFilm);
        intent.putExtra("FilmOverview", overview);
        mContext.startActivity(intent);
    }



    public static void inserisciCommentoFilm(String idFilmCommentato, String commentoDaInserire, Context mContext){
        if(commentoDaInserire.length()>0) {
          RecensioneDAO.inserisciCommentoFilm_Firebase(idFilmCommentato, commentoDaInserire, mContext);
        }
        else PopupController.mostraPopup("Errore", "Commento vuoto: inserire il testo del commento", mContext);
    }

    public static void getListaCommentiFilm(String idFilm, Context mContext, RecyclerView recyclerView){
        RecensioneDAO.getListaCommentiFilm_Firebase(idFilm, mContext, recyclerView);

    }
    private static void PutDataIntoRecyclerView(List<Commento> commentoList, RecyclerView commentiRecycler, Context mContext){
        AdapteryCommenti adaptery=new AdapteryCommenti(mContext, commentoList, commentiRecycler);
        commentiRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        commentiRecycler.setAdapter(adaptery);

    }
}
