package edu.sjsu.sase.android.roots;

/**
 * Represents a user
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String profilePicUrl;
    private String username;

    /**
     * Default constructor (required for Firestore)
     */
    public User(){}

    /**
     * Constructs a User, which contains a user id, name, email, and profile image link
     * @param id
     * @param name
     * @param email
     * @param profilePicUrl
     * @param username
     */
    public User(String id, String name, String email, String profilePicUrl, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePicUrl = profilePicUrl;
        this.username = username;
    }

    /**
     * Returns the user's unique id
     * @return the user's unique id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the user's name (first and last)
     * @return the user's name (first and last)
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user's email that was used to sign in
     * @return the user's email that was used to sign in
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's profile image url
     * @return the user's profile image url
     */
    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
