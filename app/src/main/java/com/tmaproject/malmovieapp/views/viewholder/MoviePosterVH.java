package com.tmaproject.malmovieapp.views.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.snappydb.SnappydbException;
import com.squareup.picasso.Picasso;
import com.tmaproject.malmovieapp.MyApp;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.database.MovieProvider;
import com.tmaproject.malmovieapp.logic.MovieProviderUtil;
import com.tmaproject.malmovieapp.logic.ResponsiveUi;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.events.MovieSelectedEvent;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.activities.MovieDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcel;
import org.parceler.Parcels;

/**
 * Created by TarekMA on 11/29/16.
 * facebook/tarekkma1
 */

public class MoviePosterVH extends BaseViewHolder {
    ImageView posterIV;
    ImageView favoriteStarIV;

    public MoviePosterVH(View itemView) {
        super(itemView);
        posterIV = (ImageView) itemView.findViewById(R.id.movie_item_poster);
        favoriteStarIV = (ImageView) itemView.findViewById(R.id.movieFavoriteStar);
    }

    @Override
    public void bind(Object data) {
        Movie movie = (Movie) data;
        boolean isFavorite = false;
        try {
            isFavorite = MovieProviderUtil.isMovieFavorite(itemView.getContext(),movie.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        favoriteStarIV.setVisibility((isFavorite) ? View.VISIBLE : View.GONE);

        Picasso.with(itemView.getContext())
                .load(TheMoviedbAPI.API_IMAGE_185 + movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(posterIV);

        itemView.setOnClickListener(v -> {
            if(ResponsiveUi.isTablet(itemView.getContext())){
                EventBus.getDefault().post(new MovieSelectedEvent(movie));
            }else {
                Context context = itemView.getContext();
                context.startActivity(new Intent(context, MovieDetailsActivity.class)
                        .putExtra(MovieDetailsActivity.ARG_MOVIE, Parcels.wrap(movie)));
            }
        });
    }
}
