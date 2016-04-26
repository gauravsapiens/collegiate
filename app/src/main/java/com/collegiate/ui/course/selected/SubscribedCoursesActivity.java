package com.collegiate.ui.course.selected;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.collegiate.IntentHelper;
import com.collegiate.bean.Course;
import com.collegiate.core.activity.SingleStackActivity;

/**
 * Created by gauravarora on 08/09/15.
 */
public class SubscribedCoursesActivity extends SingleStackActivity implements SubscribedCourseFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("My Courses");
    }

    @Override
    protected Fragment onCreatePane() {
        return new SubscribedCourseFragment();
    }

    @Override
    protected boolean isNavDrawerEnabled() {
        return true;
    }

    @Override
    public void onCourseSelected(Course course, View view) {
        Intent intent = IntentHelper.getIntentForCourseDetail(this, course);
        startActivity(intent);
    }

}
