package com.collegiate.core.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.collegiate.util.ClassUtils;
import com.collegiate.util.Preconditions;


/**
 * Created by gauravarora on 14/06/15.
 */
public class BaseFragment extends Fragment {

    public Object getCallbacks(Class clazz) {
        Preconditions.checkNotNull(clazz, "Class cannot be null");

        Fragment fragment = getParentFragment();
        if (fragment != null && ClassUtils.isSubclass(fragment.getClass(), clazz)) {
            return fragment;
        }

        Activity activity = getActivity();
        if (activity != null && ClassUtils.isSubclass(activity.getClass(), clazz)) {
            return activity;
        }

        return null;
    }

}
