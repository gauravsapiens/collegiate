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
public class Lecture extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey
    private String id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private long duration;

    @Column
    private int sequenceNumber;

    @Column
    private int bookmark;

    @Column
    private String courseId;

    @Column
    private String authorId;

    @Column
    private String source;

    @Column
    private boolean seen;

    public Lecture() {
    }

    public Lecture(String id, String title, String description, String imageUrl, long duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.duration = duration;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isSeen() {
        return seen;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
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
        dest.writeString(this.imageUrl);
        dest.writeLong(this.duration);
        dest.writeInt(this.sequenceNumber);
        dest.writeInt(this.bookmark);
        dest.writeString(this.courseId);
        dest.writeString(this.authorId);
        dest.writeString(this.source);
        dest.writeByte(seen ? (byte) 1 : (byte) 0);
    }

    protected Lecture(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.duration = in.readLong();
        this.sequenceNumber = in.readInt();
        this.bookmark = in.readInt();
        this.courseId = in.readString();
        this.authorId = in.readString();
        this.source = in.readString();
        this.seen = in.readByte() != 0;
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        public Lecture createFromParcel(Parcel source) {
            return new Lecture(source);
        }

        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };
}
