package com.dicoding.submissionMade.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.FavoriteMovieAdapter;
import com.dicoding.submissionMade.item.FavoriteMovie;
import com.dicoding.submissionMade.viewModel.FavoriteMovieViewModel;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        RecyclerView recyclerView = findViewById(R.id.rv_favorite_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final FavoriteMovieAdapter adapter = new FavoriteMovieAdapter();
        recyclerView.setAdapter(adapter);

        FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovie().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
//              update recycler view
                adapter.setFavoriteMovies(favoriteMovies);
            }
        });
    }
}
