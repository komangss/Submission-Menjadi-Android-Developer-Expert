package com.dicoding.submissionMade.repository;

import android.os.AsyncTask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.dicoding.submissionMade.BuildConfig;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.viewModel.MovieViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MovieRepository {
    public void getDataFromApi(String url) {
        new GetDataFromApiAsyncTask().execute(url);
    }

    static class GetDataFromApiAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            ArrayList<Movie> listMovie = new ArrayList<>();
            ANRequest request = AndroidNetworking.get(strings[0]).setPriority(Priority.LOW).setExecutor(Executors.newSingleThreadExecutor()).build();
            ANResponse<JSONObject> response = request.executeForJSONObject();

            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResult();
                JSONArray list;
                try {
                    list = jsonObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listMovie.add(movieItems);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                //handle error
            }
            return listMovie;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            MovieViewModel.listMovie.postValue(movies);
        }
    }


    public void getSearchResult(String query) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&query=" + query;
        new GetSearchResultAsyncTask().execute(url);
    }

    static class GetSearchResultAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            ArrayList<Movie> listMovie = new ArrayList<>();
            ANRequest request = AndroidNetworking.get(strings[0]).setPriority(Priority.LOW).setExecutor(Executors.newSingleThreadExecutor()).build();
            ANResponse<JSONObject> response = request.executeForJSONObject();

            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResult();
                JSONArray list;
                try {
                    list = jsonObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        listMovie.add(movieItems);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                //handle error
            }
            return listMovie;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            MovieViewModel.listMovie.postValue(movies);
        }
    }
}
