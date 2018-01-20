package com.raghu.moviereminder.pojos;

/**
 * Pojo for holding VenueCode and Venuename
 * Created by Raghu Teja on 021 21/01/2018.
 */

public class Venue {
    public String code;
    public String name;

    public Venue(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
