package com.adeeva.movieworldextendedapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShowItems implements Parcelable {
    private int id;
    private String title, release, overview, posterPath, voteAverage, backdropPath;

    public TvShowItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String overview = object.getString("overview");
            String release = object.getString("first_air_date");
            String posterPath = ("https://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
            String voteAverage = object.getString("vote_average");
            String backdropPath = ("https://image.tmdb.org/t/p/w400" + object.getString("backdrop_path"));

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.release = release;
            this.posterPath = posterPath;
            this.voteAverage = voteAverage;
            this.backdropPath = backdropPath;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TvShowItems() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteAverage);
        dest.writeString(this.backdropPath);
    }

    protected TvShowItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.backdropPath = in.readString();
    }

    public static final Creator<TvShowItems> CREATOR = new Creator<TvShowItems>() {
        @Override
        public TvShowItems createFromParcel(Parcel source) {
            return new TvShowItems(source);
        }

        @Override
        public TvShowItems[] newArray(int size) {
            return new TvShowItems[size];
        }
    };
}

