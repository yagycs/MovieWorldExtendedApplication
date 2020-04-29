package com.adeeva.favoritefilm.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adeeva.favoritefilm.R;
import com.adeeva.favoritefilm.entity.MovieItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.adeeva.favoritefilm.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

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
    private MovieItems movieItems;
    private int position;
    String textTitle, textRelease, textDescription, imgPoster, imgBackdrop, textRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvDetailTitleMovie = findViewById(R.id.detail_tv_title_movie);
        tvDetailReleaseMovie = findViewById(R.id.detail_tv_release_movie);
        tvDetailDescriptionMovie = findViewById(R.id.detail_tv_description_movie);
        ImageView imgDetailMovie = findViewById(R.id.detail_img_movie);
        ImageView imgdetailBackdrop = findViewById(R.id.detail_backdrop);
        tvDetailRateMovie = findViewById(R.id.detail_rate_movie);
        ImageView imgShare = findViewById(R.id.imgShareMovie);
        imgShare.setOnClickListener(this);

        floatingActionButton = findViewById(R.id.ic_favorite);
        floatingActionButton.setOnClickListener(this);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) movieItems = new MovieItems(cursor);
                cursor.close();
            }
        }

        if (movieItems != null){
            floatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);

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

            getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + idMovie), null, null);
            floatingActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            Snackbar.make(v, getResources().getString(R.string.delete), Snackbar.LENGTH_SHORT).show();
            finish();

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
    public void onDestroy() {
        super.onDestroy();
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


