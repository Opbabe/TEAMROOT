package edu.sjsu.sase.android.roots.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Event implements Parcelable {
    @Exclude
    private Object stability;

    private String id;
    private String name;
    private String hostId;
    private String hostName;
    private String tags; // Consider changing to a List or Collection if needed.
    private int imageResourceId; // For demo purposes, using resource IDs.
    private String picURL;
    private String description;
    private String visibility;
    private String location;
    private String guestList;
    private String eventDateStart;
    private String eventDateEnd;
    private String eventTimeStart;
    private String eventTimeEnd;

    // Default constructor required for Firestore
    public Event() {
    }

    public Event(String id, String name, String hostId, String hostName, String tags, int imageResourceId,
                 String eventDateStart, String eventDateEnd, String eventTimeStart, String eventTimeEnd,
                 String description, String visibility, String location, String picURL) {
        this.id = id;
        this.name = name;
        this.hostId = hostId;
        this.hostName = hostName;
        this.tags = tags;
        this.imageResourceId = imageResourceId;
        this.picURL = picURL;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
        this.description = description;
        this.visibility = visibility;
        this.location = location;
    }

    // Parcelable constructor
    protected Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        hostId = in.readString();
        tags = in.readString();
        imageResourceId = in.readInt();
        picURL = in.readString();
        description = in.readString();
        visibility = in.readString();
        location = in.readString();
        guestList = in.readString();
        eventDateStart = in.readString();
        eventDateEnd = in.readString();
        eventTimeStart = in.readString();
        eventTimeEnd = in.readString();
    }

    // Helper methods for nullable parceling
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeNullableToParcel(dest, id);
        writeNullableToParcel(dest, name);
        writeNullableToParcel(dest, hostId);
        writeNullableToParcel(dest, tags);
        dest.writeInt(imageResourceId);
        writeNullableToParcel(dest, picURL);
        writeNullableToParcel(dest, description);
        writeNullableToParcel(dest, visibility);
        writeNullableToParcel(dest, location);
        writeNullableToParcel(dest, guestList);
        writeNullableToParcel(dest, eventDateStart);
        writeNullableToParcel(dest, eventDateEnd);
        writeNullableToParcel(dest, eventTimeStart);
        writeNullableToParcel(dest, eventTimeEnd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHostId() {
        return hostId;
    }

    public String getHostName() {

        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getTags() {
        return tags;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getLocation() {
        return location;
    }

    public String getGuestList() {
        return guestList;
    }

    public String getEventDateStart() {
        return eventDateStart;
    }

    public String getEventDateEnd() {
        return eventDateEnd;
    }

    public String getEventTimeStart() {
        return eventTimeStart;
    }

    public String getEventTimeEnd() {
        return eventTimeEnd;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setGuestList(String guestList) {
        this.guestList = guestList;
    }

    public void setEventDateStart(String eventDateStart) {
        this.eventDateStart = eventDateStart;
    }

    public void setEventDateEnd(String eventDateEnd) {
        this.eventDateEnd = eventDateEnd;
    }

    public void setEventTimeStart(String eventTimeStart) {
        this.eventTimeStart = eventTimeStart;
    }

    public void setEventTimeEnd(String eventTimeEnd) {
        this.eventTimeEnd = eventTimeEnd;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hostName='" + hostId + '\'' +
                ", tags='" + tags + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", description='" + description + '\'' +
                ", visibility='" + visibility + '\'' +
                ", location='" + location + '\'' +
                ", guestList='" + guestList + '\'' +
                ", eventDateStart='" + eventDateStart + '\'' +
                ", eventDateEnd='" + eventDateEnd + '\'' +
                ", eventTimeStart='" + eventTimeStart + '\'' +
                ", eventTimeEnd='" + eventTimeEnd + '\'' +
                '}';
    }
}
