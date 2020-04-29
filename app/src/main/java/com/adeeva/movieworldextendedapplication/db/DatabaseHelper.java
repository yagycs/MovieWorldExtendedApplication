package com.adeeva.movieworldextendedapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_BACKDROP;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RATE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RELEASE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.TABLE_NAME;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_BACKDROP;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_DESCRIPTION;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_POSTER;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_RATE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_RELEASE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_TITLE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.TABLE_NAME_TV;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbmovieapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            _ID,
            COLUMN_TITLE,
            COLUMN_RELEASE,
            COLUMN_DESCRIPTION,
            COLUMN_POSTER,
            COLUMN_BACKDROP,
            COLUMN_RATE
    );

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME_TV,
            _ID,
            COLUMN_TV_TITLE,
            COLUMN_TV_RELEASE,
            COLUMN_TV_DESCRIPTION,
            COLUMN_TV_POSTER,
            COLUMN_TV_BACKDROP,
            COLUMN_TV_RATE
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TV);
        onCreate(db);
    }
}
