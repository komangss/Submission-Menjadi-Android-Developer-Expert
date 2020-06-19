package com.dicoding.submissionMade.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.submissionMade.BuildConfig;
import com.dicoding.submissionMade.item.TvShow;
import com.dicoding.submissionMade.repository.TvShowRepository;

import java.util.ArrayList;

public class TvShowViewModel extends ViewModel {
    private TvShowRepository tvShowRepository = new TvShowRepository();
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static MutableLiveData<ArrayList<TvShow>> listTvShow;

    public LiveData<ArrayList<TvShow>> getTvShow() {
        if (listTvShow == null) {
            listTvShow = new MutableLiveData<>();
            loadTvShow();
        }
        return listTvShow;
    }

    public void loadTvShow() {
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        tvShowRepository.getDataFromApi(url);
    }

    public void searchTvShow(String query) {
        tvShowRepository.getSearchResult(query);
    }

}
