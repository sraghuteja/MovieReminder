package com.raghu.moviereminder.action;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Raghu Teja on 028 28/04/2019.
 */
public interface DocumentFetcher {
    Document getDocument(String url) throws IOException;
}
