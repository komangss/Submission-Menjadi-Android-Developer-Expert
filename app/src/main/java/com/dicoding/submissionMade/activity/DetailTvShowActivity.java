package com.dicoding.submissionMade.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.item.FavoriteTvShow;
import com.dicoding.submissionMade.item.TvShow;
import com.dicoding.submissionMade.viewModel.FavoriteTvShowViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TvShow = "extra_tv_show";
    private FavoriteTvShowViewModel favoriteTvShowViewModel;
    private int idTvShow;
    private FavoriteTvShow favoriteTvShow;
    private ConstraintLayout parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        TextView tvTitle = findViewById(R.id.tv_title_received2),
                tvDescription = findViewById(R.id.txt_description_received2);
        ImageView imgPoster = findViewById(R.id.img_received2);

        final LikeButton likeButton = findViewById(R.id.favorite_button);
        parent_view = findViewById(R.id.coordinator_layout2);

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
        try {
            favoriteTvShowViewModel.getAllFavoriteTvShowById(idTvShow).observe(this, new Observer<List<FavoriteTvShow>>() {
                @Override
                public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
                    if (favoriteTvShows.size() == 0) {
                        likeButton.setLiked(false);
                    } else {
                        likeButton.setLiked(true);
                    }
                }
            });
        } catch (Exception ignored) {

        }

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                favoriteTvShowViewModel.insert(favoriteTvShow);
                snackBarIconSuccess();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                favoriteTvShowViewModel.deleteMovieById(idTvShow);
                snackBarIconError();
            }
        });
    }

    private void snackBarIconError() {
        final Snackbar snackbar = Snackbar.make(parent_view, "", Snackbar.LENGTH_SHORT);
        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.snackbar_icon_text, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);

        ((TextView) custom_view.findViewById(R.id.message)).setText(R.string.remove_from_favorite);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        (custom_view.findViewById(R.id.parent_view)).setBackgroundColor(getResources().getColor(R.color.red_600));
        snackBarView.addView(custom_view, 0);
        snackbar.show();
    }

    private void snackBarIconSuccess() {
        final Snackbar snackbar = Snackbar.make(parent_view, "", Snackbar.LENGTH_SHORT);
        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.snackbar_icon_text, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);

        ((TextView) custom_view.findViewById(R.id.message)).setText(R.string.add_from_favorite);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
        (custom_view.findViewById(R.id.parent_view)).setBackgroundColor(getResources().getColor(R.color.green_500));
        snackBarView.addView(custom_view, 0);
        snackbar.show();
    }
}

