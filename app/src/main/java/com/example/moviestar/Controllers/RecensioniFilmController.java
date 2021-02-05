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

    public static void onClickLeggiCommenti(Film filmCliccato, Context context, boolean isSpoiler){
        PopupController.mostraPopupDiConfermaOAnnulla("SPOILER ALERT", "I commenti potrebbero contenere spoiler, sei sicuro di voler procedere?"
        , context, "spoiler", "Commenti", filmCliccato.getId(), filmCliccato);
    }

//    public static void onClickPositiveButton(String objectId, Context context){
//        onClickLeggiCommenti(objectId, context);
//    }

    public static void onClickPositiveButton(Film filmCliccato, Context context){
        onClickLeggiCommenti(filmCliccato, context);
    }

    public static void onClickNegativeButton(Context mContext) {

    }

    public static void onClickLeggiCommenti(Film filmCliccato, Context context){
        Intent intent=new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        intent.putExtra("FilmId", filmCliccato.getId());
        intent.putExtra("FilmPicPath", filmCliccato.getImg());
        context.startActivity(intent);

    }

    public static void onClickLeggiCommenti(Film filmCliccato, Context context, RecyclerView recyclerView){
        Intent intent=new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        intent.putExtra("FilmId", filmCliccato.getId());
        intent.putExtra("FilmPicPath", filmCliccato.getImg());
        context.startActivity(intent);

        getListaCommentiFilm(filmCliccato, context, recyclerView);

    }

    public static void openListaCommentiDopoInserimentoCommento(Film filmCliccato, Context context){
        Intent intent = new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        intent.putExtra("FilmId", filmCliccato.getId());
        intent.putExtra("FilmPicPath", filmCliccato.getImg());
        context.startActivity(intent);


    }

    public static void onClickAggiungiCommento(Film filmCliccato, Context mContext) {
        Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        intent.putExtra("FilmId", filmCliccato.getId());
        intent.putExtra("FilmPicPath", filmCliccato.getImg());
        mContext.startActivity(intent);
    }



    public static void inserisciCommentoFilm(Film filmCliccato, String commentoDaInserire, Context mContext){
        if(commentoDaInserire.length()>0) {
            Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
            intent.putExtra("FilmName", filmCliccato.getName());
            intent.putExtra("FilmVoto", filmCliccato.getVote());
            intent.putExtra("FilmOverview", filmCliccato.getOverview());
            intent.putExtra("FilmId", filmCliccato.getId());
            intent.putExtra("FilmPicPath", filmCliccato.getImg());
          RecensioneDAO.inserisciCommentoFilm_Firebase(filmCliccato, commentoDaInserire, mContext);
        }
        else PopupController.mostraPopup("Errore", "Commento vuoto: inserire il testo del commento", mContext);
    }

    public static void getListaCommentiFilm(Film filmCliccato, Context mContext, RecyclerView recyclerView){
        RecensioneDAO.getListaCommentiFilm_Firebase(filmCliccato, mContext, recyclerView);

    }
    private static void PutDataIntoRecyclerView(Film filmCliccato, List<Commento> commentoList, RecyclerView commentiRecycler, Context mContext){
        AdapteryCommenti adaptery=new AdapteryCommenti(filmCliccato, mContext, commentoList, commentiRecycler);
        commentiRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        commentiRecycler.setAdapter(adaptery);

    }
}
