package com.example.moviestar.View.social.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviestar.Controllers.MostraDettagliFilmController;
import com.example.moviestar.Model.Film;
import com.example.moviestar.Model.Utente;
import com.example.moviestar.R;
import com.example.moviestar.View.home.Recycler.Adaptery;
import com.example.moviestar.View.profilo.ProfiloFragment;
import com.example.moviestar.View.social.SocialFragment;

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
        if(tipologiaSchermata.equals("social"))
        v = View.inflate(mContext, R.layout.utente_item, null);
        else
            v=View.inflate(mContext, R.layout.amico_item, null);

        return new AdapteryUtente.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapteryUtente.MyViewHolder holder, int position) {

        holder.nomeutentemostrato.setText(mData.get(position).getNomeUtenteMostrato());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
//            vote = itemView.findViewById(R.id.vote_text);
//            name = itemView.findViewById(R.id.name_text);
//            img = itemView.findViewById(R.id.imageView_film);

            nomeutentemostrato = itemView.findViewById(R.id.username_text);
        }
    }
}
