package com.collegiate.database.converter;

import com.collegiate.bean.Time;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.converter.TypeConverter;

/**
 * Created by gauravarora on 20/09/15.
 */

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class TimeConverter extends TypeConverter<String, Time>{

    private Gson mGson = new Gson();

    @Override
    public String getDBValue(Time model) {
        return mGson.toJson(model);
    }

    @Override
    public Time getModelValue(String data) {
        return mGson.fromJson(data, Time.class);
    }

}
