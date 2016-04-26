package com.collegiate.core.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.collegiate.R;

/**
 * Created by gauravarora on 18/06/15.
 */
public abstract class BaseDrawerActivity extends BaseActivity {

    protected DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNavDrawerEnabled()) {
            return;
        }

        onNavigationDrawerVisible();
    }

    @Override
    protected int getParentLayout() {
        if (isNavDrawerEnabled()) {
            return R.layout.activity_drawer;
        } else {
            return super.getParentLayout();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isNavDrawerEnabled()) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
                } else {
                    onBackPressed();
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean isNavDrawerEnabled() {
        return false;
    }

    protected boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    private void onNavigationDrawerVisible() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) mDrawerLayout.findViewById(R.id.navigation_view);
        NavigationPresenter navigationPresenter = new NavigationPresenter(this, mDrawerLayout, navigationView);
        navigationPresenter.setup();
    }

}
