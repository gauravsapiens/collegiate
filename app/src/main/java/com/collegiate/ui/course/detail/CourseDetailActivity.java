package com.collegiate.ui.course.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;

import com.collegiate.DaggerAppComponent;
import com.collegiate.IntentConstants;
import com.collegiate.IntentHelper;
import com.collegiate.R;
import com.collegiate.analytics.AnalyticsHelper;
import com.collegiate.analytics.bean.Event;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Lecture;
import com.collegiate.core.activity.SingleStackActivity;
import com.collegiate.database.CollegeDao;
import com.collegiate.util.AppUtils;
import com.collegiate.view.base.ColImageView;

import javax.inject.Inject;

/**
 * Created by gauravarora on 15/07/15.
 */
public class CourseDetailActivity extends SingleStackActivity implements CourseDetailFragment.Callbacks {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Course mCourse;
    private College mCollege;
    private boolean mOnActivityResultCalled;
    private Lecture mLastSelectedLecture;

    @Inject
    CollegeDao mCollegeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mCourse == null) {
            mCourse = getCourse(savedInstanceState);
        }
        initActionBar();
    }

    @Override
    protected Fragment onCreatePane() {
        DaggerAppComponent.create().inject(this);

        mCourse = getCourse();
        mCollege = mCollegeDao.findById(mCourse.getCollegeId());

        Fragment fragment = new CourseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(IntentConstants.COLLEGE, mCollege);
        args.putParcelable(IntentConstants.COURSE, mCourse);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mOnActivityResultCalled && mLastSelectedLecture != null) {
            getFragment().updateSelectedLectureAndRefresh(mLastSelectedLecture);

            mOnActivityResultCalled = false;
            mLastSelectedLecture = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(IntentConstants.COURSE, mCourse);
    }

    protected Course getCourse() {
        return getBundle().getParcelable(IntentConstants.COURSE);
    }

    protected Course getCourse(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return null;
        }
        return savedInstanceState.getParcelable(IntentConstants.COURSE);
    }

    @Override
    protected int getParentLayout() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected Event getScreenForAnalytics() {
        return AnalyticsHelper.getEventForCourseDetailScreen(mCollege, mCourse);
    }

    @Override
    public void onLectureSelected(Course course, Lecture lecture) {
        lecture.setSeen(true);

        Intent intent = IntentHelper.getIntentForLectureDetail(this, course, lecture);
        startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE_OPEN_LECTURE_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mOnActivityResultCalled = true;

        if (data == null || data.getExtras() == null) {
            return;
        }
        mLastSelectedLecture = data.getExtras().getParcelable(IntentConstants.LECTURE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                share();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        CourseDetailFragment fragment = getFragment();
        if (fragment.isCourseSubscribed()) {
            setResult(IntentConstants.INTENT_RESULT_CODE_COURSE_SUBSCRIBED);
        } else if (fragment.isCourseOrScheduleUpdated()) {
            setResult(IntentConstants.INTENT_RESULT_CODE_COURSE_UPDATED);
        }
        super.onBackPressed();
    }

    @Override
    public CourseDetailFragment getFragment() {
        return (CourseDetailFragment) super.getFragment();
    }

    @Override
    protected boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    private void share() {
        AppUtils.shareCourse(this, mCollege, mCourse);
        AnalyticsHelper.reportEventForCourseShared(mCourse);
    }

    private void initActionBar() {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        ColImageView imageView = (ColImageView) findViewById(R.id.backdrop);
        imageView.setCallbacks(bitmap -> Palette.from(bitmap).generate(CourseDetailActivity.this::applyPalette));
        imageView.setImageURI(mCourse.getImageUrl());

        setActivityTitle(mCourse.getTitle());
    }

    private void applyPalette(Palette palette) {
        int statusBarColor = palette.getDarkMutedColor(R.attr.colorPrimaryDark);
        mCollapsingToolbarLayout.setStatusBarScrimColor(statusBarColor);

        int toolbarColor = palette.getMutedColor(R.attr.colorPrimary);
        mCollapsingToolbarLayout.setContentScrimColor(toolbarColor);

//        int collapseTextColor = palette.getLightVibrantColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

}
