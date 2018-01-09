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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.raghu.moviereminder.pojos.VenueDetails;
import com.raghu.moviereminder.pojos.VenueNames;
import com.raghu.moviereminder.pojos.VenuePojo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MovieService extends Service {
    private static final int NOTIFICATION_ID = 958;
    private static final String TAG = "MovieService";


    public static final String ACTION_NOTIFY = "com.raghu.moviereminder.action.notify";
    public static final String ACTION_FETCH = "com.raghu.moviereminder.action.fetch";
    public static final String ACTION_STOP = "com.raghu.moviereminder.action.stop";
    private static final String ACTION_STOP_SOUND = "com.raghu.moviereminder.action.stopsound";
    public static final String URL = "com.raghu.moviereminder.extra.url";
    public static final String THEATRES = "com.raghu.moviereminder.extra.theatre";
    private static final String jsonParse = "aST_details";
    public static final String MOVIE_NAME = "com.raghu.moviereminder.movie.name";

    public MovieService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private Map<String, VenueDetails> venuePojoMap;
    private Notification.Builder mBuilder;

    private NotificationManager notificationManager;

    private LocalBroadcastManager broadcastManager;

    private ScheduledThreadPoolExecutor executor;

    private String movieName;

    //The currently running task
    private Runnable runnable;

    private MediaPlayer mediaPlayer;

    private BroadcastReceiver receiver = new Receiver();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Movie", "Service Created");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder = new Notification.Builder(this, TAG);
        }
        else {
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
        if(mBuilder == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder = new Notification.Builder(this, TAG);
            }
            else {
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
            }
            else if(ACTION_STOP.equals(action)) {
                executor.shutdown();
                stopForeground(true);
            }
            else if(ACTION_STOP_SOUND.equals(action)) {
                stopSound();
            }
        }
    }

    private void handleActionNotify(@NonNull String url, @NonNull@Size(min = 4, max = 4) String theatre) {
        if(runnable != null) {
            executor.remove(runnable);
        }
        runnable = new Work(url, theatre);
        try {
            executor.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.MINUTES);
        } catch (RejectedExecutionException e) {
            executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.MINUTES);
        }
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter(ACTION_FETCH));

/*        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Testing");
            }
        }, 10, 10, TimeUnit.SECONDS);*/
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).
                unregisterReceiver(receiver);
        receiver = null;
        executor.shutdown();
        if(mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    public void stopSound() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private static final String[] MONTH = new String[]{
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"
    };

    private class Work implements Runnable {
        final String url;
        final String theatreCode;

        Work(@NonNull String url, @NonNull@Size(max = 4, min = 4) String theatreCode) {
            this.url = url;
            this.theatreCode = theatreCode;

        }

        @Override
        public void run() {
            Log.e(TAG, "Runnable started");
            HttpsURLConnection connection = null;
            int pos = this.url.lastIndexOf("/");
            String date = this.url.substring(pos + 1);
            Log.e(TAG, date);
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6));
            String formattedDate = day + " " + MONTH[month - 1] + ", " + year;

            try {
                connection = (HttpsURLConnection) new URL(this.url).openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setRequestProperty( "User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64");
                connection.connect();
                if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    Log.e(TAG, "Response code: " + connection.getResponseCode() + " Message: " + connection.getResponseMessage());
                    return;
                }

                Document document = Jsoup.parse(connection.getInputStream(), "UTF-8", this.url);
                Elements metas = document.body().getElementsByTag("meta");
                Element meta = metas.get(metas.size() - 1);
                if("name".equals(meta.attr("itemprop"))) {
                    movieName = meta.attr("content");
                    movieName = movieName.concat(" - ").concat(formattedDate);
                }
                Elements body = document.body().getElementsByTag("script");

                if (venuePojoMap != null) {
                    venuePojoMap.clear();
                }
                for(Element e:body) {
                    parseElement(e);
                }

            } catch (IOException e) {
                Log.e(TAG, String.valueOf(e.getMessage()), e);
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
                showInActivity();
                if(venuePojoMap.containsKey(theatreCode)) {
                    mBuilder.setContentText("Movie booking at " + VenueNames.getTheatreName(theatreCode) + " is now started");
                    playNotificationSound();
                }
                else {
                    Log.e("Service", "Booking not started");
                }
                notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                sendResult();
            }
        }

        private void showInActivity() {
            Intent intent = new Intent(MovieService.this, MainActivity.class);
            ArrayList<String> venueList = new ArrayList<>(venuePojoMap.keySet());
            intent.putExtra("theatreList", venueList);

            PendingIntent pendingIntent = PendingIntent.getActivity(MovieService.this, 0, intent, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm::ss", Locale.ENGLISH);
            mBuilder.setContentIntent(pendingIntent)
                    .setContentText("Checked last at  " + sdf.format(new Date(System.currentTimeMillis())));
        }

        private void playNotificationSound() {

            mediaPlayer = MediaPlayer.create(MovieService.this, R.raw.notify);
            try {
                final AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                if(audioManager == null) {
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

        private void parseElement(Element e) {
            String data = e.data();
            if (data.length() <= 1 || !data.contains("try {")) {
                return;
            }
            String[] tokens = data.split("\n");
            for(String token:tokens) {
                parseToken(token.trim());
            }
        }

        private void parseToken(String token) {
            if (token.length() <= 0) {
                return;
            }

            String temp = token.trim();
            if (!temp.startsWith("var")) {
                Log.e(TAG, "Var no found");
                return;
            }
            int index = temp.indexOf(jsonParse);
            if (index >= 0) {
                Log.e(TAG, jsonParse + " is found");
            } else {
                return;
            }
            temp = temp.substring(index+jsonParse.length()+1).trim();
            int posOfEqual = temp.indexOf("=");
            temp = temp.substring(posOfEqual+1, temp.length() - 1);
            Gson gson = new Gson();
            List<VenuePojo> venuePojos = null;

            try {
                venuePojos = gson.fromJson(temp, new TypeToken<List<VenuePojo>>(){}.getType());
            } catch (JsonSyntaxException jsonE) {
                Log.e(TAG, "Gson error", jsonE);
            }

            if (venuePojos == null) {
                return;
            }
            if(venuePojoMap == null) {
                venuePojoMap = new HashMap<>();
            }
            for(VenuePojo vp:venuePojos) {
                VenueDetails vd = venuePojoMap.get(vp.VenueCode);
                if(vd == null) {
                    vd = new VenueDetails(vp);
                }
                else {
                    vd.addVenueDetails(vp);
                }
                venuePojoMap.put(vp.VenueCode, vd);
            }
        }
    }

    private void sendResult() {
        Intent intent = new Intent(ACTION_NOTIFY);
        ArrayList<String> venueCodes = new ArrayList<>(venuePojoMap.keySet());
        intent.putExtra("result", venueCodes);
        ArrayList<VenueDetails> pojos = new ArrayList<>(venueCodes.size());
        for(String code : venueCodes) {
            pojos.add(venuePojoMap.get(code));
        }
        intent.putParcelableArrayListExtra(THEATRES, pojos);
        intent.putExtra(MOVIE_NAME, movieName);
        broadcastManager.sendBroadcast(intent);
    }

    class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null) {
                return;
            }
            if(!ACTION_FETCH.equals(intent.getAction())) {
                return;
            }
            String url = intent.getStringExtra(MovieService.URL);
            String theatre = intent.getStringExtra(MovieService.THEATRES);
            handleActionNotify(url, theatre);
        }
    }
}
