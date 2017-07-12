package com.ramazeta.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramazeta.popularmovies.R;
import com.ramazeta.popularmovies.models.Trailers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rama on 7/12/17.
 */

public class TrailerItemAdapter extends RecyclerView.Adapter<TrailerItemAdapter.ViewHolder> {
    List<Trailers> trailers = new ArrayList<>();
    TrailerListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView titleVideo;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleVideo = (TextView)itemView.findViewById(R.id.titleVideo);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(trailers.get(getAdapterPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleVideo.setText(trailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public interface TrailerListener {
        void onClick(Trailers trailers);
    }

    public void addTrailers(List<Trailers> trailers){
        this.trailers.addAll(trailers);
        notifyDataSetChanged();
    }

    public void clearTrailers(){
        this.trailers.clear();
        notifyDataSetChanged();
    }

    public void addListener(TrailerListener listener){
        this.listener = listener;
    }
}
