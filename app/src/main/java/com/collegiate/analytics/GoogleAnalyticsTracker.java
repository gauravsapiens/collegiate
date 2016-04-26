package com.collegiate.analytics;

import com.collegiate.BuildConfig;
import com.collegiate.GlobalApplication;
import com.collegiate.analytics.bean.Dimension;
import com.collegiate.analytics.bean.Event;
import com.collegiate.util.MapUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by gauravarora on 30/09/15.
 */
public class GoogleAnalyticsTracker implements AnalyticsTracker {

    private static Tracker sTracker;

    public GoogleAnalyticsTracker() {
        initializeTracker();
    }

    @Override
    public void onScreenStarted(Event event) {
        HitBuilders.ScreenViewBuilder builder = new HitBuilders.ScreenViewBuilder();

        Map<Dimension, String> dimensions = event.getCustomDimensions();
        if (MapUtils.isNotEmpty(dimensions)) {
            for (Map.Entry<Dimension, String> entry : dimensions.entrySet()) {
                builder.setCustomDimension(entry.getKey().ordinal(), entry.getValue());
            }
        }

        sTracker.setScreenName(event.getScreen());
        sTracker.send(builder.build());
    }

    @Override
    public void onActionTriggered(Event event) {
        HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder();
        builder.setAction(event.getAction());
        builder.setCategory("Click");

        Map<Dimension, String> dimensions = event.getCustomDimensions();
        if (MapUtils.isNotEmpty(dimensions)) {
            for (Map.Entry<Dimension, String> entry : dimensions.entrySet()) {
                builder.setCustomDimension(entry.getKey().ordinal(), entry.getValue());
            }
        }

        sTracker.setScreenName(event.getScreen());
        sTracker.send(builder.build());
    }


    private void initializeTracker() {
        if (sTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(GlobalApplication.getContext());
            analytics.setDryRun(BuildConfig.DEBUG);

            sTracker = analytics.newTracker(BuildConfig.ANALYTIC_TRACKING_ID);
            sTracker.enableAutoActivityTracking(false);
        }
    }

}
