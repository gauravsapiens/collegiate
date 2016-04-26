package com.collegiate.util;

import java.util.Collection;

/**
 * Created by gauravarora on 09/07/15.
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.size() == 0);
    }

    public static int size(Collection collection) {
        if(isEmpty(collection)){
            return 0;
        }
        return collection.size();
    }

}
