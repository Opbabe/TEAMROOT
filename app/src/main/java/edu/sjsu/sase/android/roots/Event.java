package edu.sjsu.sase.android.roots;

public class Event {
    private String name;
    private String date;
    private String location;
    private String distance; // For nearby events
    private String category; // For trending events
    
    public Event(String name, String date, String location) {
        this.name = name;
        this.date = date;
        this.location = location;
    }
    
    public Event(String name, String date, String location, String distance) {
        this(name, date, location);
        this.distance = distance;
    }
    
    public Event(String name, String date, String location, String category, String distance) {
        this(name, date, location);
        this.category = category;
        this.distance = distance;
    }
    
    // Getters
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getDistance() { return distance; }
    public String getCategory() { return category; }
} 