package com.ramazeta.popularmovies.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramazeta.popularmovies.R;
import com.ramazeta.popularmovies.activities.MoviesActivity;
import com.ramazeta.popularmovies.adapters.MovieItemAdapter;
import com.ramazeta.popularmovies.controllers.MoviesController;
import com.ramazeta.popularmovies.models.Movies;
import com.ramazeta.popularmovies.models.ParentMovies;
import com.ramazeta.popularmovies.rest.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMoviesFragment extends Fragment implements MovieItemAdapter.MoviesListener{
    @BindView(R.id.listMovies)
    RecyclerView listMovies;
    MovieItemAdapter mia = new MovieItemAdapter();

    public ListMoviesFragment() {
        // Required empty public constructor
    }

    public static ListMoviesFragment newInstance(String tipe){
        ListMoviesFragment fragment = new ListMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tipe", tipe);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_movies, container, false);
        ButterKnife.bind(this,view);
        setupRecyclerView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMovies();
    }

    private void setupRecyclerView(){
        listMovies.setHasFixedSize(true);
        listMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        listMovies.setAdapter(mia);
        mia.addListener(this);
    }

    private void loadMovies(){
        if(getArguments().getString("tipe").equalsIgnoreCase("favorites")){
            mia.clearMovies();
            mia.addMovies(MoviesController.get(getActivity()).getMovies());
        } else {
            ApiClient.get(getActivity()).getPopularMovies(getArguments().getString("tipe"), getString(R.string.api_key)).enqueue(new Callback<ParentMovies>() {
                @Override
                public void onResponse(Call<ParentMovies> call, Response<ParentMovies> response) {
                    mia.addMovies(response.body().getResults());
                }

                @Override
                public void onFailure(Call<ParentMovies> call, Throwable t) {
                    Log.e("Popular Movies", "Err : " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onClick(Movies movies) {
        MoviesController.get(getActivity()).setMovie(movies);
        Intent i = new Intent(getActivity(), MoviesActivity.class);
        startActivity(i);
    }

}
