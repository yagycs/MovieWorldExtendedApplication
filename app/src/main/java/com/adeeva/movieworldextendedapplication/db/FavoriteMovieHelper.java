package com.adeeva.movieworldextendedapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adeeva.movieworldextendedapplication.entity.MovieItems;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_BACKDROP;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RATE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RELEASE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.TABLE_NAME;

public class FavoriteMovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteMovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavoriteMovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
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

     /*
    METHOD DIBAWAH INI UNTUK QUERY YANG LAMA ATAU UNTUK PROJECT MovieLocalStorage
     */

    /**
     * Gunakan method ini untuk ambil semua movieItems yang ada
     * Otomatis di parsing ke dalam model MovieItems
     *
     * @return hasil query berbentuk array model movieItems
     */

    public ArrayList<MovieItems> getAllMovies() {
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MovieItems movieItems;
        if (cursor.getCount() > 0) {
            do {
                movieItems = new MovieItems();
                movieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movieItems.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE)));
                movieItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                movieItems.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER)));
                movieItems.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKDROP)));
                movieItems.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RATE)));

                arrayList.add(movieItems);
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

    public long insertMovie(MovieItems movieItems) {
        ContentValues args = new ContentValues();
        args.put(_ID, movieItems.getId());
        args.put(COLUMN_TITLE, movieItems.getTitle());
        args.put(COLUMN_RELEASE, movieItems.getRelease());
        args.put(COLUMN_DESCRIPTION, movieItems.getOverview());
        args.put(COLUMN_POSTER, movieItems.getPosterPath());
        args.put(COLUMN_BACKDROP, movieItems.getBackdropPath());
        args.put(COLUMN_RATE, movieItems.getVoteAverage());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateMovie(MovieItems movieItems) {
        ContentValues args = new ContentValues();
        args.put(_ID, movieItems.getId());
        args.put(COLUMN_TITLE, movieItems.getTitle());
        args.put(COLUMN_RELEASE, movieItems.getRelease());
        args.put(COLUMN_DESCRIPTION, movieItems.getOverview());
        args.put(COLUMN_POSTER, movieItems.getPosterPath());
        args.put(COLUMN_BACKDROP, movieItems.getBackdropPath());
        args.put(COLUMN_RATE, movieItems.getVoteAverage());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movieItems.getId() + "'", null);
    }

    /**
     * Gunakan method ini untuk getAllNotes deleteNote
     *
     * @param id id yang akan di deleteNote
     * @return int jumlah row yang di deleteNote
     */
    public int deleteMovie(int id) {
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    /*
    METHOD DI BAWAH INI ADALAH QUERY UNTUK CONTENT PROVIDER
    NILAI BALIK CURSOR
    */

    /**
     * Ambil data dari note berdasarakan parameter id
     * Gunakan method ini untuk ambil data di dalam provider
     *
     * @param id id movieItems yang dicari
     * @return cursor hasil query
     */
    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    /**
     * Ambil data dari semua movieItems yang ada di dalam database
     * Gunakan method ini untuk ambil data di dalam provider
     *
     * @return cursor hasil query
     */
    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    /**
     * Simpan data ke dalam database
     * Gunakan method ini untuk query insert di dalam provider
     *
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    /**
     * Update data dalam database
     *
     * @param id     data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int updateProvider(String id, ContentValues values) {
        //return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
        return database.update(DATABASE_TABLE, values, _ID + " = '" + id + "'", null);
    }

    /**
     * Delete data dalam database
     *
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id) {
        //return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }
}
