package com.dicoding.submissionmade2_1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.submissionmade2_1.Item.FavoriteTvShow;
import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.Item.TvShow;
import com.dicoding.submissionmade2_1.ViewModel.FavoriteTvShowViewModel;

import java.util.List;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TvShow = "extra_tvshow";

    FavoriteTvShowViewModel favoriteTvShowViewModel;
    int idTvShow;
    LiveData<List<FavoriteTvShow>> dataCheck;
    Boolean booleanApakahFavoriteTvShowAda; // kalau true berarti delete, kalau false berarti insert

    FavoriteTvShow favoriteTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        TextView tvTitle = findViewById(R.id.tv_title_received2),
                tvDescription = findViewById(R.id.txt_description_received2);
        ImageView imgPoster = findViewById(R.id.img_received2);

        Button btn_favorite_this_movie = findViewById(R.id.favorite_this_tv_show);

        try {
            TvShow tvshow = getIntent().getParcelableExtra(EXTRA_TvShow);
            tvTitle.setText(tvshow.getTitle());
            tvDescription.setText(tvshow.getDescription());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvshow.getPoster();
            Glide.with(this)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
            idTvShow = tvshow.getIdTvShow();

            favoriteTvShow = new FavoriteTvShow(tvshow.getPoster(), tvshow.getTitle(), tvshow.getDescription(), tvshow.getIdTvShow());

        } catch (Exception e) {
            e.printStackTrace();
        }

        favoriteTvShowViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);

        dataCheck = favoriteTvShowViewModel.getAllFavoriteTvShowById(idTvShow);


        // kalau ga ada data yg terdeteksi
        booleanApakahFavoriteTvShowAda = dataCheck != null;


        btn_favorite_this_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleanApakahFavoriteTvShowAda) { // true
                    favoritkanTvShowIni(true); // data nya ada, ketika di pencet di delete
                    Toast.makeText(DetailTvShowActivity.this, "tvShow ini tidak difavoritkan", Toast.LENGTH_SHORT).show();
                } else { // ga ada data nya, di insert dong
                    favoritkanTvShowIni(false);
                    Toast.makeText(DetailTvShowActivity.this, "tvShow ini difavoritkan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void favoritkanTvShowIni(Boolean booleanApakahFavoriteTvShowAda) {
        if (!booleanApakahFavoriteTvShowAda) { // false
//            insert
            favoriteTvShowViewModel.insert(favoriteTvShow);
        } else { // true
            try {
                favoriteTvShowViewModel.deleteMovieById(idTvShow);
                dataCheck = null;
            } catch (ExceptionInInitializerError e) {
                e.printStackTrace();
            }
        }
    }

}
