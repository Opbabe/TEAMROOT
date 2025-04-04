package edu.sjsu.sase.android.roots;

public class Event {
    private String id;
    private String name;
    private String dateTime;
    private String hostName;
    private String tags;
    private int imageResourceId; // For demo purposes, use resource IDs

    public Event(String id, String name, String dateTime, String hostName, String tags, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.hostName = hostName;
        this.tags = tags;
        this.imageResourceId = imageResourceId;
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