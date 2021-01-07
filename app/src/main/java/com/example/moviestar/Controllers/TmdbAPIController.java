package com.example.moviestar.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.Recycler.Adaptery;
import com.example.moviestar.View.RegistrazioneActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TmdbAPIController {
    static List<Film> movieList;


    static Context mContext;
    static RecyclerView recyclerView;

    private static String urlJSON = "https://api.themoviedb.org/3/discover/movie?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT&sort_by=popularity.desc";

    public static void metodo(String paroleCercate, Context context, List<Film> movieList1) {
    }
}


