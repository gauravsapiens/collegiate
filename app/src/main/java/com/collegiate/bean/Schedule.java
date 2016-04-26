package com.collegiate.bean;

import com.collegiate.database.CollegiateDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by gauravarora on 20/09/15.
 */
@Table(databaseName = CollegiateDatabase.NAME)
public class Schedule extends BaseModel {

    @Column
    @PrimaryKey
    private String courseId;

    @Column
    private Days days;

    @Column
    private Time time;

    public Schedule(){
    }

    public Schedule(String courseId) {
        this.courseId = courseId;
        this.days = new Days();
        this.time = new Time(18, 0, 0);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}
