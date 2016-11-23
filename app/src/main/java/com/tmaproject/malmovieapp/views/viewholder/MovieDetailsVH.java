package com.tmaproject.malmovieapp.views.viewholder;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TagLayout;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Genre;
import com.tmaproject.malmovieapp.models.networking.Movie;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by TarekMA on 10/30/16.
 */

public class MovieDetailsVH extends BaseViewHolder<Movie> {
    public static final int layout = R.layout.item_movie_details;
    private ImageView posterIV, backgroundIV;
    private TextView tagline, yearTV, rateingTV, durationTV;
    private FlowTextView overviewFTV;
    private FloatingActionButton fab;
    private TagLayout genresLayout;

    private boolean incompleteData = false;
    public MovieDetailsVH(View v) {
        super(v);
        posterIV = ((ImageView) v.findViewById(R.id.movie_poster));
        backgroundIV = ((ImageView) v.findViewById(R.id.background_image));
        yearTV = ((TextView) v.findViewById(R.id.movie_year));
        rateingTV = ((TextView) v.findViewById(R.id.movie_rating));
        durationTV = ((TextView) v.findViewById(R.id.movie_duration));
        overviewFTV = (FlowTextView) v.findViewById(R.id.movie_plot);
        genresLayout = (TagLayout) v.findViewById(R.id.movie_genres);
        tagline = ((TextView) v.findViewById(R.id.movie_tagline));


    }

    @Override
    public void bind(Movie movie) {
        incompleteData = movie.getRuntime()==null;

        overviewFTV.setTextColor(itemView.getContext().getResources().getColor(android.R.color.secondary_text_light));
        overviewFTV.setTextSize(40);

        rateingTV.setText(movie.getVoteAverage() + "/10");
        yearTV.setText("(" + movie.getReleaseDate().split("-")[0] + ")");
        overviewFTV.setText(movie.getOverview());

        Picasso.with(itemView.getContext())
                .load(TheMoviedbAPI.API_IMAGE_185 + movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(posterIV);

        LayoutInflater layoutInflater = LayoutInflater.from(itemView.getContext());

        if (incompleteData == false) durationTV.setText(movie.getRuntime() + " min");
        if (movie.getGenres().isEmpty() == false && incompleteData == false){
            incompleteData = true;
            genresLayout.addView(layoutInflater.inflate(R.layout.item_genre_header, null, false));
            for (Genre genre : movie.getGenres()) {
                View genreView = layoutInflater.inflate(R.layout.item_genre, null, false);
                TextView tagTextView = (TextView) genreView.findViewById(R.id.genre_item_text);
                tagTextView.setText(genre.getName());
                genresLayout.addView(genreView);
                tagline.setText("“ " + movie.getTagline() + " ”");
            }
        }

    }

}
