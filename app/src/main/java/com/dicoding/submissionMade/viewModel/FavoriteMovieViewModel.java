package com.dicoding.submissionMade.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dicoding.submissionMade.item.FavoriteMovie;
import com.dicoding.submissionMade.repository.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private FavoriteMovieRepository repository;
    private LiveData<List<FavoriteMovie>> allFavoriteMovie;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteMovieRepository(application);
        allFavoriteMovie = repository.getAllFavoriteMovies();
    }

    public void insert(FavoriteMovie favoriteMovie) {
        repository.insert(favoriteMovie);
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovieById(int idMovie) {
        return repository.getFavoriteMovieById(idMovie);
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovie() {
        return allFavoriteMovie;
    }

    public void deleteMovieById(int idMovie) {
        repository.deleteByMovieId(idMovie);
    }
}
