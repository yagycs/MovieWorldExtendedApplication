package com.adeeva.movieworldextendedapplication.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.db.FavoriteMovieHelper;
import com.adeeva.movieworldextendedapplication.entity.MovieItems;
import com.adeeva.movieworldextendedapplication.widget.FavoriteWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static android.provider.BaseColumns._ID;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_BACKDROP;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_DESCRIPTION;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_POSTER;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RATE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_RELEASE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.COLUMN_TITLE;
import static com.adeeva.movieworldextendedapplication.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    private TextView tvDetailTitleMovie, tvDetailReleaseMovie, tvDetailDescriptionMovie, tvDetailRateMovie;
    private int idMovie;
    private FloatingActionButton floatingActionButton;
    private FavoriteMovieHelper favoriteMovieHelper;
    private MovieItems movieItems;
    private int position;
    String textTitle, textRelease, textDescription, imgPoster, imgBackdrop, textRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvDetailTitleMovie = findViewById(R.id.detail_tv_title_movie);
        tvDetailReleaseMovie = findViewById(R.id.detail_tv_release_movie);
        tvDetailDescriptionMovie = findViewById(R.id.detail_tv_description_movie);
        ImageView imgDetailMovie = findViewById(R.id.detail_img_movie);
        ImageView imgdetailBackdrop = findViewById(R.id.detail_backdrop);
        tvDetailRateMovie = findViewById(R.id.detail_rate_movie);
        ImageView imgShare = findViewById(R.id.imgShareMovie);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        imgShare.setOnClickListener(this);

        floatingActionButton = findViewById(R.id.ic_favorite);
        floatingActionButton.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);

        movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        idMovie = movieItems.getId();
        textTitle = movieItems.getTitle();
        textRelease = movieItems.getRelease();
        textDescription = movieItems.getOverview();
        imgPoster = movieItems.getPosterPath();
        imgBackdrop = movieItems.getBackdropPath();
        textRate = movieItems.getVoteAverage();

        tvDetailTitleMovie.setText(textTitle);
        tvDetailReleaseMovie.setText(textRelease);
        tvDetailDescriptionMovie.setText(textDescription);
        tvDetailRateMovie.setText(textRate);
        Glide.with(MovieDetailActivity.this)
                .load(imgPoster)
                .apply(new RequestOptions().override(350, 550).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(imgDetailMovie);

        Glide.with(MovieDetailActivity.this)
                .load(imgBackdrop)
                .apply(new RequestOptions().override(550, 350))
                .into(imgdetailBackdrop);

        if (imgPoster != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }

        favoriteMovieHelper = FavoriteMovieHelper.getInstance(getApplicationContext());
        favoriteMovieHelper.open();

        if (favoriteMovieHelper.checkData(idMovie)) {
            floatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(textTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ic_favorite) {

            //String title = tvDetailTitleMovie.getText().toString().trim();
            //String release = tvDetailReleaseMovie.getText().toString().trim();
            //String description = tvDetailDescriptionMovie.getText().toString().trim();
            //String rate = tvDetailRateMovie.getText().toString().trim();

            //movieItems.setId(idMovie);
            //movieItems.setTitle(title);
            //movieItems.setRelease(release);
            //movieItems.setOverview(description);
            //movieItems.setPosterPath(movieItems.getPosterPath());
            //movieItems.setVoteAverage(rate);
            //movieItems.setBackdropPath(movieItems.getBackdropPath());

            //Intent intent = new Intent();
            //intent.putExtra(EXTRA_MOVIE, movieItems);
            //intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(_ID, idMovie);
            values.put(COLUMN_TITLE, textTitle);
            values.put(COLUMN_RELEASE, textRelease);
            values.put(COLUMN_DESCRIPTION, textDescription);
            values.put(COLUMN_RATE, textRate);
            values.put(COLUMN_POSTER, imgPoster);
            values.put(COLUMN_BACKDROP, imgBackdrop);

            if (!favoriteMovieHelper.checkData(idMovie)) {
                getContentResolver().insert(CONTENT_URI, values);
                floatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                Snackbar.make(v, getResources().getString(R.string.save), Snackbar.LENGTH_SHORT).show();

                int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FavoriteWidget.class));
                for (int id : widgetIDs) {
                    AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(id, R.id.stack_view);
                }

            } else {
                getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + idMovie), null, null);
                floatingActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                Snackbar.make(v, getResources().getString(R.string.delete), Snackbar.LENGTH_SHORT).show();

                int widgetID[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FavoriteWidget.class));
                for (int ids : widgetID) {
                    AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(ids, R.id.stack_view);
                }

            }

        } else if (v.getId() == R.id.imgShareMovie) {
            Intent intent = new Intent();
            movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String title = movieItems.getTitle();
            String release = movieItems.getRelease();
            String overview = movieItems.getOverview();

            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Title : " + title + "\n\nOverview : " + overview + "\n\nRelease Date : " + release);
            intent.setType("text/plain");
            Intent.createChooser(intent, "Share via");
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteMovieHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
