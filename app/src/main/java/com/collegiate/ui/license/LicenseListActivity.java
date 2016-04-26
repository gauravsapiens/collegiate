package com.collegiate.ui.license;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.collegiate.IntentHelper;
import com.collegiate.bean.License;
import com.collegiate.core.activity.SingleStackActivity;

/**
 * Created by gauravarora on 08/09/15.
 */
public class LicenseListActivity extends SingleStackActivity implements LicenseListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityTitle("Credits");
    }

    @Override
    protected Fragment onCreatePane() {
        return new LicenseListFragment();
    }

    protected boolean isNavDrawerEnabled() {
        return true;
    }

    @Override
    public void onLicenseSelected(License license) {
//        if (license == null) {
//            return;
//        }
//
//        Intent intent = IntentHelper.getIntentForLicenseDetail(this, license);
//        startActivity(intent);
    }
}
