package com.adeeva.movieworldextendedapplication.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.adapter.MovieAdapter;
import com.adeeva.movieworldextendedapplication.db.FavoriteMovieHelper;
import com.adeeva.movieworldextendedapplication.entity.MovieItems;

import java.util.ArrayList;

import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.adeeva.movieworldextendedapplication.helper.MappingHelper.mapCursorToArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment /*implements LoadMoviesCallback*/ {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private MovieAdapter adapter;
    private FavoriteMovieHelper movieHelper;
    private static HandlerThread handlerThread;
    //private DataObserver myObserver;
    ArrayList<MovieItems> listMovies;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.movie_list);
        progressBar = view.findViewById(R.id.progressBar);

        //handlerThread = new HandlerThread("DataObserver");
        //handlerThread.start();
        //Handler handler = new Handler(handlerThread.getLooper());
        //myObserver = new DataObserver(handler, getContext());
        //getActivity().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadMoviesAsync().execute();
        } else {
            ArrayList<MovieItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMoviesAsync().execute();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
    }

    //@Override
    //public void preExecute() {
    //    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
    //       @Override
    //        public void run() {
    //            progressBar.setVisibility(View.VISIBLE);
    //      }
    //    });
    //}

    //@Override
    //public void postExecute(Cursor movies) {
    //    progressBar.setVisibility(View.INVISIBLE);

    //   listMovies = mapCursorToArrayList(movies);
    //    if (listMovies.size() > 0) {
    //        adapter.setData(listMovies);
    //    } else {
    //        adapter.setData(new ArrayList<MovieItems>());
    //       showSnackbarMessage(getString(R.string.data_favorite));
    //   }
    //}

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
            return getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI, null, null, null, null);

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

    //public static class DataObserver extends ContentObserver {

    //    final Context context;

    //    public DataObserver(Handler handler, Context context) {
    //        super(handler);
    //       this.context = context;

//}

    //@Override
    //public void onChange(boolean selfChange) {
    //    super.onChange(selfChange);
    //    new LoadMoviesAsync(context, (LoadMoviesCallback) context).execute();
    //}
    //}

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

}
