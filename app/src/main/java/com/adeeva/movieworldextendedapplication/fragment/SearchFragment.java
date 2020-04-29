package com.adeeva.movieworldextendedapplication.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.ViewModel.MovieLoader;
import com.adeeva.movieworldextendedapplication.adapter.MovieAdapter;
import com.adeeva.movieworldextendedapplication.entity.MovieItems;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;

    ArrayList<MovieItems> movieItems;
    static final String EXTRAS_MOVIE = "extras_movie";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.id_listSearch);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieItems = new ArrayList<>();

        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(movieAdapter);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = view.findViewById(R.id.id_searchMovie);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
            searchView.onActionViewExpanded();
            searchView.setQueryHint(getResources().getString(R.string.insert_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    query = searchView.getQuery().toString();
                    if (TextUtils.isEmpty(query)) return false;
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_MOVIE, query);
                    getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = searchView.getQuery().toString();
                    if (TextUtils.isEmpty(newText)) return false;
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_MOVIE, newText);
                    getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
                    return false;
                }
            });

            String title = searchView.getQuery().toString();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, title);

            getLoaderManager().initLoader(0, bundle, SearchFragment.this);
        }

    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String movie = "";
        if (bundle != null) {
            movie = bundle.getString(EXTRAS_MOVIE);
        }

        return new MovieLoader(Objects.requireNonNull(getActivity()), movie);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        movieAdapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        movieAdapter.setData(null);
    }
}
