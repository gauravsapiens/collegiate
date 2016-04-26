package com.collegiate.database.impl;


import com.collegiate.bean.License;
import com.collegiate.database.LicenseDao;

/**
 * Created by gauravarora on 08/09/15.
 */
public class LicenseDaoImpl extends BaseDao<License> implements LicenseDao {

    @Override
    protected Class<License> getBeanClass() {
        return License.class;
    }

}
