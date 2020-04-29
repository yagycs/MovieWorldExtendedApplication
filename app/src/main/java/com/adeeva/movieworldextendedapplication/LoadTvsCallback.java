package com.adeeva.movieworldextendedapplication;

import com.adeeva.movieworldextendedapplication.entity.TvShowItems;

import java.util.ArrayList;

public interface LoadTvsCallback {
    void preExecute();

    void postExecute(ArrayList<TvShowItems> tvShowItems);

}
