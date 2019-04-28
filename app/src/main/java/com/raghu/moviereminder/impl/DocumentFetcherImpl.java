package com.raghu.moviereminder.impl;

import android.util.Log;

import com.raghu.moviereminder.action.DocumentFetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Raghu Teja on 028 28/04/2019.
 */
public class DocumentFetcherImpl implements DocumentFetcher {
    private static final String TAG = "DocumentFetcherImpl";

    @Override
    public Document getDocument(String url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64");
        connection.connect();
        if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            String error = "Response code: " + connection.getResponseCode() + " Message: " +
                    connection.getResponseMessage();
            Log.e(TAG, error);
            throw new IOException(error);
        }

        return Jsoup.parse(connection.getInputStream(), "UTF-8", url);
    }
}
