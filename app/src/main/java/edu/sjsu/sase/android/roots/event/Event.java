package edu.sjsu.sase.android.roots.event;

import android.os.Parcel;
import android.os.Parcelable;

public class Event {
    private String id;
    private String name;
    private String dateTime;
    private String hostName;
    private String tags; //change to collection of tags empty arraylist or hash map?
    private int imageResourceId; // For demo purposes, use resource IDs

    public Event(String id, String name, String dateTime, String hostName, String tags, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.hostName = hostName;
        this.tags = tags;
        this.imageResourceId = imageResourceId;
    }

    protected Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        dateTime = in.readString();
        hostName = in.readString();
        tags = in.readString();
    }

    private void writeNullableToParcel(Parcel dest, String str) {
        if (str == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(str);
        }
    }

    private String readNullableString(Parcel in) {
        return in.readByte() == 0 ? null : in.readString();

    }

    public void writeToParcel(Parcel dest, int flags) {
        writeNullableToParcel(dest, id);
        writeNullableToParcel(dest, name);
        writeNullableToParcel(dest, dateTime);
        writeNullableToParcel(dest, hostName);
        writeNullableToParcel(dest, tags);
    }





    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getHostName() {
        return hostName;
    }

    public String getTags() {
        return tags;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }


}