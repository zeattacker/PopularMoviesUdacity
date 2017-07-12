package com.ramazeta.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ramazeta.popularmovies.R;
import com.ramazeta.popularmovies.models.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rama on 7/12/17.
 */

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder>{
    List<Movies> movies = new ArrayList<>();
    MoviesListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgMovies;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgMovies = (ImageView)itemView.findViewById(R.id.imgMovies);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(movies.get(getAdapterPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url = holder.imgMovies.getContext().getString(R.string.img_url) + movies.get(position).getPosterPath();
        Picasso.with(holder.imgMovies.getContext()).load(url).into(holder.imgMovies);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMovies(List<Movies> movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearMovies(){
        this.movies.clear();
        notifyDataSetChanged();
    }

    public interface MoviesListener {
        void onClick(Movies movies);
    }

    public void addListener(MoviesListener listener){
        this.listener = listener;
    }
}
