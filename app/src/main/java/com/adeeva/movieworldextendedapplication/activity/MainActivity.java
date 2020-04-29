package com.adeeva.movieworldextendedapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.fragment.FavoriteFragment;
import com.adeeva.movieworldextendedapplication.fragment.HomeFragment;
import com.adeeva.movieworldextendedapplication.fragment.SearchFragment;
import com.adeeva.movieworldextendedapplication.fragment.SearchTvFragment;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Fragment pageContent = new HomeFragment();
    private String title = "Home";

    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://www.dicoding.com/images/small/avatar/20190530100152332db43c0271c278be0e6e44dca1f61a.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        final DrawerLayout drawerLayout = findViewById(R.id.main_drawer);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        pageContent = new HomeFragment();
                        title = "Home";
                        break;

                    case R.id.nav_favorite:
                        pageContent = new FavoriteFragment();
                        title = "Favorite";
                        break;

                    case R.id.nav_searchMovie:
                        pageContent = new SearchFragment();
                        title = "Search Movie";
                        break;

                    case R.id.nav_searchTv:
                        pageContent = new SearchTvFragment();
                        title = "Search Tv";
                        break;

                    case R.id.nav_set:
                        Intent setIntent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(setIntent);
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, pageContent)
                        .commit();
                toolbar.setTitle(title);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, pageContent)
                    .commit();
            toolbar.setTitle(title);
        } else {
            pageContent = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            title = savedInstanceState.getString("title");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, pageContent)
                    .commit();
            toolbar.setTitle(title);
        }

        profileCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Glide.with(MainActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("title", title);
        getSupportFragmentManager().putFragment(outState, "fragment", pageContent);
        super.onSaveInstanceState(outState);
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu){
    //    getMenuInflater().inflate(R.menu.main_menu, menu);
    //    return super.onCreateOptionsMenu(menu);
    //}

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item){
    //    if (item.getItemId() == R.id.action_change_settings){
    //        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
    //        startActivity(intent);
    //    }
    //    return super.onOptionsItemSelected(item);
    //}
}
