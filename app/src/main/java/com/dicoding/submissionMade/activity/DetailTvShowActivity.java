package com.dicoding.submissionMade.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.item.FavoriteTvShow;
import com.dicoding.submissionMade.item.TvShow;
import com.dicoding.submissionMade.viewModel.FavoriteTvShowViewModel;

import java.util.List;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TvShow = "extra_tv_show";
    private FavoriteTvShowViewModel favoriteTvShowViewModel;
    private int idTvShow;
    private Boolean booleanCheckAvailabilityData;
    private FavoriteTvShow favoriteTvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        TextView tvTitle = findViewById(R.id.tv_title_received2),
                tvDescription = findViewById(R.id.txt_description_received2);
        ImageView imgPoster = findViewById(R.id.img_received2);
        Button btn_favorite_this_tvShow = findViewById(R.id.favorite_this_tv_show);


        try {
            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TvShow);

            tvTitle.setText(tvShow.getTitle());
            tvDescription.setText(tvShow.getDescription());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPoster();
            Glide.with(this)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
            idTvShow = tvShow.getIdTvShow();

            favoriteTvShow = new FavoriteTvShow(tvShow.getPoster(), tvShow.getTitle(), tvShow.getDescription(), tvShow.getIdTvShow());
        } catch (Exception e) {
            e.printStackTrace();
        }

        favoriteTvShowViewModel = new ViewModelProvider(this).get(FavoriteTvShowViewModel.class);
        booleanCheckAvailabilityData = false;
        try {
            favoriteTvShowViewModel.getAllFavoriteTvShowById(idTvShow).observe(this, new Observer<List<FavoriteTvShow>>() {
                @Override
                public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
                    booleanCheckAvailabilityData = favoriteTvShows.size() != 0;
                }
            });
        } catch (Exception ignored) {

        }


        btn_favorite_this_tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeThisTvShowFavourite(booleanCheckAvailabilityData);
            }
        });

    }

    private void makeThisTvShowFavourite(Boolean booleanCheckAvailabilityData) {
        if (booleanCheckAvailabilityData) {
            favoriteTvShowViewModel.deleteMovieById(idTvShow);
            Toast.makeText(DetailTvShowActivity.this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
            this.booleanCheckAvailabilityData = false;
        } else {
            favoriteTvShowViewModel.insert(favoriteTvShow);
            Toast.makeText(DetailTvShowActivity.this, R.string.add_from_favorite, Toast.LENGTH_SHORT).show();
            this.booleanCheckAvailabilityData = true;
        }
    }
}

