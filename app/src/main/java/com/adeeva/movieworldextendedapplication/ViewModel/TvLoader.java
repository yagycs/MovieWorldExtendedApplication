package com.adeeva.movieworldextendedapplication.ViewModel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.adeeva.movieworldextendedapplication.entity.TvShowItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvLoader extends AsyncTaskLoader<ArrayList<TvShowItems>> {
    private ArrayList<TvShowItems> tvShowItems;
    private boolean result = false;
    private String tv;

    public TvLoader(@NonNull Context context, String tv) {
        super(context);

        onContentChanged();
        this.tv = tv;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (result)
            deliverResult(tvShowItems);
    }

    @Override
    public void deliverResult(final ArrayList<TvShowItems> mTvShowItems) {
        tvShowItems = mTvShowItems;
        result = true;
        super.deliverResult(mTvShowItems);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (result) {
            tvShowItems = null;
            result = false;
        }
    }

    private static final String API_KEY = "7ecaf68c841768a7fcf63802fcb6dbcb";

    @Nullable
    @Override
    public ArrayList<TvShowItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<TvShowItems> tvShowItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + tv;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject tv = list.getJSONObject(i);
                        TvShowItems mTvShowItems = new TvShowItems(tv);
                        tvShowItems.add(mTvShowItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return tvShowItems;
    }
}
