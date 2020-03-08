package com.dicoding.submissionMade.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.submissionMade.BuildConfig;
import com.dicoding.submissionMade.MainActivity;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.ListMovieAdapter;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.viewModel.MovieViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private SearchView searchViewMovie;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout lyt_progress;
    private TextView tvResult;

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

        searchViewMovie = view.findViewById(R.id.search_movie); // inititate a search view
        searchViewMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                showLoading(true);

                // Todo: menaruh search nya di viewmodel

                final ArrayList<Movie> filteredList = new ArrayList<>();
                final String API_KEY = BuildConfig.TMDB_API_KEY;
                String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;

                AndroidNetworking.get(url)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray list = response.getJSONArray("results");
                                    for (int i = 0; i < list.length(); i++) {
                                        JSONObject movie = list.getJSONObject(i);
                                        Movie movieItems = new Movie(movie);
                                        filteredList.add(movieItems);
                                    }

                                    String result = getResources().getString(R.string.result_with_item, Integer.toString(filteredList.size()));
                                    tvResult.setText(result);

                                    adapter.setData(filteredList);

                                } catch (JSONException e) {
                                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("onFailure", Objects.requireNonNull(anError.getMessage()));
                            }
                        });

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

                moviesViewModel.loadMovie();

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
                String movieSize = Integer.toString(movies.size());
                String result = getResources().getString(R.string.result_with_item, movieSize);
                tvResult.setText(result);
                adapter.setData(movies);
            }

            showLoading(false);

        }
    };
}