package com.dicoding.submissionmade2_1.Item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String poster, title, description;
    private int id_movie;

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
