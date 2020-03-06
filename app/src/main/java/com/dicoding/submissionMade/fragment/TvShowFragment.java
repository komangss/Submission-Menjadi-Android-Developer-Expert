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
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.adapter.ListTvShowAdapter;
import com.dicoding.submissionMade.item.TvShow;
import com.dicoding.submissionMade.viewModel.TvShowViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private ListTvShowAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private TvShowViewModel tvShowViewModel;
    private SearchView searchViewTvShow;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout lyt_progress;
    private TextView tvResult;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        tvResult = view.findViewById(R.id.tv_result_tv_show);

        lyt_progress = view.findViewById(R.id.lyt_progress_tv_show);

        ctx = this.getContext();

        adapter = new ListTvShowAdapter();
        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);

        tvShowViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShow);

        showLoading(true);

        searchViewTvShow = view.findViewById(R.id.search_tv_show);
        searchViewTvShow.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showLoading(true);

                final ArrayList<TvShow> filteredList = new ArrayList<>();
                final String API_KEY = BuildConfig.TMDB_API_KEY;
                String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + query;

                AndroidNetworking.get(url)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray list = response.getJSONArray("results");
                                    for (int i = 0; i < list.length(); i++) {
                                        JSONObject tvShow = list.getJSONObject(i);
                                        TvShow tvShowItems = new TvShow(tvShow);
                                        filteredList.add(tvShowItems);
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

    private void pullAndRefresh() {
        swipeProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                recyclerView.setAdapter(adapter);
                tvShowViewModel.getTvShow();

                searchViewTvShow.setQuery("", false);
                searchViewTvShow.clearFocus();

                swipeProgress(false);
            }
        }, 3000);
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }


    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                String result = getResources().getString(R.string.result);
                String movieSize = Integer.toString(tvShows.size());
                tvResult.setText(result + " " + movieSize);
                adapter.setData(tvShows);
            }

            showLoading(false);

        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            lyt_progress.setVisibility(View.VISIBLE);
            lyt_progress.setAlpha(1.0f);
        } else {
            lyt_progress.setVisibility(View.GONE);
        }
    }
}