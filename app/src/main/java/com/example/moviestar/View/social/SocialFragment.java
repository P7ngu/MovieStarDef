package com.example.moviestar.View.social;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.RichiesteAmicoController;
import com.example.moviestar.Controllers.PopupController;
import com.example.moviestar.Controllers.RicercaUtentiController;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.social.recycler.AdapteryUtente;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {
    static boolean flag=false;

    private SocialViewModel socialViewModel;
    private static RecyclerView recyclerView;
    private View root;
    private List<Utente> UtenteList;
    private static EditText searchbarET;
    public static Context mContext;
    private ImageButton deleteButton, addButton, searchButton;
    private boolean flag_reloaded;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        socialViewModel =
                ViewModelProviders.of(this).get(SocialViewModel.class);

        View root = inflater.inflate(R.layout.fragment_social, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);

        final RecyclerView recyclerView1= root.findViewById(R.id.recycler_view_social);
        recyclerView=recyclerView1;

        final EditText searchbar = root.findViewById(R.id.ET_searchbar_social);
        searchbarET=searchbar;


        //searchbutton

        searchButton = root.findViewById(R.id.search_user_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutDataIntoRecyclerView(new ArrayList<Utente>(), "ricerca");
                RicercaUtentiController.cercaAmicoByNome(searchbarET.getText().toString().trim());
                PutDataIntoRecyclerView(CurrentUser.getInstance().getListaUtenti(), "ricerca");
            }
        });


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
        View root = getView();
   

        UtenteList=new ArrayList<>();
        //if(!flag_reloaded) {
            try {
                CaricaRecyclerView caricaRecyclerView = new CaricaRecyclerView();
                caricaRecyclerView.execute();
                flag_reloaded=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}

    }


    public static void PutDataIntoRecyclerView(List<Utente> utenteList, String tipologia){
                AdapteryUtente adaptery = new AdapteryUtente(mContext, utenteList, tipologia);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(adaptery);
                adaptery.notifyDataSetChanged();

    }

    private class CaricaRecyclerView extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
               RichiesteAmicoController.getRichiesteAmico();
                UtenteList=CurrentUser.getInstance().getListaRichiesteAmico();
               // PopupController.mostraPopup("dede", UtenteList.get(0)+" ", mContext);
                PutDataIntoRecyclerView(UtenteList, "richieste");
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                RichiesteAmicoController.getRichiesteAmico();
                UtenteList=CurrentUser.getInstance().getListaRichiesteAmico();
               // PopupController.mostraPopup("dede", UtenteList.get(0)+" ", mContext);
                PutDataIntoRecyclerView(UtenteList, "richieste");
            } catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                UtenteList=CurrentUser.getInstance().getListaRichiesteAmico();
                //PopupController.mostraPopup("dede", UtenteList.get(0)+" ", mContext);
                PutDataIntoRecyclerView(UtenteList, "richieste");
            } catch(Exception e){
                e.printStackTrace();
            }


        }
    }

}