package com.dicoding.submissionMade.repository;

import android.os.AsyncTask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.dicoding.submissionMade.item.TvShow;
import com.dicoding.submissionMade.viewModel.TvShowViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class TvShowRepository {
    public void getDataFromApi(String url) {
        new GetDataFromApiAsyncTask().execute(url);
    }

    private static class GetDataFromApiAsyncTask extends AsyncTask<String, Void, ArrayList<TvShow>> {

        @Override
        protected ArrayList<TvShow> doInBackground(String... strings) {
            ArrayList<TvShow> listTvShow = new ArrayList<>();
            ANRequest request = AndroidNetworking.get(strings[0]).setPriority(Priority.LOW).setExecutor(Executors.newSingleThreadExecutor()).build();
            ANResponse<JSONObject> response = request.executeForJSONObject();

            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResult();
                JSONArray list;
                try {
                    list = jsonObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShowJsonObject = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(tvShowJsonObject);
                        listTvShow.add(tvShow);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                //handle error
            }
            return listTvShow;
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            TvShowViewModel.listTvShow.postValue(tvShows);
        }
    }

}
