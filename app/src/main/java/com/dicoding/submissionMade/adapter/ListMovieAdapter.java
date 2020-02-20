package com.dicoding.submissionMade.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.activity.DetailMovieActivity;
import com.dicoding.submissionMade.item.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> implements Filterable {
    private ArrayList<Movie> listMovieFull = new ArrayList<>();
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        listMovieFull = new ArrayList<>(items);
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

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final ArrayList<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listMovieFull);
            } else {
                String filterPattern = constraint.toString().trim();

                final String API_KEY = "d9c1d6e1b7d10d2ad0ac0c8e7e9abb81";
                String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + filterPattern;

                AndroidNetworking.get(url)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray list = response.getJSONArray("results");
                                    for (int i = 0; i < list.length(); i++) {
                                        JSONObject movie = list.getJSONObject(i);
                                        Movie movieItems = new Movie(movie);
                                        filteredList.add(movieItems);
                                    }
                                } catch (JSONException e) {
                                    Log.d("Exception", e.getMessage());
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("onFailure", anError.getMessage());
                            }
                        });
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listMovie.clear();
            listMovie.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tvName, tvFrom;

        ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvFrom = itemView.findViewById(R.id.tv_item_from);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = listMovie.get(position);

            movie.setTitle(movie.getTitle());
            movie.setDescription(movie.getDescription());
            movie.setPoster(movie.getPoster());

            Intent showMovieActivityIntent = new Intent(itemView.getContext(), DetailMovieActivity.class);
            showMovieActivityIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            view.getContext().startActivity(showMovieActivityIntent);
        }

        void bind(Movie item) {
            String url_image = "https://image.tmdb.org/t/p/w185" + item.getPoster();

            tvName.setText(item.getTitle());
            tvFrom.setText(item.getDescription());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPhoto);
        }
    }


}
