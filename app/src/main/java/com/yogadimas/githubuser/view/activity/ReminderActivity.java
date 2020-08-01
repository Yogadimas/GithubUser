package com.yogadimas.githubuser.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yogadimas.githubuser.R;
import com.yogadimas.githubuser.reminder.ReminderReceiver;

import java.util.Objects;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String SHARED_PREF_REMINDER = "sharedprefreminder";
    public static final String REMINDER_KEY = "reminder_key";
    private ReminderReceiver mReminderReceiver;
    private Switch mSwitchDaily;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mSwitchDaily = findViewById(R.id.sw_daily);
        Toolbar mToolbarReminder = findViewById(R.id.toolbar_reminder);

        setSupportActionBar(mToolbarReminder);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.menu_title_daily_reminder);

        mReminderReceiver = new ReminderReceiver();

        mSharedPreferences = getSharedPreferences(SHARED_PREF_REMINDER,
                MODE_PRIVATE);

        stateSwitch();
        mSwitchDaily.setOnCheckedChangeListener(this);
    }

    private void stateSwitch() {
        mSwitchDaily.setChecked(false);
        if (mSharedPreferences.getString(REMINDER_KEY, null) != null) {
            mSwitchDaily.setChecked(true);
        } else {
            mSwitchDaily.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isChecked()) {
            setReminder();
        } else {
            cancelReminder();
        }
    }

    private void setReminder() {
        mReminderReceiver.setReminder(ReminderActivity.this);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(REMINDER_KEY, "Reminder Daily");
        mEditor.apply();
    }

    private void cancelReminder() {
        mReminderReceiver.cancelReminder(ReminderActivity.this);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.remove(REMINDER_KEY);
        mEditor.apply();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}