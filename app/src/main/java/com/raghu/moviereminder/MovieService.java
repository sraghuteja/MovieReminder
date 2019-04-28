package com.raghu.moviereminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.raghu.moviereminder.action.DisplayMovies;
import com.raghu.moviereminder.action.NotifyListener;
import com.raghu.moviereminder.action.TheareFoundActionListener;
import com.raghu.moviereminder.impl.DocumentFetcherImpl;
import com.raghu.moviereminder.pojo.VenueDetails;
import com.raghu.moviereminder.receiver.NotifyReceiver;
import com.raghu.moviereminder.runnable.Work;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MovieService extends Service implements NotifyListener, DisplayMovies, TheareFoundActionListener {
    private static final int NOTIFICATION_ID = 958;
    private static final String TAG = "MovieService";


    public static final String ACTION_NOTIFY = "com.raghu.movie.reminder.action.notify";
    public static final String ACTION_FETCH = "com.raghu.movie.reminder.action.fetch";
    public static final String ACTION_STOP = "com.raghu.movie.reminder.action.stop";
    private static final String ACTION_STOP_SOUND = "com.raghu.movie.reminder.action.stop.sound";
    public static final String URL = "com.raghu.movie.reminder.extra.url";
    public static final String THEATRES = "com.raghu.movie.reminder.extra.theatre";
    public static final String MOVIE_NAME = "com.raghu.movie.reminder.movie.name";

    public MovieService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private Notification.Builder mBuilder;

    private NotificationManager notificationManager;

    private LocalBroadcastManager broadcastManager;

    private ScheduledThreadPoolExecutor executor;

    //The currently running task
    private Runnable runnable;

    private MediaPlayer mediaPlayer;

    private BroadcastReceiver receiver = new NotifyReceiver(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Movie", "Service Created");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder = new Notification.Builder(this, TAG);
        } else {
            //noinspection deprecation
            mBuilder = new Notification.Builder(this);
        }
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Fetching")
                .setContentTitle("Movie Reminder");
        int corePoolSize = 1;
        executor = new ScheduledThreadPoolExecutor(corePoolSize);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        startForeground(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Movie", "OnStartCommand Called");
        onHandleIntent(intent);
        return START_REDELIVER_INTENT;
    }

    protected void onHandleIntent(Intent intent) {
        Log.e("Service", "onHandleIntent called");
        if (mBuilder == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder = new Notification.Builder(this, TAG);
            } else {
                //noinspection deprecation
                mBuilder = new Notification.Builder(this);
            }
        }
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFY.equals(action)) {
                final String movieUrl = intent.getStringExtra(URL);
                final String theatreCode = intent.getStringExtra(THEATRES);
                handleActionNotify(movieUrl, theatreCode);
            } else if (ACTION_STOP.equals(action)) {
                executor.shutdown();
                stopForeground(true);
            } else if (ACTION_STOP_SOUND.equals(action)) {
                stopSound();
            }
        }
    }

    public void handleActionNotify(@NonNull String url, @NonNull @Size(min = 4, max = 4) String
            theatre) {
        if (runnable != null) {
            executor.remove(runnable);
        }
        runnable = new Work(url, theatre, new DocumentFetcherImpl(),this, this);
        try {
            executor.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.MINUTES);
        } catch (RejectedExecutionException e) {
            executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.MINUTES);
        }
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter(ACTION_FETCH));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).
                unregisterReceiver(receiver);
        receiver = null;
        executor.shutdown();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    public void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public static final String[] MONTH = new String[]{
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"
    };

    @Override
    public void onTheatreFound(String theatre) {
        mBuilder.setContentText(String.format("Movie booking at %s is now started", theatre));
        mediaPlayer = MediaPlayer.create(MovieService.this, R.raw.notify);
        try {
            final AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager == null) {
                return;
            }
            final int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                    AudioManager.FLAG_PLAY_SOUND);
            Log.e(TAG, "Current volume: " + currentVolume);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_PLAY_SOUND);
                    executor.shutdown();
                    stopSelf();
                }
            });
            Intent stopSound = new Intent(MovieService.this, MovieService.class);
            stopSound.setAction(ACTION_STOP_SOUND);
            PendingIntent stopSoundIntent = PendingIntent.getService(MovieService.this, 32, stopSound, PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.addAction(R.mipmap.ic_launcher, getString(R.string.app_name), stopSoundIntent);
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            Log.e(TAG, String.valueOf(e.getMessage()), e);
        }
    }

    @Override
    public void display(Collection<String> strings) {
        Intent intent = new Intent(MovieService.this, MainActivity.class);
        ArrayList<String> venueList = new ArrayList<>(strings);
        intent.putExtra("theatreList", venueList);

        PendingIntent pendingIntent = PendingIntent.getActivity(MovieService.this, 0, intent, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm::ss", Locale.ENGLISH);
        mBuilder.setContentIntent(pendingIntent)
                .setContentText("Checked last at  " + sdf.format(new Date(System.currentTimeMillis())));
    }

    @Override
    public void sendNotification(String movieName, Map<String, VenueDetails> venuePojoMap) {
        notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        sendResult(movieName, venuePojoMap);
    }

    private void sendResult(String movieName, Map<String, VenueDetails> venuePojoMap) {
        Intent intent = new Intent(ACTION_NOTIFY);
        ArrayList<String> venueCodes = new ArrayList<>(venuePojoMap.keySet());
        intent.putExtra("result", venueCodes);
        ArrayList<VenueDetails> pojos = new ArrayList<>(venueCodes.size());
        for (String code : venueCodes) {
            pojos.add(venuePojoMap.get(code));
        }
        intent.putParcelableArrayListExtra(THEATRES, pojos);
        intent.putExtra(MOVIE_NAME, movieName);
        broadcastManager.sendBroadcast(intent);
    }
}
