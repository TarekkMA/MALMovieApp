package com.tmaproject.malmovieapp.models.events;

import com.tmaproject.malmovieapp.models.networking.Movie;

/**
 * Created by TarekMA on 12/1/16.
 * facebook/tarekkma1
 */

public class MovieSelectedEvent {
    public Movie movie;

    public MovieSelectedEvent(Movie movie) {
        this.movie = movie;
    }
}
