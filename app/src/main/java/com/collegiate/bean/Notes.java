package com.collegiate.bean;

import com.collegiate.database.CollegiateDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by gauravarora on 24/09/15.
 */

@Table(databaseName = CollegiateDatabase.NAME)
public class Notes extends BaseModel{

    @Column
    @PrimaryKey
    private String lectureId;

    @Column
    private String note;

    public Notes(){

    }

    public Notes(String lectureId, String note) {
        this.lectureId = lectureId;
        this.note = note;
    }

    public Notes(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
