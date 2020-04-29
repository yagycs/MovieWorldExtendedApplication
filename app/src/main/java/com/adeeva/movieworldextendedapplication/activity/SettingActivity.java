package com.adeeva.movieworldextendedapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.adeeva.movieworldextendedapplication.DailyReminderService;
import com.adeeva.movieworldextendedapplication.R;
import com.adeeva.movieworldextendedapplication.ReleaseTodayReminderService;
import com.adeeva.movieworldextendedapplication.SettingPreferences;

public class SettingActivity extends AppCompatActivity {

    private SettingPreferences preferences;

    TextView tvLanguage;
    Switch switchDaily, switchToday;

    DailyReminderService dailyReminderService;
    ReleaseTodayReminderService releaseTodayReminderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = new SettingPreferences(this);

        dailyReminderService = new DailyReminderService();
        releaseTodayReminderService = new ReleaseTodayReminderService();

        tvLanguage = findViewById(R.id.tv_change_language);
        switchDaily = findViewById(R.id.switch_daily_reminder);
        switchToday = findViewById(R.id.switch_release_today);

        switchDaily.setChecked(preferences.dailyChecked());
        switchToday.setChecked(preferences.releaseChecked());

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = switchDaily.isChecked();
                if (checked){
                    dailyReminderService.setDailyReminderNotif(getApplicationContext(),
                            DailyReminderService.TYPE_REPEATING,"07:00", getString(R.string.notif_msg));
                }else {
                    dailyReminderService.cancelDailyReminder(getApplicationContext(),
                            DailyReminderService.TYPE_REPEATING);
                }
                preferences.setDailyReminder(checked);
            }
        });

        switchToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = switchToday.isChecked();
                if (checked){
                    releaseTodayReminderService.setMovieReleaseNotif(getApplicationContext(),
                            ReleaseTodayReminderService.TYPE_REPEATING,"08:00");
                }else {
                    releaseTodayReminderService.cancelMovieNotif(getApplicationContext(),
                            ReleaseTodayReminderService.TYPE_REPEATING);
                }
                preferences.setReleaseTodayReminder(checked);
            }
        });

        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
