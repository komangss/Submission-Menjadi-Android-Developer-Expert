package com.dicoding.submissionMade.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.ListMovieAdapter;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.viewModel.MovieViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ListMovieAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private MovieViewModel moviesViewModel;
    private SearchView searchViewMovie;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout lyt_progress;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

//        layout for progress bounce
        lyt_progress = view.findViewById(R.id.lyt_progress);
        showLoading(true);

        adapter = new ListMovieAdapter();
        recyclerView = view.findViewById(R.id.rv_movies);
        ctx = this.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);

        moviesViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        moviesViewModel.getMovie().observe(getViewLifecycleOwner(), getMovies);

        searchViewMovie = view.findViewById(R.id.search_movie); // inititate a search view
        searchViewMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                showLoading(true);

                ArrayList<Movie> result = moviesViewModel.getResultSearch(query);
                adapter.setData(result);

                showLoading(false);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullAndRefresh();
            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void pullAndRefresh() {
        swipeProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                recyclerView.setAdapter(adapter);
                moviesViewModel.getMovie();

                searchViewMovie.setQuery("", false);
                searchViewMovie.clearFocus();

                swipeProgress(false);
            }
        }, 3000);
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            swipeRefreshLayout.setRefreshing(show);
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            lyt_progress.setVisibility(View.VISIBLE);
            lyt_progress.setAlpha(1.0f);
        } else {
            lyt_progress.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }

            showLoading(false);

        }
    };
}