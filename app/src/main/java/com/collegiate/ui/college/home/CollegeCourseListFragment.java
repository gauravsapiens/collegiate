package com.collegiate.ui.college.home;

import com.collegiate.DataFactory;
import com.collegiate.IntentConstants;
import com.collegiate.bean.Category;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.ui.common.AbstractCourseListFragment;

import java.util.Collection;

/**
 * Created by gauravarora on 09/07/15.
 */
public class CollegeCourseListFragment extends AbstractCourseListFragment {

    @Override
    protected Collection<Course> getCourses() {
        return DataFactory.getUnsubscribedCourses(getCollege(), getCategory());
    }

    public College getCollege() {
        return getArguments().getParcelable(IntentConstants.COLLEGE);
    }

    public Category getCategory() {
        return getArguments().getParcelable(IntentConstants.CATEGORY);
    }

}
