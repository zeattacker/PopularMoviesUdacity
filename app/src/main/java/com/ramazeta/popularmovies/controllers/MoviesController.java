package com.ramazeta.popularmovies.controllers;

import android.app.Activity;
import android.app.Application;

import com.ramazeta.popularmovies.models.FavMovies;
import com.ramazeta.popularmovies.models.Movies;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rama on 7/12/17.
 */

public class MoviesController {
    static MoviesController mc;
    private Movies selectedMovie;
    private final Realm realm;

    public MoviesController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static MoviesController get(Activity ac){
        if(mc == null)
            mc = new MoviesController(ac.getApplication());
        return mc;
    }

    public void setMovie(Movies movies){
        this.selectedMovie = movies;
    }

    public Movies getMovie(){
        return this.selectedMovie;
    }

    public ArrayList<Movies> getMovies() {
        ArrayList<Movies> movies=new ArrayList<>();
        RealmResults<FavMovies> mv = realm.where(FavMovies.class).findAll();
        for(FavMovies s:mv)
        {
            movies.add(new Movies(s.getVoteCount(),s.getId(),s.getVideo(),s.getVoteAverage(),s.getTitle(),s.getPopularity(),s.getPosterPath(),s.getOriginalLanguage(),s.getOriginalTitle(),s.getGenreIds(),s.getBackdropPath(),s.getAdult(),s.getOverview(),s.getReleaseDate()));
        }
        return movies;
    }

    public void deleteMovies(int id){
        realm.beginTransaction();
        FavMovies fm = realm.where(FavMovies.class).equalTo("id", id).findFirst();
        fm.deleteFromRealm();
        realm.commitTransaction();
    }

    public boolean isFavorite(int id){
        return realm.where(FavMovies.class).equalTo("id", id).findFirst() != null;
    }

    public Realm getRealm() {

        return realm;
    }
}
