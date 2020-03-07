package com.dicoding.submissionMade.fragment.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.FavoriteMovieAdapter;
import com.dicoding.submissionMade.item.FavoriteMovie;
import com.dicoding.submissionMade.viewModel.FavoriteMovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {

    private FavoriteMovieAdapter adapter;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        RecyclerView rv = view.findViewById(R.id.rv_favorite_movie);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);

        adapter = new FavoriteMovieAdapter();
        rv.setAdapter(adapter);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FavoriteMovieViewModel favoriteMovieViewModel = new ViewModelProvider(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.getAllFavoriteMovie().observe(getViewLifecycleOwner(), new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                adapter.setFavoriteMovies(favoriteMovies);
            }
        });


    }
}
