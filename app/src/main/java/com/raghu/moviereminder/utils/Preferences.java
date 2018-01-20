package com.raghu.moviereminder.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Wrapper for accessing Shared Preferences
 * Created by sragh_000 on 018 18/09/2017.
 */

public class Preferences {
    private static final String PREF = "movieReminder";

    private static final String KEY_THEATRE = "theatre";
    private static final String KEY_MOVIE = "movie";

    public static void setTheatreCode(Context context, Set<String> theatre) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit();
        editor.putStringSet(KEY_THEATRE, theatre);
        editor.apply();
    }

    public static void setMovieUrl(Context context, String movieUrl) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_MOVIE, movieUrl);
        editor.apply();
    }

    public static Set<String> getTheatreCode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return preferences.getStringSet(KEY_THEATRE, null);
    }

    public static String getMovieUrl(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return preferences.getString(KEY_MOVIE, null);
    }
}
