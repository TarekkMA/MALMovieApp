package com.tmaproject.malmovieapp.views.fragments;

/**
 * Created by tarekkma on 10/20/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.snappydb.DB;
import com.snappydb.KeyIterator;
import com.snappydb.SnappydbException;
import com.tmaproject.malmovieapp.MyApp;
import com.tmaproject.malmovieapp.R;
import com.tmaproject.malmovieapp.logic.ResponsiveUi;
import com.tmaproject.malmovieapp.logic.TheMoviedbAPI;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.models.networking.request_result.MovieRequestResult;
import com.tmaproject.malmovieapp.views.adapters.MovieListAdapter;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    private static final String TAG = "MoviesFragment";
    private static final String ARG_MOVIES_TYPE = "moviesssType";
    private static final String ARG_MOVIES_LIST = "ARG_MOVIES_LIST";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_MOVIES_LIST, Parcels.wrap(movieListAdapter.getMoviesList()));
    }

    public MoviesFragment() {
    }


    public static MoviesFragment newInstance(Movie.MovieType movieType) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIES_TYPE, movieType);
        fragment.setArguments(args);
        return fragment;
    }

    public void setArguments() {
        movieType = (Movie.MovieType) getArguments().getSerializable(ARG_MOVIES_TYPE);
    }

    RecyclerView moviesList;
    MovieListAdapter movieListAdapter;
    Movie.MovieType movieType;
    RelativeLayout refreshLayout;
    RelativeLayout loadingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        setArguments();
        moviesList = (RecyclerView) rootView.findViewById(R.id.movies_list);
        refreshLayout = (RelativeLayout) rootView.findViewById(R.id.refresh_layout);
        loadingLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);
        int numOfCol = ResponsiveUi.calculateNoOfColumns(getContext(),170,true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), numOfCol);
        moviesList.setLayoutManager(layoutManager);
        movieListAdapter = new MovieListAdapter();
        moviesList.setAdapter(movieListAdapter);
        refreshLayout.setOnClickListener(v -> {
            requestMoviesFromServer();
            hideLoading();
        });

        if(savedInstanceState!=null && savedInstanceState.containsKey(ARG_MOVIES_LIST)){
            movieListAdapter.setData(Parcels.unwrap(savedInstanceState.getParcelable(ARG_MOVIES_LIST)));
        }else {
            if (movieType == Movie.MovieType.FAVORITES) {
                requestMoviesFromDatabase();
            } else {
                requestMoviesFromServer();
            }
        }
        return rootView;
    }

    private void requestMoviesFromServer() {
        viewLoading();
        TheMoviedbAPI.createService().getMovies(movieType, 1)
                .enqueue(new Callback<MovieRequestResult>() {
                    @Override
                    public void onResponse(Call<MovieRequestResult> call, Response<MovieRequestResult> response) {
                        if (response.isSuccessful()) {
                            hideLoading();
                            movieListAdapter.addPage(response.body().getResults());
                        } else {
                            Log.e(TAG, "onResponse: " + response.code() + ": RequestFailed");
                            viewRefresh();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieRequestResult> call, Throwable t) {
                        Log.e(TAG, "onFailure: Unable to reach server", t);
                        viewRefresh();
                    }
                });
    }

    void requestMoviesFromDatabase(){
        hideLoading();
        try {
            DB favDb = MyApp.getInstance().getDBManager().getFavoritesDB();
            KeyIterator it = favDb.allKeysIterator();
            List<Movie> movieList= new ArrayList<>();
            while (it.hasNext()){
                Movie m = favDb.getObject(it.next(1)[0],Movie.class);
                movieList.add(m);
            }
            movieListAdapter.setData(movieList);
        } catch (SnappydbException e) {
            e.printStackTrace();
            viewRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (movieType == Movie.MovieType.FAVORITES){
            requestMoviesFromDatabase();
        }else{
            movieListAdapter.notifyDataSetChanged();
        }
    }

    private void viewLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
    }

    private void viewRefresh() {
        loadingLayout.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    private void hideRefresh() {
        loadingLayout.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
    }
}