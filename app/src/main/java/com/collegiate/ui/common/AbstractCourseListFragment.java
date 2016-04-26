package com.collegiate.ui.common;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.collegiate.DaggerAppComponent;
import com.collegiate.bean.Course;
import com.collegiate.core.ItemizedRecyclableAdapter;
import com.collegiate.core.RecyclableItem;
import com.collegiate.core.fragment.BaseItemFragment;
import com.collegiate.database.CourseDao;
import com.collegiate.util.CollectionUtils;
import com.collegiate.view.item.CourseItem;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by gauravarora on 11/07/15.
 */
public abstract class AbstractCourseListFragment extends BaseItemFragment {

    public interface Callback {
        void onCourseSelected(Course course, View view);
    }

    @Inject
    CourseDao mCourseDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    @Override
    public List<RecyclableItem> loadListItemsInBackground() {
        Collection<Course> courses = getCourses();
        if (CollectionUtils.isEmpty(courses)) {
            return null;
        }

        List<RecyclableItem> items = new ArrayList<>();
        for (Course course : courses) {
            CourseItem courseItem = newItem(course) ;
            items.add(courseItem);
        }
        return items;
    }

    protected CourseItem newItem(Course course){
        return new CourseItem(course);
    }

    @Override
    public void onItemClicked(RecyclableItem item, long position) {
        if (!(item instanceof CourseItem)) {
            return;
        }

        CourseItem courseItem = (CourseItem) item;

        Object callbacks = getCallbacks(Callback.class);
        if (callbacks != null) {
            ((Callback) callbacks).onCourseSelected(courseItem.getUserInfo(), courseItem.getView());
        }
    }

    @Override
    protected RecyclerView.Adapter decorateAdapter(ItemizedRecyclableAdapter adapter) {
        return new RecyclerViewMaterialAdapter(adapter);
    }

    protected abstract Collection<Course> getCourses();

}
