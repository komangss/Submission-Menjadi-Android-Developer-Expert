package com.dicoding.submissionmade2_1.Item;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;


public class Movie implements Parcelable {
    private String poster, title, description;

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

//    untuk Stringent parcable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.poster);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
    }

    public Movie(JSONObject object) {
        try {
            String title = object.getString("title");
            String description = object.getString("overview");
            String poster_path = object.getString("poster_path");

            this.title = title;
            this.description = description;
            this.poster = poster_path;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Movie(Parcel in) {
        poster = in.readString();
        title = in.readString();
        description = in.readString();
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
}

