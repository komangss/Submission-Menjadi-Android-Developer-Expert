package com.dicoding.submissionmade2_1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.submissionmade2_1.Item.Movie;
import com.dicoding.submissionmade2_1.R;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

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
//            imgPoster.setImageResource(movie.getPoster());
        } catch (Exception e) {

        }

    }
}
