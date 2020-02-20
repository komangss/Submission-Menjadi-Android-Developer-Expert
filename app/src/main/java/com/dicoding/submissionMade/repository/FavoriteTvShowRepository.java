package com.dicoding.submissionMade.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dicoding.submissionMade.dao.FavoriteTvShowDao;
import com.dicoding.submissionMade.database.FavoriteTvShowDatabase;
import com.dicoding.submissionMade.item.FavoriteTvShow;

import java.util.List;

public class FavoriteTvShowRepository {
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> allFavoriteTvShows;


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

    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows() {
        return allFavoriteTvShows;
    }

    public LiveData<List<FavoriteTvShow>> getFavoriteTvShowById(int idTvShow) {
        return favoriteTvShowDao.getFavoriteTvShowById(idTvShow);
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
