package com.tmaproject.malmovieapp.logic;

import com.tmaproject.malmovieapp.Secrets;
import com.tmaproject.malmovieapp.models.networking.Movie;
import com.tmaproject.malmovieapp.models.networking.request_result.MovieRequestResult;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tarekkma on 10/20/16.
 */

public class TheMoviedbAPI {

    private static final String API_URL = "https://api.themoviedb.org/3/";
    public static final String API_IMAGE_185 = "http://image.tmdb.org/t/p/w185";
    public static final String API_IMAGE_500 = "http://image.tmdb.org/t/p/w500";
    public static final String API_IMAGE_ORIGINAL = "http://image.tmdb.org/t/p/original";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Interceptor apikeyInterceptor = chain -> {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                //.addQueryParameter("api_key", Secrets.THEMOVIEDB_API_KEY)/
                .addQueryParameter("api_key",/*Adding api key like this isn't recommended*/"c47731ea1e10d39215bd02c846eaf928")
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    };
    private static HttpLoggingInterceptor loggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static ApiCalls createService() {

        if (httpClient.interceptors().contains(apikeyInterceptor) == false)
            httpClient.addInterceptor(apikeyInterceptor);
        //if (httpClient.interceptors().contains(loggingInterceptor) == false) httpClient.addInterceptor(loggingInterceptor);


        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(ApiCalls.class);
    }

    public interface ApiCalls {
        @GET("movie/{type}/")
        Call<MovieRequestResult> getMovies(
                @Path("type")Movie.MovieType type,
                @Query("page")int page);

        //Hopefully this will be enough. Lel
        //*This only ~20kb request
        @GET("movie/{id}?append_to_response=videos,images,similar,reviews,recommendations")
        Call<Movie> getMovieDetails(@Path("id") int movieId);
    }
}
