package com.raghu.moviereminder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raghu.moviereminder.MovieService;
import com.raghu.moviereminder.action.NotifyListener;

import static com.raghu.moviereminder.MovieService.ACTION_FETCH;

/**
 * Created by Raghu Teja on 028 28/04/2019.
 */
public class NotifyReceiver extends BroadcastReceiver {
    private final NotifyListener notifyListener;

    public NotifyReceiver(NotifyListener notifyListener) {
        this.notifyListener = notifyListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (!ACTION_FETCH.equals(intent.getAction())) {
            return;
        }
        String url = intent.getStringExtra(MovieService.URL);
        String theatre = intent.getStringExtra(MovieService.THEATRES);
        if (notifyListener != null) {
            notifyListener.handleActionNotify(url, theatre);
        }
    }
}
