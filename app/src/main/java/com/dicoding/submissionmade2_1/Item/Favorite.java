package com.dicoding.submissionmade2_1.Item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Favorite(int id_movie) {
        this.id_movie = id_movie;
    }

    private int id_movie;

    public int getId() {
        return id;
    }

    public int getId_movie() {
        return id_movie;
    }

    public void setId_movie(int id_movie) {
        this.id_movie = id_movie;
    }

    public void setId(int id) {
        this.id = id;
    }
}
