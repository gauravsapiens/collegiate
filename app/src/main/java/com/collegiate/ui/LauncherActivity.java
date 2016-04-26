package com.collegiate.ui;

import android.os.Bundle;
import android.os.Handler;

import com.collegiate.IntentHelper;
import com.collegiate.R;
import com.collegiate.core.activity.BaseActivity;

/**
 * Created by gauravarora on 06/07/15.
 */
public class LauncherActivity extends BaseActivity {

    /*
    MIT OpenCourseware
    Stanford
    UCLA
    UC Berkley
    Yale Courses
    UCIrvineOCW
     */
    private static final int SPLASH_DURATION_IN_MILLISECONDS = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(this::launchAppropriateActivity, SPLASH_DURATION_IN_MILLISECONDS);
    }

    private void launchAppropriateActivity() {
        startActivity(IntentHelper.getIntentForHomeActivity(this));
    }

    @Override
    protected boolean isToolbarEnabled() {
        return false;
    }
}
