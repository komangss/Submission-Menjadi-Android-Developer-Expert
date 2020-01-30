package com.dicoding.submissionmade2_1.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dicoding.submissionmade2_1.Dao.FavoriteDao;
import com.dicoding.submissionmade2_1.Item.Favorite;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            execute on asyncTask
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public abstract FavoriteDao favoriteDao();

    //    Create Database
    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "favorite_database")
                    .addCallback(roomCallback) // when database created then do callback (insert dummy data)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteDao favoriteDao;

        private PopulateDbAsyncTask(FavoriteDatabase db) {
            favoriteDao = db.favoriteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoriteDao.insert(new Favorite(419704));
//            favoriteDao.insert(new Favorite());
//            favoriteDao.insert(new Favorite());
            return null;
        }
    }
}
