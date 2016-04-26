package com.collegiate.core.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewStub;

import com.collegiate.DaggerAppComponent;
import com.collegiate.R;
import com.collegiate.analytics.AnalyticsTracker;
import com.collegiate.analytics.bean.Event;

import javax.inject.Inject;


/**
 * Standards : app_bar, toolbar, app_bar_view_stub, content_bar_view_stub : Whoever overrides getParentLayout() should provide same
 * naming convention for these
 * <p>
 * Created by gauravarora on 14/06/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected CoordinatorLayout mCoordinatorLayout;
    private ViewStub mAppBarViewStub;
    private ViewStub mContentViewStub;

    @Inject
    protected AnalyticsTracker mAnalyticsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);

        setContentView(getParentLayout());

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mAppBarViewStub = (ViewStub) findViewById(R.id.app_bar_view_stub);
        mContentViewStub = (ViewStub) findViewById(R.id.content_view_stub);

        initAppbar();
    }

    protected void addContentView(int layoutResourceId) {
        mContentViewStub.setLayoutResource(layoutResourceId);
        mContentViewStub.inflate();
    }

    protected int getAppBarLayout() {
        return R.layout.app_bar;
    }

    protected int getParentLayout() {
        return R.layout.activity_base;
    }

    protected boolean isToolbarEnabled() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Event event = getScreenForAnalytics();
        reportScreenToAnalytics(event);
    }

    protected void reportScreenToAnalytics(Event event){
        if(event == null){
            return;
        }
        mAnalyticsTracker.onScreenStarted(event);
    }

    protected void reportActionToAnalytics(Event event){
        if(event == null){
            return;
        }
        mAnalyticsTracker.onActionTriggered(event);
    }

    protected Event getScreenForAnalytics() {
        return null;
    }

    public void setActivityTitle(int resourceId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(resourceId);
        }
    }

    public void setActivityTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void attachInputBundle(Fragment fragment) {
        Bundle bundle = getBundle();
        fragment.setArguments(bundle);
    }

    protected Bundle getBundle() {
        return getIntent().getExtras();
    }

    protected boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onActionBarHomeIconClicked();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAppbar() {
        if (!isToolbarEnabled()) {
            return;
        }

        if (mAppBarViewStub != null) {
            mAppBarViewStub.setLayoutResource(getAppBarLayout());
            mAppBarViewStub.inflate();
        }

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        if (appBarLayout == null) {
            return;
        }

        Toolbar toolbar = (Toolbar) appBarLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled());
            supportActionBar.setHomeButtonEnabled(true);
        }
    }

    private void onActionBarHomeIconClicked() {
        if (isDisplayHomeAsUpEnabled())
            onBackPressed();
        else
            this.finish();
    }

}
