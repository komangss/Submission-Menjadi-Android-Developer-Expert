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
    FavoriteMovieViewModel favoriteMovieViewModel;
    int idMovie;
    LiveData<List<FavoriteMovie>> dataCheck;
    Boolean booleanApakahFavoriteMovieIniAda;

    FavoriteMovie favoriteMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        TextView tvTitle = findViewById(R.id.tv_title_received),
                tvDescription = findViewById(R.id.txt_description_received);
        ImageView imgPoster = findViewById(R.id.img_received);
        Button btn_favorite_this_movie = findViewById(R.id.favorite_this_movie);


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

            Log.d("id movie", String.valueOf(idMovie));
        } catch (Exception e) {
            e.printStackTrace();
        }

        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);

        try {

            dataCheck = favoriteMovieViewModel.getAllFavoriteMovieById(idMovie);

            if (dataCheck == null) {
                booleanApakahFavoriteMovieIniAda = false;
            } else {
                booleanApakahFavoriteMovieIniAda = true;
            }


        } catch (NullPointerException e) {
            Log.d("ini bug nya", e.getMessage());
        }

        btn_favorite_this_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleanApakahFavoriteMovieIniAda) { // true
                    favoritkanMovieIni(true); // delete
                    Toast.makeText(DetailMovieActivity.this, "movie ini tidak difavoritkan", Toast.LENGTH_SHORT).show();
                } else {
                    favoritkanMovieIni(false);
                    Toast.makeText(DetailMovieActivity.this, "movie ini difavoritkan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
//        if (!booleanApakahFavoriteMovieIniAda) {
////            insert
//            favoriteMovieViewModel.insert(favoriteMovie);
//            finish();
//        } else {
//            favoriteMovieViewModel.delete(favoriteMovie);
//            finish();
//        }
//    }


//    Atau
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
//        if (!booleanApakahFavoriteMovieIniAda) {
////            insert
//            favoriteMovieViewModel.insert(favoriteMovie);
//            finish();
//        } else {
//            favoriteMovieViewModel.delete(favoriteMovie);
//            finish();
//        }
//    }

    private void favoritkanMovieIni(Boolean booleanApakahFavoriteMovieIniAda) {
        if (!booleanApakahFavoriteMovieIniAda) { // false
//            insert
            Log.d("insert data", "berhasil");
            favoriteMovieViewModel.insert(favoriteMovie);
            this.booleanApakahFavoriteMovieIniAda = true;
        } else { // true
            favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
            Log.d("delete data", "berhasil kesini");
            favoriteMovieViewModel.delete(favoriteMovie);
            this.booleanApakahFavoriteMovieIniAda = false;
        }
    }
}
