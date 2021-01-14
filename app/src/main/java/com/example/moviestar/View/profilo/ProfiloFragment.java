package com.example.moviestar.View.profilo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.R;
import com.example.moviestar.View.login.RegistrazioneActivity;

import org.w3c.dom.Text;

import java.util.List;

public class ProfiloFragment extends Fragment {

    private ProfiloViewModel notificationsViewModel;
    static ImageButton editProfiloButton;
    static Button vediAmiciButton, vediFilmPreferitiButton, vediFilmVistiButton;
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

        final TextView usernameTV1=root.findViewById(R.id.textView_nomeutente_profilo);
        usernameTV=usernameTV1;
        usernameTV.setText(currentUsername);



        final Button vediAmici=root.findViewById(R.id.button_showallamici_profilo);
        vediAmiciButton=vediAmici;
        vediAmiciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(mContext, EditProfiloActivity.class);
                //startActivity(intent);
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

        mContext = container.getContext();

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}