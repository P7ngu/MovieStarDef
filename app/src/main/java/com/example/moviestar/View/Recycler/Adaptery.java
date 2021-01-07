package com.example.moviestar.View.Recycler;

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
import com.example.moviestar.R;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder>{
    private static Context mContext;
    private static List<Film> mData;

    public Adaptery(Context mContext, List<Film> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //v=inflater.inflate(R.layout.movie_item, parent, false);
        v = View.inflate(mContext, R.layout.film_item, null);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.vote.setText(mData.get(position).getVote());
        holder.name.setText((mData.get(position).getName()));

        //Glide for the image + https://image.tmdb.org/t/p/w500 + poster_path
        try {
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + mData.get(position).getImg()).into(holder.img);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView vote, name;
        ImageView img;


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("Test", "Clicked " + mData.get(pos).toString());
            MostraDettagliFilmController.openFilm(mData.get(pos), mContext);
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            vote = itemView.findViewById(R.id.vote_text);
            name = itemView.findViewById(R.id.name_text);
            img = itemView.findViewById(R.id.imageView_film);


        }
    }
}