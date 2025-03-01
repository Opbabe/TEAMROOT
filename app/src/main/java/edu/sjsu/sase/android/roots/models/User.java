package edu.sjsu.sase.android.roots.models;

public class User {
    public String userID;
    public String name;
    public String userEmail;

    public User(){
        //default
    }

    public User(String userID, String name, String userEmail){
        this.userID = userID;
        this.name = name;
        this.userEmail = userEmail;
    }
}
