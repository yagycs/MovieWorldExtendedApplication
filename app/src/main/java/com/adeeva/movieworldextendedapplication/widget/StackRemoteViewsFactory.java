package com.adeeva.movieworldextendedapplication.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.db.FavoriteMovieHelper;
import com.adeeva.movieworldextendedapplication.entity.MovieItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<MovieItems> movieList;
    private Context mContext;
    private int mAppWidgetId;
    private FavoriteMovieHelper helper;
    private MovieItems movieItems;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        helper = new FavoriteMovieHelper(mContext);
        helper.open();
        movieList = new ArrayList<>();
        movieList.addAll(helper.getAllMovies());
        helper.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(movieList.get(position).getBackdropPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("log", movieList.get(position).getBackdropPath());
        rv.setImageViewBitmap(R.id.imageViewWidget, bmp);
        rv.setTextViewText(R.id.tv_title, movieList.get(position).getTitle());

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);
        rv.setOnClickFillInIntent(R.id.imageViewWidget, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
