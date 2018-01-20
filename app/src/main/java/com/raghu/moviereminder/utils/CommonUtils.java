package com.raghu.moviereminder.utils;

import java.util.Collection;

/**
 * Common functionalities
 * Created by Raghu Teja on 021 21/01/2018.
 */

public final class CommonUtils {
    private CommonUtils() {}

    public static boolean isCollectionEmpty(Collection<?> collection) {
        return collection == null || !collection.isEmpty();
    }
}
