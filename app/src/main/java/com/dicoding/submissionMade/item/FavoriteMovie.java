package com.dicoding.submissionMade.item;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteMovie.TABLE_NAME)
public class FavoriteMovie {

    /**
     * The name of the Cheese table.
     */
    public static final String TABLE_NAME = "favorite_movie_table";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int id;

    private String poster, title, description;
    private int id_movie;

    /**
     * The name of the ID column.
     */
    private static final String COLUMN_ID = BaseColumns._ID;

    public FavoriteMovie(String poster, String title, String description, int id_movie) {
        this.poster = poster;
        this.title = title;
        this.description = description;
        this.id_movie = id_movie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId_movie() {
        return id_movie;
    }
}
