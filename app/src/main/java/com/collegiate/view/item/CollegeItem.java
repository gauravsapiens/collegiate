package com.collegiate.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegiate.R;
import com.collegiate.bean.College;
import com.collegiate.core.RecyclableItem;
import com.collegiate.util.ViewUtils;
import com.collegiate.view.base.ColImageView;

/**
 * Created by gauravarora on 17/06/15.
 */
public class CollegeItem implements RecyclableItem {

    private College mCollege;
    private ViewHolder mViewHolder;

    public CollegeItem(College college) {
        this.mCollege = college;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.item_college, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        mViewHolder = (ViewHolder) holder;
        mViewHolder.textView.setText(mCollege.getShortName());

        mViewHolder.imageView.setImageURI(mCollege.getImageResourceId(), mCollege.getImageUrl());
    }

    @Override
    public College getUserInfo() {
        return mCollege;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        View frameLayout;
        ColImageView imageView;
        TextView textView;

        ViewHolder(View rootView) {
            super(rootView);
            frameLayout = ViewUtils.findView(rootView, R.id.frame_layout);
            imageView = ViewUtils.findView(rootView, R.id.image_view);
            textView = ViewUtils.findView(rootView, R.id.text_view);
        }

    }

}
