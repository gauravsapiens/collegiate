package com.collegiate.core.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.collegiate.DaggerAppComponent;
import com.collegiate.DataFactory;
import com.collegiate.IntentHelper;
import com.collegiate.LauncherOption;
import com.collegiate.analytics.AnalyticsHelper;
import com.collegiate.bean.College;
import com.collegiate.database.CollegeDao;
import com.collegiate.ui.college.selection.CollegeSelectionActivity;
import com.collegiate.util.AppUtils;

import javax.inject.Inject;

/**
 * Created by gauravarora on 08/09/15.
 */
public class NavigationPresenter {

    private BaseActivity mBaseActivity;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private College mSelectedCollege;

    @Inject
    CollegeDao mCollegeDao;

    public NavigationPresenter(BaseActivity baseActivity, DrawerLayout drawerLayout, NavigationView navigationView) {
        this.mBaseActivity = baseActivity;
        mDrawerLayout = drawerLayout;
        mNavigationView = navigationView;

        DaggerAppComponent.create().inject(this);
    }

    public void setup() {
        mSelectedCollege = DataFactory.getSelectedCollege();
        Class clazz = mBaseActivity.getClass();

        Menu menu = mNavigationView.getMenu();
        for (LauncherOption launcherOption : LauncherOption.values()) {
            String title = launcherOption.getTitle();

            //set college name
            if (launcherOption == LauncherOption.HOME) {
                if (mSelectedCollege != null) {
                    title = mSelectedCollege.getShortName();
                }
            }

            int groupId = (launcherOption == LauncherOption.SHARE) ? 0 : 1;

            MenuItem menuItem = menu.add(groupId, launcherOption.getIndex(), launcherOption.getIndex(), title);
            menuItem.setIcon(launcherOption.getImageResourceId());
            menuItem.setCheckable(true);

            if (clazz != null && clazz.equals(launcherOption.getIntentClass())) {
                menuItem.setChecked(true);
            }
        }

        mNavigationView.setNavigationItemSelectedListener(this::onNavigationButtonSelected);
    }

    private boolean onNavigationButtonSelected(MenuItem menuItem) {
        int index = menuItem.getOrder();
        LauncherOption mLauncherOption = LauncherOption.getLauncherOption(index);
        onLauncherOptionSelected(mLauncherOption);

        mDrawerLayout.closeDrawers();
        return true;
    }

    private void onLauncherOptionSelected(LauncherOption launcherOption) {
        switch (launcherOption) {
            case SWITCH_COLLEGE:
                showConfirmationDialog();
                AnalyticsHelper.reportEventForCollegeSwitched(mSelectedCollege);
                break;

            case SHARE:
                AppUtils.shareApp(mBaseActivity);
                AnalyticsHelper.reportEventForAppShared(mSelectedCollege);
                break;

            default:
                launchNodeActivity(launcherOption.getIntentClass());
        }
    }

    private void showConfirmationDialog() {
        String title = "Do you really want to switch college?";

        StringBuilder message = new StringBuilder("Relax, you can always come back. And we will still keep your subscribed courses");
        if (mSelectedCollege != null) {
            message.append(" from ").append(mSelectedCollege.getShortName());
        }
        message.append(".");

        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, id) -> {
                    onCollegeSwitchConfirmed();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                })
                .show();
    }

    private void onCollegeSwitchConfirmed() {
        mCollegeDao.clearSelectedCollege();
        launchNodeActivity(CollegeSelectionActivity.class);
    }

    private void launchNodeActivity(Class intentClass) {
        Intent intent = new Intent(mBaseActivity, intentClass);
        IntentHelper.makeIntentNode(intent);
        mBaseActivity.startActivity(intent);
    }
}
