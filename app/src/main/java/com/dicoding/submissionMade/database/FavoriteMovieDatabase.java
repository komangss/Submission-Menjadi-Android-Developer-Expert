package com.dicoding.submissionMade.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dicoding.submissionMade.dao.FavoriteMovieDao;
import com.dicoding.submissionMade.item.FavoriteMovie;

@Database(entities = {FavoriteMovie.class}, version = 1)
public abstract class FavoriteMovieDatabase extends RoomDatabase {

    private static FavoriteMovieDatabase instance;

    public abstract FavoriteMovieDao favoriteMovieDao();

    public static synchronized FavoriteMovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteMovieDatabase.class, "favorite_movie_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
}
