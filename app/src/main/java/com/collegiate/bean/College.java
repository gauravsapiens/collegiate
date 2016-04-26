package com.collegiate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.collegiate.database.CollegiateDatabase;
import com.collegiate.util.ResourceUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by gauravarora on 17/06/15.
 */

@Table(databaseName = CollegiateDatabase.NAME)
public class College extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey
    private String id;

    @Column
    private String shortName;

    @Column
    private String fullName;

    @Column
    private String about;

    @Column
    private String imageUrl;

    @Column
    private String introVideo;

    @Column
    private boolean selected;

    public College() {

    }

    public College(String id, String shortName, String about, String imageUrl, int imageResourceId) {
        this.id = id;
        this.shortName = shortName;
        this.about = about;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIntroVideo() {
        return introVideo;
    }

    public void setIntroVideo(String introVideo) {
        this.introVideo = introVideo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return selected;
    }

    public int getImageResourceId() {
        return ResourceUtils.getResourceId(getId().toLowerCase(), "drawable");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.shortName);
        dest.writeString(this.fullName);
        dest.writeString(this.about);
        dest.writeString(this.imageUrl);
        dest.writeString(this.introVideo);
        dest.writeByte(selected ? (byte) 1 : (byte) 0);
    }

    protected College(Parcel in) {
        this.id = in.readString();
        this.shortName = in.readString();
        this.fullName = in.readString();
        this.about = in.readString();
        this.imageUrl = in.readString();
        this.introVideo = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Creator<College> CREATOR = new Creator<College>() {
        public College createFromParcel(Parcel source) {
            return new College(source);
        }

        public College[] newArray(int size) {
            return new College[size];
        }
    };

}
