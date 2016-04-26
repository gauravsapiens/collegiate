package com.collegiate;

import com.collegiate.analytics.AnalyticsTracker;
import com.collegiate.analytics.GoogleAnalyticsTracker;
import com.collegiate.database.CategoryDao;
import com.collegiate.database.CollegeDao;
import com.collegiate.database.CourseDao;
import com.collegiate.database.LectureDao;
import com.collegiate.database.LicenseDao;
import com.collegiate.database.NotesDao;
import com.collegiate.database.ScheduleDao;
import com.collegiate.database.impl.CategoryDaoImpl;
import com.collegiate.database.impl.CollegeDaoImpl;
import com.collegiate.database.impl.CourseDaoImpl;
import com.collegiate.database.impl.LectureDaoImpl;
import com.collegiate.database.impl.LicenseDaoImpl;
import com.collegiate.database.impl.NotesDaoImpl;
import com.collegiate.database.impl.ScheduleDaoImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gauravarora on 08/09/15.
 */
@Module
public class AppModule {

    @Provides
    CollegeDao providesCollegeDao() {
        return new CollegeDaoImpl();
    }

    @Provides
    CategoryDao providesCategoryDao() {
        return new CategoryDaoImpl();
    }

    @Provides
    CourseDao providesCourseDao() {
        return new CourseDaoImpl();
    }

    @Provides
    LectureDao provideLectureDao() {
        return new LectureDaoImpl();
    }

    @Provides
    LicenseDao provideLicenseDao() {
        return new LicenseDaoImpl();
    }

    @Provides
    ScheduleDao provideScheduleDao() {
        return new ScheduleDaoImpl();
    }

    @Provides
    NotesDao provideNotesDao() {
        return new NotesDaoImpl();
    }

    @Provides
    AnalyticsTracker providesAnalyticsTracker() {
        return new GoogleAnalyticsTracker();
    }

}
