package com.raghu.moviereminder.runnable;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.raghu.moviereminder.action.DisplayMovies;
import com.raghu.moviereminder.action.DocumentFetcher;
import com.raghu.moviereminder.action.TheareFoundActionListener;
import com.raghu.moviereminder.pojo.VenueDetails;
import com.raghu.moviereminder.pojo.VenueNames;
import com.raghu.moviereminder.pojo.VenuePojo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.raghu.moviereminder.MovieService.MONTH;

/**
 * Created by Raghu Teja on 028 28/04/2019.
 */
public class Work implements Runnable {
    private static final String TAG = "Work";
    private static final String jsonParse = "aST_details";


    private final String url;
    private final String theatreCode;
    private final DocumentFetcher documentFetcher;
    private final DisplayMovies displayMovies;
    private final TheareFoundActionListener listener;

    private Map<String, VenueDetails> venuePojoMap = new HashMap<>();

    public Work(@NonNull String url, @NonNull @Size(max = 4, min = 4) String theatreCode, DocumentFetcher documentFetcher,
                DisplayMovies displayMovies, TheareFoundActionListener listener) {
        this.url = url;
        this.theatreCode = theatreCode;
        this.documentFetcher = documentFetcher;
        this.displayMovies = displayMovies;
        this.listener = listener;
    }

    @Override
    public void run() {
        Log.e(TAG, "Runnable started");
        int pos = this.url.lastIndexOf("/");
        String date = this.url.substring(pos + 1);
        Log.e(TAG, date);
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        String formattedDate = day + " " + MONTH[month - 1] + ", " + year;

        String movieName = "";
        try {
            Document document = this.documentFetcher.getDocument(this.url);
            Elements metas = document.body().getElementsByTag("meta");
            Element meta = metas.get(metas.size() - 1);
            if ("name".equals(meta.attr("itemprop"))) {
                movieName = meta.attr("content");
                movieName = movieName.concat(" - ").concat(formattedDate);
            }
            Elements body = document.body().getElementsByTag("script");

            if (venuePojoMap != null) {
                venuePojoMap.clear();
            }
            for (Element e : body) {
                parseElement(e);
            }

        } catch (IOException e) {
            Log.e(TAG, String.valueOf(e.getMessage()), e);
        } finally {
            if (venuePojoMap != null) {
                this.displayMovies.display(venuePojoMap.keySet());
                if (venuePojoMap.containsKey(theatreCode)) {
                    listener.onTheatreFound(VenueNames.getTheatreName(theatreCode));
                } else {
                    Log.e("Service", "Booking not started");
                }
                this.displayMovies.sendNotification(movieName, venuePojoMap);
            }
        }
    }

    private void parseElement(Element e) {
        String data = e.data();
        if (data.length() <= 1 || !data.contains("try {")) {
            return;
        }
        String[] tokens = data.split("\n");
        for (String token : tokens) {
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
        temp = temp.substring(index + jsonParse.length() + 1).trim();
        int posOfEqual = temp.indexOf("=");
        temp = temp.substring(posOfEqual + 1, temp.length() - 1);
        Gson gson = new Gson();
        List<VenuePojo> venuePojos = null;

        try {
            venuePojos = gson.fromJson(temp, new TypeToken<List<VenuePojo>>() {
            }.getType());
        } catch (JsonSyntaxException jsonE) {
            Log.e(TAG, "Gson error", jsonE);
        }

        if (venuePojos == null) {
            return;
        }
        if (venuePojoMap == null) {
            venuePojoMap = new HashMap<>();
        }
        for (VenuePojo vp : venuePojos) {
            VenueDetails vd = venuePojoMap.get(vp.VenueCode);
            if (vd == null) {
                vd = new VenueDetails(vp);
            } else {
                vd.addVenueDetails(vp);
            }
            venuePojoMap.put(vp.VenueCode, vd);
        }
    }

}
