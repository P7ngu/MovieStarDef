package com.example.moviestar.View.profilo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.LoginController;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.social.SocialFragment;
import com.example.moviestar.View.social.recycler.AdapteryUtente;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ListaAmiciActivity extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private View root;
    private List<Utente> UtenteList;
    static Context mContext;
    private ImageButton deleteButton, addButton, searchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.listaamici);
        LoginController.loadListaAmiciFromDB();

        recyclerView=findViewById(R.id.recycler_view_listaamici);

        //searchbutton
        searchButton = findViewById(R.id.search_user_button_listaamici);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RICERCA UTENTI
            }
        });
        try {
            UtenteList = new ArrayList<>();
            LoginController.loadListaAmiciFromDB();
            PutDataIntoRecyclerView(CurrentUser.getInstance().getListaAmici());
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void PutDataIntoRecyclerView(List<Utente> utenteList){
        AdapteryUtente adaptery =new AdapteryUtente(mContext, utenteList, "listaamici");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adaptery);

    }


    private String formattaStringaPerQuery(String url_forSearching) throws UnsupportedEncodingException {
        String result = url_forSearching.replaceAll(" ","%20");
        return result;
    }
}
