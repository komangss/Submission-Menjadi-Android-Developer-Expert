package com.dicoding.submissionMade.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dicoding.submissionMade.MainActivity;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.ListMovieAdapter;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.viewModel.MovieViewModel;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ListMovieAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private MovieViewModel moviesViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout lyt_progress;
    private TextView tvResult;
    private String keyword = "";

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle(getResources().getString(R.string.action_bar_title_movie));
        setHasOptionsMenu(true);

        ctx = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        tvResult = view.findViewById(R.id.tv_result);

//        layout for progress bounce
        lyt_progress = view.findViewById(R.id.lyt_progress);
        showLoading(true);

        adapter = new ListMovieAdapter();
        recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);

        moviesViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        moviesViewModel.getMovie().observe(getViewLifecycleOwner(), getMovies);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullAndRefresh();
            }
        });

        return view;

    }

    private void pullAndRefresh() {
        swipeProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                recyclerView.setAdapter(adapter);

                moviesViewModel.loadMovie();

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
                String movieSize = Integer.toString(movies.size());
                String result = getResources().getString(R.string.result_with_item, movieSize);
                tvResult.setText(result);
                adapter.setData(movies);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            }

//            Todo: cek jika datanya 0 berarti tidak ada movie

            showLoading(false);

        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem mSearchAction = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchAction.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    keyword = query;
                    loadData(keyword);
                } else {
                    keyword = "";
                    loadData(keyword);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    keyword = newText;
                    loadData(keyword);
                } else {
                    keyword = "";
                    loadData(keyword);
                }
                return false;
            }
        });
    }

    private void loadData(String keyword) {
        if (keyword.equals("")) {
            moviesViewModel.getMovie();
        } else {
            moviesViewModel.searchMovie(keyword);
        }
    }
}