package com.dicoding.submissionmade2_1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.submissionmade2_1.R;
import com.dicoding.submissionmade2_1.Item.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TvShow = "extra_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        TextView tvTitle = findViewById(R.id.tv_title_received2),
                tvDescription = findViewById(R.id.txt_description_received2);
        ImageView imgPoster = findViewById(R.id.img_received2);


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
        } catch (Exception e) {

        }

    }
}
