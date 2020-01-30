package com.dicoding.submissionmade2_1.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dicoding.submissionmade2_1.Dao.FavoriteMovieDao;
import com.dicoding.submissionmade2_1.Dao.FavoriteTvShowDao;
import com.dicoding.submissionmade2_1.Item.FavoriteMovie;
import com.dicoding.submissionmade2_1.Item.FavoriteTvShow;

@Database(entities = {FavoriteTvShow.class}, version = 1)
public abstract class FavoriteTvShowDatabase extends RoomDatabase {

    private static FavoriteTvShowDatabase instance;

    public abstract FavoriteTvShowDao favoriteTvShowDao();

    public static synchronized FavoriteTvShowDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteTvShowDatabase.class, "favorite_movie_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        public PopulateDbAsyncTask(FavoriteTvShowDatabase db) {
            favoriteTvShowDao = db.favoriteTvShowDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            insert dummy data
            favoriteTvShowDao.insert(new FavoriteTvShow("test 1", "test 1", "test 1", 1));
            favoriteTvShowDao.insert(new FavoriteTvShow("test 2", "test 2", "test 2", 1));
            favoriteTvShowDao.insert(new FavoriteTvShow("test 3", "test 3", "test 3", 1));
            return null;
        }
    }
}
