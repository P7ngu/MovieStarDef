package com.example.moviestar.View.home.RecyclerCommenti;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviestar.Model.Commento;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;

import java.util.List;

public class AdapteryCommenti extends RecyclerView.Adapter<AdapteryCommenti.MyViewHolder>{
    private static Context mContext;
    private static List<Commento> mData;

    public AdapteryCommenti(Context mContext, List<Commento> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdapteryCommenti.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //v=inflater.inflate(R.layout.movie_item, parent, false);
        v = View.inflate(mContext, R.layout.commento_item, null);

        return new AdapteryCommenti.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapteryCommenti.MyViewHolder holder, int position) {

        holder.nomeutentemostrato.setText(mData.get(position).getNomeAutore());
        holder.testo.setText(mData.get(position).getTesto());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView vote, testo, nomeutentemostrato;
        ImageView img;


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
//            MostraDettagliFilmController.openFilm(mData.get(pos), mContext);
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            testo=itemView.findViewById(R.id.testo_text);
            nomeutentemostrato=itemView.findViewById(R.id.nome_text);
//            vote = itemView.findViewById(R.id.vote_text);
//            name = itemView.findViewById(R.id.name_text);
//            img = itemView.findViewById(R.id.imageView_film);

        }
    }
}