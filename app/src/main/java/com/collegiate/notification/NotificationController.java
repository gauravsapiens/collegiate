package com.collegiate.notification;

import android.content.Context;
import android.content.Intent;

import com.collegiate.bean.Course;
import com.collegiate.bean.Schedule;

/**
 * Created by gauravarora on 22/09/15.
 */
public class NotificationController {

    private static NotificationController sController;

    public static NotificationController getInstance(){
        if(sController == null){
            sController = new NotificationController();
        }
        return sController;
    }

    public void startNotificationScheduler(Context context){
        NotificationHelper.startNotificationScheduler(context);
    }

    public void scheduleNotificationScheduler(Context context){
        NotificationHelper.scheduleNotificationScheduler(context);
    }

    public void addNotification(Context context, Course course, Schedule schedule){
        NotificationHelper.addNotification(context, course, schedule);
        scheduleNotificationScheduler(context);
    }

    public void updateNotification(Context context, Course course, Schedule schedule){
        NotificationHelper.addNotification(context, course, schedule);
        scheduleNotificationScheduler(context);
    }

    public void removeNotification(Context context, Course course){
        NotificationHelper.removeNotification(context, course);
    }

}
