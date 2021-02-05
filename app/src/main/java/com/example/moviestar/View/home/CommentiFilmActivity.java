package com.example.moviestar.View.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.MostraDettagliFilmController;
import com.example.moviestar.Controllers.RecensioniFilmController;
import com.example.moviestar.DAO.FilmDAO;
import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class CommentiFilmActivity extends AppCompatActivity {
    RecyclerView commentiRecycler;
    static Film filmCliccato;
    static String URL;
    Context mContext=this;
    public List<Commento> commentoList;

    public static void setFilm(Film filmCliccato1) {
        filmCliccato=filmCliccato1;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        String activity_type=intent.getStringExtra("tipologia");
        String filmName = intent.getStringExtra("FilmName");
        String filmVoto = intent.getStringExtra("FilmVoto");
        String filmOverview = intent.getStringExtra("FilmOverview");
        String filmId = intent.getStringExtra("FilmId");
        String filmFotoPath=intent.getStringExtra("FilmPicPath");

        filmCliccato = new Film(filmId, filmName, filmFotoPath, filmVoto, filmOverview);
        MostraDettagliFilmController.openFilm(filmCliccato, mContext);
//        mContext.startActivity(new Intent(mContext, MainActivity.class));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentifilm);
        commentiRecycler=findViewById(R.id.commentifilm_recyclerview);
        Intent intent = getIntent();
        String filmName = intent.getStringExtra("FilmName");
        String filmVoto = intent.getStringExtra("FilmVoto");
        String filmOverview = intent.getStringExtra("FilmOverview");
        String filmId = intent.getStringExtra("FilmId");
        String filmFotoPath=intent.getStringExtra("FilmPicPath");

        filmCliccato = new Film(filmId, filmName, filmFotoPath, filmVoto, filmOverview);

        commentoList=new ArrayList<>();

        RecensioniFilmController.getListaCommentiFilm(filmCliccato, mContext, commentiRecycler);



    }


}
