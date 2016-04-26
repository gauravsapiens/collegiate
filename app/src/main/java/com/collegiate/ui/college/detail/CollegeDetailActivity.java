package com.collegiate.ui.college.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.collegiate.DaggerAppComponent;
import com.collegiate.IntentConstants;
import com.collegiate.IntentHelper;
import com.collegiate.R;
import com.collegiate.bean.College;
import com.collegiate.core.activity.BaseActivity;
import com.collegiate.database.CollegeDao;
import com.collegiate.util.FragmentUtils;

import javax.inject.Inject;

/**
 * Created by gauravarora on 09/09/15.
 */
public class CollegeDetailActivity extends BaseActivity {

    private College mCollege;
    private TextView mTitle;
    private TextView mDescription;
    private Button mEnrollButton;

    @Inject
    CollegeDao mCollegeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);

        DaggerAppComponent.create().inject(this);
        setup();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setup() {
        mCollege = getCollege();

        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(mCollege.getFullName());

        mDescription = (TextView) findViewById(R.id.description);
        mDescription.setText(mCollege.getAbout());

        mEnrollButton = (Button) findViewById(R.id.enroll_button);
        mEnrollButton.setOnClickListener(view -> onCollegeSelected());

        IntroVideoPlayerFragment fragment = new IntroVideoPlayerFragment();
        fragment.setArguments(getBundleForYoutubePlayerFragment());
        FragmentUtils.replaceFragment(this, fragment, R.id.player_frame);
    }

    private void onCollegeSelected() {
        mCollegeDao.setSelectedCollege(mCollege.getId());

        Intent intent = IntentHelper.getIntentForCollegeHome(this);
        IntentHelper.makeIntentNode(intent);
        startActivity(intent);
    }

    private Bundle getBundleForYoutubePlayerFragment() {
        Bundle args = new Bundle();
        args.putString(IntentConstants.YOUTUBE_VIDEO_ID, mCollege.getIntroVideo());
        return args;
    }

    private College getCollege() {
        return getIntent().getExtras().getParcelable(IntentConstants.COLLEGE);
    }

}
