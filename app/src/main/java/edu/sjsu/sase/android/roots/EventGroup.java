package edu.sjsu.sase.android.roots;

public class EventGroup {
    private String name;
    private String eventName;
    private int memberCount;
    
    public EventGroup(String name, String eventName, int memberCount) {
        this.name = name;
        this.eventName = eventName;
        this.memberCount = memberCount;
    }
    
    // Getters
    public String getName() { return name; }
    public String getEventName() { return eventName; }
    public int getMemberCount() { return memberCount; }
} 