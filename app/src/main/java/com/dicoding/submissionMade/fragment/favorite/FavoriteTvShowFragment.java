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
import com.dicoding.submissionMade.adapter.FavoriteTvShowAdapter;
import com.dicoding.submissionMade.item.FavoriteTvShow;
import com.dicoding.submissionMade.viewModel.FavoriteTvShowViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {

    private FavoriteTvShowAdapter adapter;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_favorite_tv_show);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new FavoriteTvShowAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FavoriteTvShowViewModel favoriteMovieViewModel = new ViewModelProvider(this).get(FavoriteTvShowViewModel.class);
        favoriteMovieViewModel.getAllFavoriteTvShow().observe(getViewLifecycleOwner(), new Observer<List<FavoriteTvShow>>() {
            @Override
            public void onChanged(List<FavoriteTvShow> favoriteTvShows) {
//              update recycler view
                adapter.setFavoriteTvShows(favoriteTvShows);
            }
        });
    }
}
