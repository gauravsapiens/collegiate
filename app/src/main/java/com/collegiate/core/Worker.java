package com.collegiate.core;

/**
 * Created by gauravarora on 23/09/15.
 */
public interface Worker<T> {

    T performWork(Object... params);
}
