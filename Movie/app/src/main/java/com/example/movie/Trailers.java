package com.example.movie;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailers implements Parcelable {

    // Get the trailer ID
    private String mTrailerId;
    // Get trailer key
    private String mKey;
    // get the name of the trailer
    private String mName;

    public Trailers(String ID, String KEY, String name) {
        mTrailerId = ID;
        mKey = KEY;
        mName = name;
    }

    //The get methods
    public String getID() {
        return mTrailerId;
    }

    public String getKey() {
        return mKey;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeString(mTrailerId);
        dest.writeString(mName);
    }

    public Trailers(Parcel parcelable) {
        mTrailerId = parcelable.readString();
        mKey = parcelable.readString();
        mName = parcelable.readString();
    }

    public static final Parcelable.Creator<Trailers> CREATOR = new Parcelable.Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel source) {
            return new Trailers(source);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[0];
        }
    };
}
