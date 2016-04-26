package com.collegiate.util;

import com.collegiate.bean.Days;
import com.collegiate.bean.Schedule;
import com.collegiate.bean.Time;

import java.util.Calendar;
import java.util.List;

/**
 * Created by gauravarora on 22/09/15.
 */
public class TimeUtils {

    public static Calendar getScheduledTimeForToday(Schedule schedule) {
        if (schedule == null || schedule.getDays() == null || schedule.getTime() == null) {
            return null;
        }

        Days days = schedule.getDays();
        Time time = schedule.getTime();

        List<Integer> selectedDays = days.getSelectedDays();
        if (CollectionUtils.isEmpty(selectedDays)) {
            return null;
        }

        Calendar scheduledTime = Calendar.getInstance();
        int currentWeekDay = scheduledTime.get(Calendar.DAY_OF_WEEK) - 1;
        if (!selectedDays.contains(currentWeekDay)) {
            return null;
        }

        scheduledTime.set(Calendar.HOUR_OF_DAY, time.getHourOfDay());
        scheduledTime.set(Calendar.MINUTE, time.getMinute());
        scheduledTime.set(Calendar.SECOND, time.getSecond());

        Calendar currentTime = Calendar.getInstance();
        if(scheduledTime.compareTo(currentTime) == -1){
            return null;
        }

        return scheduledTime;
    }

    public static String getDisplayValueForDayNumber(int dayNumber) {
        switch (dayNumber) {
            case 0:
                return "Sun";
            case 1:
                return "Mon";
            case 2:
                return "Tue";
            case 3:
                return "Wed";
            case 4:
                return "Thu";
            case 5:
                return "Fri";
            case 6:
                return "Sat";
            default:
                return "";
        }
    }

}
