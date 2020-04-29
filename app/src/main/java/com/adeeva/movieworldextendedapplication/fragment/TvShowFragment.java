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
import com.adeeva.movieworldextendedapplication.ViewModel.TvMainViewModel;
import com.adeeva.movieworldextendedapplication.adapter.TvShowAdapter;
import com.adeeva.movieworldextendedapplication.entity.TvShowItems;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private TvShowAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public TvShowFragment() {
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

        TvMainViewModel tvMainViewModel = ViewModelProviders.of(this).get(TvMainViewModel.class);
        tvMainViewModel.LANGUAGE = Objects.requireNonNull(getContext()).getString(R.string.language_code);
        tvMainViewModel.getTvShows().observe(this, getTvShows);

        recyclerView = view.findViewById(R.id.movie_list);

        showRecycler();

        progressBar = view.findViewById(R.id.progressBar);

        tvMainViewModel.setTv();
        showLoading(true);
    }

    private void showRecycler() {
        adapter = new TvShowAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
        //    @Override
        //    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        //        Toast.makeText(getContext(), getString(R.string.you_choose) + Objects.requireNonNull(tvMainViewModel.getTvShows().getValue()).get(position).getTitle(), Toast.LENGTH_SHORT).show();

        //        Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
        //        intent.putExtra(TvShowDetailActivity.EXTRA_TV, tvMainViewModel.getTvShows().getValue().get(position));
        //        startActivity(intent);

        //     }
        //});
    }

    private Observer<ArrayList<TvShowItems>> getTvShows = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShowItems> tvShowItems) {
            if (tvShowItems != null) {
                adapter.setData(tvShowItems);
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
