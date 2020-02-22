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
    private final Context context;
    private Uri CONTENT_URI = Uri.parse("content://com.dicoding.submissionMade/favorite_movie_table");


    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        initData();
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

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        FavoriteMovie movie = movies.get(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/" + "w500" + movie.getPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        rv.setImageViewBitmap(R.id.imageView, bitmap);
        Bundle extras = new Bundle();
        extras.putInt(ImagesBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
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
    public long getItemId(int position) {
        long id;
        if (!movies.isEmpty()) id = (long) movies.get(0).getId();
        else {
            id = (long) position;
        }
        return id;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("Recycle")
    private void initData() {
        movies.clear();
        Cursor cur = context.getContentResolver().query(CONTENT_URI, null, null, null, null);

        if (cur != null) {
            movies.addAll(mapCursorToArrayList(cur));
            cur.close();
        }
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
