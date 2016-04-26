package com.collegiate.analytics;

import com.collegiate.DaggerAppComponent;
import com.collegiate.analytics.bean.Dimension;
import com.collegiate.analytics.bean.Event;
import com.collegiate.analytics.bean.EventType;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Lecture;
import com.collegiate.util.StringUtils;

/**
 * Created by gauravarora on 01/10/15.
 */
public class AnalyticsHelper {

    private static AnalyticsTracker sAnalyticsTracker;
    static {
        sAnalyticsTracker = DaggerAppComponent.create().getAnalyticsTracker();
    }

    public static Event getEventForCollegeHomeScreen(College college, Integer subscribedCoursesCount) {
        Event.Builder builder = getBaseBuilderForCollegeHomeScreen(college, subscribedCoursesCount);
        builder.setEventType(EventType.SCREEN_STARTED);

        return builder.build();
    }

    public static Event getEventForCourseDetailScreen(College college, Course course) {
        Event.Builder builder = getBaseBuilderForCourseDetailScreen(college, course);
        builder.setEventType(EventType.SCREEN_STARTED);
        return builder.build();
    }

    public static Event getEventForLectureDetailScreen(College college, Course course, Lecture lecture) {
        Event.Builder builder = getBaseBuilderForLectureDetailScreen(college, course, lecture);
        builder.setEventType(EventType.SCREEN_STARTED);

        return builder.build();
    }

    public static void reportEventForCollegeSwitched(College college) {
        Event.Builder builder = new Event.Builder();
        builder.setEventType(EventType.BUTTON_PRESSED);
        builder.setAction("College Switched");

        if (StringUtils.isNotEmpty(college.getShortName())) {
            builder.addCustomDimension(Dimension.COLLEGE_NAME, college.getShortName());
        }

        reportActionToAnalytics(builder.build());
    }

    public static void reportEventForAppShared(College college) {
        Event.Builder builder = new Event.Builder();
        builder.setEventType(EventType.BUTTON_PRESSED);
        builder.setAction("App Shared");

        if (StringUtils.isNotEmpty(college.getShortName())) {
            builder.addCustomDimension(Dimension.COLLEGE_NAME, college.getShortName());
        }

        reportActionToAnalytics(builder.build());
    }

    public static void reportEventForSubscribedCourseButtonClicked(College college, Course course) {
        Event.Builder builder = getBaseBuilderForCourseDetailScreen(college, course);
        builder.setEventType(EventType.BUTTON_PRESSED);
        builder.setAction("Course Subscribed");

        reportActionToAnalytics(builder.build());
    }

    public static void reportEventForScheduleButtonClicked(College college, Course course) {
        Event.Builder builder = getBaseBuilderForCourseDetailScreen(college, course);
        builder.setEventType(EventType.BUTTON_PRESSED);
        builder.setAction("Course Scheduled");

        reportActionToAnalytics(builder.build());
    }

    public static void reportEventForCourseShared(Course course) {
        Event.Builder builder = new Event.Builder();
        builder.setEventType(EventType.BUTTON_PRESSED);
        builder.setAction("Course Shared");

        if (StringUtils.isNotEmpty(course.getId())) {
            builder.addCustomDimension(Dimension.COURSE_ID, course.getId());
        }

        if (StringUtils.isNotEmpty(course.getTitle())) {
            builder.addCustomDimension(Dimension.COURSE_NAME, course.getTitle());
        }

        reportActionToAnalytics(builder.build());
    }

    public static void reportEventForNotesButtonClicked(College college, Course course, Lecture lecture) {
        Event event = getEventForLectureDetailScreen(college, course, lecture);
        event.setEventType(EventType.BUTTON_PRESSED);
        event.setAction("Notes Clicked");

        reportActionToAnalytics(event);
    }

    public static void reportEventForNotesSaved(College college, Course course, Lecture lecture) {
        Event event = getEventForLectureDetailScreen(college, course, lecture);
        event.setEventType(EventType.BUTTON_PRESSED);
        event.setAction("Notes Saved");

        reportActionToAnalytics(event);
    }

    private static Event.Builder getBaseBuilderForCollegeHomeScreen(College college, Integer subscribedCoursesCount){
        Event.Builder builder = new Event.Builder();
        builder.setEventType(EventType.SCREEN_STARTED);
        builder.setScreen("College Home");

        if (StringUtils.isNotEmpty(college.getShortName())) {
            builder.addCustomDimension(Dimension.COLLEGE_NAME, college.getShortName());
        }

        if (subscribedCoursesCount != null) {
            builder.addCustomDimension(Dimension.SUBSCRIBED_COURSES_COUNT, subscribedCoursesCount + "");
        }

        return builder;
    }

    private static Event.Builder getBaseBuilderForCourseDetailScreen(College college, Course course){
        Event.Builder builder = new Event.Builder();
        builder.setScreen("Course Detail");

        if (college != null && StringUtils.isNotEmpty(college.getShortName())) {
            builder.addCustomDimension(Dimension.COLLEGE_NAME, college.getShortName());
        }

        if (course != null && StringUtils.isNotEmpty(course.getId())) {
            builder.addCustomDimension(Dimension.COURSE_ID, course.getId());
        }

        if (course != null && StringUtils.isNotEmpty(course.getTitle())) {
            builder.addCustomDimension(Dimension.COURSE_NAME, course.getTitle());
        }

        if (course != null && course.getYear() != 0) {
            builder.addCustomDimension(Dimension.COURSE_YEAR, course.getYear() + "");
        }

        return builder;
    }

    private static Event.Builder getBaseBuilderForLectureDetailScreen(College college, Course course, Lecture lecture){
        Event.Builder builder = new Event.Builder();
        builder.setScreen("Lecture Detail");

        if (college != null && StringUtils.isNotEmpty(college.getShortName())) {
            builder.addCustomDimension(Dimension.COLLEGE_NAME, college.getShortName());
        }

        if (course != null && StringUtils.isNotEmpty(course.getId())) {
            builder.addCustomDimension(Dimension.COURSE_ID, course.getId());
        }

        if (course !=null && StringUtils.isNotEmpty(course.getTitle())) {
            builder.addCustomDimension(Dimension.COURSE_NAME, course.getTitle());
        }

        if (course !=null && course.getYear() != 0) {
            builder.addCustomDimension(Dimension.COURSE_YEAR, course.getYear() + "");
        }

        if (lecture != null && StringUtils.isNotEmpty(lecture.getId())) {
            builder.addCustomDimension(Dimension.LECTURE_ID, lecture.getId());
        }

        if (lecture != null && StringUtils.isNotEmpty(lecture.getTitle())) {
            builder.addCustomDimension(Dimension.LECTURE_NAME, lecture.getTitle());
        }

        if (lecture != null && lecture.getDuration() != 0l) {
            builder.addCustomDimension(Dimension.LECTURE_DURATION, lecture.getDuration() + "");
        }

        return builder;
    }

    private static void reportActionToAnalytics(Event event){
        if(event == null){
            return;
        }

        sAnalyticsTracker.onActionTriggered(event);
    }

}
