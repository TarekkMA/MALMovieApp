package com.tmaproject.malmovieapp.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
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
import com.snappydb.SnappydbException;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tmaproject.malmovieapp.MyApp;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

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
    public static final String ARG_MOVIE_JSON = "MOVIE<<LML<TMA";

    private Movie movie;
    private boolean isFavorite =false;

    //This is not the normal CollapsingToolbarLayout
    //see https://github.com/opacapp/multiline-collapsingtoolbar
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private ImageView backgroundIV;
    private FloatingActionButton fab;

    private RecyclerView mainList;
    private MovieDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movie = new Gson().fromJson(getIntent().getStringExtra(ARG_MOVIE_JSON), Movie.class);
        List<Integer> viewOrder = new ArrayList<>();
        viewOrder.add(R.layout.item_movie_details);
        adapter = new MovieDetailsAdapter(viewOrder, movie);

        try {
            isFavorite = MyApp.getInstance().getDBManager().getFavoritesDB().exists(movie.getId().toString());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }


        bindViews();
        setupViews();
        if(movie.getGenres().isEmpty()){//A way to know if the movie has all the data
            fillViews(false);
            getDatafromServer();
        }else{
            fillViews(true);
        }

    }

    private void bindViews() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        backgroundIV = ((ImageView) findViewById(R.id.background_image));

        //
        mainList = (RecyclerView) findViewById(R.id.movie_details_list);
    }

    private void setupViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainList.setLayoutManager(layoutManager);
        mainList.setAdapter(adapter);
        //mainList.setNestedScrollingEnabled(false);
        //mainList.smoothScrollToPosition(0);
        //layoutManager.scrollToPositionWithOffset(0,0);

        fab.setImageResource((isFavorite)?R.drawable.star_filled_96:R.drawable.star_unfilled_96);
        fab.setOnClickListener(view -> {
            try {
                if (isFavorite) {
                    Snackbar.make(view, "\'" + "Movie" + "\' has been removed from the favorites",Snackbar.LENGTH_LONG).show();
                    MyApp.getInstance().getDBManager().getFavoritesDB().del(movie.getId().toString());
                } else {
                    Snackbar.make(view, "\'" + "Movie" + "\' has been added to the favorites",Snackbar.LENGTH_LONG).show();
                    MyApp.getInstance().getDBManager().getFavoritesDB().put(movie.getId().toString(), movie);
                }
                isFavorite = !isFavorite;
                fab.setImageResource((isFavorite)?R.drawable.star_filled_96:R.drawable.star_unfilled_96);
            } catch (SnappydbException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed adding \'" + "the movie" + "\' to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
    }

    private void getDatafromServer() {
        TheMoviedbAPI.createService().getMovieDetails(movie.getId()).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    movie = response.body();
                    fillViews(true);
                } else {
                    Log.e(TAG, "onResponse: " + response.code() + ": RequestFailed");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to reach server", t);
            }
        });
    }

    private void fillViews(boolean extendedInfo) {
        collapsingToolbarLayout.setTitle(movie.getTitle());

        if (extendedInfo) {
            adapter.fulfilData(movie);
            Log.d(TAG, "fillViews: Hey adapter view more data");
        }

        final ImageView backgroundIV = this.backgroundIV;
        final Target target = new Target() { //picasso
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getMutedSwatch();

                        int color, colorDarker, colorText;
                        if (swatch == null) {
                            color = getResources().getColor(R.color.colorPrimary);
                            colorDarker = getResources().getColor(R.color.colorPrimaryDark);
                            colorText = Color.WHITE;
                        } else {
                            color = swatch.getRgb();

                            float[] hslColor = swatch.getHsl();
                            hslColor[2] -= .025f;
                            colorDarker = Color.HSVToColor(hslColor);

                            colorText = swatch.getTitleTextColor();
                        }


                        collapsingToolbarLayout.setContentScrimColor(color);
                        collapsingToolbarLayout.setStatusBarScrimColor(colorDarker);
                        collapsingToolbarLayout.setCollapsedTitleTextColor(colorText);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(colorDarker);
                        }
                    }
                });
                backgroundIV.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

                backgroundIV.setImageResource(R.drawable.placeholder);
            }
        };
        Picasso.with(this)
                .load(TheMoviedbAPI.API_IMAGE_500 + movie.getBackdropPath())
                .into(target);

    }

}
