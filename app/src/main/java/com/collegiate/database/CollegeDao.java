package com.collegiate.database;

import com.collegiate.bean.College;

/**
 * Created by gauravarora on 01/08/15.
 */
public interface CollegeDao extends Dao<College> {

    College getSelectedCollege();

    void setSelectedCollege(String collegeId);

    void clearSelectedCollege();

}
