package com.dicoding.submissionmade2_1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.submissionmade2_1.item.FavoriteMovie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Insert
    void insert(FavoriteMovie favoriteMovie);

    @Query("DELETE FROM favorite_table WHERE id_movie = :idMovie")
    void deleteByMovieId(int idMovie);

    @Query("SELECT * FROM favorite_table")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovie();

    @Query("SELECT * FROM favorite_table WHERE id_movie = :id")
    LiveData<List<FavoriteMovie>> getFavoriteMovieById(int id);


}
