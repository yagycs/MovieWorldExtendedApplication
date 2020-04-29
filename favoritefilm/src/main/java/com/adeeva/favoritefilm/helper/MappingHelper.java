package com.adeeva.favoritefilm.helper;

import android.database.Cursor;

import com.adeeva.favoritefilm.entity.MovieItems;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.COLUMN_BACKDROP;
import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION;
import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.COLUMN_RATE;
import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.COLUMN_RELEASE;
import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.COLUMN_TITLE;

public class MappingHelper {

    public static ArrayList<MovieItems> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<MovieItems> moviesList = new ArrayList<>();

        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_RELEASE));
            String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_POSTER));
            String backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_BACKDROP));
            String rate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_RATE));
            moviesList.add(new MovieItems(id, title, release, description, poster, backdrop, rate));
        }

        return moviesList;
    }
}
