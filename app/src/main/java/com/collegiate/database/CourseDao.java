package com.collegiate.database;

import com.collegiate.bean.College;
import com.collegiate.bean.Course;

import java.util.Collection;

/**
 * Created by gauravarora on 01/08/15.
 */
public interface CourseDao extends Dao<Course> {

    Collection<Course> getCourses(String collegeId);

    Collection<Course> getCourses(String collegeId, String categoryId);

    Collection<Course> getUnSubscribedCourses(String collegeId, String categoryId);

    Collection<Course> getUncategorizedCourses(String collegeId);

    void setCourseSubscription(String courseId, boolean subscribed);

    void setCategory(String courseId, String categoryId);

    Collection<Course> getAllSubscribedCourses();

    Collection<Course> getSubscribedCourses(College college);

    void disableCourse(String courseId);
}
