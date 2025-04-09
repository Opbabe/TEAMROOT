package edu.sjsu.sase.android.roots.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Represents a user
 */
public class User implements Parcelable {
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
     * Constructs a dummy User for testing purposes.
     * @param i an integer to differentiate between multiple dummy users
     */
    public User(int i) {
        this.id = "id" + i;
        this.name = "name" + i;
        this.email = "username" + i + "@gmail.com";
        this.profilePicUrl = "";
        this.username = "username" + i;
    }

    /**
     * Constructs a User, which contains a user id, name, email, and profile image link
     * @param id the user's unique id
     * @param name the user's name
     * @param email the user's email
     * @param profilePicUrl the url to the user's profile picture
     * @param username the user's username
     */
    public User(String id, String name, String email, String profilePicUrl, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePicUrl = profilePicUrl;
        this.username = username;
    }

    /**
     * Constructs a User from the specified Parcel
     * @param in the Parcel
     */
    protected User(Parcel in) {
        id = readNullableString(in);
        name = readNullableString(in);
        email = readNullableString(in);
        profilePicUrl = readNullableString(in);
        username = readNullableString(in);
    }

    /**
     * Helper method to read strings in the Parcel
     * @param in the Parcel
     * @return null if the byte is 0, otherwise the string
     */
    private String readNullableString(Parcel in) {
        return in.readByte() == 0 ? null : in.readString();
    }

    /**
     * Writes the User's attributes to the specified Parcel
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        writeNullableToParcel(dest, id);
        writeNullableToParcel(dest, name);
        writeNullableToParcel(dest, email);
        writeNullableToParcel(dest, profilePicUrl);
        writeNullableToParcel(dest, username);
    }

    /**
     * Helper method to write a string to the specified Parcel
     * @param dest the parcel
     * @param str the string to write to the parcel
     */
    private void writeNullableToParcel(Parcel dest, String str) {
        if (str == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(str);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
