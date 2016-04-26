package com.collegiate.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegiate.R;
import com.collegiate.core.RecyclableItem;

/**
 * Created by gauravarora on 08/09/15.
 */
public class SimpleTextItem implements RecyclableItem {

    private String mText;

    public SimpleTextItem(String text) {
        mText = text;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_simple_text, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(mText);
    }

    @Override
    public Object getUserInfo() {
        return mText;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
