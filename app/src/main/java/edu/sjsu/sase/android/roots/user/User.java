package edu.sjsu.sase.android.roots.user;

public class User {

    public String userID;
    public String firstName;
    public String lastName;
    public String username; //@todo make this customizable
    public String profilePic;
    public String email;
    public String password;

    //@todo add these:
    /*
    *Pronoun section
    *Age
    User bio
    Link socials
    *User email
    Field list for friends’ user ids
    Field list for buddy system match //default empty, no limit
    Interested events: list, people can add or remove event ids from here
    MAP: Event id key, review id value
    Interests
    *mandatory to add state and city, anything else like zipcode is optional
    * */
    public User(){

    }
    public User(String username, String email){

    }


}
