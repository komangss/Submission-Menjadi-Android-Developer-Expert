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
import com.dicoding.submissionmade2_1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {
    int id_movie;
    private static final String API_KEY = "d9c1d6e1b7d10d2ad0ac0c8e7e9abb81";
    String url = "https://api.themoviedb.org/3/movie/" + id_movie + "?api_key=" + API_KEY + "&language=en-US";

    public FavoriteMovieAdapter(int id_movie) {
        this.id_movie = id_movie;
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieHolder holder, int position) {
//    set text
        Log.d("onBindViewHolder", "test");
        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("original_title");
                            Log.d("ini isi JSON : ", title);
                            holder.tvTitle.setText(title);
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

    @Override
    public int getItemCount() {
        return id_movie;
    }

    class FavoriteMovieHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.text_view_title);
        }

    }
}
