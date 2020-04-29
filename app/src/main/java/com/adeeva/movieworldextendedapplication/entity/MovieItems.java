package com.adeeva.movieworldextendedapplication.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_BACKDROP;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RATE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RELEASE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.getColumnInt;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    private int id;
    private String title, release, overview, posterPath, voteAverage, backdropPath;

    public MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release = object.getString("release_date");
            String posterPath = ("https://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
            String voteAverage = object.getString("vote_average");
            String backdropPath = ("https://image.tmdb.org/t/p/w300" + object.getString("backdrop_path"));

            this.id = id;
            this.title = title;
            this.release = release;
            this.overview = overview;
            this.posterPath = posterPath;
            this.voteAverage = voteAverage;
            this.backdropPath = backdropPath;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MovieItems() {

    }

    public MovieItems(int id, String title, String release, String description, String poster, String backdrop, String rate) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.overview = description;
        this.posterPath = poster;
        this.backdropPath = backdrop;
        this.voteAverage = rate;
    }

    public MovieItems(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, COLUMN_TITLE);
        this.release = getColumnString(cursor, COLUMN_RELEASE);
        this.overview = getColumnString(cursor, COLUMN_DESCRIPTION);
        this.posterPath = getColumnString(cursor, COLUMN_POSTER);
        this.backdropPath = getColumnString(cursor, COLUMN_BACKDROP);
        this.voteAverage = getColumnString(cursor, COLUMN_RATE);
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

    private MovieItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.backdropPath = in.readString();
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}

