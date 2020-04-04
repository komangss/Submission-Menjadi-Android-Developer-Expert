package com.dicoding.submissionMade.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.item.FavoriteMovie;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.viewModel.FavoriteMovieViewModel;
import com.dicoding.submissionMade.widget.ImagesBannerWidget;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;
import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private FavoriteMovieViewModel favoriteMovieViewModel;
    private int idMovie;
    private FavoriteMovie favoriteMovie;
    private ConstraintLayout parent_view;
    private Context ctx;

//    Todo: merapihkan code di onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ctx = getApplicationContext();
        TextView tvTitle = findViewById(R.id.tv_title_movie),
                tvOverview = findViewById(R.id.tv_overview_movie),
                tvRating = findViewById(R.id.tv_rating_movie);
//                tvDuration = findViewById(R.id.tv_duration_movie);

        ImageView imgPoster = findViewById(R.id.img_poster_movie),
                imgBackdrop = findViewById(R.id.img_backdrop);

        final LikeButton likeButton = findViewById(R.id.favorite_button);
        parent_view = findViewById(R.id.parent_constraint_layout);
//        tv_rating_movie

        try {

            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String rating = movie.getVote_average() / 2 + " / 5";
            tvRating.setText(rating);
            String url_img_poster = "https://image.tmdb.org/t/p/w185" + movie.getPoster_path();
            String url_img_backdrop = "https://image.tmdb.org/t/p/w780" + movie.getBackdrop_path();
            Glide.with(this)
                    .load(url_img_poster)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
            Glide.with(this)
                    .load(url_img_backdrop)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgBackdrop);
            idMovie = movie.getIdMovie();

            getSupportActionBar().setTitle(movie.getTitle());

            favoriteMovie = new FavoriteMovie(movie.getPoster_path(), movie.getTitle(), movie.getOverview(), movie.getIdMovie());

        } catch (Exception e) {
            e.printStackTrace();
        }

        favoriteMovieViewModel = new ViewModelProvider(this).get(FavoriteMovieViewModel.class);
        try {
            favoriteMovieViewModel.getAllFavoriteMovieById(idMovie).observe(this, new Observer<List<FavoriteMovie>>() {
                @Override
                public void onChanged(List<FavoriteMovie> favoriteMovies) {
                    if (favoriteMovies.size() == 0) {
                        likeButton.setLiked(false);
                    } else {
                        likeButton.setLiked(true);
                    }
                }
            });

        } catch (NullPointerException e) {
            Log.d("ini bug nya", Objects.requireNonNull(e.getMessage()));
        }

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                favoriteMovieViewModel.insert(favoriteMovie);
                snackBarIconSuccess();
                notifyWidgetToUpdate();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                favoriteMovieViewModel.deleteMovieById(idMovie);
                snackBarIconError();
                notifyWidgetToUpdate();
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

    private void notifyWidgetToUpdate() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);
        ComponentName thisWidget = new ComponentName(ctx, ImagesBannerWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }
}