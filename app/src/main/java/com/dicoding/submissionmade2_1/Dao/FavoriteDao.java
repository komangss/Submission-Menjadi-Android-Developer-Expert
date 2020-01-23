package com.dicoding.submissionmade2_1.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dicoding.submissionmade2_1.Item.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorite_table")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("SELECT * FROM favorite_table WHERE id_movie = :idMovie")
    LiveData<List<Favorite>> checkMoviesById(int idMovie);
}
