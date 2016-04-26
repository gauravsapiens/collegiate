package com.collegiate.core.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.collegiate.R;
import com.collegiate.util.CollectionUtils;
import com.collegiate.view.base.ColImageView;

import java.util.List;

/**
 * Created by gauravarora on 18/02/15.
 */
public abstract class PagerActivity extends BaseDrawerActivity {

    protected ColImageView mToolbarImage;
    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected PagerAdapter mAdapter;
    protected List<PagerAdapter.FragmentInfo> mFragmentInfos;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_pager);

        setup();
    }

    @Override
    protected int getAppBarLayout() {
        return R.layout.app_bar_tabbed;
    }

    private void setup() {
        mToolbarImage = (ColImageView) findViewById(R.id.backdrop);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mFragmentInfos = getFragmentInfos();
        if (CollectionUtils.isEmpty(mFragmentInfos)) {
            return;
        }

        mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragmentInfos);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    protected abstract List<PagerAdapter.FragmentInfo> getFragmentInfos();

}
