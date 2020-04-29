package com.adeeva.movieworldextendedapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.activity.MovieDetailActivity;
import com.adeeva.movieworldextendedapplication.entity.MovieItems;
import com.adeeva.movieworldextendedapplication.listener.CustomOnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private Activity activity;

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieItems> getListMovies() {
        return mData;
    }

    public void setData(ArrayList<MovieItems> items) {

        //if (items.size() > 0) {
            this.mData.clear();
        //}
        this.mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(MovieItems item) {
        this.mData.add(item);
        notifyItemInserted(mData.size() - 1);
        //notifyDataSetChanged();
    }

    public void updateItem(int position, MovieItems movieItems) {
        this.mData.set(position, movieItems);
        notifyItemChanged(position, movieItems);
    }

    public void removeItem(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.tvTitle.setText(getListMovies().get(position).getTitle());
        movieViewHolder.tvRelease.setText(getListMovies().get(position).getRelease());
        movieViewHolder.tvDescription.setText(getListMovies().get(position).getOverview());
        movieViewHolder.tvRate.setText(getListMovies().get(position).getVoteAverage());

        Glide.with(movieViewHolder.itemView)
                .load(getListMovies().get(position).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(movieViewHolder.imgPoster);

        movieViewHolder.cardView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, MovieDetailActivity.class);
                //intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);

                //Uri uri = Uri.parse(CONTENT_URI + "/" + getListMovies().get(position).getId());
                //intent.setData(uri);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mData.get(position));
                activity.startActivity(intent);
                //activity.startActivityForResult(intent, MovieDetailActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease, tvDescription, tvRate;
        ImageView imgPoster;
        CardView cardView;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvRelease = itemView.findViewById(R.id.item_release);
            tvDescription = itemView.findViewById(R.id.item_description);
            imgPoster = itemView.findViewById(R.id.item_img);
            tvRate = itemView.findViewById(R.id.item_rate_movie);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}

