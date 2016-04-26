package com.collegiate.util;

import android.content.Context;
import android.content.Intent;

import com.collegiate.GlobalApplication;
import com.collegiate.R;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.core.activity.BaseActivity;

/**
 * Created by gauravarora on 09/07/15.
 */
public class AppUtils {

    public static void shareApp(BaseActivity activity){
        String url = ResourceUtils.getString(R.string.app_url);
        String message = "Learn from Stanford, MIT, Berkeley at the touch of a button. Check out the awesome app Collegiate - " + url;

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share Collegiate"));
    }

    public static void shareCourse(BaseActivity activity, College college, Course course){
        String url = ResourceUtils.getString(R.string.app_url);
        String message = "Check out " + college.getShortName() + "'s course on " + course.getTitle() + " on Collegiate: " + url;

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public static Context getApplicationContext() {
        return GlobalApplication.getContext();
    }
}
