package com.collegiate.database.impl;

import com.collegiate.bean.College;
import com.collegiate.database.CollegeDao;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Update;

/**
 * Created by gauravarora on 01/08/15.
 */
public class CollegeDaoImpl extends BaseDao<College> implements CollegeDao {

    @Override
    public College getSelectedCollege() {
        return new Select()
                .from(getBeanClass())
                .where("selected=?", true)
                .querySingle();
    }

    @Override
    public void setSelectedCollege(String collegeId) {
        clearSelectedCollege();

        new Update<>(getBeanClass())
                .set("selected=?", true)
                .where("id=?", collegeId)
                .queryClose();
    }

    @Override
    public void clearSelectedCollege() {
        new Update<>(getBeanClass())
                .set("selected=?", false)
                .queryClose();
    }

    @Override
    protected Class<College> getBeanClass() {
        return College.class;
    }

}
