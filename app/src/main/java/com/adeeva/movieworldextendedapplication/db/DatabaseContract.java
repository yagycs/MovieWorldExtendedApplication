package com.adeeva.movieworldextendedapplication.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.adeeva.movieworldextendedapplication";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class FavoriteColumns implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE = "release_date";
        public static final String COLUMN_DESCRIPTION = "overview";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_BACKDROP = "backdrop_path";
        public static final String COLUMN_RATE = "vote_average";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }

    public static final class FavoriteTvColumns implements BaseColumns {

        public static final String TABLE_NAME_TV = "favorite_tv";
        public static final String COLUMN_TV_TITLE = "name";
        public static final String COLUMN_TV_RELEASE = "first_air_date";
        public static final String COLUMN_TV_DESCRIPTION = "overview";
        public static final String COLUMN_TV_POSTER = "poster_path";
        public static final String COLUMN_TV_BACKDROP = "backdrop_path";
        public static final String COLUMN_TV_RATE = "vote_average";

    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

}
