package com.collegiate.database.impl;

import com.collegiate.bean.Schedule;
import com.collegiate.database.ScheduleDao;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Collection;

/**
 * Created by gauravarora on 20/09/15.
 */
public class ScheduleDaoImpl extends BaseDao<Schedule> implements ScheduleDao {

    @Override
    protected String getIdFieldName() {
        return "courseId";
    }

    @Override
    protected Class<Schedule> getBeanClass() {
        return Schedule.class;
    }

}

