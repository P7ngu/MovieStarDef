package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;

import com.example.moviestar.Model.Film;
import com.example.moviestar.View.MostraDettagliFilm;

public class MostraDettagliFilmController {
    public static void openFilm(Film filmCliccato, Context context){
        Intent intent=new Intent(context, MostraDettagliFilm.class);
        intent.putExtra("FilmName", filmCliccato.getName());
        intent.putExtra("FilmVoto", filmCliccato.getVote());
        intent.putExtra("FilmOverview", filmCliccato.getOverview());
        context.startActivity(intent);
    }
}
