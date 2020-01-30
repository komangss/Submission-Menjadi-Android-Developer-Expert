package com.dicoding.submissionmade2_1.Item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_tv_show_table")
public class FavoriteTvShow {

    @PrimaryKey(autoGenerate = true)
    private int id;

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
