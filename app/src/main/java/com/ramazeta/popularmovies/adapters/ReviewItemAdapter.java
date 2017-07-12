package com.ramazeta.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramazeta.popularmovies.R;
import com.ramazeta.popularmovies.models.Reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rama on 7/12/17.
 */

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder> {
    List<Reviews> reviews = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentReview,authorReview;

        public ViewHolder(View itemView) {
            super(itemView);
            contentReview = (TextView)itemView.findViewById(R.id.contentReview);
            authorReview = (TextView)itemView.findViewById(R.id.authorReview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.authorReview.setText(reviews.get(position).getAuthor());
        holder.contentReview.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addReviews(List<Reviews> reviews){
        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }

    public void clearReviews(){
        this.reviews.clear();
        notifyDataSetChanged();
    }
}
