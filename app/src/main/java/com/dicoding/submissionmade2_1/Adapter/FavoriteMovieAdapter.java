package com.dicoding.submissionmade2_1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.submissionmade2_1.Item.FavoriteMovie;
import com.dicoding.submissionmade2_1.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {

    private List<FavoriteMovie> favoriteMovies = new ArrayList<>();

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_movie_item, parent, false);
        return new FavoriteMovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieHolder holder, int position) {
        FavoriteMovie currentFavoriteMovie = favoriteMovies.get(position);
        holder.tvTitle.setText(currentFavoriteMovie.getTitle());
        holder.tvDescription.setText(currentFavoriteMovie.getDescription());
    }

    @Override
    public int getItemCount() {
        return favoriteMovies.size();
    }

    public void setFavoriteMovies(List<FavoriteMovie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
        notifyDataSetChanged();
    }

    public FavoriteMovie getFavoriteMovieAt(int positionn) {
        return favoriteMovies.get(positionn);
    }


    class FavoriteMovieHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;

        public FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
