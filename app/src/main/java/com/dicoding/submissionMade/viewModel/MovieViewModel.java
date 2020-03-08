package com.dicoding.submissionMade.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.submissionMade.BuildConfig;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.repository.MovieRepository;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {
    private MovieRepository movieRepository = new MovieRepository();
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    public static MutableLiveData<ArrayList<Movie>> listMovie;

    public LiveData<ArrayList<Movie>> getMovie() {
        if (listMovie == null) {
            listMovie = new MutableLiveData<>();
            loadMovie();
        }
        return listMovie;
    }

    public void loadMovie() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        movieRepository.getDataFromApi(url);
    }

    public void searchMovie(String query) {
        movieRepository.getSearchResult(query);
    }
}
