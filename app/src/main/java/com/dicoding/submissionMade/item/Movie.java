package com.dicoding.submissionMade.item;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {
    private double popularity;
    private int vote_count;
    private boolean video;
    private String poster_path;
    private int idMovie;
    private boolean adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    //    private ArrayList<Integer> genre_ids; // Todo : mencari cara agar bisa menampilkan genre ini
    private String title;
    private double vote_average;
    private String overview;
    private String release_date;

    public Movie(JSONObject object) {
        try {
            this.popularity = object.getDouble("popularity");
            this.vote_count = object.getInt("vote_count");
            this.video = object.getBoolean("video");
            this.poster_path = object.getString("poster_path");
            this.idMovie = object.getInt("id");
            this.adult = object.getBoolean("adult");
            this.backdrop_path = object.getString("backdrop_path");
            this.original_language = object.getString("original_language");
            this.original_title = object.getString("original_title");

            this.title = object.getString("title");
            this.vote_average = object.getDouble("vote_average");
            this.overview = object.getString("overview");
            this.release_date = object.getString("release_date");

//            JSONArray genre = object.getJSONArray("genre_ids"); // ini bikin bug
//            for (int i = 0; i < genre.length(); i++) {
//                this.genre_ids.add(genre.getInt(i));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Movie(Parcel in) {
        popularity = in.readDouble();
        vote_count = in.readInt();
        video = in.readByte() != 0;
        poster_path = in.readString();
        idMovie = in.readInt();
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        title = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        release_date = in.readString();
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

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

//    public ArrayList<Integer> getGenre_ids() {
//        return genre_ids;
//    }
//
//    public void setGenre_ids(ArrayList<Integer> genre_ids) {
//        this.genre_ids = genre_ids;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeInt(vote_count);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(poster_path);
        dest.writeInt(idMovie);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(title);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
        dest.writeString(release_date);
    }
}

