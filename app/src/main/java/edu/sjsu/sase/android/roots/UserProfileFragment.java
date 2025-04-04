package edu.sjsu.sase.android.roots;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a user's profile screen.
 */
public class UserProfileFragment extends Fragment {
    private MyApplication app;
    private User currUser;
    private User userToDisplay;
    
    private TextView tvPronouns, tvAge, tvLocation, tvInterests, tvBio;
    private RecyclerView rvEvents;
    private TabLayout tabLayout;

    public UserProfileFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve global application instance
        app = MyApplication.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        currUser = app.getCurrUser();

        Bundle argument = getArguments();
        if (argument != null && getArguments().containsKey(getString(R.string.user_argument_key))) {
            String key = requireContext().getString(R.string.user_argument_key);
            userToDisplay = argument.getParcelable(key);
            Log.d("Profile", "display chosen user: " + userToDisplay.getName());
        } else {
            userToDisplay = currUser;
            Log.d("Profile", "display curr user: " + currUser.getName());
        }

        // Initialize views
        tvPronouns = view.findViewById(R.id.tvPronouns);
        tvAge = view.findViewById(R.id.tvAge);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvInterests = view.findViewById(R.id.tvInterests);
        tvBio = view.findViewById(R.id.tvBio);
        rvEvents = view.findViewById(R.id.rvEvents);
        tabLayout = view.findViewById(R.id.tabLayout);
        
        // Set user data
        // tvPronouns.setText(userToDisplay.getPronouns());
        // tvAge.setText(userToDisplay.getAge() + " y/o");
        // tvLocation.setText(userToDisplay.getLocation());
        // tvInterests.setText(userToDisplay.getInterests());
        // tvBio.setText(userToDisplay.getBio());

        // Setup events list
        setupEventsList();
        
        // Setup tab selection listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateEventsForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Not needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Not needed
            }
        });

        // Initialize navigation buttons
        Button editProfileBtn = view.findViewById(R.id.btnEditProfile);
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button buddiesBtn = view.findViewById(R.id.buddySystemBtn);

        editProfileBtn.setOnClickListener(this::onClickEditProfile);
        logoutBtn.setOnClickListener(this::onClickLogout);
        homeBtn.setOnClickListener(this::onClickHome);
        buddiesBtn.setOnClickListener(this::onClickBuddySystem);

        return view;
    }
    
    private void setupEventsList() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event name", "April 8 - 7 pm", "host name", "outdoor, social, music", "", R.drawable.ic_profile));
        events.add(new Event("Event name", "April 8 - 7 pm", "host name", "outdoor, social, music", "", R.drawable.ic_profile));
        
        EventAdapter adapter = new EventAdapter(events, event -> {
            // Handle event click if needed
        });
        rvEvents.setAdapter(adapter);
    }
    
    private void updateEventsForTab(int tabPosition) {
        List<Event> events = new ArrayList<>();
        switch (tabPosition) {
            case 0:
                events.add(new Event("Upcoming Event", "April 10 - 8 pm", "host name", "outdoor, social", "", R.drawable.ic_profile));
                events.add(new Event("Upcoming Event 2", "April 15 - 6 pm", "host name", "music, food", "", R.drawable.ic_profile));
                break;
            case 1:
                events.add(new Event("My Hosted Event", "April 20 - 7 pm", "You", "outdoor, music", "", R.drawable.ic_profile));
                break;
            case 2:
                events.add(new Event("Invited Event", "April 12 - 9 pm", "friend name", "social, food", "", R.drawable.ic_profile));
                events.add(new Event("Invited Event 2", "April 18 - 8 pm", "friend name", "music, outdoor", "", R.drawable.ic_profile));
                break;
            case 3:
                events.add(new Event("Past Event", "March 25 - 7 pm", "host name", "social, music", "", R.drawable.ic_profile));
                break;
        }
        EventAdapter adapter = new EventAdapter(events, event -> {
            // Handle event click if needed
        });
        rvEvents.setAdapter(adapter);
    }

    private void onClickEditProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_editProfileFragment);
    }

    private void onClickLogout(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_startFragment);
    }

    private void onClickHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_homeFragment);
    }

    private void onClickBuddySystem(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_buddySystemFragment);
    }
}