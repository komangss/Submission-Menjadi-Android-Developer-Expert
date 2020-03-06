package com.dicoding.submissionMade.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.submissionMade.BuildConfig;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.repository.MovieRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MovieViewModel extends ViewModel {
    private final ArrayList<Movie> filteredList = new ArrayList<>();
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

    private void loadMovie() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        movieRepository.getDataFromApi(url);
    }

    public ArrayList<Movie> getResultSearch(String query) {
//        movieRepository.getSearchDataFromApi();
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
                        } catch (JSONException e) {
                            Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("onFailure", Objects.requireNonNull(anError.getMessage()));
                    }
                });
        return filteredList;
    }
}
