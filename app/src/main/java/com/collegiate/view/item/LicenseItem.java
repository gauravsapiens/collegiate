package com.collegiate.view.item;

import com.collegiate.bean.License;

/**
 * Created by gauravarora on 08/09/15.
 */
public class LicenseItem extends SimpleTitleSubtitleItem {

    private License mLicense;

    public LicenseItem(License license) {
        super(license.getHolder(), license.getName());
        mLicense = license;
    }

    @Override
    public License getUserInfo() {
        return mLicense;
    }
}
