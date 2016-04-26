package com.collegiate;

import android.app.Application;
import android.content.Context;

import com.collegiate.database.CollegiateDatabase;
import com.collegiate.util.DatabaseUtils;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.raizlabs.android.dbflow.config.FlowManager;

import io.fabric.sdk.android.Fabric;

/**
 * Created by gauravarora on 09/07/15.
 */
public class GlobalApplication extends Application {

    private static GlobalApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        DatabaseUtils.addBundledDatabase(mContext, CollegiateDatabase.NAME);

        FlowManager.init(this);
        Fresco.initialize(this);

        Fabric.with(this, new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder()
                        .disabled(BuildConfig.DEBUG)
                        .build())
                .build());
    }

    public static Context getContext() {
        return mContext;
    }
}
