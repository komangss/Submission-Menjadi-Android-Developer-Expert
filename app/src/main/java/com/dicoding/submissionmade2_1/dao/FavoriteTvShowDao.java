package com.dicoding.submissionmade2_1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dicoding.submissionmade2_1.item.FavoriteTvShow;

import java.util.List;

@Dao
public interface FavoriteTvShowDao {

    @Insert
    void insert(FavoriteTvShow favoriteTvShow);

    @Update
    void update(FavoriteTvShow favoriteTvShow);

    @Delete
    void delete(FavoriteTvShow favoriteTvShow);

    @Query("DELETE FROM favorite_tv_show_table WHERE id_tv_show = :idTvShow")
    void deleteByTvShowId(int idTvShow);

    @Query("SELECT * FROM favorite_tv_show_table")
    LiveData<List<FavoriteTvShow>> getAllFavoriteTvShow();

    @Query("SELECT * FROM favorite_tv_show_table WHERE id_tv_show = :id")
    LiveData<List<FavoriteTvShow>> getFavoriteTvShowById(int id);


}
