package com.collegiate.core;

/**
 * Created by gauravarora on 23/09/15.
 */
public interface ExecutionCallbacks<T> {

    void onSuccess(T t);

    void onFailure();

}
