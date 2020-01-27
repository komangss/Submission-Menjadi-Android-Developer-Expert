package com.dicoding.submissionmade2_1;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dicoding.submissionmade2_1.activity.FavoriteMovieActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    Button btnToMovie, btnToTvShow;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        btnToMovie = getView().findViewById(R.id.btn_to_favorite_movie);
        btnToMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavoriteMovieActivity();
            }
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    private void openFavoriteMovieActivity() {
        Intent intent = new Intent(getActivity(), FavoriteMovieActivity.class);
        startActivity(intent);
    }
}
