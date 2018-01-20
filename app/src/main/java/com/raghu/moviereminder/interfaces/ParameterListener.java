package com.raghu.moviereminder.interfaces;

import android.support.annotation.NonNull;

import java.util.Set;

/**
 * Interface for getting result from {@link com.raghu.moviereminder.fragments.MovieAndTheatreSelection}
 * Created by sragh_000 on 018 18/09/2017.
 */

public interface ParameterListener {
    void setMovieAndTheatre(@NonNull String movieUrl, Set<String> theatreCode);
}
