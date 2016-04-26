package com.collegiate.analytics;

import com.collegiate.analytics.bean.Event;

/**
 * Created by gauravarora on 30/09/15.
 */
public interface AnalyticsTracker {

    void onScreenStarted(Event event);

    void onActionTriggered(Event event);

}
