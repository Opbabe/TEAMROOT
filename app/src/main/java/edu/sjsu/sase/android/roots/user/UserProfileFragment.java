package edu.sjsu.sase.android.roots.user;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sase.android.roots.event.Event;
import edu.sjsu.sase.android.roots.event.EventAdapter;
import edu.sjsu.sase.android.roots.MyApplication;
import edu.sjsu.sase.android.roots.R;

/**
 * A fragment representing a user's profile screen.
 */
public class UserProfileFragment extends Fragment {
    private MyApplication app;
    private User currUser;
    private User userToDisplay;
    
    private TextView tvPronouns, tvAge, tvLocation, tvInterests, tvBio;
    private RecyclerView rvEvents;
    private Button btnUpcoming, btnHosting, btnInvites, btnAttended;
    private ImageButton btnOptions;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve global application instance for global variables tied to app's lifecycle
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
        TextView name = view.findViewById(R.id.tvFullName);
        TextView username = view.findViewById(R.id.tvUsername);
        ImageView profilePic = view.findViewById(R.id.profilePic);
        tvPronouns = view.findViewById(R.id.tvPronouns);
        tvAge = view.findViewById(R.id.tvAge);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvInterests = view.findViewById(R.id.tvInterests);
        tvBio = view.findViewById(R.id.tvBio);
        rvEvents = view.findViewById(R.id.rvEvents);
        btnOptions = view.findViewById(R.id.btnOptions);
        
        // Initialize tab buttons
        btnUpcoming = view.findViewById(R.id.btnUpcoming);
        btnHosting = view.findViewById(R.id.btnHosting);
        btnInvites = view.findViewById(R.id.btnInvites);
        btnAttended = view.findViewById(R.id.btnAttended);
        
        // Set user data
        name.setText(userToDisplay.getName());
        username.setText(userToDisplay.getUsername());
        
        // Load profile picture
        String picUrl = userToDisplay.getProfilePicUrl();
        if (picUrl != null && !picUrl.isEmpty()) {
            Picasso.with(getContext())
                    .load(userToDisplay.getProfilePicUrl())
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(profilePic);
        }
        
        // Set additional user info (fields may need to be added to the User class)
        // tvPronouns.setText(userToDisplay.getPronouns());
        // tvAge.setText(userToDisplay.getAge() + " y/o");
        // tvLocation.setText(userToDisplay.getLocation());
        // tvInterests.setText(userToDisplay.getInterests());
        // tvBio.setText(userToDisplay.getBio());

        // Setup event list
        setupEventsList();
        
        // Setup tab buttons
        btnUpcoming.setOnClickListener(v -> updateEventsForTab(0));
        btnHosting.setOnClickListener(v -> updateEventsForTab(1));
        btnInvites.setOnClickListener(v -> updateEventsForTab(2));
        btnAttended.setOnClickListener(v -> updateEventsForTab(3));
        
        // Options menu
        btnOptions.setOnClickListener(v -> showOptionsMenu());

        // Bottom navigation buttons
        Button editProfileBtn = view.findViewById(R.id.btnEditProfile);

        // Set onClick listeners
        editProfileBtn.setOnClickListener(this::onClickEditProfile);

        return view;
    }
    
    /**
     * Shows the options menu
     */
    private void showOptionsMenu() {
        // In a real app, you would show a popup menu here
        Toast.makeText(getContext(), "Options menu clicked", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Sets up the events RecyclerView with sample data
     */
    private void setupEventsList() {
        // Create sample events (in a real app, this data would come from a database)
        List<Event> events = new ArrayList<>();
        
        // Add sample events
        events.add(new Event("Event name", "April 8 - 7 pm", "host name", "outdoor, social, music", "", R.drawable.ic_profile));
        events.add(new Event("Event name", "April 8 - 7 pm", "host name", "outdoor, social, music", "", R.drawable.ic_profile));
        
        // Create and set adapter
        EventAdapter adapter = new EventAdapter(events, event -> {
            // Handle event click if needed
        });
        rvEvents.setAdapter(adapter);
    }
    
    /**
     * Updates the events list based on the selected tab
     * @param tabPosition The position of the selected tab
     */
    private void updateEventsForTab(int tabPosition) {
        // Reset all tab button backgrounds
        btnUpcoming.setBackgroundTintList(null);
        btnHosting.setBackgroundTintList(null);
        btnInvites.setBackgroundTintList(null);
        btnAttended.setBackgroundTintList(null);
        
        // Highlight the selected tab
        switch (tabPosition) {
            case 0:
                btnUpcoming.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFE0E0FF));
                break;
            case 1:
                btnHosting.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFE0E0FF));
                break;
            case 2:
                btnInvites.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFE0E0FF));
                break;
            case 3:
                btnAttended.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFE0E0FF));
                break;
        }
        
        // For demonstration, we create sample events for each tab
        List<Event> events = new ArrayList<>();
        
        switch (tabPosition) {
            case 0: // Upcoming
                events.add(new Event("Upcoming Event", "April 10 - 8 pm", "host name", "outdoor, social", "", R.drawable.ic_profile));
                events.add(new Event("Upcoming Event 2", "April 15 - 6 pm", "host name", "music, food", "", R.drawable.ic_profile));
                break;
            case 1: // Hosting
                events.add(new Event("My Hosted Event", "April 20 - 7 pm", "You", "outdoor, music", "", R.drawable.ic_profile));
                break;
            case 2: // Invites
                events.add(new Event("Invited Event", "April 12 - 9 pm", "friend name", "social, food", "", R.drawable.ic_profile));
                events.add(new Event("Invited Event 2", "April 18 - 8 pm", "friend name", "music, outdoor", "", R.drawable.ic_profile));
                break;
            case 3: // Attended
                events.add(new Event("Past Event", "March 25 - 7 pm", "host name", "social, music", "", R.drawable.ic_profile));
                break;
        }
        
        // Update adapter with new events
        EventAdapter adapter = new EventAdapter(events, event -> {
            // Handle event click if needed
        });
        rvEvents.setAdapter(adapter);
    }

    /**
     * Navigates to edit profile screen.
     * @param view
     */
    private void onClickEditProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_editProfileFragment);
    }
}