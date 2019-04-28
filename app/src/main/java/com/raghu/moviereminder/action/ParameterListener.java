package com.raghu.moviereminder.action;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

/**
 * Interface for getting result from {@link com.raghu.moviereminder.fragment.MovieAndTheatreSelection}
 * Created by sragh_000 on 018 18/09/2017.
 */

public interface ParameterListener {
    void setMovieAndTheatre(@NonNull String movieUrl, @NonNull@Size(min = 4, max = 4) String theatreCode);
}
