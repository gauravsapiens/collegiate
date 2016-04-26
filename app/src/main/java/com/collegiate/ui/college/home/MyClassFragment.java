package com.collegiate.ui.college.home;

import android.os.Bundle;

import com.collegiate.DaggerAppComponent;
import com.collegiate.DataFactory;
import com.collegiate.IntentConstants;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Schedule;
import com.collegiate.database.ScheduleDao;
import com.collegiate.ui.common.AbstractCourseListFragment;
import com.collegiate.view.item.CourseItem;
import com.collegiate.view.item.CourseScheduledItem;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by gauravarora on 21/09/15.
 */
public class MyClassFragment extends AbstractCourseListFragment{

    @Inject
    ScheduleDao mScheduleDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);
    }

    protected CourseItem newItem(Course course){
        Schedule schedule = mScheduleDao.findById(course.getId());
        if(schedule == null){
            return new CourseItem(course);
        }
        return new CourseScheduledItem(course, schedule);
    }

    @Override
    protected Collection<Course> getCourses() {
        return DataFactory.getSubscribedCourses(getCollege());
    }

    public College getCollege() {
        return getArguments().getParcelable(IntentConstants.COLLEGE);
    }

}
