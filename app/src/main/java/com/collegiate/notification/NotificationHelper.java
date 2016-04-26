package com.collegiate.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.collegiate.IntentConstants;
import com.collegiate.bean.Course;
import com.collegiate.bean.Schedule;
import com.collegiate.util.TimeUtils;

import java.util.Calendar;

/**
 * Created by gauravarora on 22/09/15.
 */
public class NotificationHelper {

    public static Integer SCHEDULE_REQUEST_CODE = 20;

    public static void addNotification(Context context, Course course, Schedule schedule) {
        Calendar calendar = TimeUtils.getScheduledTimeForToday(schedule);
        if (calendar == null) {
            return;
        }

        PendingIntent intent = getIntent(context, course);
        if (intent == null) {
            return;
        }

        AlarmManager alarmManager = getAlarmManager(context);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent);
    }

    public static void removeNotification(Context context, Course course) {
        PendingIntent intent = getIntent(context, course);
        if (intent == null) {
            return;
        }

        AlarmManager alarmManager = getAlarmManager(context);
        alarmManager.cancel(intent);
    }

    public static void startNotificationScheduler(Context context) {
        Intent service = new Intent(context, NotificationSchedulerService.class);
        context.startService(service);
    }

    public static void scheduleNotificationScheduler(Context context) {
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, 1);
        time.set(Calendar.HOUR_OF_DAY, 0);
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, NotificationSchedulerService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, SCHEDULE_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC, time.getTimeInMillis(), pendingIntent);
    }

    private static PendingIntent getIntent(Context context, Course course) {
        int hashCode = course.hashCode();

        Intent intent = new Intent(context, NotificationAlarmReceiver.class);
        intent.putExtra(IntentConstants.COURSE, course);
        return PendingIntent.getBroadcast(context, hashCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

}
