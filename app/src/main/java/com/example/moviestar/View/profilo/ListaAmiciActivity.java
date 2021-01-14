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
    private Context mContext;
    private ImageButton deleteButton, addButton, searchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaamici);
        mContext=this;

        recyclerView=findViewById(R.id.recycler_view_listaamici);

        //searchbutton
        searchButton = findViewById(R.id.search_user_button_listaamici);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RICERCA UTENTI
            }
        });
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

            } catch (Exception e){
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(UtenteList);
        }
    }

    private void PutDataIntoRecyclerView(List<Utente> utenteList){
        AdapteryUtente adaptery =new AdapteryUtente(mContext, utenteList, "listaamici");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(adaptery);

    }


    private String formattaStringaPerQuery(String url_forSearching) throws UnsupportedEncodingException {
        String result = url_forSearching.replaceAll(" ","%20");
        return result;
    }
}
