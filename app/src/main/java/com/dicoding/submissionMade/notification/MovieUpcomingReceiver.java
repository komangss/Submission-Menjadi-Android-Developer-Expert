package com.dicoding.submissionMade.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.submissionMade.BuildConfig;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.item.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MovieUpcomingReceiver extends BroadcastReceiver {
    //    Todo: menampilkan semua movie yang rilis dengan Inbox Style, bedakan ID dari setiap notifikasi agar tidak ter - replace oleh notifikasi berikutnya
    //    Todo: mempercantik notifikasi

    private static int mNotifId = 2000;
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;

    private void sendNotification(Context context, String title, String mDesc, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(mDesc)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("11011",
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId("11011");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieUpcomingReceiver.class);
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void setAlarm(Context context) {
        int delay = 0;

        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieUpcomingReceiver.class);
        intent.putExtra("id", mNotifId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay, pendingIntent);
        }
        mNotifId += 1;
        delay += 3000;
        Toast.makeText(context, "Upcoming Notif ON", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String today = dateFormat.format(date);

        getReleaseMovieTodayThenSendNotif("https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today + "", context, id);

//        Example
        //        getReleaseMovieTodayThenSendNotif("https://api.themoviedb.org/3/discover/movie?api_key=d9c1d6e1b7d10d2ad0ac0c8e7e9abb81&primary_release_date.gte=2020-02-20&primary_release_date.lte=2020-02-20", context, id);

    }

    public void getReleaseMovieTodayThenSendNotif(String url, final Context ctx, final int notifId) {

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("results");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movieItems = new Movie(movie);
                                sendNotification(ctx, ctx.getString(R.string.app_name), ctx.getString(R.string.today_release) + " " + movieItems.getTitle(), notifId + i); // Untuk membedakan ID dari setiap notifikasi agar tidak ter-replace oleh notifikasi berikutnya.
                            }
                        } catch (JSONException e) {
                            Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("onFailure", Objects.requireNonNull(anError.getMessage()));
                    }
                });
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, "Upcoming Notif OFF", Toast.LENGTH_SHORT).show();
    }
}
