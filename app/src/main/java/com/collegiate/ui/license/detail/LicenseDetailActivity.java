package com.collegiate.ui.license.detail;

import android.support.v4.app.Fragment;

import com.collegiate.core.activity.SingleStackActivity;

/**
 * Created by gauravarora on 08/09/15.
 */
public class LicenseDetailActivity extends SingleStackActivity {

    @Override
    protected Fragment onCreatePane() {
        return new LicenseDetailFragment();
    }

}
