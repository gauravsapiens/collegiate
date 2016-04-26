package com.collegiate;

import android.content.Context;
import android.content.Intent;

import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Lecture;
import com.collegiate.bean.License;
import com.collegiate.ui.college.detail.CollegeDetailActivity;
import com.collegiate.ui.college.home.CollegeHomeActivity;
import com.collegiate.ui.college.selection.CollegeSelectionActivity;
import com.collegiate.ui.course.detail.CourseDetailActivity;
import com.collegiate.ui.lecture.LectureDetailActivity;
import com.collegiate.ui.license.detail.LicenseDetailActivity;

/**
 * Created by gauravarora on 06/07/15.
 */
public class IntentHelper {

    public static Intent getIntentForHomeActivity(Context context) {
        Class homeClass = CollegeHomeActivity.class;
        College selectedCollege = DataFactory.getSelectedCollege();
        if (selectedCollege == null) {
            homeClass = CollegeSelectionActivity.class;
        }

        return new Intent(context, homeClass);
    }

    public static Intent getIntentForCollegeDetail(Context context, College college) {
        Intent intent = new Intent(context, CollegeDetailActivity.class);
        intent.putExtra(IntentConstants.COLLEGE, college);
        return intent;
    }

    public static Intent getIntentForCollegeHome(Context context) {
        return new Intent(context, CollegeHomeActivity.class);
    }

    public static Intent getIntentForCourseDetail(Context context, Course course) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra(IntentConstants.COURSE, course);
        return intent;
    }

    public static Intent getIntentForLectureDetail(Context context, Course course, Lecture lecture) {
        Intent intent = new Intent(context, LectureDetailActivity.class);
        intent.putExtra(IntentConstants.COURSE, course);
        intent.putExtra(IntentConstants.LECTURE, lecture);
        return intent;
    }

    public static Intent getIntentForLicenseDetail(Context context, License license) {
        Intent intent = new Intent(context, LicenseDetailActivity.class);
        intent.putExtra(IntentConstants.LICENSE, license);
        return intent;
    }

    public static void makeIntentNode(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
