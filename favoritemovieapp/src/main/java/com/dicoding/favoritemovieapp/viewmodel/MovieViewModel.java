package com.dicoding.favoritemovieapp.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.favoritemovieapp.model.Movie;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

    private Uri CONTENT_URI = Uri.parse("content://com.dicoding.submissionMade/favorite_movie_table");

    public void setData(Context context) {
        Cursor cur = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        ArrayList<Movie> result = new ArrayList<>();

        while (cur.moveToNext()) {
            Movie data = new Movie(
                    cur.getString(cur.getColumnIndexOrThrow("title")), // title
                    cur.getString(cur.getColumnIndexOrThrow("description")), // description
                    cur.getString(cur.getColumnIndexOrThrow("poster")), // poster path
                    cur.getInt(cur.getColumnIndexOrThrow("id_movie")) // id movie
            );
            result.add(data);
        }

        movieList.postValue(result);
    }

    public LiveData<ArrayList<Movie>> getData() {
        return movieList;
    }

}
