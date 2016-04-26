package com.collegiate;

import com.collegiate.analytics.AnalyticsTracker;
import com.collegiate.core.activity.BaseActivity;
import com.collegiate.core.activity.NavigationPresenter;
import com.collegiate.database.CategoryDao;
import com.collegiate.database.CollegeDao;
import com.collegiate.database.CourseDao;
import com.collegiate.database.LectureDao;
import com.collegiate.database.LicenseDao;
import com.collegiate.database.NotesDao;
import com.collegiate.database.ScheduleDao;
import com.collegiate.notification.NotificationSchedulerService;
import com.collegiate.ui.college.detail.CollegeDetailActivity;
import com.collegiate.ui.college.home.MyClassFragment;
import com.collegiate.ui.common.AbstractCourseListFragment;
import com.collegiate.ui.course.detail.CourseDetailActivity;
import com.collegiate.ui.course.detail.CourseDetailFragment;
import com.collegiate.ui.lecture.LectureDetailActivity;

import dagger.Component;

/**
 * Created by gauravarora on 08/09/15.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {

    CategoryDao getCategoryDao();

    CollegeDao getCollegeDao();

    CourseDao getCourseDao();

    LectureDao getLectureDao();

    LicenseDao getLicenseDao();

    ScheduleDao getScheduleDao();

    NotesDao getNotesDao();

    AnalyticsTracker getAnalyticsTracker();

    void inject(CourseDetailFragment fragment);

    void inject(CollegeDetailActivity activity);

    void inject(NavigationPresenter navigationPresenter);

    void inject(AbstractCourseListFragment courseListFragment);

    void inject(MyClassFragment myClassFragment);

    void inject(CourseDetailActivity courseListFragment);

    void inject(NotificationSchedulerService notificationSchedulerService);

    void inject(LectureDetailActivity lectureDetailActivity);

    void inject(BaseActivity baseActivity);

}
