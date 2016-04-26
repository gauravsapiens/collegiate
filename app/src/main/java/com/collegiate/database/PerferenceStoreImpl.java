package com.collegiate.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gauravarora on 29/10/15.
 */
public class PerferenceStoreImpl implements PreferencesStore {

    private SharedPreferences sharedPreferences;

    public PerferenceStoreImpl(Context context){
        sharedPreferences = context.getSharedPreferences("CollegiatePreference", Context.MODE_PRIVATE);
    }

    @Override
    public void saveObject(String key, Integer value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public Integer getObject(String key, Integer defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
}
