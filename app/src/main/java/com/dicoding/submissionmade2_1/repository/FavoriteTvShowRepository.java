package com.dicoding.submissionmade2_1.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dicoding.submissionmade2_1.dao.FavoriteTvShowDao;
import com.dicoding.submissionmade2_1.database.FavoriteTvShowDatabase;
import com.dicoding.submissionmade2_1.item.FavoriteTvShow;

import java.util.List;

public class FavoriteTvShowRepository {
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> allFavoriteTvShows;
    private static LiveData<List<FavoriteTvShow>> dataFavoriteMovieById;


    public FavoriteTvShowRepository(Application application) {
        FavoriteTvShowDatabase database = FavoriteTvShowDatabase.getInstance(application);
        favoriteTvShowDao = database.favoriteTvShowDao();
        allFavoriteTvShows = favoriteTvShowDao.getAllFavoriteTvShow();
    }


    //    database operation method
    public void insert(FavoriteTvShow tvShow) {
        new InsertFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(tvShow);
    }

    private static class InsertFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        private InsertFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... tvShows) {
            favoriteTvShowDao.insert(tvShows[0]);
            return null;
        }
    }

    public void delete(FavoriteTvShow tvShow) {
        new DeleteFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(tvShow);
    }

    private static class DeleteFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        private DeleteFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... tvShow) {
            favoriteTvShowDao.delete(tvShow[0]);
            return null;
        }
    }


    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows() {
        return allFavoriteTvShows;
    }

    public LiveData<List<FavoriteTvShow>> getFavoriteTvShowById(int idTvShow) {
        dataFavoriteMovieById = favoriteTvShowDao.getFavoriteTvShowById(idTvShow);
        return dataFavoriteMovieById;
    }

    public void deleteByMovieId(int idTvShow) {
        new DeleteByMovieIdAsyncTask(favoriteTvShowDao).execute(idTvShow);
    }

    private static class DeleteByMovieIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        FavoriteTvShowDao favoriteTvShowDao;

        DeleteByMovieIdAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            favoriteTvShowDao.deleteByTvShowId(integers[0]);
            return null;
        }
    }
}
