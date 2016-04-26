package com.collegiate.core.fragment;

import android.os.Bundle;

import com.collegiate.R;
import com.collegiate.core.RecyclableItem;
import com.collegiate.core.RecycleSelectableItem;
import com.collegiate.util.CollectionUtils;
import com.collegiate.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gauravarora on 19/07/15.
 */
public abstract class BaseItemSelectionFragment extends BaseItemSearchFragment {

    public interface Callbacks {
        public void onItemSelected(BaseItemSelectionFragment fragment, List<Object> selectedValues);
    }

    private boolean isMultiSelectionAllowed = false;
    private boolean isZeroSelectionAllowed = false;
    private RecycleSelectableItem lastItemSelected;
    private Set<RecycleSelectableItem> selectedItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedItems = new HashSet<>();
    }

    @Override
    public void onItemClicked(RecyclableItem recyclableItem, long position) {
        if (recyclableItem instanceof RecycleSelectableItem) {
            onItemSelected((RecycleSelectableItem) recyclableItem);
            getAdapter().notifyDataSetChanged();
        } else {
            onUnselectableItemClicked(recyclableItem);
        }
    }

    protected void onItemSelected(RecycleSelectableItem selectableItem) {
        boolean selected = selectableItem.isSelected();

        if (isMultiSelectionAllowed()) {
            if (selected) {
                if (selectedItems.contains(selectableItem)) {
                    selectedItems.remove(selectableItem);
                }
                selectableItem.setSelected(false);
            } else {
                selectableItem.setSelected(true);
                selectedItems.add(selectableItem);
            }
        } else {
            if (selected) {
                if (selectedItems.contains(selectableItem)) {
                    selectedItems.remove(selectableItem);
                }
                selectableItem.setSelected(false);
                lastItemSelected = null;
            } else {
                selectableItem.setSelected(true);
                selectedItems.add(selectableItem);

                if (lastItemSelected != null) {
                    lastItemSelected.setSelected(false);
                    selectedItems.remove(lastItemSelected);
                }

                lastItemSelected = selectableItem;
            }
        }
    }

    @Override
    protected void onActionBarItemDoneClicked() {
        super.onActionBarItemDoneClicked();

        Set<RecycleSelectableItem> selectedItems = getSelectedItems();
        int errResId = validate(selectedItems);
        if (errResId > 0) {
            ToastUtils.showError(getActivity(), errResId);
        } else {
            notifyListener();
        }
    }

    private void notifyListener() {
        Object callbacks = getCallbacks(Callbacks.class);
        if (callbacks != null) {
            ((Callbacks) callbacks).onItemSelected(this, getSelectedValues());
        }
    }

    private List<Object> getSelectedValues() {
        if (CollectionUtils.isEmpty(selectedItems)) {
            return null;
        }

        List<Object> objects = new ArrayList<>();
        for (RecycleSelectableItem item : selectedItems) {
            objects.add(item.getUserInfo());
        }
        return objects;
    }

    protected int validate(Set<RecycleSelectableItem> selectedItems) {
        if (!isZeroSelectionAllowed() && selectedItems.size() == 0)
            return R.string.error_empty_selection_not_allowed;
        return -1;
    }

    protected void onUnselectableItemClicked(RecyclableItem clickedItem) {

    }

    protected Set<RecycleSelectableItem> getSelectedItems() {
        return selectedItems;
    }

    protected boolean isMultiSelectionAllowed() {
        return isMultiSelectionAllowed;
    }

    protected boolean isZeroSelectionAllowed() {
        return isZeroSelectionAllowed;
    }

    protected void setMultiSelectionAllowed(boolean multiSelectionAllowed) {
        isMultiSelectionAllowed = multiSelectionAllowed;
    }

    protected void setZeroSelectionAllowed(boolean zeroSelectionAllowed) {
        isZeroSelectionAllowed = zeroSelectionAllowed;
    }

    protected void unSelectAll() {
        if (!isZeroSelectionAllowed()) {
            return;
        }

        for (RecycleSelectableItem selectedItem : selectedItems) {
            selectedItem.setSelected(false);
        }

        selectedItems.clear();
    }

}
