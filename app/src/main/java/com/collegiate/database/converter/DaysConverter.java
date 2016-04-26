package com.collegiate.database.converter;

import com.collegiate.bean.Days;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.converter.TypeConverter;

/**
 * Created by gauravarora on 20/09/15.
 */

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class DaysConverter extends TypeConverter<String, Days> {

    private Gson mGson = new Gson();

    @Override
    public String getDBValue(Days model) {
        return mGson.toJson(model);
    }

    @Override
    public Days getModelValue(String data) {
        return mGson.fromJson(data, Days.class);
    }

}
