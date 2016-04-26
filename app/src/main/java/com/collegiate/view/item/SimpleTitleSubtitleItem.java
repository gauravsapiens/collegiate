package com.collegiate.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegiate.R;
import com.collegiate.core.RecyclableItem;
import com.collegiate.util.ViewUtils;

/**
 * Created by gauravarora on 10/09/15.
 */
public class SimpleTitleSubtitleItem implements RecyclableItem {

    private String mTitle;
    private String mSubtitle;

    public SimpleTitleSubtitleItem(String title, String subtitle) {
        mTitle = title;
        mSubtitle = subtitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_title_subtitle, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;

        ViewUtils.setText(viewHolder.titleTextView, mTitle, View.GONE);
        ViewUtils.setText(viewHolder.subtitleTextView, mSubtitle, View.GONE);
    }

    @Override
    public Object getUserInfo() {
        return null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView subtitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            subtitleTextView = (TextView) itemView.findViewById(R.id.subtitle);
        }
    }

}
