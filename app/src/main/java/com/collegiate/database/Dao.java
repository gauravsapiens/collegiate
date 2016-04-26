package com.collegiate.database;

import com.collegiate.database.async.DbTransactionCallbacks;

import java.util.Collection;

/**
 * Created by gauravarora on 01/08/15.
 */
public interface Dao<T> {

    T create(T object);

    T update(T object);

    T createOrUpdate(T object);

    void delete(String objectId);

    T findById(String objectId);

    void findById(String objectId, DbTransactionCallbacks<T> callbacks);

    Collection<T> findByIds(Collection<String> id);

    Collection<T> findAll();

    int count();

}
