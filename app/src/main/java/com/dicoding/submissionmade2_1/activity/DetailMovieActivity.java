package com.dicoding.submissionmade2_1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    TextView tv_favorite;
    public static final String EXTRA_MOVIE = "extra_movie";
    public static int EXTRA_ID_MOVIE = 0;
    public LiveData<List<Favorite>> dataCheck;
    public boolean isFavoriteAlready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        btn_favorite_this = findViewById(R.id.favorite_this_movie);
        tv_favorite = findViewById(R.id.favorite_already);

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

            // favorite = new Favorite(EXTRA_ID_MOVIE);

            //            imgPoster.setImageResource(movie.getPoster());
        } catch (Exception e) {

        }

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        try {

            dataCheck = favoriteViewModel.checkMoviesById(EXTRA_ID_MOVIE);

            if(dataCheck == null ) {
                isFavoriteAlready = false;
                tv_favorite.setText("favorite : nope");
            } else {
                isFavoriteAlready = true;
                tv_favorite.setText("favorite : already");
            }

            dataCheck.observe(this, new Observer<List<Favorite>>() {
                @Override
                public void onChanged(List<Favorite> favorites) {
                    Log.d("isi favorite ", String.valueOf(favorites));
                    if(favorites == null || favorites.size() == 0) {
                        isFavoriteAlready = false;
                        tv_favorite.setText("favorite : nope");
                    } else {
                        isFavoriteAlready = true;
                        tv_favorite.setText("favorite : already");
                    }

                }
            });

        } catch (NullPointerException e) {
            Log.d("ini bug nya", e.getMessage());
        }


        btn_favorite_this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite(EXTRA_ID_MOVIE);
                if (isFavoriteAlready) {
//                    delete
                    favoriteViewModel.delete(favorite);
                    isFavoriteAlready = false;
                    Toast.makeText(DetailMovieActivity.this, "You delete Favorite This Movie : " + isFavoriteAlready , Toast.LENGTH_SHORT).show();
                } else {
                    favoriteViewModel.insert(favorite);
                    isFavoriteAlready = true;
                    Toast.makeText(DetailMovieActivity.this, "You Favorite This Movie", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
