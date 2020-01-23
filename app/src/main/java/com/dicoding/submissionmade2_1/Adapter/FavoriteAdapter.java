package com.dicoding.submissionmade2_1.Adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.submissionmade2_1.Item.Favorite;
import com.dicoding.submissionmade2_1.Item.Movie;
import com.dicoding.submissionmade2_1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    int id_movie;
    private List<Favorite> favorites = new ArrayList<>();
    private static final String API_KEY = "d9c1d6e1b7d10d2ad0ac0c8e7e9abb81";
    String url = "https://api.themoviedb.org/3/discover/movie?" + id_movie + "api_key=" + API_KEY + "&language=en-US";


    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new FavoriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteHolder holder, int position) {
        Favorite currentFavorite = favorites.get(position);


        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("results");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movieFavorite = list.getJSONObject(i);
                                holder.tvTitle.setText(movieFavorite.getJSONArray("0").toString());
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
//        holder.tvTitle.setText(currentFavorite.getId());
//        holder.tvDescription.setText(currentFavorite.getDescription());
//        holder.tvPriority.setText(String.valueOf(currentFavorite.getPriority()));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription, tvPriority;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_view_title);
            tvDescription = itemView.findViewById(R.id.text_view_description);
            tvPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}