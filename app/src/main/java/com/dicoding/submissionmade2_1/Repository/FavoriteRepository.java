package com.dicoding.submissionmade2_1.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dicoding.submissionmade2_1.Dao.FavoriteDao;
import com.dicoding.submissionmade2_1.Database.FavoriteDatabase;
import com.dicoding.submissionmade2_1.Item.Favorite;

import java.util.List;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavoritesData;
    private static LiveData<List<Favorite>> dataFavoriteById;

    public FavoriteRepository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        favoriteDao = database.favoriteDao();
        allFavoritesData = favoriteDao.getAllFavorites();
    }

    public void insert(Favorite favorite) {
        new InsertFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void update(Favorite favorite) {
        new UpdateFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete(Favorite favorite) {
        new DeleteFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavoritesData;
    }

    public LiveData<List<Favorite>> checkMoviesById(int id_movie) {
        new CheckMovieById(id_movie, favoriteDao).execute();
        return dataFavoriteById;
    }

    private static class CheckMovieById extends AsyncTask<Void, Void, Void> {
        private FavoriteDao favoriteDao;
        private int idMovie;

        private CheckMovieById(int idMovie, FavoriteDao favoriteDao) {
            this.idMovie = idMovie;
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            LiveData<List<Favorite>> favoriteData = favoriteDao.checkMoviesById(idMovie);
            FavoriteRepository.dataFavoriteById = favoriteData;
            return null;
        }
    }







    private static class InsertFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private InsertFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

    private static class UpdateFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private UpdateFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.update(favorites[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.delete(favorites[0]);
            return null;
        }
    }
}

