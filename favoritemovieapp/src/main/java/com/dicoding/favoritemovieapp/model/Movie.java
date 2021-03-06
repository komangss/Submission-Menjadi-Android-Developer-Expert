package com.dicoding.favoritemovieapp.model;


import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    private String poster, title, description;
    private int idMovie;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.poster);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeInt(this.idMovie);
    }

    public Movie(String title, String description, String poster_path, int idMovie) {
        try {
            this.title = title;
            this.description = description;
            this.poster = poster_path;
            this.idMovie = idMovie;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Movie(Parcel in) {
        poster = in.readString();
        title = in.readString();
        description = in.readString();
        idMovie = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getIdMovie() {
        return idMovie;
    }


}
