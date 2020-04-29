package com.adeeva.movieworldextendedapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adeeva.movieworldextendedapplication.entity.TvShowItems;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_BACKDROP;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_DESCRIPTION;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_POSTER;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_RATE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_RELEASE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.COLUMN_TV_TITLE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteTvColumns.TABLE_NAME_TV;

public class FavoriteTvHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_TV;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteTvHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteTvHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteTvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteTvHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TvShowItems> getAllTvs() {
        ArrayList<TvShowItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShowItems tvShowItems;
        if (cursor.getCount() > 0) {
            do {
                tvShowItems = new TvShowItems();
                tvShowItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShowItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_TITLE)));
                tvShowItems.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_RELEASE)));
                tvShowItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_DESCRIPTION)));
                tvShowItems.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_POSTER)));
                tvShowItems.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_BACKDROP)));
                tvShowItems.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_RATE)));

                arrayList.add(tvShowItems);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean checkData(int id) {
        Cursor cursor;
        cursor = database.rawQuery("select * from " + DATABASE_TABLE + " where " + _ID + " = " + id + "", null);
        cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insertMovie(TvShowItems tvShowItems) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShowItems.getId());
        args.put(COLUMN_TV_TITLE, tvShowItems.getTitle());
        args.put(COLUMN_TV_RELEASE, tvShowItems.getRelease());
        args.put(COLUMN_TV_DESCRIPTION, tvShowItems.getOverview());
        args.put(COLUMN_TV_POSTER, tvShowItems.getPosterPath());
        args.put(COLUMN_TV_BACKDROP, tvShowItems.getBackdropPath());
        args.put(COLUMN_TV_RATE, tvShowItems.getVoteAverage());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateMovie(TvShowItems tvShowItems) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShowItems.getId());
        args.put(COLUMN_TV_TITLE, tvShowItems.getTitle());
        args.put(COLUMN_TV_RELEASE, tvShowItems.getRelease());
        args.put(COLUMN_TV_DESCRIPTION, tvShowItems.getOverview());
        args.put(COLUMN_TV_POSTER, tvShowItems.getPosterPath());
        args.put(COLUMN_TV_BACKDROP, tvShowItems.getBackdropPath());
        args.put(COLUMN_TV_RATE, tvShowItems.getVoteAverage());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + tvShowItems.getId() + "'", null);
    }

    /**
     * Gunakan method ini untuk getAllNotes deleteNote
     *
     * @param id id yang akan di deleteNote
     * @return int jumlah row yang di deleteNote
     */
    public int deleteMovie(int id) {
        return database.delete(TABLE_NAME_TV, _ID + " = '" + id + "'", null);
    }
}
