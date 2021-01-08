package com.example.moviestar.View.home;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.TmdbAPIController;
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
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    static RecyclerView recyclerView;
    View root;
    List<Film> movieList;
    Context mContext;
    static ImageButton searchButton;
    String paroleCercate;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

       final RecyclerView recyclerView1= root.findViewById(R.id.recycler_view);
       recyclerView=recyclerView1;
       final ImageButton searchButton1 = root.findViewById(R.id.imageButton_search_home);
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
                    GetData getData1 = new GetData(URL_ForSearching);
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
            GetData getData = new GetData(urlJSON);
            getData.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }



    public class GetData extends AsyncTask<String, String, String> {
        String URL1;

        public GetData(String url_forSearching) throws UnsupportedEncodingException {
            String ulr_updated = formattaStringaPerQuery(url_forSearching);
            this.URL1=ulr_updated;
        }

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(URL1);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }



            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    Film model = new Film();
                    model.setVote(jsonObject1.getString("vote_average"));
                    model.setId(jsonObject1.getString("id"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setOverview(jsonObject1.getString("overview"));
                    movieList.add(model);

                }


            } catch (Exception e){
                e.printStackTrace();
            }


            PutDataIntoRecyclerView(movieList);
        }



    }

    private String formattaStringaPerQuery(String url_forSearching) throws UnsupportedEncodingException {
        String result = url_forSearching.replaceAll(" ","%20");
        return result;
    }

    private void PutDataIntoRecyclerView(List<Film> movieList){
        Adaptery adaptery=new Adaptery(mContext, movieList);
        Log.d("Test", ""+mContext.toString()+recyclerView.toString());
        Log.d("Test1", recyclerView.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(adaptery);

    }
}