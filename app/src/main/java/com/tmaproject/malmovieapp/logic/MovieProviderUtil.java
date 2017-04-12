package com.tmaproject.malmovieapp.logic;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.tmaproject.malmovieapp.database.MovieProvider;
import com.tmaproject.malmovieapp.database.tabels.MovieTable;
import com.tmaproject.malmovieapp.models.networking.Genre;
import com.tmaproject.malmovieapp.models.networking.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 4/11/17.
 */

public class MovieProviderUtil {
    private static final String TAG = "MovieProviderUtil";

    public static List<Movie> getFavMovies(Context ctx){
        List<Movie> movies = new ArrayList<>();
        Cursor c = ctx.getContentResolver().query(
                MovieProvider.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if(c.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.isLocal = true;
                movie.setId(c.getInt(c.getColumnIndex(MovieTable.COLUMN_ID)));
                movie.setTitle(c.getString(c.getColumnIndex(MovieTable.COLUMN_TITLE)));
                movie.setOverview(c.getString(c.getColumnIndex(MovieTable.COLUMN_OVERVIEW)));
                movie.setReleaseDate(c.getString(c.getColumnIndex(MovieTable.COLUMN_RELEASE_DATE)));
                movie.setBackdropPath(c.getString(c.getColumnIndex(MovieTable.COLUMN_BACKDROP_PATH)));
                movie.setPosterPath(c.getString(c.getColumnIndex(MovieTable.COLUMN_POSTER_PATH)));
                movie.setGenres(
                        stringToGenres(c.getString(c.getColumnIndex(MovieTable.COLUMN_GENRES))));
                movie.setTagline(c.getString(c.getColumnIndex(MovieTable.COLUMN_TAGLINE)));
                movie.setVoteAverage(c.getDouble(c.getColumnIndex(MovieTable.COLUMN_VOTE_AVERAGE)));
                movie.setRuntime(c.getInt(c.getColumnIndex(MovieTable.COLUMN_RUNTIME)));

                movies.add(movie);
            }while (c.moveToNext());
        }
        return movies;
    }

    public static boolean isMovieFavorite(Context ctx, long id){
        Cursor c = ctx.getContentResolver().query(
                ContentUris.withAppendedId(MovieProvider.CONTENT_URI,id),
                null,
                null,
                null,
                null
        );
        return c.getCount()>0;
    }

    public static void addMovie(Context ctx,Movie movie) {

        ContentValues vals = new ContentValues();
        vals.put(MovieTable.COLUMN_ID,movie.getId());
        vals.put(MovieTable.COLUMN_RELEASE_DATE,movie.getReleaseDate());
        vals.put(MovieTable.COLUMN_TITLE,movie.getTitle());
        vals.put(MovieTable.COLUMN_OVERVIEW,movie.getOverview());
        vals.put(MovieTable.COLUMN_POSTER_PATH,movie.getPosterPath());
        vals.put(MovieTable.COLUMN_BACKDROP_PATH,movie.getBackdropPath());
        vals.put(MovieTable.COLUMN_GENRES,
                genresToString(movie.getGenres()));
        vals.put(MovieTable.COLUMN_TAGLINE,movie.getTagline());
        vals.put(MovieTable.COLUMN_RUNTIME,movie.getRuntime());
        vals.put(MovieTable.COLUMN_VOTE_AVERAGE,movie.getVoteAverage());

        ctx.getContentResolver().insert(
                MovieProvider.CONTENT_URI,vals
        );
    }

    public static void delMovie(Context ctx,int id){
        ctx.getContentResolver().delete(
                ContentUris.withAppendedId(MovieProvider.CONTENT_URI,id),
                null,
                null
        );

    }

    private static String genresToString(List<Genre> l){
        String s="";
        for (Genre genre : l) {
            s+=genre.getName()+",";
        }
        return s;
    }
    private static List<Genre> stringToGenres(String s){
        List<Genre> l = new ArrayList<>();
        for (String genre : s.split(",")) {
            Genre g = new Genre();
            g.setName(genre);
            l.add(g);
        }
        return l;
    }

}
