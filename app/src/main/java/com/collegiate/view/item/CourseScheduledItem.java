package com.collegiate.view.item;

import android.view.View;

import com.collegiate.bean.Course;
import com.collegiate.bean.Days;
import com.collegiate.bean.Schedule;
import com.collegiate.bean.Time;
import com.collegiate.util.CollectionUtils;
import com.collegiate.util.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gauravarora on 21/09/15.
 */
public class CourseScheduledItem extends CourseItem {

    private Schedule mSchedule;

    public CourseScheduledItem(Course course, Schedule schedule) {
        super(course);
        this.mSchedule = schedule;
    }

    @Override
    protected void configureView(CourseItem.ViewHolder viewHolder) {
        if (mSchedule == null) {
            return;
        }

        viewHolder.schedule.setVisibility(View.VISIBLE);
        viewHolder.scheduleSeparator.setVisibility(View.VISIBLE);

        String displayText = getDisplayText(mSchedule.getTime()) + " - " + getDisplayText(mSchedule.getDays());
        viewHolder.schedule.setText(displayText);
    }

    private String getDisplayText(Days days) {
        if (days == null || days.getWeek() == null) {
            return null;
        }

        List<Integer> selectedDays = days.getSelectedDays();
        if (CollectionUtils.isEmpty(selectedDays)) {
            return "";
        }

        StringBuilder displayText = new StringBuilder();
        for (Integer day : selectedDays) {
            displayText.append(TimeUtils.getDisplayValueForDayNumber(day)).append(", ");
        }

        return displayText.substring(0, displayText.length() - 2);
    }

    private String getDisplayText(Time time) {
        Date date = new Date(0, 0, 0, time.getHourOfDay(), time.getMinute());
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(date);
    }

}
