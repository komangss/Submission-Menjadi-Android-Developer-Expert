package com.dicoding.submissionMade.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.activity.DetailMovieActivity;
import com.dicoding.submissionMade.item.Movie;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tvName, tvFrom;
        RatingBar ratingBar;

        ListViewHolder(final View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvFrom = itemView.findViewById(R.id.tv_item_from);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = listMovie.get(position);

            movie.setTitle(movie.getTitle());
            movie.setOverview(movie.getOverview());
            movie.setPoster_path(movie.getPoster_path());

            Intent showMovieActivityIntent = new Intent(itemView.getContext(), DetailMovieActivity.class);
            showMovieActivityIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            view.getContext().startActivity(showMovieActivityIntent);
        }

        void bind(Movie item) {
            tvName.setText(item.getTitle());
            tvFrom.setText(item.getOverview());

            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w185" + item.getPoster_path())
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPhoto);

            ratingBar.setRating((float) (item.getVote_average() / 2));
        }
    }
}
