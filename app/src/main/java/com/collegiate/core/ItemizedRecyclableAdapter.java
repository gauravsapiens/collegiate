package com.collegiate.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.collegiate.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemizedRecyclableAdapter extends RecyclerView.Adapter {

    private OnRecyclableItemClickListener mOnRecyclableItemListener;
    private List<RecyclableItem> mOriginalItems;
    private List<RecyclableItem> mItems;
    private Map<Class, Integer> itemClassVsType;
    private Map<Integer, RecyclableItem> typeVsItem;
    private Context mContext;
    private Filter mFilter;

    public ItemizedRecyclableAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclableItem item = typeVsItem.get(viewType);
        return item.onCreateViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        RecyclableItem item = getItem(i);
        if (item == null) {
            return;
        }

        item.onBindViewHolder(viewHolder, i, mContext);
        viewHolder.itemView.setOnClickListener(getOnClickListener(item, i));
    }

    @Override
    public int getItemViewType(int position) {
        return itemClassVsType.size();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public RecyclableItem getItem(int position) {
        if (mItems != null && position >= 0 && position < mItems.size()) {
            return mItems.get(position);
        }

        return null;
    }

    public void setRecyclableItems(List<RecyclableItem> recyclableItems) {
        clear();
        initializeItemsWithType(recyclableItems);
    }

    public void setOnItemClickListener(OnRecyclableItemClickListener onRecyclableItemListener) {
        mOnRecyclableItemListener = onRecyclableItemListener;
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private void initializeItemsWithType(List<RecyclableItem> recyclableItems) {
        if (CollectionUtils.isEmpty(recyclableItems)) {
            return;
        }

        int index = 1;
        for (RecyclableItem item : recyclableItems) {
            if (!itemClassVsType.containsKey(item.getClass())) {
                itemClassVsType.put(item.getClass(), index);
                typeVsItem.put(index++, item);
            }
            mOriginalItems.add(item);
        }
        this.mItems = mOriginalItems;
    }

    private View.OnClickListener getOnClickListener(RecyclableItem itemz, int position) {
        return v -> {
            if (mOnRecyclableItemListener != null) {
                mOnRecyclableItemListener.onRecyclableItemClicked(itemz, position);
            }
        };
    }

    private List<RecyclableItem> getFilteredItems(List<RecyclableItem> items, String searchText) {
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }

        List<RecyclableItem> filteredItems = new ArrayList<>();
        for (RecyclableItem item : items) {
            if (item instanceof RecycleSearchableItem) {
                boolean matches = ((RecycleSearchableItem) item).matches(searchText);
                if (matches) {
                    filteredItems.add(item);
                }
            }
        }

        return filteredItems;
    }

    private void clear(){
        mItems = new ArrayList<>();
        mOriginalItems = new ArrayList<>();
        itemClassVsType = new HashMap<>();
        typeVsItem = new HashMap<>();
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<RecyclableItem> items = new ArrayList<>(mOriginalItems);

            if (constraint == null || constraint.length() == 0) {
                results.values = items;
                results.count = items.size();
                return results;
            }

            String searchText = constraint.toString();
            List<RecyclableItem> filteredItems = getFilteredItems(items, searchText);

            results.values = filteredItems;
            results.count = filteredItems.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mItems = (List<RecyclableItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
