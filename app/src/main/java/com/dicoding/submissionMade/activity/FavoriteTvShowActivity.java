package com.dicoding.submissionMade.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.FavoriteTvShowAdapter;
import com.dicoding.submissionMade.item.FavoriteTvShow;
import com.dicoding.submissionMade.viewModel.FavoriteTvShowViewModel;

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

        FavoriteTvShowViewModel favoriteMovieViewModel = new ViewModelProvider(this).get(FavoriteTvShowViewModel.class);
        favoriteMovieViewModel.getAllFavoriteTvShow().observe(this, new Observer<List<FavoriteTvShow>>() {
            @Override
            public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
//              update recycler view
                adapter.setFavoriteTvShows(favoriteTvShows);
            }
        });
    }
}






























