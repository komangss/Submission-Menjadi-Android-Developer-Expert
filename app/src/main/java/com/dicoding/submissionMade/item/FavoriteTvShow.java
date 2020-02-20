package com.dicoding.submissionMade.item;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = FavoriteTvShow.TABLE_NAME)
public class FavoriteTvShow {

    public static final String TABLE_NAME = "favorite_tv_show_table";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int id;

    /**
     * The name of the ID column.
     */
    private static final String COLUMN_ID = BaseColumns._ID;


    private String poster, title, description;
    private int id_tv_show;

    public FavoriteTvShow(String poster, String title, String description, int id_tv_show) {
        this.poster = poster;
        this.title = title;
        this.description = description;
        this.id_tv_show = id_tv_show;
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

    public int getId_tv_show() {
        return id_tv_show;
    }
}
