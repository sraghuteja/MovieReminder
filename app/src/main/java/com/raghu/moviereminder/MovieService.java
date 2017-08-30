package com.raghu.moviereminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class MovieService extends Service {
    private static final int NOTIFICATION_ID = 958;
    private static final String TAG = "MovieService";
    private static final String HARD_CODED_THEATRE = "INHY";

    public MovieService() {
    }

    private class MovieList extends Binder {
    }

    private final IBinder iBinder = new MovieList();

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public static final String ACTION_NOTIFY = "com.raghu.moviereminder.action.notify";

    public static final String ACTION_FETCH = "com.raghu.moviereminder.action.fetch";

    public static final String URL = "com.raghu.moviereminder.extra.url";
    public static final String THEATRES = "com.raghu.moviereminder.extra.theatre";

    public static final String movieUrl = "https://in.bookmyshow.com/buytickets/paisa-vasool-hyderabad/movie-hyd-ET00058405-MT/20170901";
    private static final String jsonParse = "JSON.parse(";

    private Set<String> venueSet;
    private Notification.Builder mBuilder;

    private NotificationManager notificationManager;

    private LocalBroadcastManager broadcastManager;

    private ScheduledThreadPoolExecutor executor;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Movie", "Service Created");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Fetching")
                .setContentTitle("Movie Reminder");
        int corePoolSize = Runtime.getRuntime().availableProcessors();
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
            mBuilder = new Notification.Builder(this);
        }
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NOTIFY.equals(action)) {
                final String param1 = intent.getStringExtra(URL);
                final String param2 = intent.getStringExtra(THEATRES);
                handleActionNotify(param1, param2);
            }
            else {
                handleActionNotify(null, null);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNotify(String url, String theatre) {
        Runnable runnable = new Work(url, theatre);
        executor.scheduleWithFixedDelay(runnable, 0, 15, TimeUnit.MINUTES);
    }

    private class Work implements Runnable {
        final String url;
        final String theatreCode;

        Work(String url, String theatreCode) {
            if (url != null) {
                this.url = url;
            } else {
                this.url = movieUrl;
            }
            if (theatreCode != null) {
                this.theatreCode = theatreCode;
            } else {
                this.theatreCode = HARD_CODED_THEATRE;
            }
        }

        @Override
        public void run() {
            Log.e(TAG, "Runnable started");
            HttpsURLConnection connection = null;
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
                Elements body = document.body().getElementsByTag("script");

                if (venueSet != null) {
                    venueSet.clear();
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
                if(venueSet.contains(theatreCode)) {
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
            ArrayList<String> venueList = new ArrayList<>(venueSet);
            intent.putExtra("theatreList", venueList);
            PendingIntent pendingIntent = PendingIntent.getActivity(MovieService.this, 0, intent, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm::ss", Locale.ENGLISH);
            mBuilder.setContentIntent(pendingIntent)
                    .setContentText("Checked last at  " + sdf.format(new Date(System.currentTimeMillis())));
        }

        private void playNotificationSound() {
            MediaPlayer mediaPlayer = MediaPlayer.create(MovieService.this, R.raw.notify);
            try {
                AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                        AudioManager.FLAG_PLAY_SOUND);

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
                parseToken(token);
            }
        }

        private void parseToken(String token) {
            if (token.length() <= 0) {
                return;
            }

            String temp = token.trim();
            if (!temp.startsWith("var")) {
                return;
            }
            int index = temp.indexOf(jsonParse);
            if (index < 0) {
                return;
            }
            temp = temp.substring(index+jsonParse.length()+1, temp.length()-3).trim();
            temp = temp.replaceAll("\\\\", "");
            Gson gson = new Gson();
            List<VenuePojo> venuePojos = null;

            try {
                venuePojos = gson.fromJson(temp, new TypeToken<List<VenuePojo>>(){}.getType());
            } catch (JsonSyntaxException ignored) {
            }

            if (venuePojos == null) {
                return;
            }
            if(venueSet == null) {
                venueSet = new HashSet<>();
            }
            for(VenuePojo vp:venuePojos) {
                venueSet.add(vp.VenueCode);
            }
        }
    }

    private void sendResult() {
        Intent intent = new Intent(ACTION_NOTIFY);
        intent.putExtra("result", new ArrayList<>(venueSet));
        broadcastManager.sendBroadcast(intent);
    }

    class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null) {
                return;
            }
            String url = intent.getStringExtra(MovieService.URL);
            String theatre = intent.getStringExtra(MovieService.THEATRES);
            executor.getQueue().clear();
            handleActionNotify(url, theatre);
        }
    }
}
