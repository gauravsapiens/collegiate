package com.collegiate.notification;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import com.collegiate.DaggerAppComponent;
import com.collegiate.bean.Course;
import com.collegiate.bean.Schedule;
import com.collegiate.database.CourseDao;
import com.collegiate.database.ScheduleDao;
import com.collegiate.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gauravarora on 22/09/15.
 */
public class NotificationSchedulerService extends IntentService {

    public static String WORKER_THREAD_TAG = "NotificationSchedulerWorkerTag";

    @Inject
    CourseDao mCourseDao;

    @Inject
    ScheduleDao mScheduleDao;

    public NotificationSchedulerService() {
        super(WORKER_THREAD_TAG);
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        scheduleCourseNotification();
    }

    private void scheduleCourseNotification() {
        List<Pair<Course, Schedule>> courseScheduleList = getCourseScheduleList();
        if (CollectionUtils.isEmpty(courseScheduleList)) {
            return;
        }

        for (Pair<Course, Schedule> courseSchedulePair : courseScheduleList) {
            Course course = courseSchedulePair.first;
            Schedule schedule = courseSchedulePair.second;
            NotificationHelper.addNotification(getApplicationContext(), course, schedule);
        }

        rescheduleAlarm(getApplicationContext());
    }

    private List<Pair<Course, Schedule>> getCourseScheduleList() {
        Collection<Schedule> schedules = mScheduleDao.findAll();
        if (CollectionUtils.isEmpty(schedules)) {
            return null;
        }

        List<Pair<Course, Schedule>> courseScheduleList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            String courseId = schedule.getCourseId();
            if (courseId == null) {
                continue;
            }
            Course course = mCourseDao.findById(courseId);
            if (course == null) {
                continue;
            }
            courseScheduleList.add(new Pair<>(course, schedule));
        }
        return courseScheduleList;
    }

    private void rescheduleAlarm(Context context) {
        NotificationHelper.scheduleNotificationScheduler(context);
    }

}
