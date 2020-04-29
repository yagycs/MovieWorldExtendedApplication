package com.adeeva.movieworldextendedapplication.activity;

import android.content.Intent;
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
import com.adeeva.movieworldextendedapplication.db.FavoriteTvHelper;
import com.adeeva.movieworldextendedapplication.entity.TvShowItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TV = "extra_tv";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    private TextView tvDetailTitleTv, tvDetailReleaseTv, tvDetailDescriptionTv, tvDetailRateTv;
    private int idTv;
    private FloatingActionButton floatingActionButton;
    private FavoriteTvHelper favoriteTvHelper;
    private TvShowItems tvShowItems;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        tvDetailTitleTv = findViewById(R.id.detail_tv_title_tv);
        tvDetailReleaseTv = findViewById(R.id.detail_tv_release_tv);
        tvDetailDescriptionTv = findViewById(R.id.detail_tv_description_movie);
        ImageView imgDetailTv = findViewById(R.id.detail_img_tv);
        ImageView imgdetailBackdropTv = findViewById(R.id.detail_backdrop_tv);
        tvDetailRateTv = findViewById(R.id.detail_rate_tv);
        ImageView imgShareTv = findViewById(R.id.imgShareTv);
        imgShareTv.setOnClickListener(this);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        floatingActionButton = findViewById(R.id.ic_favorite_tv);
        floatingActionButton.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);

        tvShowItems = getIntent().getParcelableExtra(EXTRA_TV);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        idTv = tvShowItems.getId();
        String textTitle = tvShowItems.getTitle();
        String textRelease = tvShowItems.getRelease();
        String textDescription = tvShowItems.getOverview();
        String imgPoster = tvShowItems.getPosterPath();
        String imgBackdrop = tvShowItems.getBackdropPath();
        String textRate = tvShowItems.getVoteAverage();

        tvDetailTitleTv.setText(textTitle);
        tvDetailReleaseTv.setText(textRelease);
        tvDetailDescriptionTv.setText(textDescription);
        tvDetailRateTv.setText(textRate);
        Glide.with(TvShowDetailActivity.this)
                .load(imgPoster)
                .apply(new RequestOptions().override(350, 550).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
                .into(imgDetailTv);

        Glide.with(TvShowDetailActivity.this)
                .load(imgBackdrop)
                .apply(new RequestOptions().override(550, 350))
                .into(imgdetailBackdropTv);

        if (imgPoster != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }

        favoriteTvHelper = FavoriteTvHelper.getInstance(getApplicationContext());
        favoriteTvHelper.open();

        if (favoriteTvHelper.checkData(idTv)) {
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
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ic_favorite_tv) {

            String title = tvDetailTitleTv.getText().toString().trim();
            String release = tvDetailReleaseTv.getText().toString().trim();
            String description = tvDetailDescriptionTv.getText().toString().trim();
            String rate = tvDetailRateTv.getText().toString().trim();

            tvShowItems.setId(idTv);
            tvShowItems.setTitle(title);
            tvShowItems.setRelease(release);
            tvShowItems.setOverview(description);
            tvShowItems.setPosterPath(tvShowItems.getPosterPath());
            tvShowItems.setVoteAverage(rate);
            tvShowItems.setBackdropPath(tvShowItems.getBackdropPath());

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TV, tvShowItems);
            intent.putExtra(EXTRA_POSITION, position);

            if (!favoriteTvHelper.checkData(idTv)) {
                long result = favoriteTvHelper.insertMovie(tvShowItems);
                if (result > 0) {
                    tvShowItems.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                    Snackbar.make(v, getResources().getString(R.string.save), Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, getResources().getString(R.string.failed), Snackbar.LENGTH_SHORT).show();
                }
            } else {
                long result = favoriteTvHelper.deleteMovie(tvShowItems.getId());
                if (result > 0) {
                    intent.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_DELETE, intent);
                    floatingActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    Snackbar.make(v, getResources().getString(R.string.delete), Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(v, getResources().getString(R.string.delete_failed), Snackbar.LENGTH_SHORT).show();
                }
            }
        } else if (v.getId() == R.id.imgShareTv) {
            Intent intent = new Intent();
            tvShowItems = getIntent().getParcelableExtra(EXTRA_TV);
            String title = tvShowItems.getTitle();
            String release = tvShowItems.getRelease();
            String overview = tvShowItems.getOverview();

            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Title : " + title + "\n\nOverview : " + overview + "\n\nRelease Date : " + release);
            intent.setType("text/plain");
            Intent.createChooser(intent, "Share via");
            startActivity(intent);
        }
    }
}
