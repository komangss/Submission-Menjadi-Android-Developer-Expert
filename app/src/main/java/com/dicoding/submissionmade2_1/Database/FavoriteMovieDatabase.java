package com.dicoding.submissionmade2_1.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private FavoriteMovieDao favoriteMovieDao;
//
//        public PopulateDbAsyncTask(FavoriteMovieDatabase db) {
//            favoriteMovieDao = db.favoriteMovieDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
////            insert dummy data
//            favoriteMovieDao.insert(new FavoriteMovie("test 1", "test 1", "test 1", 1));
//            favoriteMovieDao.insert(new FavoriteMovie("test 2", "test 2", "test 2", 1));
//            favoriteMovieDao.insert(new FavoriteMovie("test 3", "test 3", "test 3", 1));
//            return null;
//        }
//    }
}
