package com.collegiate.ui.college.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.collegiate.BuildConfig;
import com.collegiate.DataFactory;
import com.collegiate.IntentConstants;
import com.collegiate.IntentHelper;
import com.collegiate.R;
import com.collegiate.analytics.AnalyticsHelper;
import com.collegiate.analytics.bean.Event;
import com.collegiate.bean.Category;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.core.activity.PagerAdapter;
import com.collegiate.core.activity.PagerCustomActivity;
import com.collegiate.ui.common.AbstractCourseListFragment;
import com.collegiate.util.CollectionUtils;
import com.collegiate.util.DatabaseUtils;
import com.collegiate.util.ResourceUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gauravarora on 09/07/15.
 */
public class CollegeHomeActivity extends PagerCustomActivity implements CollegeCourseListFragment.Callback {

    private College mCollege;
    private Integer mSubscribedCourseCount;

    private int mResultCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCollege = getCollege();
        setActivityTitle(mCollege.getShortName());
    }

    @Override
    protected List<PagerAdapter.FragmentInfo> getFragmentInfos() {
        mCollege = getCollege();

        Drawable drawable = ResourceUtils.getLayeredDrawable(mCollege.getImageResourceId(), R.color.college_overlay_unselected);
        setImageDrawable(drawable);

        List<PagerAdapter.FragmentInfo> fragmentInfos = new ArrayList<>();

        Collection<Course> courses = DataFactory.getSubscribedCourses(mCollege);
        mSubscribedCourseCount = CollectionUtils.size(courses);

        if (mSubscribedCourseCount != 0) {
            Bundle args = new Bundle();
            args.putParcelable(IntentConstants.COLLEGE, mCollege);

            MyClassFragment fragment = new MyClassFragment();
            fragment.setArguments(args);

            fragmentInfos.add(new PagerAdapter.FragmentInfo(fragment, "My Class"));
        }

        Collection<Category> categories = getCategories(mCollege);
        for (Category category : categories) {
            CollegeCourseListFragment fragment = new CollegeCourseListFragment();

            Bundle args = new Bundle();
            args.putParcelable(IntentConstants.COLLEGE, mCollege);
            args.putParcelable(IntentConstants.CATEGORY, category);
            fragment.setArguments(args);

            fragmentInfos.add(new PagerAdapter.FragmentInfo(fragment, category.getName()));
        }

        return fragmentInfos;
    }

    @Override
    protected Event getScreenForAnalytics() {
        return AnalyticsHelper.getEventForCollegeHomeScreen(mCollege, mSubscribedCourseCount);
    }

    private Collection<Category> getCategories(College college) {
        return DataFactory.getAllCategories(college);
    }

    private College getCollege() {
        if (mCollege == null) {
            mCollege = DataFactory.getSelectedCollege();
        }
        return mCollege;
    }

    @Override
    public void onCourseSelected(Course course, View view) {
        Intent intent = IntentHelper.getIntentForCourseDetail(this, course);
        startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE_OPEN_COURSE_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstants.INTENT_REQUEST_CODE_OPEN_COURSE_DETAIL) {
            mResultCode = resultCode;
        }
    }

    @Override
    protected boolean isNavDrawerEnabled() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mResultCode == 0) {
            return;
        }

        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            return;
        }

        if (mSubscribedCourseCount == 0 && mResultCode == IntentConstants.INTENT_RESULT_CODE_COURSE_SUBSCRIBED) {
            setupViewPager();
        } else {
            AbstractCourseListFragment courseListFragment = (AbstractCourseListFragment) fragment;
            courseListFragment.refreshLoader(true);
        }

        mResultCode = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.AUDIT_MODE) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_college_home, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_export:
                DatabaseUtils.exportDb(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

