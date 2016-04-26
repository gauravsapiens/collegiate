package com.collegiate;

import com.collegiate.ui.college.home.CollegeHomeActivity;
import com.collegiate.ui.college.selection.CollegeSelectionActivity;
import com.collegiate.ui.course.selected.SubscribedCoursesActivity;
import com.collegiate.ui.license.LicenseListActivity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gauravarora on 08/09/15.
 */
public enum LauncherOption {

    HOME(1, "Home", R.drawable.ic_college, CollegeHomeActivity.class),
    SWITCH_COLLEGE(2, "Switch College", R.drawable.ic_switch, null),
    CREDIT(3, "Credits", R.drawable.ic_license, LicenseListActivity.class),
    SHARE(4, "Share", R.drawable.ic_share_grey600_24dp, null);

    private static Map<Integer, LauncherOption> sOrderedLauncherOption = new HashMap<>();

    static {
        for (LauncherOption launcherOption : EnumSet.allOf(LauncherOption.class)) {
            sOrderedLauncherOption.put(launcherOption.getIndex(), launcherOption);
        }
    }

    private int mIndex;
    private String mTitle;
    private int mImageResourceId;
    private Class mClass;

    LauncherOption(int index, String title, int imageResourceId, Class clazz) {
        this.mIndex = index;
        this.mTitle = title;
        this.mImageResourceId = imageResourceId;
        this.mClass = clazz;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public Class getIntentClass() {
        return mClass;
    }

    public static Map<Integer, LauncherOption> getOrderedLauncherOption() {
        return sOrderedLauncherOption;
    }

    public static LauncherOption getLauncherOption(int index) {
        return sOrderedLauncherOption.get(index);
    }
}
