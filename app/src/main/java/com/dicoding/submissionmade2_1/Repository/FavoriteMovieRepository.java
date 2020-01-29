package com.dicoding.submissionmade2_1.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dicoding.submissionmade2_1.Dao.FavoriteMovieDao;
import com.dicoding.submissionmade2_1.Database.FavoriteMovieDatabase;
import com.dicoding.submissionmade2_1.Item.FavoriteMovie;

import java.util.List;

public class FavoriteMovieRepository {
    private FavoriteMovieDao favoriteMovieDao;
    private LiveData<List<FavoriteMovie>> allFavoriteMovies;
    protected static LiveData<List<FavoriteMovie>> favoriteMovieById;


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
        new GetFavoriteMovieByIdAsyncTask(favoriteMovieDao).execute(id_movie);
        return favoriteMovieById;
    }

    private static class GetFavoriteMovieByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        FavoriteMovieDao favoriteMovieDao;

        public GetFavoriteMovieByIdAsyncTask(FavoriteMovieDao favoriteMovieDao) {
            this.favoriteMovieDao = favoriteMovieDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            FavoriteMovieRepository.favoriteMovieById = favoriteMovieDao.getFavoriteMovieById(integers[0]);
            return null;
        }
    }

}
