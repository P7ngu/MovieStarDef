package com.example.moviestar.View.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.EffettuaRicercheController;
import com.example.moviestar.Controllers.TrendingFilmController;
import com.example.moviestar.Model.Film;
import com.example.moviestar.R;
import com.example.moviestar.View.MainActivity;
import com.example.moviestar.View.home.Recycler.Adaptery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.moviestar.View.MainActivity.hideProgressBar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    static RecyclerView recyclerView;
    static ProgressBar progBar;
    View root;
    List<Film> movieList;
    static Context mContext;
    static ImageButton searchButton;
    String paroleCercate;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

       final RecyclerView recyclerView1= root.findViewById(R.id.recycler_view);
       recyclerView=recyclerView1;


       final ImageButton searchButton1 = root.findViewById(R.id.search_user_button);
       searchButton=searchButton1;

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL_ForSearching = "";
                movieList.clear();
                EditText searchbarET = root.findViewById(R.id.ET_searchbar_home);
                paroleCercate = searchbarET.getText().toString().trim();
                URL_ForSearching = "https://api.themoviedb.org/3/search/movie?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT&query=" + paroleCercate;
                Log.i("Test", URL_ForSearching);
                try {
                    EffettuaRicercheController.GetData getData1 = new EffettuaRicercheController.GetData(URL_ForSearching, movieList);
                    getData1.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });


        mContext = container.getContext();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       String urlJSON="https://api.themoviedb.org/3/discover/movie?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT&sort_by=popularity.desc";
        movieList=new ArrayList<>();
        try {
            TrendingFilmController.GetData getData = new TrendingFilmController.GetData(urlJSON, movieList);
            getData.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }




    public static void PutDataIntoRecyclerView(List<Film> movieList){
        Adaptery adaptery=new Adaptery(mContext, movieList);
        Log.d("Test", ""+mContext.toString()+recyclerView.toString());
        Log.d("Test1", recyclerView.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adaptery);


    }




    private String formattaStringaPerQuery(String url_forSearching) throws UnsupportedEncodingException {
        String result = url_forSearching.replaceAll(" ","%20");
        return result;
    }


}