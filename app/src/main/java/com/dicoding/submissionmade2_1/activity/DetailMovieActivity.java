package com.dicoding.submissionmade2_1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.submissionmade2_1.Item.FavoriteMovie;
import com.dicoding.submissionmade2_1.Item.Movie;
import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.ViewModel.FavoriteMovieViewModel;

import java.util.List;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private int idMovie;
    private Boolean booleanCheckAvailabilityData; // kalau true berarti delete, kalau false berarti insert

    private FavoriteMovie favoriteMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        TextView tvTitle = findViewById(R.id.tv_title_received),
                tvDescription = findViewById(R.id.txt_description_received);
        ImageView imgPoster = findViewById(R.id.img_received);
        Button btn_favorite_this_movie = findViewById(R.id.favorite_this_movie);

        LiveData<List<FavoriteMovie>> dataFavoriteMovieById;

        try {
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            tvTitle.setText(movie.getTitle());
            tvDescription.setText(movie.getDescription());
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPoster();
            Glide.with(this)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
            idMovie = movie.getIdMovie();

            favoriteMovie = new FavoriteMovie(movie.getPoster(), movie.getTitle(), movie.getDescription(), movie.getIdMovie());

        } catch (Exception e) {
            e.printStackTrace();
        }

        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        booleanCheckAvailabilityData = false;
        try {
            favoriteMovieViewModel.getAllFavoriteMovieById(idMovie).observe(this, new Observer<List<FavoriteMovie>>() {
                @Override
                public void onChanged(List<FavoriteMovie> favoriteMovies) {
                    if (favoriteMovies == null || favoriteMovies.size() == 0) {
                        booleanCheckAvailabilityData = false;
                    } else {
                        booleanCheckAvailabilityData = true;
                    }
                }
            });

        } catch (NullPointerException e) {
            Log.d("ini bug nya", e.getMessage());
        }


        btn_favorite_this_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeThisMovieFavorite(booleanCheckAvailabilityData);
            }
        });

    }

    private void makeThisMovieFavorite(Boolean booleanCheckAvailabilityData) {
        if (booleanCheckAvailabilityData) { // true // kalo ada berarti di delete
            favoriteMovieViewModel.deleteMovieById(idMovie);
            Toast.makeText(DetailMovieActivity.this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
            this.booleanCheckAvailabilityData = false;
//            insert
        } else { // false // kalau ga ada berarti di insert
//                this.favoriteMovieViewModel.delete(this.favoriteMovie);
            favoriteMovieViewModel.insert(favoriteMovie);
            Toast.makeText(DetailMovieActivity.this, R.string.add_from_favorite, Toast.LENGTH_SHORT).show();
            this.booleanCheckAvailabilityData = true;
        }
    }
}