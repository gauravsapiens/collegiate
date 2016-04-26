package com.collegiate.database.impl;

import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.database.CourseDao;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Update;

import java.util.Collection;

/**
 * Created by gauravarora on 01/08/15.
 */
public class CourseDaoImpl extends BaseDao<Course> implements CourseDao {

    @Override
    public Collection<Course> getCourses(String collegeId) {
        return new Select()
                .from(getBeanClass())
                .where("collegeId=?", collegeId)
                .and(Condition.column("enabled").eq(true))
                .queryList();
    }

    @Override
    public Collection<Course> getCourses(String collegeId, String categoryId) {
        return new Select()
                .from(getBeanClass())
                .where("collegeId=?", collegeId)
                .and(Condition.column("categoryId").eq(categoryId))
                .and(Condition.column("enabled").eq(true))
                .orderBy(false, "year")
                .queryList();
    }

    @Override
    public Collection<Course> getUnSubscribedCourses(String collegeId, String categoryId) {
        return new Select()
                .from(getBeanClass())
                .where("collegeId=?", collegeId)
                .and(Condition.column("categoryId").eq(categoryId))
                .and(Condition.column("subscribed").eq(false))
                .and(Condition.column("enabled").eq(true))
                .orderBy(false, "year")
                .queryList();
    }

    @Override
    public Collection<Course> getUncategorizedCourses(String collegeId) {
        return new Select()
                .from(getBeanClass())
                .where("collegeId=?", collegeId)
                .and(Condition.column("categoryId").isNull())
                .queryList();
    }

    @Override
    public void setCourseSubscription(String courseId, boolean subscribed) {
        new Update<>(getBeanClass())
                .set("subscribed=?", subscribed)
                .where("id=?", courseId)
                .queryClose();
    }

    @Override
    public void setCategory(String courseId, String categoryId) {
        new Update<>(getBeanClass())
                .set("categoryId=?", categoryId)
                .where("id=?", courseId)
                .queryClose();
    }

    @Override
    public Collection<Course> getAllSubscribedCourses() {
        return new Select()
                .from(getBeanClass())
                .where("subscribed=?", true)
                .queryList();
    }

    @Override
    public Collection<Course> getSubscribedCourses(College college) {
        return new Select()
                .from(getBeanClass())
                .where("subscribed=?", true)
                .and(Condition.column("collegeId").eq(college.getId()))
                .queryList();
    }

    @Override
    public void disableCourse(String courseId) {
        new Update<>(getBeanClass())
                .set("enabled=?", false)
                .where("id=?", courseId)
                .queryClose();
    }

    @Override
    protected Class<Course> getBeanClass() {
        return Course.class;
    }
}
