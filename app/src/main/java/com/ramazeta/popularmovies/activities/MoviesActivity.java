package com.ramazeta.popularmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazeta.popularmovies.R;
import com.ramazeta.popularmovies.adapters.ReviewItemAdapter;
import com.ramazeta.popularmovies.adapters.TrailerItemAdapter;
import com.ramazeta.popularmovies.controllers.MoviesController;
import com.ramazeta.popularmovies.models.FavMovies;
import com.ramazeta.popularmovies.models.Movies;
import com.ramazeta.popularmovies.models.ParentReviews;
import com.ramazeta.popularmovies.models.ParentTrailers;
import com.ramazeta.popularmovies.models.Trailers;
import com.ramazeta.popularmovies.rest.ApiClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements TrailerItemAdapter.TrailerListener{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.imgMovies) ImageView imgMovies;
    @BindView(R.id.titleMovies) TextView titleMovies;
    @BindView(R.id.posterMovies) ImageView posterMovies;
    @BindView(R.id.descMovies) TextView descMovies;
    @BindView(R.id.ratingMovies) TextView ratingMovies;
    @BindView(R.id.releaseMovies) TextView releaseMovies;
    @BindView(R.id.listReviews) RecyclerView listReviews;
    ReviewItemAdapter ria = new ReviewItemAdapter();
    @BindView(R.id.listTrailers) RecyclerView listTrailers;
    TrailerItemAdapter tia = new TrailerItemAdapter();
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        setupToolbar();
        setupMovies();
        setupReviews();
        setupTrailers();
        setupRealm();
    }

    private void setupRealm(){
        this.realm = MoviesController.get(this).getRealm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(MoviesController.get(this).isFavorite(MoviesController.get(this).getMovie().getId())){
            menu.findItem(R.id.ac_fav).setIcon(R.drawable.ic_favorite_white_24dp);
        } else {
            menu.findItem(R.id.ac_fav).setIcon(R.drawable.ic_favorite_border_white_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ac_fav:
                if(MoviesController.get(this).isFavorite(MoviesController.get(this).getMovie().getId())){
                    MoviesController.get(this).deleteMovies(MoviesController.get(this).getMovie().getId());
                    item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                } else {
                    Movies mov = MoviesController.get(this).getMovie();
                    FavMovies fm = new FavMovies(mov.getVoteCount(), mov.getId(), mov.getVideo(), mov.getVoteAverage(), mov.getTitle(), mov.getPopularity(), mov.getPosterPath(), mov.getOriginalLanguage(), mov.getOriginalTitle(), mov.getGenreIds(), mov.getBackdropPath(), mov.getAdult(), mov.getOverview(), mov.getReleaseDate());
                    realm.beginTransaction();
                    realm.copyToRealm(fm);
                    realm.commitTransaction();
                    item.setIcon(R.drawable.ic_favorite_white_24dp);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupMovies(){
        Picasso.with(this).load(getString(R.string.img_url) + MoviesController.get(this).getMovie().getBackdropPath()).fit().centerCrop().into(posterMovies);
        titleMovies.setText(MoviesController.get(this).getMovie().getOriginalTitle());
        Picasso.with(this).load(getString(R.string.img_url) + MoviesController.get(this).getMovie().getPosterPath()).fit().centerCrop().into(imgMovies);
        descMovies.setText(MoviesController.get(this).getMovie().getOverview());
        releaseMovies.setText("Release date : " + MoviesController.get(this).getMovie().getReleaseDate());
        ratingMovies.setText("Rating : " + MoviesController.get(this).getMovie().getVoteAverage());
    }

    private void setupReviews(){
        listReviews.setHasFixedSize(true);
        listReviews.setLayoutManager(new LinearLayoutManager(this));
        listReviews.setAdapter(ria);
        ApiClient.get(this).getReviews(MoviesController.get(this).getMovie().getId(), getString(R.string.api_key)).enqueue(new Callback<ParentReviews>() {
            @Override
            public void onResponse(Call<ParentReviews> call, Response<ParentReviews> response) {
                ria.addReviews(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ParentReviews> call, Throwable t) {

            }
        });
    }

    private void setupTrailers(){
        listTrailers.setHasFixedSize(true);
        listTrailers.setLayoutManager(new LinearLayoutManager(this));
        listTrailers.setAdapter(tia);
        tia.addListener(this);
        ApiClient.get(this).getTrailers(MoviesController.get(this).getMovie().getId(), getString(R.string.api_key)).enqueue(new Callback<ParentTrailers>() {
            @Override
            public void onResponse(Call<ParentTrailers> call, Response<ParentTrailers> response) {
                tia.addTrailers(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ParentTrailers> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(Trailers trailers) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailers.getKey())));
    }
}
