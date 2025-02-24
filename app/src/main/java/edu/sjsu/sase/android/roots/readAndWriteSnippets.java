package edu.sjsu.sase.android.roots;

import edu.sjsu.sase.android.roots.user.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
public class readAndWriteSnippets {
    private final DatabaseReference mDatabase;

    public readAndWriteSnippets(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void writeNewUser(String userID, String name, String email){
        User user = new User(name, email);

        mDatabase.child("users").child(userID).setValue(user);

    }


}
