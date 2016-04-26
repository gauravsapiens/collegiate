package com.collegiate.database;


/**
 * Created by gauravarora on 29/10/15.
 */
public interface PreferencesStore {

    void saveObject(String key, Integer value);

    Integer getObject(String key, Integer defaultValue);

}
