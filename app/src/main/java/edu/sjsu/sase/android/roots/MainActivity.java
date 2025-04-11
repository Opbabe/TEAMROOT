package edu.sjsu.sase.android.roots;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        // link nav bar to nav controller
        BottomNavigationView navbar = findViewById(R.id.navbar);
        Log.d("NavControllerError", "nav bar retrieved");
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(navbar, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destId = destination.getId();
            // visibility
            if (destId == R.id.startFragment || destId == R.id.signupFragment || destId == R.id.loginFragment) {
                navbar.setVisibility(View.GONE);
                return;
            }
            navbar.setVisibility(View.VISIBLE);

            // Ensure only relevant items are checked
            if (destId == R.id.homeFragment || destId == R.id.buddySystemFragment || destId == R.id.userProfileFragment) {
                navbar.setSelectedItemId(destId);
                Log.e("navbar select", "selected navbar item");
            } else {
                navbar.setSelectedItemId(R.id.dummy);

                Log.e("navbar select", "selected dummy");
            }
        });

        Menu menu = navbar.getMenu();
        menu.findItem(R.id.dummy).setVisible(false); // Hide the dummy item

        navbar.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                navController.navigate(R.id.homeFragment);
                Log.d("nav bar", "go to home");
                return true;
            } else if (id == R.id.nav_buddies) {
                navController.navigate(R.id.buddySystemFragment);
                Log.d("nav bar", "go to buddy system");
                return true;
            } else if (id == R.id.nav_profile) {
                navController.navigate(R.id.userProfileFragment);
                Log.d("nav bar", "go to profile");
                return true;
            }
            Log.d("nav bar", "not selected");
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navbar.setOnApplyWindowInsetsListener((v, insets) -> {
            // Consume the system insets to avoid extra padding
            return insets.consumeSystemWindowInsets();
        });

    }
}