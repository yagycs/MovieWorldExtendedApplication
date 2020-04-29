package com.adeeva.movieworldextendedapplication.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.adeeva.movieworldextendedapplication.fragment.FavoriteMovieFragment;
import com.adeeva.movieworldextendedapplication.fragment.FavoriteTvFragment;

public class TabAdapterFavorite extends FragmentPagerAdapter {

    public TabAdapterFavorite(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new FavoriteMovieFragment();
        }
        return new FavoriteTvFragment();

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Movies";
        }
        return "Tv Shows";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
