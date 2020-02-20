package com.dicoding.submissionMade;

import android.content.Context;
import android.database.Cursor;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.dicoding.submissionMade.dao.FavoriteMovieDao;
import com.dicoding.submissionMade.database.FavoriteMovieDatabase;
import com.dicoding.submissionMade.item.FavoriteMovie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MovieProviderTest {

    private FavoriteMovieDao favoriteMovieDao;
    private FavoriteMovieDatabase db;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.databaseBuilder(context, FavoriteMovieDatabase.class, "favorite_movie_database").build();
        favoriteMovieDao = db.favoriteMovieDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void getMovieProvider() {
        FavoriteMovie favoriteMovie = new FavoriteMovie("poster", "title", "des", 123);
        favoriteMovieDao.insert(favoriteMovie);
        Cursor cur = favoriteMovieDao.getAllFavoriteMovieProvider();
//        List<User> byName = userDao.findUsersByName("george");
//        assertThat(cur, equalTo(favoriteMovie));
    }
}
