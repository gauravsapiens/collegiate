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
public class Author extends BaseModel implements Parcelable {

    @Column
    @PrimaryKey
    private int id;

    @Column
    private String name;

    @Column
    private String imageUrl;

    public Author() {

    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
    }

    protected Author(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
