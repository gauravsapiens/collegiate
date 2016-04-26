package com.collegiate.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.collegiate.IntentConstants;
import com.collegiate.R;
import com.collegiate.bean.Course;
import com.collegiate.ui.course.detail.CourseDetailActivity;
import com.collegiate.util.ResourceUtils;

import java.util.Date;

/**
 * Created by gauravarora on 20/09/15.
 */
public class NotificationAlarmReceiver extends BroadcastReceiver {

    private static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Course course = intent.getParcelableExtra(IntentConstants.COURSE);
        if (course == null || course.getId() == null) {
            return;
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getNotification(course, context);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private Notification getNotification(Course course, Context context) {
        long time = new Date().getTime();

        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(IntentConstants.COURSE, course);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context)
                .setContentTitle("Collegiate")
                .setContentText("Time for " + course.getTitle() + " class")
                .setSmallIcon(R.drawable.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setWhen(time)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            builder.setSmallIcon(R.drawable.ic_notification)
                    .setColor(ResourceUtils.getColor(R.color.primary));
        }

        return builder.build();
    }


}
