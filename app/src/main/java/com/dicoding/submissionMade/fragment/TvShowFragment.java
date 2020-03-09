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
import com.dicoding.submissionMade.adapter.ListTvShowAdapter;
import com.dicoding.submissionMade.item.TvShow;
import com.dicoding.submissionMade.viewModel.TvShowViewModel;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout lyt_progress;
    private TextView tvResult;
    private String keyword = "";

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle(getResources().getString(R.string.action_bar_title_tv_show));
        setHasOptionsMenu(true);

        ctx = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        tvResult = view.findViewById(R.id.tv_result_tv_show);
        lyt_progress = view.findViewById(R.id.lyt_progress_tv_show);
        showLoading(true);

        adapter = new ListTvShowAdapter();
        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);

        tvShowViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), getTvShow);


        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullAndRefresh();
            }
        });

        showLoading(false);
        return view;

    }

    private void pullAndRefresh() {
        swipeProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                recyclerView.setAdapter(adapter);
                tvShowViewModel.loadTvShow();

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
                String tvShowSize = Integer.toString(tvShows.size());
                String result = getResources().getString(R.string.result_with_item, tvShowSize);
                tvResult.setText(result);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            tvShowViewModel.getTvShow();
        } else {
            tvShowViewModel.searchTvShow(keyword);
        }
    }
}