package com.ramazeta.popularmovies;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by rama on 7/12/17.
 */

public class PopularMoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
