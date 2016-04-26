package com.collegiate.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.collegiate.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemizedArrayAdapter extends ArrayAdapter<TableItem>  {

    private Map<Object, Integer> itemViewTypeLookup = new HashMap<>();
    private Integer currentViewTypeCode = 0;

    public ItemizedArrayAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TableItem item = getItem(position);
        return item.getView(position, convertView, parent, this.getContext());
    }

    @Override
    public int getViewTypeCount() {
        return 100;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    @Override
    public int getItemViewType(int position) {
        TableItem item = getItem(position);
        if (item == null) {
            return 0;
        }

        Object reuseIdentifier = getReuseIdentifier(item);
        Integer itemViewType = itemViewTypeLookup.get(reuseIdentifier);
        if (itemViewType == null) {
            itemViewType = currentViewTypeCode++;
            itemViewTypeLookup.put(reuseIdentifier, itemViewType);
        }
        return itemViewType;
    }

    private Object getReuseIdentifier(TableItem item) {
        return item.getClass();
    }

    public void setData(List<? extends TableItem> data) {
        clear();
        if (data != null)
            addAll(data);
    }

    public void addAll(List<? extends TableItem> tableItems) {
        addAll(tableItems, false);
    }

    public void addAll(List<? extends TableItem> tableItems, boolean addFromTop) {
        if (CollectionUtils.isEmpty(tableItems))
            return;

        if (addFromTop) {
            List<TableItem> allItems = new ArrayList<>();
            allItems.addAll(tableItems);
            allItems.addAll(getItems());
            this.clear();
            for (TableItem item : allItems)
                this.add(item);
        } else {
            for (TableItem item : tableItems)
                this.add(item);
        }
    }

    public List<TableItem> getItems() {
        int size = this.getCount();
        if (size == 0)
            return Collections.EMPTY_LIST;
        List<TableItem> allItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            allItems.add(this.getItem(i));
        }
        return allItems;
    }


}
