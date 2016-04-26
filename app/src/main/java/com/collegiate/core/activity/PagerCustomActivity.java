package com.collegiate.core.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.collegiate.R;
import com.collegiate.util.CollectionUtils;
import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.List;

/**
 * Created by gauravarora on 15/08/15.
 */
public abstract class PagerCustomActivity extends BaseDrawerActivity {

    protected MaterialViewPager mMaterialViewPager;
    protected PagerAdapter mAdapter;
    protected List<PagerAdapter.FragmentInfo> mFragmentInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
    }

    private void setup() {
        mMaterialViewPager = (MaterialViewPager) findViewById(R.id.material_view_pager);
        mMaterialViewPager.getViewPager().setOffscreenPageLimit(getViewPagerOffset());

        Toolbar toolbar = mMaterialViewPager.getToolbar();
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (isNavDrawerEnabled()) {
            toolbar.setNavigationIcon(R.drawable.ic_menu);
        }

        setupViewPager();
    }

    protected void setupViewPager(){
        mFragmentInfos = getFragmentInfos();
        if (CollectionUtils.isEmpty(mFragmentInfos)) {
            return;
        }

        mAdapter = new PagerAdapter(getSupportFragmentManager(), mFragmentInfos);
        mMaterialViewPager.getViewPager().setAdapter(mAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = mMaterialViewPager.getPagerTitleStrip();
        pagerSlidingTabStrip.setTextColorStateListResource(R.drawable.selector_tab_indicator);
        pagerSlidingTabStrip.setViewPager(mMaterialViewPager.getViewPager());
    }

    protected abstract List<PagerAdapter.FragmentInfo> getFragmentInfos();

    protected void setImageDrawable(Drawable imageDrawable) {
        mMaterialViewPager.setImageDrawable(imageDrawable, 0);
    }

    protected void setHeaderText(String text) {
        TextView textView = (TextView) mMaterialViewPager.findViewById(R.id.logo_text);
        if (textView != null) {
            textView.setText(text);
        }
    }

    protected int getParentLayout() {
        return R.layout.activity_custom_pager;
    }

    protected boolean isToolbarEnabled() {
        return false;
    }

    protected int getViewPagerOffset() {
        return 2;
    }

    protected Fragment getCurrentFragment() {
        ViewPager viewPager = mMaterialViewPager.getViewPager();
        if (viewPager == null) {
            return null;
        }

        int currentPosition = viewPager.getCurrentItem();
        return mAdapter.getFragmentAtPosition(currentPosition);
    }

}
