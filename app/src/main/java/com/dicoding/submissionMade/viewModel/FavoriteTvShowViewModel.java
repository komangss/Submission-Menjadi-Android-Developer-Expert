package com.dicoding.submissionMade.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dicoding.submissionMade.item.FavoriteTvShow;
import com.dicoding.submissionMade.repository.FavoriteTvShowRepository;

import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {

    private FavoriteTvShowRepository repository;
    private LiveData<List<FavoriteTvShow>> allFavoriteTvShow;

    public FavoriteTvShowViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteTvShowRepository(application);
        allFavoriteTvShow = repository.getAllFavoriteTvShows();
    }

    public void insert(FavoriteTvShow favoriteTvShow) {
        repository.insert(favoriteTvShow);
    }

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShowById(int idMovie) {
        return repository.getFavoriteTvShowById(idMovie);
    }

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShow() {
        return allFavoriteTvShow;
    }

    public void deleteMovieById(int idMovie) {
        repository.deleteByMovieId(idMovie);
    }
}
