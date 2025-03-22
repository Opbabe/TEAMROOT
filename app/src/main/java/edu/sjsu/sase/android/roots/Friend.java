package edu.sjsu.sase.android.roots;

public class Friend {
    private String name;
    private String eventAttending;
    
    public Friend(String name, String eventAttending) {
        this.name = name;
        this.eventAttending = eventAttending;
    }
    
    // Getters
    public String getName() { return name; }
    public String getEventAttending() { return eventAttending; }
} 