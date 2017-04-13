package com.tmaproject.malmovieapp.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tmaproject.malmovieapp.MyApp;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter;
import com.tmaproject.malmovieapp.views.fragments.MainFragment;
import com.tmaproject.malmovieapp.views.fragments.MovieDetailsFragment;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter.BACKDROP;
import static com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter.POSTER;
import static com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter.REVIEW;
import static com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter.REVIEWS_HEADER;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";
    public static final String ARG_MOVIE = "MOVIE<<LML<TMA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(ARG_MOVIE));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, MovieDetailsFragment.newInstance(movie))
                .commit();
    }


}
