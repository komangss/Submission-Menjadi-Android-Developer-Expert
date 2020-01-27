package com.dicoding.submissionmade2_1.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dicoding.submissionmade2_1.Dao.FavoriteMovieDao;
import com.dicoding.submissionmade2_1.Item.FavoriteMovie;

@Database(entities = {FavoriteMovie.class}, version = 1)
public abstract class FavoriteMovieDatabase extends RoomDatabase {

    private static FavoriteMovieDatabase instance;

    public abstract FavoriteMovieDao favoriteMovieDao();

    public static synchronized FavoriteMovieDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteMovieDatabase.class, "favorite_movie_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
