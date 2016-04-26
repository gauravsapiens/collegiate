package com.collegiate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.collegiate.database.CollegiateDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by gauravarora on 14/06/15.
 */

@Table(databaseName = CollegiateDatabase.NAME)
public class Course extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String code;

    @Column
    private int year;

    @Column
    private String season;

    @Column
    private String courseUrl;

    @Column
    private String courseHomeUrl;

    @Column
    private String imageUrl;

    @Column
    private String categoryId;

    @Column
    private String collegeId;

    @Column
    private String authorId;

    @Column
    private int enabled;

    @Column
    private String extras;

    @Column
    private String source;

    @Column
    private boolean subscribed;

    public Course() {

    }

    public Course(String id, String title, String description, String imageUrl, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.authorId = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getCourseHomeUrl() {
        return courseHomeUrl;
    }

    public void setCourseHomeUrl(String courseHomeUrl) {
        this.courseHomeUrl = courseHomeUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public boolean toggleSubscribed() {
        this.subscribed = !subscribed;
        return subscribed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.code);
        dest.writeInt(this.year);
        dest.writeString(this.season);
        dest.writeString(this.courseUrl);
        dest.writeString(this.courseHomeUrl);
        dest.writeString(this.imageUrl);
        dest.writeString(this.categoryId);
        dest.writeString(this.collegeId);
        dest.writeString(this.authorId);
        dest.writeInt(this.enabled);
        dest.writeString(this.extras);
        dest.writeString(this.source);
        dest.writeByte(subscribed ? (byte) 1 : (byte) 0);
    }

    protected Course(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.code = in.readString();
        this.year = in.readInt();
        this.season = in.readString();
        this.courseUrl = in.readString();
        this.courseHomeUrl = in.readString();
        this.imageUrl = in.readString();
        this.categoryId = in.readString();
        this.collegeId = in.readString();
        this.authorId = in.readString();
        this.enabled = in.readInt();
        this.extras = in.readString();
        this.source = in.readString();
        this.subscribed = in.readByte() != 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
