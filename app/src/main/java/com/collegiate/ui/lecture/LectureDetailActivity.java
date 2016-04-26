package com.collegiate.ui.lecture;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.collegiate.DaggerAppComponent;
import com.collegiate.DataFactory;
import com.collegiate.IntentConstants;
import com.collegiate.R;
import com.collegiate.analytics.AnalyticsHelper;
import com.collegiate.analytics.bean.Event;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.bean.Lecture;
import com.collegiate.bean.Notes;
import com.collegiate.core.activity.BaseActivity;
import com.collegiate.database.CollegeDao;
import com.collegiate.database.CourseDao;
import com.collegiate.database.LectureDao;
import com.collegiate.database.NotesDao;
import com.collegiate.database.async.SafeDbTransactionCallbacks;
import com.collegiate.util.FragmentUtils;
import com.collegiate.util.ViewUtils;

import javax.inject.Inject;

/**
 * Created by gauravarora on 21/07/15.
 */
public class LectureDetailActivity extends BaseActivity {

    private LectureVideoPlayerFragment mFragment;

    private Toolbar mToolbar;
    private TextView mTitle;
    private ImageButton mNotesButton;

    private College mCollege;
    private Course mCourse;
    private Lecture mLecture;

    @Inject
    CollegeDao mCollegeDao;

    @Inject
    CourseDao mCourseDao;

    @Inject
    LectureDao mLectureDao;

    @Inject
    NotesDao mNotesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);

        mLecture = getLecture();
        if (mLecture == null) {
            return;
        }

        configureView();
        markAsSeen();
        performAnalytics();
    }

    private void configureView() {
        setContentView(R.layout.activity_lecture_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_cross);
        mToolbar.setTitle("");

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(mLecture.getTitle());

        mNotesButton = (ImageButton) findViewById(R.id.notes);
        mNotesButton.setOnClickListener(v -> onNotesButtonClicked(mLecture));

        mFragment = new LectureVideoPlayerFragment();
        mFragment.setArguments(getBundleForYoutubePlayerFragment(mLecture));
        FragmentUtils.replaceFragment(this, mFragment, R.id.player_frame);
    }

    private void markAsSeen() {
        mLectureDao.markAsSeen(mLecture.getId(), null);
    }

    private void performAnalytics() {
        mCourseDao.findById(mLecture.getCourseId(), new SafeDbTransactionCallbacks<Course>() {
            @Override
            public void onSuccess(Course course) {
                mCourse = course;
                if (mCourse == null) {
                    return;
                }
                mCollegeDao.findById(mCourse.getId(), new SafeDbTransactionCallbacks<College>() {
                    @Override
                    public void onSuccess(College college) {
                        mCollege = college;
                        reportScreenToAnalytics();
                    }
                });
            }
        });
    }

    private void reportScreenToAnalytics() {
        Event event = AnalyticsHelper.getEventForLectureDetailScreen(mCollege, mCourse, mLecture);
        reportScreenToAnalytics(event);
    }

    private void onNotesButtonClicked(Lecture lecture) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_notes, null);
        EditText noteInput = (EditText) dialogView.findViewById(R.id.note_input);

        Notes notes = getNote(lecture);
        if (notes != null) {
            noteInput.setText(notes.getNote());
        }

        String title = "Notes";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setView(dialogView)
                .setTitle(title)
                .setPositiveButton("Save", (dialog1, id) -> {
                    String note = ViewUtils.getText(noteInput);
                    saveNote(lecture.getId(), note);
                })
                .create();

        dialog.show();

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        AnalyticsHelper.reportEventForNotesButtonClicked(mCollege, mCourse, mLecture);
    }

    private Notes getNote(Lecture lecture) {
        return DataFactory.getNote(lecture.getId());
    }

    private void saveNote(String lectureId, String note) {
        mNotesDao.createOrUpdate(new Notes(lectureId, note));

        AnalyticsHelper.reportEventForNotesSaved(mCollege, mCourse, mLecture);
    }

    protected Lecture getLecture() {
        return getIntent().getExtras().getParcelable(IntentConstants.LECTURE);
    }

    @Override
    public void onBackPressed() {
        addBookmarkAtCurrentIndex();

        Intent intent = new Intent();
        intent.putExtra(IntentConstants.LECTURE, mLecture);
        setResult(RESULT_OK, intent);

        finish();
    }

    private void addBookmarkAtCurrentIndex() {
        if (mFragment == null || mLecture == null) {
            return;
        }

        int bookmark = mFragment.getTimeElapsed();
        mLecture.setBookmark(bookmark);
        mLectureDao.updateBookmark(mLecture.getId(), bookmark);
    }

    private Bundle getBundleForYoutubePlayerFragment(Lecture lecture) {
        Bundle args = new Bundle();
        args.putString(IntentConstants.YOUTUBE_VIDEO_ID, lecture.getId());
        args.putInt(IntentConstants.YOUTUBE_PLAYER_START_TIME, lecture.getBookmark());
        return args;
    }

    protected boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

}