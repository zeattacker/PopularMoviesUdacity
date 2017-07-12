package com.ramazeta.popularmovies.rest;

import com.ramazeta.popularmovies.models.ParentMovies;
import com.ramazeta.popularmovies.models.ParentReviews;
import com.ramazeta.popularmovies.models.ParentTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rama on 7/12/17.
 */

public interface ApiInterface {
    @GET("movie/{tipe}")
    Call<ParentMovies> getPopularMovies(@Path("tipe") String tipe, @Query("api_key") String key);

    @GET("movie/top_rated")
    Call<ParentMovies> getTopMovies(@Query("api_key") String key);

    @GET("movie/{id}/reviews")
    Call<ParentReviews> getReviews(@Path("id") int id, @Query("api_key") String key);

    @GET("movie/{id}/videos")
    Call<ParentTrailers> getTrailers(@Path("id") int id, @Query("api_key") String key);
}
