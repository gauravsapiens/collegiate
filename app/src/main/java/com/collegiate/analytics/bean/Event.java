package com.collegiate.analytics.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gauravarora on 01/10/15.
 */
public class Event {

    private String screen;
    private String action;
    private EventType eventType;

    private Map<Dimension, String> customDimensions;

    private Event(Builder builder) {
        screen = builder.screen;
        action = builder.action;
        eventType = builder.eventType;
        customDimensions = builder.customDimensions;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Map<Dimension, String> getCustomDimensions() {
        return customDimensions;
    }

    public void setCustomDimensions(Map<Dimension, String> customDimensions) {
        this.customDimensions = customDimensions;
    }

    public static class Builder {

        private String screen;
        private String action;
        private EventType eventType;

        private Map<Dimension, String> customDimensions;

        public Builder setScreen(String screen) {
            this.screen = screen;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setEventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder setCustomDimensions(Map<Dimension, String> customDimensions) {
            this.customDimensions = customDimensions;
            return this;
        }

        public Builder addCustomDimension(Dimension dimension, String value) {
            if (customDimensions == null) {
                customDimensions = new HashMap<>();
            }
            customDimensions.put(dimension, value);
            return this;
        }

        public Event build() {
            return new Event(this);
        }

    }
}
