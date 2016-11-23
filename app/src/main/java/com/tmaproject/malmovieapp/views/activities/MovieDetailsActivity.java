package com.tmaproject.malmovieapp.views.activities;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.TagLayout;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.flowtextview.FlowTextView;

import static com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter.BACKDROP;
import static com.tmaproject.malmovieapp.views.adapters.MovieDetailsAdapter.POSTER;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MovieDetailsActivity";
    public static final String ARG_MOVIE_JSON = "MOVIE<<LML<TMA";

    private Movie movie;

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

        movie = new Gson().fromJson(getIntent().getStringExtra(ARG_MOVIE_JSON),Movie.class);
        adapter = new MovieDetailsAdapter(Arrays.asList(
                R.layout.item_movie_details,
                R.layout.item_movie_video,
                POSTER,
                BACKDROP
        ),movie);


        bindViews();
        setupViews();
        fillViews(false);
        getDatafromServer();

    }

    private void bindViews(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        backgroundIV = ((ImageView)findViewById(R.id.background_image));

        //
        mainList = (RecyclerView)findViewById(R.id.movie_details_list);
    }

    private void setupViews(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainList.setLayoutManager(layoutManager);
        mainList.setAdapter(adapter);
        //mainList.setNestedScrollingEnabled(false);
        //mainList.smoothScrollToPosition(0);
        //layoutManager.scrollToPositionWithOffset(0,0);

        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
    }

    private void getDatafromServer(){
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

    private void fillViews(boolean extendedInfo){
        collapsingToolbarLayout.setTitle(movie.getTitle());

        if(extendedInfo){
            adapter.refreshData(movie);
            Log.d(TAG, "fillViews: Hey adapter view more data");
        }

        Picasso.with(this)
                .load(TheMoviedbAPI.API_IMAGE_500+movie.getBackdropPath())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch swatch = palette.getMutedSwatch();

                                int color,colorDarker,colorText;
                                if(swatch == null){
                                    color = getResources().getColor(R.color.colorPrimary);
                                    colorDarker = getResources().getColor(R.color.colorPrimaryDark);
                                    colorText = Color.WHITE;
                                }else{
                                    color = swatch.getRgb();

                                    float[] hslColor = swatch.getHsl();
                                    hslColor[2]-=.025f;
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
                });

    }

}
