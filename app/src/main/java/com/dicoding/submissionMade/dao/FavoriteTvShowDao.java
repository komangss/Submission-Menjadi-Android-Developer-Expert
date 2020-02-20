package com.dicoding.submissionMade.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.submissionMade.item.FavoriteTvShow;

import java.util.List;

@Dao
public interface FavoriteTvShowDao {

    @Insert
    void insert(FavoriteTvShow favoriteTvShow);

    @Query("DELETE FROM favorite_tv_show_table WHERE id_tv_show = :idTvShow")
    void deleteByTvShowId(int idTvShow);

    @Query("SELECT * FROM favorite_tv_show_table")
    LiveData<List<FavoriteTvShow>> getAllFavoriteTvShow();

    @Query("SELECT * FROM favorite_tv_show_table WHERE id_tv_show = :id")
    LiveData<List<FavoriteTvShow>> getFavoriteTvShowById(int id);

    @Query("SELECT * FROM " + FavoriteTvShow.TABLE_NAME)
    Cursor getAllFavoriteTvShowProvider();
}
