package com.collegiate.ui.college.selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.collegiate.IntentHelper;
import com.collegiate.bean.College;
import com.collegiate.core.activity.SingleStackActivity;
import com.collegiate.database.CollegeDao;

import javax.inject.Inject;

public class CollegeSelectionActivity extends SingleStackActivity implements CollegeSelectionFragment.Callbacks {

    @Inject
    CollegeDao mCollegeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment onCreatePane() {
        return new CollegeSelectionFragment();
    }

    @Override
    public void onCollegeSelected(College college) {
        Intent intent = IntentHelper.getIntentForCollegeDetail(this, college);
        startActivity(intent);
    }

    @Override
    protected boolean isToolbarEnabled() {
        return false;
    }

}
