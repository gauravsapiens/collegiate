package com.collegiate.ui.college.selection;

import com.collegiate.DataFactory;
import com.collegiate.bean.College;
import com.collegiate.core.RecyclableItem;
import com.collegiate.core.fragment.BaseItemFragment;
import com.collegiate.util.CollectionUtils;
import com.collegiate.view.item.CollegeItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gauravarora on 06/07/15.
 */
public class CollegeSelectionFragment extends BaseItemFragment {

    public interface Callbacks {
        void onCollegeSelected(College college);
    }

    private Collection<College> mColleges;

    @Override
    public List<RecyclableItem> loadListItemsInBackground() {
        Collection<College> colleges = getColleges();
        if (CollectionUtils.isEmpty(colleges)) {
            return null;
        }

        List<RecyclableItem> items = new ArrayList<>();
        for (College college : colleges) {
            CollegeItem collegeItem = new CollegeItem(college);
            items.add(collegeItem);
        }
        return items;
    }

    private Collection<College> getColleges() {
        if (mColleges == null) {
            mColleges = DataFactory.getColleges();
        }
        return mColleges;
    }

    @Override
    public void onItemClicked(RecyclableItem item, long position) {
        Object userInfo = item.getUserInfo();
        if (userInfo == null) {
            return;
        }

        Object callback = getCallbacks(Callbacks.class);
        if (callback != null) {
            ((Callbacks) callback).onCollegeSelected((College) userInfo);
        }
    }
}
