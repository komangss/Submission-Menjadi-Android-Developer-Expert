package com.dicoding.submissionMade.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dicoding.submissionMade.dao.FavoriteMovieDao;
import com.dicoding.submissionMade.dao.FavoriteTvShowDao;
import com.dicoding.submissionMade.database.FavoriteMovieDatabase;
import com.dicoding.submissionMade.database.FavoriteTvShowDatabase;
import com.dicoding.submissionMade.item.FavoriteMovie;
import com.dicoding.submissionMade.item.FavoriteTvShow;

public class MovieProvider extends ContentProvider {

    public static final String AUTHORITY = "com.dicoding.submissionMade";
    public static final String MOVIE_TABLE = FavoriteMovie.TABLE_NAME;
    private static final int MOVIE_DIR = 1;
    public static final String TV_TABLE = FavoriteTvShow.TABLE_NAME;
    private static final int TV_DIR = 2;
    public static Cursor cursor;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
//        content://com.dicoding.submissionMade/favorite_movie_table
        MATCHER.addURI(AUTHORITY, MOVIE_TABLE, MOVIE_DIR);

//        content://com.dicoding.submissionMade/
        MATCHER.addURI(AUTHORITY, TV_TABLE, TV_DIR);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);
        if (code == MOVIE_DIR || code == TV_DIR) { // 1 || 2

            final Context context = getContext();
            if (context == null) {
                return null;
            }

            final FavoriteMovieDao favoriteMovieDao = FavoriteMovieDatabase.getInstance(context).favoriteMovieDao();
            final FavoriteTvShowDao favoriteTvShowDao = FavoriteTvShowDatabase.getInstance(context).favoriteTvShowDao();

            Thread getMovie = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (code == MOVIE_DIR) {
                        cursor = favoriteMovieDao.getAllFavoriteMovieProvider();
                    } else {
                        cursor = favoriteTvShowDao.getAllFavoriteTvShowProvider();
                    }
                }
            });

            getMovie.start();
            try {
                getMovie.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
