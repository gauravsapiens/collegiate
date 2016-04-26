package com.collegiate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.collegiate.database.CollegiateDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by gauravarora on 08/09/15.
 */

@Table(databaseName = CollegiateDatabase.NAME)
public class License extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey
    private String id;

    @Column
    private String holder;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.holder);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.url);
    }

    public License() {
    }

    protected License(Parcel in) {
        this.id = in.readString();
        this.holder = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.url = in.readString();
    }

    public static final Creator<License> CREATOR = new Creator<License>() {
        public License createFromParcel(Parcel source) {
            return new License(source);
        }

        public License[] newArray(int size) {
            return new License[size];
        }
    };
}
