package edu.sjsu.sase.android.roots.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Event implements Parcelable {
    @Exclude
    private Object stability;

    private String id;
    private String name;
    private String hostName;
    private String tags;
    private String imageUrl; // Firebase Storage download URL
    private String description;
    private String visibility;
    private String location;
    private List<String> guestList;

    private String eventDateStart;
    private String eventDateEnd;
    private String eventTimeStart;
    private String eventTimeEnd;

    // Default constructor required for Firestore
    public Event() {
        this.guestList = new ArrayList<>();
    }

    // Full constructor
    public Event(
            String id,
            String name,
            String hostName,
            String tags,
            String imageUrl,
            String eventDateStart,
            String eventDateEnd,
            String eventTimeStart,
            String eventTimeEnd,
            String description,
            String visibility,
            String location,
            List<String> guestList
    ) {
        this.id = id;
        this.name = name;
        this.hostName = hostName;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.eventDateStart = eventDateStart;
        this.eventDateEnd = eventDateEnd;
        this.eventTimeStart = eventTimeStart;
        this.eventTimeEnd = eventTimeEnd;
        this.description = description;
        this.visibility = visibility;
        this.location = location;
        this.guestList = guestList != null ? guestList : new ArrayList<>();
    }

    // Parcelable constructor
    protected Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        hostName = in.readString();
        tags = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        visibility = in.readString();
        location = in.readString();
        guestList = in.createStringArrayList();
        eventDateStart = in.readString();
        eventDateEnd = in.readString();
        eventTimeStart = in.readString();
        eventTimeEnd = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(hostName);
        dest.writeString(tags);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(visibility);
        dest.writeString(location);
        dest.writeStringList(guestList);
        dest.writeString(eventDateStart);
        dest.writeString(eventDateEnd);
        dest.writeString(eventTimeStart);
        dest.writeString(eventTimeEnd);
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

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public List<String> getGuestList() { return guestList; }
    public void setGuestList(List<String> guestList) { this.guestList = guestList; }

    public String getEventDateStart() { return eventDateStart; }
    public void setEventDateStart(String eventDateStart) { this.eventDateStart = eventDateStart; }

    public String getEventDateEnd() { return eventDateEnd; }
    public void setEventDateEnd(String eventDateEnd) { this.eventDateEnd = eventDateEnd; }

    public String getEventTimeStart() { return eventTimeStart; }
    public void setEventTimeStart(String eventTimeStart) { this.eventTimeStart = eventTimeStart; }

    public String getEventTimeEnd() { return eventTimeEnd; }
    public void setEventTimeEnd(String eventTimeEnd) { this.eventTimeEnd = eventTimeEnd; }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hostName='" + hostName + '\'' +
                ", tags='" + tags + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
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
