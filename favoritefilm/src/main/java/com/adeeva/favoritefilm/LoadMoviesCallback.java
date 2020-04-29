package com.adeeva.favoritefilm;

import android.database.Cursor;

public interface LoadMoviesCallback {
    //void preExecute();

    //void postExecute(ArrayList<MovieItems> movieItems);
    void postExecute(Cursor movieItems);
}
