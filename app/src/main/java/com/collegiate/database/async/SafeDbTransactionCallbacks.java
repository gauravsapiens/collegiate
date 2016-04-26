package com.collegiate.database.async;

/**
 * Created by gauravarora on 01/10/15.
 */
public abstract class SafeDbTransactionCallbacks<T> implements DbTransactionCallbacks<T> {

    @Override
    public void onFailure() {

    }
}
