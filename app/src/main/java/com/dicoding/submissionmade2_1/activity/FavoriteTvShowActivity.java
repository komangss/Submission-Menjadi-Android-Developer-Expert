package com.dicoding.submissionmade2_1.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.adapter.FavoriteTvShowAdapter;
import com.dicoding.submissionmade2_1.item.FavoriteTvShow;
import com.dicoding.submissionmade2_1.viewModel.FavoriteTvShowViewModel;

import java.util.List;

public class FavoriteTvShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_tv_show);


        RecyclerView recyclerView = findViewById(R.id.rv_favorite_tv_show);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final FavoriteTvShowAdapter adapter = new FavoriteTvShowAdapter();
        recyclerView.setAdapter(adapter);

        FavoriteTvShowViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        favoriteMovieViewModel.getAllFavoriteTvShow().observe(this, new Observer<List<FavoriteTvShow>>() {
            @Override
            public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
//              update recycler view
                adapter.setFavoriteTvShows(favoriteTvShows);
            }
        });
    }
}






























