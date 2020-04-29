package com.adeeva.movieworldextendedapplication.fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.ViewModel.MovieMainViewModel;
import com.adeeva.movieworldextendedapplication.adapter.MovieAdapter;
import com.adeeva.movieworldextendedapplication.entity.MovieItems;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MovieMainViewModel movieMainViewModel = ViewModelProviders.of(this).get(MovieMainViewModel.class);
        movieMainViewModel.LANGUAGE = getString(R.string.language_code);
        movieMainViewModel.getMovies().observe(this, getMovies);

        recyclerView = view.findViewById(R.id.movie_list);

        showRecycler();

        progressBar = view.findViewById(R.id.progressBar);

        movieMainViewModel.setMovie();
        showLoading(true);

    }

    private void showRecycler() {
        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
        //    @Override
        //    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        //       Toast.makeText(getContext(), getString(R.string.you_choose) + Objects.requireNonNull(movieMainViewModel.getMovies().getValue()).get(position).getTitle(), Toast.LENGTH_SHORT).show();

        //       Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        //       intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieMainViewModel.getMovies().getValue().get(position));
        //        startActivity(intent);

        //    }
        //});

    }

    private Observer<ArrayList<MovieItems>> getMovies = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                adapter.setData(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
