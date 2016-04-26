package com.collegiate.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegiate.R;
import com.collegiate.bean.Days;
import com.collegiate.bean.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gauravarora on 18/09/15.
 */
public class ScheduleDayTimeView extends LinearLayout {

    public interface Callbacks {
        void onDaySelectionChanged(Days days);

        void onTimeChanged(Time time);
    }

    private TextView[] mWeekTextView;
    private TextView mTimeTextView;

    private Days mSelectedDays;
    private Time mSelectedTime;
    private Callbacks mCallbacks;

    public ScheduleDayTimeView(Context context) {
        super(context);
        init();
    }

    public ScheduleDayTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScheduleDayTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setSchedule(Days days, Time time) {
        this.mSelectedDays = days;
        this.mSelectedTime = time;

        updateView();
    }

    public void setCallbacks(Callbacks callbacks){
        this.mCallbacks = callbacks;
    }

    private void init() {
        setOrientation(VERTICAL);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_schedule_day_time, this, true);

        mWeekTextView = new TextView[7];

        mWeekTextView[0] = (TextView) findViewById(R.id.sun);
        mWeekTextView[1] = (TextView) findViewById(R.id.mon);
        mWeekTextView[2] = (TextView) findViewById(R.id.tue);
        mWeekTextView[3] = (TextView) findViewById(R.id.wed);
        mWeekTextView[4] = (TextView) findViewById(R.id.thu);
        mWeekTextView[5] = (TextView) findViewById(R.id.fri);
        mWeekTextView[6] = (TextView) findViewById(R.id.sat);

        int index = 0;
        for (TextView dayTv : mWeekTextView) {
            dayTv.setOnClickListener(getOnClickListener(dayTv, index++));
        }

        mTimeTextView = (TextView) findViewById(R.id.time);
        mTimeTextView.setOnClickListener(v -> onTimeClicked());
    }

    private void updateView() {
        if (mSelectedDays == null) {
            return;
        }

        int index = 0;
        for (TextView dayTv : mWeekTextView) {
            dayTv.setSelected(mSelectedDays.isSelected(index++));
        }

        String displayTime = getTime(mSelectedTime);
        mTimeTextView.setText(displayTime);
    }

    private OnClickListener getOnClickListener(TextView tv, int day) {
        return v -> {
            boolean selectionStatus = setSelected(day, !isSelected(day));
            tv.setSelected(selectionStatus);
            if (mCallbacks != null) {
                mCallbacks.onDaySelectionChanged(mSelectedDays);
            }
        };
    }

    private void onTimeClicked() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if (mSelectedTime != null) {
            hour = mSelectedTime.getHourOfDay();
            minute = mSelectedTime.getMinute();
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), mOnTimeSetListener, hour, minute, false);
        timePickerDialog.show();
    }

    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener = (view, hourOfDay, minute) -> {
        mSelectedTime = new Time(hourOfDay, minute, 0);
        mTimeTextView.setText(getTime(mSelectedTime));
        if(mCallbacks != null){
            mCallbacks.onTimeChanged(mSelectedTime);
        }
    };

    private String getTime(Time time){
        Date date = new Date(0, 0, 0, time.getHourOfDay(), time.getMinute());
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(date);
    }

    private boolean isSelected(int day) {
        return mSelectedDays != null && mSelectedDays.isSelected(day);
    }

    private boolean setSelected(int day, boolean isSelected) {
        return mSelectedDays.updateSelection(day, isSelected);
    }

}
