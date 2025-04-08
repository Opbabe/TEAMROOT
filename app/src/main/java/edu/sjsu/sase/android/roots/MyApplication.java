package edu.sjsu.sase.android.roots;

import android.app.Application;
import android.util.Log;
import com.google.firebase.FirebaseApp;

import edu.sjsu.sase.android.roots.user.User;

public class MyApplication extends Application {
    private static MyApplication instance;
    private User currUser;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
            Log.d("Firebase", "Firebase initialized manually in MyApplication.java");
        } else {
            Log.d("Firebase", "Firebase was already initialized.");
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }
}
