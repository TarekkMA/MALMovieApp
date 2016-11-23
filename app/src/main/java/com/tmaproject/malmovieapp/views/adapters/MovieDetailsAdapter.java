package com.tmaproject.malmovieapp.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.viewholder.BaseViewHolder;
import com.tmaproject.malmovieapp.views.viewholder.MovieDetailsVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieImageVH;
import com.tmaproject.malmovieapp.views.viewholder.MovieVideosVH;

import java.util.List;

/**
 * Created by tarekkma on 10/21/16.
 */

public class MovieDetailsAdapter extends RecyclerView.Adapter<BaseViewHolder<Movie>> {

    public static final int POSTER = 835;
    public static final int BACKDROP = 396;

    List<Integer> viewsOrder;
    Movie movie;

    public MovieDetailsAdapter(List<Integer> viewsOrder, Movie movie) {
        this.viewsOrder = viewsOrder;
        this.movie = movie;
    }

    public void refreshData(Movie movie){
        this.movie = movie;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder<Movie> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_movie_details:
                return new MovieDetailsVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_details,parent,false));
            case R.layout.item_movie_video:
                return new MovieVideosVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_horizontal_list,parent,false));
            case POSTER:
                return new MovieImageVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_horizontal_list,parent,false),true);
            case BACKDROP:
                return new MovieImageVH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_horizontal_list,parent,false),false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<Movie> holder, int position) {
       holder.bind(movie);
    }


    @Override
    public int getItemCount() {
        return viewsOrder.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewsOrder.get(position);
    }


}
