package com.tmaproject.malmovieapp.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.activities.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 10/21/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.VH> {

    List<Movie> moviesList =  new ArrayList<>();

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_movie,parent,false));
    }

    public void addPage(List<Movie> newList){
        int oldLastIndex = moviesList.size()-1;
        moviesList.addAll(newList);
        notifyItemRangeChanged(oldLastIndex,moviesList.size()-1);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    class VH extends RecyclerView.ViewHolder{
        ImageView posterIV;
        public VH(View itemView) {
            super(itemView);
            posterIV = (ImageView)itemView.findViewById(R.id.movie_item_poster);
        }
        public void bind(final Movie movie){
            Picasso.with(itemView.getContext())
                    .load(TheMoviedbAPI.API_IMAGE_185 +movie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .into(posterIV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    context.startActivity(new Intent(context, MovieDetailsActivity.class)
                    .putExtra(MovieDetailsActivity.ARG_MOVIE_JSON,new Gson().toJson(movie,Movie.class)));
                }
            });
        }
    }
}
