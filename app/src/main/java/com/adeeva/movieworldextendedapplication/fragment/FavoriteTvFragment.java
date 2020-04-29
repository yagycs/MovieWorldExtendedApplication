package com.adeeva.movieworldextendedapplication.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.adeeva.movieworldextendedapplication.LoadTvsCallback;
import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.activity.TvShowDetailActivity;
import com.adeeva.movieworldextendedapplication.adapter.TvShowAdapter;
import com.adeeva.movieworldextendedapplication.db.FavoriteTvHelper;
import com.adeeva.movieworldextendedapplication.entity.TvShowItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements LoadTvsCallback {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private TvShowAdapter adapter;
    private FavoriteTvHelper tvHelper;

    public FavoriteTvFragment() {
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

        tvHelper = FavoriteTvHelper.getInstance(getContext());
        tvHelper.open();

        adapter = new TvShowAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTvsAsync(tvHelper, this).execute();
        } else {
            ArrayList<TvShowItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListTvs());
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShowItems> tvShowItems) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setData(tvShowItems);
    }

    private static class LoadTvsAsync extends AsyncTask<Void, Void, ArrayList<TvShowItems>> {

        private final WeakReference<FavoriteTvHelper> weakTvHelper;
        private final WeakReference<LoadTvsCallback> weakCallback;

        private LoadTvsAsync(FavoriteTvHelper tvHelper, LoadTvsCallback callback) {
            weakTvHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShowItems> doInBackground(Void... voids) {

            return weakTvHelper.get().getAllTvs();

        }

        @Override
        protected void onPostExecute(ArrayList<TvShowItems> tvShowItems) {
            super.onPostExecute(tvShowItems);

            weakCallback.get().postExecute(tvShowItems);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == TvShowDetailActivity.REQUEST_ADD) {
                if (resultCode == TvShowDetailActivity.RESULT_ADD) {
                    TvShowItems tvShowItems = data.getParcelableExtra(TvShowDetailActivity.EXTRA_TV);

                    adapter.addItem(tvShowItems);
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (requestCode == TvShowDetailActivity.REQUEST_UPDATE) {
                if (resultCode == TvShowDetailActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(TvShowDetailActivity.EXTRA_POSITION, 0);

                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }


}
