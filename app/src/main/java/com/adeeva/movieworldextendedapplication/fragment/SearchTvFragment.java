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
import com.adeeva.movieworldextendedapplication.ViewModel.TvLoader;
import com.adeeva.movieworldextendedapplication.adapter.TvShowAdapter;
import com.adeeva.movieworldextendedapplication.entity.TvShowItems;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTvFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<TvShowItems>> {

    RecyclerView recyclerView;
    TvShowAdapter tvShowAdapter;

    ArrayList<TvShowItems> tvShowItems;
    static final String EXTRAS_TV = "extras_tv";

    public SearchTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.id_listSearchTv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowItems = new ArrayList<>();

        tvShowAdapter = new TvShowAdapter(getContext());
        tvShowAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(tvShowAdapter);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getContext()).getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = view.findViewById(R.id.id_searchTv);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Objects.requireNonNull(getActivity()).getComponentName()));
            searchView.onActionViewExpanded();
            searchView.setQueryHint(getResources().getString(R.string.insert_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    query = searchView.getQuery().toString();
                    if (TextUtils.isEmpty(query)) return false;
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_TV, query);
                    getLoaderManager().restartLoader(0, bundle, SearchTvFragment.this);
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = searchView.getQuery().toString();
                    if (TextUtils.isEmpty(newText)) return false;
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_TV, newText);
                    getLoaderManager().restartLoader(0, bundle, SearchTvFragment.this);
                    return false;
                }
            });

            String title = searchView.getQuery().toString();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_TV, title);

            getLoaderManager().initLoader(0, bundle, SearchTvFragment.this);
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<TvShowItems>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String tv = "";
        if (bundle != null) {
            tv = bundle.getString(EXTRAS_TV);
        }
        return new TvLoader(Objects.requireNonNull(getActivity()), tv);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<TvShowItems>> loader, ArrayList<TvShowItems> tvShowItems) {
        tvShowAdapter.setData(tvShowItems);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<TvShowItems>> loader) {
        tvShowAdapter.setData(null);
    }
}
