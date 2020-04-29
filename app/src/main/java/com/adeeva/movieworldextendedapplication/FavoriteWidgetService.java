package com.adeeva.movieworldextendedapplication;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.adeeva.movieworldextendedapplication.widget.StackRemoteViewsFactory;

public class FavoriteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }
}
