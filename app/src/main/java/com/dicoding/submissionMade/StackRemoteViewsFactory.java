package com.dicoding.submissionMade;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dicoding.submissionMade.item.FavoriteMovie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<FavoriteMovie> movies = new ArrayList<FavoriteMovie>();
    //    private final Context context;
    private Uri CONTENT_URI = Uri.parse("content://com.dicoding.submissionMade/favorite_movie_table");


    private Context mContext;

    private Cursor movieCursor;

    StackRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @SuppressLint("Recycle")
    private void initData() {
        movies.clear();

        movieCursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (movieCursor != null) {
            movies.addAll(mapCursorToArrayList(movieCursor));
//            movieCursor.close();
        }

    }

    @Override
    public void onCreate() {
        initData();
    }


    @Override
    public RemoteViews getViewAt(int i) {
        FavoriteMovie item = movies.get(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favourite_widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/" + "w342" + item.getPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(ImagesBannerWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }


    private FavoriteMovie getItem(int position) {
        if (!movieCursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        ArrayList<FavoriteMovie> movies = mapCursorToArrayList(movieCursor);
        FavoriteMovie movie = movies.get(0);
        return new FavoriteMovie(movie.getPoster(), movie.getTitle(), movie.getDescription(), movie.getId_movie());
    }

    @Override
    public int getCount() {
        return movieCursor.getCount();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        Long p;
        if (!movies.isEmpty()) {
            p = (long) movies.get(0).getId();
        } else {
            p = (long) movies.get(i).getId();
        }
        return p;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDataSetChanged() {
        long token = Binder.clearCallingIdentity();
        initData();
        Binder.restoreCallingIdentity(token);
    }

    @Override
    public void onDestroy() {

    }


    private ArrayList<FavoriteMovie> mapCursorToArrayList(Cursor cur) {
        ArrayList<FavoriteMovie> result = new ArrayList<>();
        while (cur.moveToNext()) {
            FavoriteMovie data = new FavoriteMovie(
                    cur.getString(cur.getColumnIndexOrThrow("poster")), // poster path
                    cur.getString(cur.getColumnIndexOrThrow("title")), // title
                    cur.getString(cur.getColumnIndexOrThrow("description")), // description
                    cur.getInt(cur.getColumnIndexOrThrow("id_movie")) // id movie
            );
            result.add(data);
        }
        return result;
    }
}
