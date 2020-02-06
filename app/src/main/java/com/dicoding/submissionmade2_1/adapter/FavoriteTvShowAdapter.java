package com.dicoding.submissionmade2_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.item.FavoriteTvShow;

import java.util.ArrayList;
import java.util.List;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowHolder> {

    private List<FavoriteTvShow> favoriteTvShows = new ArrayList<>();

    @NonNull
    @Override
    public FavoriteTvShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_tv_show, parent, false);
        return new FavoriteTvShowHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvShowHolder holder, int position) {
        FavoriteTvShow currentFavoriteTvShow = favoriteTvShows.get(position);
        holder.tvTitle.setText(currentFavoriteTvShow.getTitle());
        holder.tvDescription.setText(currentFavoriteTvShow.getDescription());
    }

    @Override
    public int getItemCount() {
        return favoriteTvShows.size();
    }

    public void setFavoriteTvShows(List<FavoriteTvShow> favoriteTvShows) {
        this.favoriteTvShows = favoriteTvShows;
        notifyDataSetChanged();
    }

    class FavoriteTvShowHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;

        FavoriteTvShowHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_tv_show);
            tvDescription = itemView.findViewById(R.id.tv_description_tv_show);
        }
    }
}
