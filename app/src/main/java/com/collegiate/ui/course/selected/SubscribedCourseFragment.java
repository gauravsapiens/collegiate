package com.collegiate.ui.course.selected;

import com.collegiate.DataFactory;
import com.collegiate.R;
import com.collegiate.bean.Course;
import com.collegiate.ui.common.AbstractCourseListFragment;

import java.util.Collection;

/**
 * Created by gauravarora on 08/09/15.
 */
public class SubscribedCourseFragment extends AbstractCourseListFragment {

    @Override
    protected Collection<Course> getCourses() {
        return DataFactory.getSubscribedCourses();
    }

    protected int getEmptyLayoutId() {
        return R.layout.subscribed_course_empty;
    }

}
