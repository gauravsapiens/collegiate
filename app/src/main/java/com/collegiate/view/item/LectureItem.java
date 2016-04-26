package com.collegiate.view.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegiate.R;
import com.collegiate.bean.Lecture;
import com.collegiate.core.TableItem;
import com.collegiate.util.ResourceUtils;
import com.collegiate.util.StringUtils;
import com.collegiate.util.ViewUtils;
import com.collegiate.view.base.ColImageView;

/**
 * Created by gauravarora on 16/07/15.
 */
public class LectureItem implements TableItem {

    private Lecture mLecture;
    private boolean mSeparatorVisibilty = true;

    public LectureItem(Lecture lecture) {
        mLecture = lecture;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, Context context) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_lecture, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(mLecture.getTitle());
        viewHolder.duration.setText(StringUtils.toFormattedTime(mLecture.getDuration()));
        viewHolder.imageView.setImageURI(mLecture.getImageUrl());
        ViewUtils.setVisibility(viewHolder.separator, mSeparatorVisibilty);

        int textColor = mLecture.isSeen() ? ResourceUtils.getColor(R.color.lecture_seen) : ResourceUtils.getColor(R.color.lecture_unseen);
        viewHolder.title.setTextColor(textColor);
        viewHolder.duration.setTextColor(textColor);

        int imageOverlayBackground = mLecture.isSeen() ? ResourceUtils.getColor(R.color.lecture_image_overlay_seen) : ResourceUtils.getColor(R.color.lecture_image_overlay_unseen);
        viewHolder.imageOverlay.setBackgroundColor(imageOverlayBackground);

        return convertView;
    }

    public void setLecture(Lecture lecture){
        mLecture = lecture;
    }

    @Override
    public Lecture getUserInfo() {
        return mLecture;
    }

    public void setSeparatorVisibility(boolean isVisible) {
        mSeparatorVisibilty = isVisible;
    }

    private static class ViewHolder {
        ColImageView imageView;
        View imageOverlay;
        TextView title;
        TextView duration;
        View separator;

        ViewHolder(View rootView) {
            title = ViewUtils.findView(rootView, R.id.title);
            duration = ViewUtils.findView(rootView, R.id.duration);
            imageView = ViewUtils.findView(rootView, R.id.image_view);
            imageOverlay = ViewUtils.findView(rootView, R.id.image_overlay);
            separator = ViewUtils.findView(rootView, R.id.separator);
        }

    }
}
