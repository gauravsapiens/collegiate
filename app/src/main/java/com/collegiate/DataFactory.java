package com.collegiate;

import com.collegiate.bean.Category;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Lecture;
import com.collegiate.bean.License;
import com.collegiate.bean.Notes;
import com.collegiate.database.CategoryDao;
import com.collegiate.database.CollegeDao;
import com.collegiate.database.CourseDao;
import com.collegiate.database.LectureDao;
import com.collegiate.database.LicenseDao;
import com.collegiate.database.NotesDao;
import com.collegiate.database.async.DbTransactionCallbacks;

import java.util.Collection;

/**
 * Created by gauravarora on 09/07/15.
 */
public class DataFactory {

    public static Collection<College> getColleges() {
        CollegeDao collegeDao = getAppComponent().getCollegeDao();
        return collegeDao.findAll();
    }

    public static Collection<Category> getAllCategories(College college) {
        CategoryDao categoryDao = getAppComponent().getCategoryDao();
        return categoryDao.getCategory(college);
    }

    public static Collection<Category> getAllCategories() {
        CategoryDao categoryDao = getAppComponent().getCategoryDao();
        return categoryDao.findAll();
    }

    public static Collection<Course> getUnsubscribedCourses(College college, Category category) {
        CourseDao courseDao = getAppComponent().getCourseDao();
        return courseDao.getUnSubscribedCourses(college.getId(), category.getId());
    }

    public static Collection<Lecture> getLectures(Course course) {
        LectureDao lectureDao = getAppComponent().getLectureDao();
        return lectureDao.getLecturesForCourse(course.getId());
    }

    public static void getLectures(Course course, DbTransactionCallbacks<Collection<Lecture>> callbacks) {
        LectureDao lectureDao = getAppComponent().getLectureDao();
        lectureDao.getLecturesForCourse(course.getId(), callbacks);
    }

    public static Collection<License> getLicenses() {
        LicenseDao licenseDao = getAppComponent().getLicenseDao();
        return licenseDao.findAll();
    }

    public static College getSelectedCollege() {
        CollegeDao collegeDao = getAppComponent().getCollegeDao();
        return collegeDao.getSelectedCollege();
    }

    public static Collection<Course> getSubscribedCourses() {
        CourseDao courseDao = getAppComponent().getCourseDao();
        return courseDao.getAllSubscribedCourses();
    }

    public static Collection<Course> getSubscribedCourses(College college) {
        CourseDao courseDao = getAppComponent().getCourseDao();
        return courseDao.getSubscribedCourses(college);
    }

    public static Notes getNote(String lectureId) {
        NotesDao notesDao = getAppComponent().getNotesDao();
        return notesDao.getNotesForLecture(lectureId);
    }

    private static AppComponent getAppComponent() {
        return DaggerAppComponent.create();
    }
}
