package com.collegiate.view.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.collegiate.BuildConfig;
import com.collegiate.DaggerAppComponent;
import com.collegiate.DataFactory;
import com.collegiate.R;
import com.collegiate.bean.Category;
import com.collegiate.bean.Course;
import com.collegiate.core.RecyclableItem;
import com.collegiate.database.CourseDao;
import com.collegiate.database.LectureDao;
import com.collegiate.util.ToastUtils;
import com.collegiate.util.ViewUtils;
import com.collegiate.view.base.ColImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gauravarora on 17/06/15.
 */
public class CourseItem implements RecyclableItem {

    private Course mCourse;
    private ViewHolder mViewHolder;

    private boolean mTouched;

    public CourseItem(Course course) {
        this.mCourse = course;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context) {
        mViewHolder = (ViewHolder) holder;
        mViewHolder.textView.setText(mCourse.getTitle());
        mViewHolder.imageView.setImageURI(mCourse.getImageUrl());

        ViewUtils.setVisibility(mViewHolder.subscribed, mCourse.getSubscribed());

        configureView(mViewHolder);

        if (BuildConfig.AUDIT_MODE) {
            setupAuditMode(context);
        }
    }

    protected void configureView(ViewHolder viewHolder){

    }

    @Override
    public Course getUserInfo() {
        return mCourse;
    }

    public View getView() {
        return mViewHolder.itemView;
    }

    private void setupAuditMode(Context context) {
        mViewHolder.spinner.setVisibility(View.VISIBLE);
        mViewHolder.delete.setVisibility(View.VISIBLE);
        mViewHolder.disable.setVisibility(View.VISIBLE);

        CourseDao courseDao = DaggerAppComponent.create().getCourseDao();
        LectureDao lectureDao = DaggerAppComponent.create().getLectureDao();

        List<Category> categories = new ArrayList<>(DataFactory.getAllCategories());
        List<String> categoryIds = new ArrayList<>();
        for (Category category : categories) {
            categoryIds.add(category.getId());
        }

        String selectedCategory = mCourse.getCategoryId();
        int selectedPosition = categoryIds.indexOf(selectedCategory);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categoryIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mViewHolder.spinner.setAdapter(adapter);
        mViewHolder.spinner.setSelection(selectedPosition, false);
        mViewHolder.spinner.setOnTouchListener((view, motionEvent) -> {
            mTouched = true;
            return false;
        });

        mViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!mTouched) {
                    return;
                }
                String selected = categoryIds.get(i);
                ToastUtils.showError(context, selected);
                courseDao.setCategory(mCourse.getId(), selected);
                mTouched = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mViewHolder.delete.setOnClickListener(view -> {
            courseDao.delete(mCourse.getId());
            lectureDao.deleteLecturesForCourse(mCourse.getId());
            ToastUtils.showError(context, "Deleted: " + mCourse.getTitle());
        });

        mViewHolder.disable.setOnClickListener(v -> {
            courseDao.disableCourse(mCourse.getId());
            ToastUtils.showError(context, "Disabled: " + mCourse.getTitle());
        });
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ColImageView imageView;
        TextView textView;
        ImageView subscribed;
        View scheduleSeparator;
        TextView schedule;
        Spinner spinner;
        Button disable;
        Button delete;

        ViewHolder(View rootView) {
            super(rootView);
            imageView = ViewUtils.findView(rootView, R.id.image_view);
            textView = ViewUtils.findView(rootView, R.id.text_view);
            subscribed = ViewUtils.findView(rootView, R.id.subscribed);
            scheduleSeparator = ViewUtils.findView(rootView, R.id.schedule_separator);
            schedule = ViewUtils.findView(rootView, R.id.schedule);
            spinner = ViewUtils.findView(rootView, R.id.spinner);
            disable = ViewUtils.findView(rootView, R.id.disable);
            delete = ViewUtils.findView(rootView, R.id.delete);
        }

    }

}
