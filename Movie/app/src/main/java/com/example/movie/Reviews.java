package com.example.movie;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable {
    // Review ID
    private String mId;
    // The Author
    private String mAuthor;
    // The content
    private String mContent;

    public Reviews(String Id, String Author, String Content) {
        mId = Id;
        mAuthor = Author;
        mContent = Content;
    }

    // Get methods
    public String getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mAuthor);
        dest.writeString(mContent);
    }

    public Reviews(Parcel parcelable) {
        mId = parcelable.readString();
        mContent = parcelable.readString();
        mAuthor = parcelable.readString();
    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[0];
        }
    };
}
