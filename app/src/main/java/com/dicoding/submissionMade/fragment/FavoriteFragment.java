package com.dicoding.submissionMade.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.activity.FavoriteMovieActivity;
import com.dicoding.submissionMade.activity.FavoriteTvShowActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnToMovie = getView().findViewById(R.id.btn_to_favorite_movie);
        btnToMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteMovieActivity.class);
                startActivity(intent);
            }
        });

        Button btnToTvShow = getView().findViewById(R.id.btn_to_favorite_tv_show);
        btnToTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteTvShowActivity.class);
                startActivity(intent);
            }
        });

    }
}
