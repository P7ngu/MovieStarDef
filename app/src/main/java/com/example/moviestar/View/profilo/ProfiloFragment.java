package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.R;

public class ProfiloFragment extends Fragment {

    private ProfiloViewModel notificationsViewModel;
    static ImageButton editProfiloButton;
    static Button vediAmiciButton, vediFilmPreferitiButton, vediFilmVistiButton, vediFilmDaVedereButton;
    static ImageView propicImg;
   Context mContext;
   TextView usernameTV;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ProfiloViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profilo, container, false);
        //final TextView textView = root.findViewById();
        String currentUserID= CurrentUser.getUserId();
        String currentUsername=CurrentUser.getUsername();
        mContext = container.getContext();

        final TextView usernameTV1=root.findViewById(R.id.textView_nomeutente_profilo);
        usernameTV=usernameTV1;
        usernameTV.setText(currentUsername);

        final ImageView propic=root.findViewById(R.id.imageView_utente_propic_profilo);
        propicImg=propic;
        Glide.with(mContext).load("https://i.ibb.co/QmmFv5T/batman-hero-avatar-comics-512.png").into(propicImg);



        final Button vediAmici=root.findViewById(R.id.button_showallamici_profilo);
        vediAmiciButton=vediAmici;
        vediAmiciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListaAmiciActivity.class);
                intent.putExtra("tipologia", "listaAmici");
                startActivity(intent);
               // ListaAmiciActivity.PutDataIntoRecyclerView(CurrentUser.getInstance().getListaAmici());
            }
        });


        final Button vediFilmPreferiti=root.findViewById(R.id.button_filmpref);
        vediFilmPreferitiButton=vediFilmPreferiti;
        vediFilmPreferitiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListaFilmActivity.class);
                intent.putExtra("tipologia", "filmpreferiti");
                mContext.startActivity(intent);
            }
        });

        final Button daVedere=root.findViewById(R.id.button_showallfilmdavedere_profilo);
        vediFilmDaVedereButton=daVedere;
        vediFilmDaVedereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListaFilmActivity.class);
                intent.putExtra("tipologia", "filmdavedere");
                mContext.startActivity(intent);
            }
        });




        final Button vediFilmVisti=root.findViewById(R.id.button_showallfilmvisti_profilo2);
        vediFilmVistiButton=vediFilmVisti;
        vediFilmVistiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ListaFilmActivity.class);
                intent.putExtra("tipologia", "filmvisti");
                mContext.startActivity(intent);
            }
        });

        final ImageButton editProfilo1 = root.findViewById(R.id.imageButton_editprofilo_profilo);
        editProfiloButton=editProfilo1;

        editProfiloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditProfiloActivity.class);
                startActivity(intent);
            }
        });



        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}