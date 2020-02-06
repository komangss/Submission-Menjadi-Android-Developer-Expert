package com.dicoding.submissionmade2_1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dicoding.submissionmade2_1.dao.FavoriteMovieDao;
import com.dicoding.submissionmade2_1.database.FavoriteMovieDatabase;
import com.dicoding.submissionmade2_1.item.FavoriteMovie;

import java.util.List;

public class FavoriteMovieRepository {
    private FavoriteMovieDao favoriteMovieDao;
    private LiveData<List<FavoriteMovie>> allFavoriteMovies;


    public FavoriteMovieRepository(Application application) {
        FavoriteMovieDatabase database = FavoriteMovieDatabase.getInstance(application);
        favoriteMovieDao = database.favoriteMovieDao();
        allFavoriteMovies = favoriteMovieDao.getAllFavoriteMovie();
    }


    //    database operation method
    public void insert(FavoriteMovie movie) {
        new InsertFavoriteMovieAsyncTask(favoriteMovieDao).execute(movie);
    }

    private static class InsertFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private InsertFavoriteMovieAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            favoriteMovieDao.insert(movies[0]);
            return null;
        }
    }

    public void delete(FavoriteMovie movie) {
        new DeleteFavoriteMovieAsyncTask(favoriteMovieDao).execute(movie);
    }

    private static class DeleteFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovie, Void, Void> {
        private FavoriteMovieDao favoriteMovieDao;

        private DeleteFavoriteMovieAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            favoriteMovieDao.delete(movies[0]);
            return null;
        }
    }


    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovieById(int id_movie) {
        LiveData<List<FavoriteMovie>> dataFavoriteMovieById = favoriteMovieDao.getFavoriteMovieById(id_movie);
        return dataFavoriteMovieById;
    }

    public void deleteByMovieId(int id_movie) {
        new DeleteByMovieIdAsyncTask(favoriteMovieDao).execute(id_movie);
    }

    private static class DeleteByMovieIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        FavoriteMovieDao favoriteMovieDao;

        DeleteByMovieIdAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            favoriteMovieDao.deleteByMovieId(integers[0]);
            return null;
        }
    }
}
