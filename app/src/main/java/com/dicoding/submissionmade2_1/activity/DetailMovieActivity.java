package com.dicoding.submissionmade2_1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.submissionmade2_1.Item.Favorite;
import com.dicoding.submissionmade2_1.Item.Movie;
import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.ViewModel.FavoriteViewModel;

import java.util.List;

public class DetailMovieActivity extends AppCompatActivity {

    FavoriteViewModel favoriteViewModel;
    Button btn_favorite_this;
    public static final String EXTRA_MOVIE = "extra_movie";
    public static int EXTRA_ID_MOVIE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        btn_favorite_this = findViewById(R.id.favorite_this_movie);

        TextView tvTitle = findViewById(R.id.tv_title_received),
                tvDescription = findViewById(R.id.txt_description_received);
        ImageView imgPoster = findViewById(R.id.img_received);


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
            EXTRA_ID_MOVIE = movie.getIdMovie();
//            imgPoster.setImageResource(movie.getPoster());
        } catch (Exception e) {

        }

        btn_favorite_this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(EXTRA_ID_MOVIE);
//                if () {
                new InsertInBackground(favorite).execute();
//                } else {
//                    liked true
//                }
                Toast.makeText(DetailMovieActivity.this, "You Favorite This Movie", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class InsertInBackground extends AsyncTask<Favorite, Void, Void> {
        private Favorite favorite;

        private InsertInBackground(Favorite favorite) {
            this.favorite = favorite;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteViewModel.insert(favorite);
            return null;
        }
    }

    private class CheckMovieById extends AsyncTask<Void, Void, Void> {
        private int idMovie;

        private CheckMovieById(int idMovie) {
            this.idMovie = idMovie;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            LiveData<List<Favorite>> datanya = favoriteViewModel.checkMoviesById(idMovie);
            return null;
        }
    }

}
