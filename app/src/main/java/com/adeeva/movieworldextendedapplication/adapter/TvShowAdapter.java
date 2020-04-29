package com.adeeva.movieworldextendedapplication.adapter;

import android.content.Context;
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
import com.adeeva.movieworldextendedapplication.activity.TvShowDetailActivity;
import com.adeeva.movieworldextendedapplication.entity.TvShowItems;
import com.adeeva.movieworldextendedapplication.listener.CustomOnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {
    private ArrayList<TvShowItems> mData = new ArrayList<>();
    private Context context;

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvShowItems> getListTvs() {
        return mData;
    }

    public void setData(ArrayList<TvShowItems> items) {

        if (items.size() > 0) {
            this.mData.clear();
        }
        this.mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(TvShowItems item) {
        this.mData.add(item);
        notifyItemInserted(mData.size() - 1);
        //notifyDataSetChanged();
    }

    public void updateItem(int position, TvShowItems tvShowItems) {
        this.mData.set(position, tvShowItems);
        notifyItemChanged(position, tvShowItems);
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
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        return new TvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder tvShowViewHolder, int position) {
        tvShowViewHolder.tvTitle.setText(mData.get(position).getTitle());
        tvShowViewHolder.tvRelease.setText(mData.get(position).getRelease());
        tvShowViewHolder.tvDescription.setText(mData.get(position).getOverview());
        tvShowViewHolder.tvRate.setText(mData.get(position).getVoteAverage());

        Glide.with(context)
                .load(mData.get(position).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(tvShowViewHolder.imgPoster);
        tvShowViewHolder.cardView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, TvShowDetailActivity.class);
                //intent.putExtra(TvShowDetailActivity.EXTRA_POSITION, position);
                intent.putExtra(TvShowDetailActivity.EXTRA_TV, mData.get(position));
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease, tvDescription, tvRate;
        ImageView imgPoster;
        CardView cardView;

        TvShowViewHolder(@NonNull View itemView) {
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
