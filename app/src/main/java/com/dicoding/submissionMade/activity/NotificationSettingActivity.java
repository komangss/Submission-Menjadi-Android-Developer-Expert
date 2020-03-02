package com.dicoding.submissionMade.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.notification.MovieDailyReceiver;
import com.dicoding.submissionMade.notification.MovieUpcomingReceiver;

import java.util.ArrayList;
import java.util.List;

public class NotificationSettingActivity extends PreferenceActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        List<Movie> mNotifList;
        MovieDailyReceiver mMovieDailyReceiver = new MovieDailyReceiver();
        MovieUpcomingReceiver mMovieUpcomingReceiver = new MovieUpcomingReceiver();
        SwitchPreference mSwitchReminder;
        SwitchPreference mSwitchToday;


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean value = (boolean) newValue;
            if (key.equals(getString(R.string.key_today_reminder))) {
                if (value) {
                    mMovieDailyReceiver.setAlarm(getActivity());
                } else {
                    mMovieDailyReceiver.cancelAlarm(getActivity());
                }
            } else {
                if (value) {
                    mMovieUpcomingReceiver.setAlarm(getActivity());
//                    setReleaseAlarm();
                } else {
                    mMovieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }
            return true;
        }


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.notification_setting_preference);
            mNotifList = new ArrayList<>();
            mSwitchReminder = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
            mSwitchReminder.setOnPreferenceChangeListener(this);
            mSwitchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            mSwitchToday.setOnPreferenceChangeListener(this);
        }

    }
}
