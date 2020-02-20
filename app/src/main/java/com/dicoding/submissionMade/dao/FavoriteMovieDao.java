package com.dicoding.submissionMade.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.submissionMade.item.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Insert
    void insert(FavoriteMovie favoriteMovie);

    @Query("DELETE FROM " + FavoriteMovie.TABLE_NAME + " WHERE id_movie = :idMovie")
    void deleteByMovieId(int idMovie);

    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME + "")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovie();

    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME + " WHERE id_movie = :id")
    LiveData<List<FavoriteMovie>> getFavoriteMovieById(int id);

    @Query("SELECT * FROM " + FavoriteMovie.TABLE_NAME)
    Cursor getAllFavoriteMovieProvider();

}
