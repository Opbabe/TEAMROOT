package edu.sjsu.sase.android.roots.buddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.sjsu.sase.android.roots.R;

public class BuddySystemFragment extends Fragment {

    private ImageView buddyImage;
    private TextView buddyName, tvInterests, tvBio;
    private CardView skipBtn, digDeeperBtn, beBudsBtn;
    private ImageButton buddiesBtn, nextBtn;

    public BuddySystemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buddy_system, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        buddyImage = view.findViewById(R.id.buddyImage);
        buddyName = view.findViewById(R.id.buddyName);
        tvInterests = view.findViewById(R.id.tvInterests);
        tvBio = view.findViewById(R.id.tvBio);
        
        // Initialize buttons
        skipBtn = view.findViewById(R.id.skipBtn);
        digDeeperBtn = view.findViewById(R.id.digDeeperBtn);
        beBudsBtn = view.findViewById(R.id.beBudsBtn);
        buddiesBtn = view.findViewById(R.id.buddiesBtn);
        nextBtn = view.findViewById(R.id.nextBtn);

        // Set up click listeners
        skipBtn.setOnClickListener(v -> skipProfile());
        digDeeperBtn.setOnClickListener(v -> viewProfileDetails());
        beBudsBtn.setOnClickListener(v -> connectWithBuddy());
        buddiesBtn.setOnClickListener(v -> navigateToMyBuddies());
        nextBtn.setOnClickListener(v -> showNextProfile());

        // Load initial profile data
        loadProfileData();
    }

    private void loadProfileData() {
        // In a real app, you would load data from your database or API
        // For now, we'll use sample data
        buddyName.setText("Sandra Le");
        tvInterests.setText("social, food, music, outdoor");
        tvBio.setText("erm this is my user bio for the profile :0");
        
        // Load image (using placeholder for now)
        buddyImage.setImageResource(R.drawable.placeholder_image);
    }

    private void skipProfile() {
        Toast.makeText(getContext(), "Skipped profile", Toast.LENGTH_SHORT).show();
        // In a real app, you would load the next profile
        showNextProfile();
    }

    private void viewProfileDetails() {
        Toast.makeText(getContext(), "Viewing profile details", Toast.LENGTH_SHORT).show();
        // In a real app, navigate to detailed profile view
    }

    private void connectWithBuddy() {
        Toast.makeText(getContext(), "Connection request sent!", Toast.LENGTH_SHORT).show();
        // In a real app, send connection request and load the next profile
        showNextProfile();
    }

    private void navigateToMyBuddies() {
        NavController controller = Navigation.findNavController(requireView());
        controller.navigate(R.id.action_buddySystemFragment_to_buddyListFragment);
    }

    private void showNextProfile() {
        Toast.makeText(getContext(), "Loading next profile...", Toast.LENGTH_SHORT).show();
        
        String[] names = {"Alex Kim", "Jordan Taylor", "Casey Morgan", "Riley Johnson"};
        String[] interests = {
            "hiking, photography, travel", 
            "gaming, anime, tech", 
            "cooking, reading, yoga", 
            "sports, music, art"
        };
        String[] bios = {
            "Adventure seeker looking for hiking buddies!",
            "Gamer by night, coder by day. Let's talk tech!",
            "Foodie who loves trying new recipes and restaurants.",
            "Sports enthusiast who also plays in a band."
        };
        
        int randomIndex = (int) (Math.random() * names.length);
        buddyName.setText(names[randomIndex]);
        tvInterests.setText(interests[randomIndex]);
        tvBio.setText(bios[randomIndex]);
    }
}