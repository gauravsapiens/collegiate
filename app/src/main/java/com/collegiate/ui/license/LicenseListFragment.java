package com.collegiate.ui.license;

import com.collegiate.DataFactory;
import com.collegiate.bean.License;
import com.collegiate.core.DividerItemDecoration;
import com.collegiate.core.RecyclableItem;
import com.collegiate.core.fragment.BaseItemFragment;
import com.collegiate.util.CollectionUtils;
import com.collegiate.view.item.LicenseItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gauravarora on 08/09/15.
 */
public class LicenseListFragment extends BaseItemFragment {

    public interface Callbacks {
        void onLicenseSelected(License license);
    }

    @Override
    public List<RecyclableItem> loadListItemsInBackground() {
        Collection<License> licenses = getLicenses();
        if (CollectionUtils.isEmpty(licenses)) {
            return null;
        }

        List<RecyclableItem> items = new ArrayList<>();
        for (License license : licenses) {
            items.add(new LicenseItem(license));
        }
        return items;
    }

    @Override
    protected void customizeView() {
        super.customizeView();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onItemClicked(RecyclableItem item, long position) {
        if (item == null) {
            return;
        }

        LicenseItem licenseItem = (LicenseItem) item;
        Object callbacks = getCallbacks(Callbacks.class);
        if (callbacks != null) {
            ((Callbacks) callbacks).onLicenseSelected(licenseItem.getUserInfo());
        }
    }

    private Collection<License> getLicenses() {
        return DataFactory.getLicenses();
    }

}
