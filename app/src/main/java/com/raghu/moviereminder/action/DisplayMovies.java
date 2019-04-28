package com.raghu.moviereminder.action;

import com.raghu.moviereminder.pojo.VenueDetails;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Raghu Teja on 028 28/04/2019.
 */
public interface DisplayMovies {
    void display(Collection<String> strings);
    void sendNotification(String movieName, Map<String, VenueDetails> venuePojoMap);
}
