package com.example.moviestar.View.social;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.social.recycler.AdapteryUtente;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {

    private SocialViewModel socialViewModel;
    private static RecyclerView recyclerView;
    private View root;
    private List<Utente> UtenteList;
    private Context mContext;
    private ImageButton deleteButton, addButton, searchButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        socialViewModel =
                ViewModelProviders.of(this).get(SocialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_social, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        //recycleview
        final RecyclerView recyclerView1= root.findViewById(R.id.recycler_view);
        recyclerView=recyclerView1;

        //searchbutton
        searchButton = root.findViewById(R.id.search_user_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RICERCA UTENTI
            }
        });

        mContext = container.getContext(); //CONTEXT

        socialViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AUTOFILL ON CREATE
//        String urlJSON="https://api.themoviedb.org/3/discover/movie?api_key=89d40cd46523243c6d553bb54b2ca47e&language=it-IT&sort_by=popularity.desc";
        UtenteList=new ArrayList<>();
        try {
            GetData getData = new GetData("");
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
            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            try{
                //ResultSet temporaryUsersRS= UtenteDAO1.getUtentiFromDB();
                //while(temporaryUsersRS.next()){
                  //  Utente userTemp=new Utente(temporaryUsersRS.getString(1), temporaryUsersRS.getString(2));
                    //UtenteList.add(userTemp);
                //}
                Utente u1 = new Utente("user1", "password");
                u1.setNomeUtenteMostrato("user1");
                Utente u2 = new Utente("user2", "password");
                u2.setNomeUtenteMostrato("user2");
                Utente u3 = new Utente("user3", "password");
                u3.setNomeUtenteMostrato("user3");
                Utente u4 = new Utente("user4", "password");
                u4.setNomeUtenteMostrato("user4");
                Utente u5 = new Utente("user5", "password");
                u5.setNomeUtenteMostrato("user5");
                Utente u6 = new Utente("user6", "password");
                u6.setNomeUtenteMostrato("user6");
                Utente u7 = new Utente("user7", "password");
                u7.setNomeUtenteMostrato("user7");
                Utente u8 = new Utente("user8", "password");
                u8.setNomeUtenteMostrato("user8");
                UtenteList.add(u1);
                UtenteList.add(u2);
                UtenteList.add(u3);
                UtenteList.add(u4);
                UtenteList.add(u5);
                UtenteList.add(u6);
                UtenteList.add(u7);
                UtenteList.add(u8);
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray=jsonObject.getJSONArray("results");
//                for(int i=0; i<jsonArray.length(); i++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    Film model = new Film();
//                    model.setVote(jsonObject1.getString("vote_average"));
//                    model.setId(jsonObject1.getString("id"));
//                    model.setName(jsonObject1.getString("title"));
//                    model.setImg(jsonObject1.getString("poster_path"));
//                    model.setOverview(jsonObject1.getString("overview"));
//                    movieList.add(model);
//                }
            } catch (Exception e){
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(UtenteList);
        }
    }

    private void PutDataIntoRecyclerView(List<Utente> utenteList){
        AdapteryUtente adaptery =new AdapteryUtente(mContext, utenteList, "social");
//        Log.d("TestU", ""+mContext.toString()+recyclerView.toString());
//        Log.d("TestU1", recyclerView.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(adaptery);

    }


    private String formattaStringaPerQuery(String url_forSearching) throws UnsupportedEncodingException {
        String result = url_forSearching.replaceAll(" ","%20");
        return result;
    }
}