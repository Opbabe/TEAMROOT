package edu.sjsu.sase.android.roots;

/**
 * Represents a user
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String profileImage;

    /**
     * Default constructor (required for Firestore)
     */
    public User(){}

    /**
     * Constructs a User, which contains a user id, name, email, and profile image link
     * @param id
     * @param name
     * @param email
     * @param profileImage
     */
    public User(String id, String name, String email, String profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
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
     * Returns the user's profile image
     * @return the user's profile image
     */
    public String getProfileImage() {
        return profileImage;
    }
}
