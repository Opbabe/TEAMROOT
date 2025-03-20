package edu.sjsu.sase.android.roots;

import android.app.Application;
import android.util.Log;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
            Log.d("Firebase", "Firebase initialized manually in MyApplication.java");
        } else {
            Log.d("Firebase", "Firebase was already initialized.");
        }
    }
}
