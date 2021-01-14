package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.home.AggiungiCommentoActivity;
import com.example.moviestar.View.home.CommentiFilmActivity;
import com.example.moviestar.View.home.MostraDettagliFilmActivity;

public class MostraDettagliFilmController {
    public static void openFilm(Film filmCliccato, Context context){
        Intent intent=new Intent(context, MostraDettagliFilmActivity.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        context.startActivity(intent);
    }

    public static void onClickLeggiCommenti(String filmId, Context context){
        Intent intent=new Intent(context, CommentiFilmActivity.class);
        intent.putExtra("filmCliccatoId", filmId);
        context.startActivity(intent);

    }


    public static void onClickAggiungiCommento(String filmName, Context mContext) {
        Intent intent = new Intent(mContext, AggiungiCommentoActivity.class);
        intent.putExtra("film", filmName);
        mContext.startActivity(intent);
    }
}
