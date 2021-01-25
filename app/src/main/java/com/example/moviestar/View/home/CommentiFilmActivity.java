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
        String filmID = intent.getStringExtra("filmCliccatoId");
        mContext.startActivity(new Intent(mContext, MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentifilm);
        commentiRecycler=findViewById(R.id.commentifilm_recyclerview);
        Intent intent = getIntent();
        String filmID = intent.getStringExtra("filmCliccatoId");

        commentoList=new ArrayList<>();

        RecensioniFilmController.getListaCommentiFilm(filmID, mContext, commentiRecycler);



    }


}
