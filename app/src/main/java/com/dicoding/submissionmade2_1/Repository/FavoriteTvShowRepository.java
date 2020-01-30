package com.dicoding.submissionmade2_1.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dicoding.submissionmade2_1.Dao.FavoriteTvShowDao;
import com.dicoding.submissionmade2_1.Database.FavoriteTvShowDatabase;
import com.dicoding.submissionmade2_1.Item.FavoriteTvShow;

import java.util.List;

public class FavoriteTvShowRepository {
    private FavoriteTvShowDao favoriteTvShowDao;
    private LiveData<List<FavoriteTvShow>> allFavoriteTvShows;
    protected static LiveData<List<FavoriteTvShow>> dataFavoriteMovieById;


    public FavoriteTvShowRepository(Application application) {
        FavoriteTvShowDatabase database = FavoriteTvShowDatabase.getInstance(application);
        favoriteTvShowDao = database.favoriteTvShowDao();
        allFavoriteTvShows = favoriteTvShowDao.getAllFavoriteTvShow();
    }


    //    database operation method
    public void insert(FavoriteTvShow movie) {
        new InsertFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(movie);
    }

    private static class InsertFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        private InsertFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... movies) {
            favoriteTvShowDao.insert(movies[0]);
            return null;
        }
    }

    public void delete(FavoriteTvShow movie) {
        new DeleteFavoriteTvShowAsyncTask(favoriteTvShowDao).execute(movie);
    }

    private static class DeleteFavoriteTvShowAsyncTask extends AsyncTask<FavoriteTvShow, Void, Void> {
        private FavoriteTvShowDao favoriteTvShowDao;

        private DeleteFavoriteTvShowAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(FavoriteTvShow... movies) {
            favoriteTvShowDao.delete(movies[0]);
            return null;
        }
    }


    public LiveData<List<FavoriteTvShow>> getAllFavoriteTvShows() {
        return allFavoriteTvShows;
    }

    public LiveData<List<FavoriteTvShow>> getFavoriteTvShowById(int id_movie) {
        new GetFavoriteTvShowByIdAsyncTask(favoriteTvShowDao).execute(id_movie);
        return dataFavoriteMovieById;
    }

    private static class GetFavoriteTvShowByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        FavoriteTvShowDao favoriteTvShowDao;

        public GetFavoriteTvShowByIdAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            FavoriteTvShowRepository.dataFavoriteMovieById = favoriteTvShowDao.getFavoriteTvShowById(integers[0]);
            return null;
        }
    }

    public void deleteByMovieId(int id_movie) {
        new DeleteByMovieIdAsyncTask(favoriteTvShowDao).execute(id_movie);
    }

    private static class DeleteByMovieIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        FavoriteTvShowDao favoriteTvShowDao;

        public DeleteByMovieIdAsyncTask(FavoriteTvShowDao favoriteTvShowDao) {
            this.favoriteTvShowDao = favoriteTvShowDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            favoriteTvShowDao.deleteByTvShowId(integers[0]);
            return null;
        }
    }
}
