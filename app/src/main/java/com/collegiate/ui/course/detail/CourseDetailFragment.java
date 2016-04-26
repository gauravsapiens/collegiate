package com.collegiate.ui.course.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegiate.DaggerAppComponent;
import com.collegiate.DataFactory;
import com.collegiate.IntentConstants;
import com.collegiate.R;
import com.collegiate.analytics.AnalyticsHelper;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Days;
import com.collegiate.bean.Lecture;
import com.collegiate.bean.Schedule;
import com.collegiate.bean.Time;
import com.collegiate.core.ItemizedArrayAdapter;
import com.collegiate.core.TableItem;
import com.collegiate.core.fragment.BaseFragment;
import com.collegiate.database.CourseDao;
import com.collegiate.database.ScheduleDao;
import com.collegiate.database.async.DbTransactionCallbacks;
import com.collegiate.notification.NotificationController;
import com.collegiate.util.CollectionUtils;
import com.collegiate.util.ResourceUtils;
import com.collegiate.util.ViewUtils;
import com.collegiate.view.ScheduleDayTimeView;
import com.collegiate.view.item.LectureItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gauravarora on 15/07/15.
 */
public class CourseDetailFragment extends BaseFragment implements ScheduleDayTimeView.Callbacks, DbTransactionCallbacks<Collection<Lecture>> {

    public interface Callbacks {
        void onLectureSelected(Course course, Lecture lecture);
    }

    private static int DESCRIPTION_MARGIN_IN_DP = 8;

    private View mRootView;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTitle;
    private TextView mDescription;
    private ImageButton mExpandButton;
    private View mScheduleCardView;
    private View mScheduleHeader;
    private SwitchCompat mScheduleSwitch;
    private ScheduleDayTimeView mScheduleTimeView;
    private ListView mLectureList;
    private ItemizedArrayAdapter mAdapter;
    private ProgressBar mLectureProgressBar;

    private College mCollege;
    private Course mCourse;
    private Schedule mSchedule;

    private LectureItem mLastSelectedItem;
    private boolean mIsCourseOrScheduleUpdated;

    @Inject
    CourseDao mCourseDao;

    @Inject
    ScheduleDao mScheduleDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_course_detail, null);

        mTitle = (TextView) mRootView.findViewById(R.id.title);
        mDescription = (TextView) mRootView.findViewById(R.id.description);
        mExpandButton = (ImageButton) mRootView.findViewById(R.id.button_expand);
        mFloatingActionButton = (FloatingActionButton) mRootView.findViewById(R.id.floating_action_button);

        mScheduleCardView = mRootView.findViewById(R.id.schedule_card);
        mScheduleHeader = mScheduleCardView.findViewById(R.id.schedule_header);
        mScheduleSwitch = (SwitchCompat) mScheduleCardView.findViewById(R.id.switch_schedule);
        mScheduleTimeView = (ScheduleDayTimeView) mScheduleCardView.findViewById(R.id.schedule_day_time_view);
        mScheduleTimeView.setCallbacks(this);

        mLectureList = (ListView) mRootView.findViewById(R.id.lecture_list);
        mLectureProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCourse = getCourse();
        mCollege = getCollege();

        mTitle.setText(mCourse.getTitle());
        mDescription.setText(mCourse.getDescription());
        mDescription.post(this::initDescriptionExpandButton);

        mFloatingActionButton.setSelected(mCourse.getSubscribed());
        mFloatingActionButton.setOnClickListener(view -> onSubscribeButtonClicked());

        initScheduleCard();

        initLectureCard();
    }

    public void updateSelectedLectureAndRefresh(Lecture lecture) {
        if(mLastSelectedItem != null){
            mLastSelectedItem.setLecture(lecture);
        }

        refreshLectureList();
    }

    public void refreshLectureList() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDaySelectionChanged(Days days) {
        if (days == null) {
            return;
        }

        mSchedule.setDays(days);
        mScheduleDao.update(mSchedule);

        updateCourseNotification();
        mIsCourseOrScheduleUpdated = true;
    }

    @Override
    public void onTimeChanged(Time time) {
        if (time == null) {
            return;
        }

        mSchedule.setTime(time);
        mScheduleDao.update(mSchedule);

        updateCourseNotification();
        mIsCourseOrScheduleUpdated = true;
    }

    public boolean isCourseOrScheduleUpdated() {
        return mIsCourseOrScheduleUpdated;
    }

    public boolean isCourseSubscribed() {
        return mCourse != null && mCourse.getSubscribed();
    }

    @Override
    public void onSuccess(Collection<Lecture> lectures) {
        mLectureProgressBar.setVisibility(View.GONE);
        onLectureFetched(lectures);
    }

    @Override
    public void onFailure() {
        mLectureProgressBar.setVisibility(View.GONE);
    }

    private void initScheduleCard() {
        boolean isSubscribed = mCourse.getSubscribed();
        ViewUtils.setVisibility(mScheduleCardView, isSubscribed);

        if (!isSubscribed) {
            mScheduleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> onScheduleSwitchToggled(isChecked));
            mScheduleHeader.setOnClickListener(v -> mScheduleSwitch.toggle());
            return;
        }

        mSchedule = mScheduleDao.findById(mCourse.getId());

        boolean isCourseScheduled = mSchedule != null;
        mScheduleSwitch.setChecked(isCourseScheduled);
        ViewUtils.setVisibility(mScheduleTimeView, isCourseScheduled);
        if (isCourseScheduled) {
            mScheduleTimeView.setSchedule(mSchedule.getDays(), mSchedule.getTime());
        }

        mScheduleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> onScheduleSwitchToggled(isChecked));
        mScheduleHeader.setOnClickListener(v -> mScheduleSwitch.toggle());
    }

    private void initLectureCard() {
        mLectureProgressBar.setVisibility(View.VISIBLE);
        DataFactory.getLectures(mCourse, this);
    }

    private void onLectureFetched(Collection<Lecture> lectures) {
        List<TableItem> items = getLectureItems(lectures);
        if (CollectionUtils.isEmpty(items)) {
            return;
        }

        mAdapter = new ItemizedArrayAdapter(getActivity());
        mAdapter.setData(items);

        mLectureList.setAdapter(mAdapter);
        mLectureList.setOnItemClickListener((parent, view, position, id) -> {
            TableItem item = mAdapter.getItem(position);
            Object userInfo = item.getUserInfo();
            if (userInfo == null) {
                return;
            }

            mLastSelectedItem = (LectureItem) item;
            onLectureItemClicked((Lecture) userInfo);
        });
    }

    private List<TableItem> getLectureItems(Collection<Lecture> lectures) {
        if (CollectionUtils.isEmpty(lectures)) {
            return null;
        }

        int counter = 0;
        List<TableItem> items = new ArrayList<>();
        for (Lecture lecture : lectures) {
            LectureItem lectureItem = new LectureItem(lecture);
            items.add(lectureItem);

            if (++counter == lectures.size()) {
                lectureItem.setSeparatorVisibility(false);
            }
        }
        return items;
    }

    private void onSubscribeButtonClicked() {
        boolean isSubscribedNew = mCourse.toggleSubscribed();
        mFloatingActionButton.setSelected(isSubscribedNew);

        ViewUtils.setVisibility(mScheduleCardView, isSubscribedNew);
        updateCourseSubscription(mCourse.getId(), isSubscribedNew);

        if (!isSubscribedNew) {
            mScheduleSwitch.setChecked(false);
        }

        mIsCourseOrScheduleUpdated = true;

        AnalyticsHelper.reportEventForSubscribedCourseButtonClicked(mCollege, mCourse);
    }

    private void onScheduleSwitchToggled(boolean isChecked) {
        ViewUtils.setVisibility(mScheduleTimeView, isChecked);
        if (isChecked) {
            mSchedule = new Schedule(mCourse.getId());
            mScheduleDao.create(mSchedule);
            mScheduleTimeView.setSchedule(mSchedule.getDays(), mSchedule.getTime());
        } else {
            mScheduleDao.delete(mSchedule.getCourseId());
            removeCourseNotification();
        }

        mIsCourseOrScheduleUpdated = true;

        AnalyticsHelper.reportEventForScheduleButtonClicked(mCollege, mCourse);
    }

    private void initDescriptionExpandButton() {
        if (mDescription.getLineCount() > ResourceUtils.getInteger(R.integer.description_collapsed_lines)) {
            mExpandButton.setVisibility(View.VISIBLE);
            mDescription.setOnClickListener(v -> onDescriptionExpandButtonClicked());
            mExpandButton.setOnClickListener(v -> onDescriptionExpandButtonClicked());
        } else {
            int margins = ViewUtils.dpToPx(DESCRIPTION_MARGIN_IN_DP);
            mExpandButton.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mDescription.getLayoutParams();
            layoutParams.setMargins(0, margins, 0, 2 * margins);
        }
    }

    private void onDescriptionExpandButtonClicked() {
        boolean isExpanded = mExpandButton.isSelected();

        int settableMaxLines = !isExpanded ? ResourceUtils.getInteger(R.integer.description_expanded_lines) : ResourceUtils.getInteger(R.integer.description_collapsed_lines);
        mDescription.setMaxLines(settableMaxLines);
        mExpandButton.setSelected(!isExpanded);
    }

    private void updateCourseSubscription(String courseId, boolean subscriptionStatus) {
        if (courseId == null) {
            return;
        }

        mCourseDao.setCourseSubscription(courseId, subscriptionStatus);
    }

    private void onLectureItemClicked(Lecture lecture) {
        Object callbacks = getCallbacks(Callbacks.class);
        if (callbacks != null) {
            ((Callbacks) callbacks).onLectureSelected(mCourse, lecture);
        }
    }

    private void updateCourseNotification() {
        NotificationController.getInstance().updateNotification(getActivity(), mCourse, mSchedule);
    }

    private void removeCourseNotification() {
        NotificationController.getInstance().removeNotification(getActivity(), mCourse);
    }

    private Course getCourse() {
        return getArguments().getParcelable(IntentConstants.COURSE);
    }

    private College getCollege() {
        return getArguments().getParcelable(IntentConstants.COLLEGE);
    }

}
