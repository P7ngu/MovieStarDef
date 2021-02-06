package com.example.moviestar.View.social.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.CurrentUser;
import com.example.moviestar.Controllers.RichiesteAmicoController;
import com.example.moviestar.Controllers.RimuoviAmicoController;
import com.example.moviestar.Controllers.RispondiRichiestaAmicoController;
import com.example.moviestar.DAO.UtenteDAO;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;

import java.util.ArrayList;
import java.util.List;

public class AdapteryUtente extends RecyclerView.Adapter<AdapteryUtente.MyViewHolder>{
    private static Context mContext;
    private static List<Utente> mData;
    private static String tipologiaSchermata;

    public AdapteryUtente(Context mContext, List<Utente> mData, String tipologiaSchermata) {
        this.mContext = mContext;
        this.mData = mData;
        this.tipologiaSchermata=tipologiaSchermata;
    }

    @NonNull
    @Override
    public AdapteryUtente.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //v=inflater.inflate(R.layout.movie_item, parent, false);
        if(tipologiaSchermata.equals("ricerca") || tipologiaSchermata.equals("richieste"))
        v = View.inflate(mContext, R.layout.utente_item, null);
        else
            v=View.inflate(mContext, R.layout.amico_item, null);

        return new AdapteryUtente.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapteryUtente.MyViewHolder holder, int position) {
        ArrayList<Utente> tempList = new ArrayList<>();
        try {
            UtenteDAO.getUtentiByID(mData.get(position).getIdUtente());
        } finally {
           if(tipologiaSchermata.equals("ricerca")|| tipologiaSchermata.equals("richieste"))
               tempList = CurrentUser.getInstance().getListaUtenti();
           else {
               tempList=CurrentUser.getInstance().getListaAmici();

           }
        }

        holder.nomeutentemostrato.setText(mData.get(position).getNomeUtenteMostrato()+" #"+mData.get(position).getIdUtente());

        if(tempList!=null && tempList.size()>0) {
            //String nomeUtente= UtenteDAO.getNameUserById(mData.get(position).getIdUtente());
            //holder.nomeutentemostrato.setText(nomeUtente+" #"+mData.get(position).getIdUtente());
            Glide.with(mContext).load("https://i.ibb.co/7tbJ1Sv/user.png").into(holder.img);
        }


    }

    @Override
    public int getItemCount() {
       if(mData!=null) return mData.size();
       else return 0;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public void removeAt(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mData.size());
        }


        TextView vote, name, nomeutentemostrato;
        ImageView img;


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("Test", "Clicked " + mData.get(pos).toString());
//            MostraDettagliFilmController.openFilm(mData.get(pos), mContext);
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ImageButton addToListaAmiciButton = itemView.findViewById(R.id.add_imageButton);
            if(tipologiaSchermata.equals("ricerca") || tipologiaSchermata.equals("richieste")) addToListaAmiciButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(tipologiaSchermata.equals("ricerca"))  RichiesteAmicoController.sendRichiestaAmico
                          (mData.get(getAdapterPosition()).getIdUtente().toString(), mContext);
                  else RispondiRichiestaAmicoController.accettaRichiestaAmico(mData.get(getAdapterPosition()).getIdUtente().toString(), mContext);
                }
            });

            ImageButton  respingiRichiestaAmicoButton = itemView.findViewById(R.id.delete_imageButton);
            if(tipologiaSchermata.equals("ricerca") || tipologiaSchermata.equals("richieste"))
                respingiRichiestaAmicoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tipologiaSchermata.equals("ricerca"))  RispondiRichiestaAmicoController.respingiRichiestaAmico
                            (mData.get(getAdapterPosition()).getIdUtente().toString(), mContext);
                    else if(tipologiaSchermata.equals("richieste")) RispondiRichiestaAmicoController.respingiRichiestaAmico
                            (mData.get(getAdapterPosition()).getIdUtente().toString(), mContext);
                    else {
                        RimuoviAmicoController.eliminaAmicoDaListaAmici
                                (mData.get(getAdapterPosition()).getIdUtente().toString(), mContext);
                       removeAt(getAdapterPosition());
                    }
                }
            });
            else respingiRichiestaAmicoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RimuoviAmicoController.eliminaAmicoDaListaAmici
                            (mData.get(getAdapterPosition()).getIdUtente().toString(), mContext);
                }
            });

            nomeutentemostrato = itemView.findViewById(R.id.username_text);
            img = itemView.findViewById(R.id.imageView_film);

        }
    }
}
