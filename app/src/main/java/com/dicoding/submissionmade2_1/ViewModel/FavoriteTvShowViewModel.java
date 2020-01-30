package com.dicoding.submissionmade2_1.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dicoding.submissionmade2_1.Item.FavoriteTvShow;
import com.dicoding.submissionmade2_1.Repository.FavoriteTvShowRepository;

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


    public void delete(FavoriteTvShow favoriteTvShow) {
        repository.delete(favoriteTvShow);
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
