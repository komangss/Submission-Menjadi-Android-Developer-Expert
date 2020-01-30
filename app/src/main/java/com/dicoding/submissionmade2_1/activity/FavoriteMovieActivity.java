package com.dicoding.submissionmade2_1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.dicoding.submissionmade2_1.Adapter.FavoriteMovieAdapter;
import com.dicoding.submissionmade2_1.Item.FavoriteMovie;
import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.ViewModel.FavoriteMovieViewModel;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {

    private FavoriteMovieViewModel favoriteMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        RecyclerView recyclerView = findViewById(R.id.rv_favorite_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final FavoriteMovieAdapter adapter = new FavoriteMovieAdapter();
        recyclerView.setAdapter(adapter);

        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovie().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                adapter.setFavoriteMovies(favoriteMovies);
//            update recycler view
                Toast.makeText(FavoriteMovieActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                favoriteMovieViewModel.delete(adapter.getFavoriteMovieAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FavoriteMovieActivity.this, "Favorite Movie Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
