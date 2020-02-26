package com.dicoding.submissionMade.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.item.Movie;
import com.dicoding.submissionMade.notification.MovieDailyReceiver;
import com.dicoding.submissionMade.notification.MovieUpcomingReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationSettingActivity extends PreferenceActivity {


    private static final String API_KEY = "d9c1d6e1b7d10d2ad0ac0c8e7e9abb81";

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

        public class GetMovieTask extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {
                getData(strings[0]);
                return null;
            }
        }

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
                    setReleaseAlarm();
                } else {
                    mMovieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }
            return true;
        }

        private void setReleaseAlarm() {
            MainPreferenceFragment.GetMovieTask getDataAsync = new MainPreferenceFragment.GetMovieTask();
            getDataAsync.execute("https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US");
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

        public void getData(String url) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String today = dateFormat.format(date);

            AndroidNetworking.get(url)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray list = response.getJSONArray("results");
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject movie = list.getJSONObject(i);
                                    if (movie.getString("release_date").equals(today)) {
                                        Movie movieItems = new Movie(movie);
                                        mNotifList.add(movieItems);
                                    }
                                }
                                mMovieUpcomingReceiver.setAlarm(getActivity(), mNotifList);
                            } catch (JSONException e) {
                                Log.d("Exception", e.getMessage());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("onFailure", anError.getMessage());
                        }
                    });
        }
    }
}
