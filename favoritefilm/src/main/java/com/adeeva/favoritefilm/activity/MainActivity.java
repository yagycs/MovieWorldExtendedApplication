package com.adeeva.favoritefilm.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adeeva.favoritefilm.R;
import com.adeeva.favoritefilm.adapter.MovieAdapter;
import com.adeeva.favoritefilm.entity.MovieItems;

import java.util.ArrayList;

import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.adeeva.favoritefilm.helper.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity /*implements LoadMoviesCallback*/ {

    private RecyclerView recyclerView;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieAdapter adapter;
    ArrayList<MovieItems> listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.movie_list);
        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        new LoadMoviesAsync().execute();

    }

    @Override
    public void onResume(){
        super.onResume();
        new LoadMoviesAsync().execute();
    }
    private class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {

        //private final WeakReference<Context> weakContext;
        //private final WeakReference<LoadMoviesCallback> weakCallback;

        //private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
        //    weakContext = new WeakReference<>(context);
        //    weakCallback = new WeakReference<>(callback);
        //}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            //Context context = weakContext.get();
            //return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            return getContentResolver().query(CONTENT_URI, null, null, null, null);

        }

        @Override
        protected void onPostExecute(Cursor movieItems) {
            super.onPostExecute(movieItems);
            //weakCallback.get().postExecute(movieItems);

            listMovies = mapCursorToArrayList(movieItems);
            adapter.setData(listMovies);
            adapter.notifyDataSetChanged();

            if (listMovies.size() == 0) {
                showSnackbarMessage(getString(R.string.data_favorite));
            }

        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

}
