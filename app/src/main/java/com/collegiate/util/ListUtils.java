package com.collegiate.util;

import java.util.ArrayList;

/**
 * Created by gauravarora on 09/07/15.
 */
public class ListUtils {

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <E> ArrayList<E> newArrayListWithExpectedCapacity(int capacity) {
        return new ArrayList<E>(capacity);
    }
}
