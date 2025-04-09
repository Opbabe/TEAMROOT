package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sase.android.roots.event.Event;
import edu.sjsu.sase.android.roots.event.EventAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements EventAdapter.OnEventClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView eventsRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> allEvents;
    private List<Event> filteredEvents;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        FloatingActionButton createEventBtn = view.findViewById(R.id.createBttn);
        MaterialCardView searchCard = view.findViewById(R.id.searchCard);
        Button profileBtn = view.findViewById(R.id.profileBttn);
        Button homeBtn = view.findViewById(R.id.homeButtn);
        Button buddyBtn = view.findViewById(R.id.buddyBttn);

        // Set up RecyclerView with grid layout (2 columns)
        eventsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize event data
        setupEventData();

        // Create a copy for filtering
        filteredEvents = new ArrayList<>(allEvents);

        // Set up adapter
        eventAdapter = new EventAdapter(filteredEvents, this);
        eventsRecyclerView.setAdapter(eventAdapter);

        // Set up category chips click listeners
        setupCategoryChips(view);

        // Set up button click listeners
        createEventBtn.setOnClickListener(v -> goToEventCreation(v));
        searchCard.setOnClickListener(v -> goToEventListing(v));
        profileBtn.setOnClickListener(v -> gotToUserProfile(v));
        homeBtn.setOnClickListener(v -> goHome(v));
        buddyBtn.setOnClickListener(v -> goToBuddySystem(v));

        return view;
    }

    private void setupEventData() {
        // Create sample event data; replace with actual data as needed.
        allEvents = new ArrayList<>();
        int eventImagePlaceholder = android.R.drawable.ic_menu_gallery; // Replace with your drawable resource
        allEvents.add(new Event("1", "Summer Music Festival", "April 8 - 7 pm", "Live Nation", "outdoor, music, festival", eventImagePlaceholder));
        allEvents.add(new Event("2", "Tech Conference 2023", "May 15 - 9 am", "TechCorp", "tech, conference, networking", eventImagePlaceholder));
        allEvents.add(new Event("3", "Charity Run", "June 10 - 8 am", "RunForGood", "sports, charity, outdoor", eventImagePlaceholder));
        allEvents.add(new Event("4", "Art Exhibition", "April 20 - 6 pm", "City Gallery", "art, culture, indoor", eventImagePlaceholder));
        allEvents.add(new Event("5", "Food & Wine Festival", "May 5 - 12 pm", "Taste Inc.", "food, social, outdoor", eventImagePlaceholder));
        allEvents.add(new Event("6", "Comedy Night", "April 12 - 8 pm", "Laugh Factory", "comedy, entertainment, indoor", eventImagePlaceholder));
        allEvents.add(new Event("7", "Yoga in the Park", "Every Sunday - 9 am", "Zen Studios", "yoga, wellness, outdoor", eventImagePlaceholder));
        allEvents.add(new Event("8", "Book Club Meeting", "April 18 - 7 pm", "Page Turners", "books, discussion, social", eventImagePlaceholder));
    }

    private void setupCategoryChips(View view) {
        // Assuming your fragment_home.xml contains chips with these IDs
        Chip chipAll = view.findViewById(R.id.chipAll);
        Chip chipSocial = view.findViewById(R.id.chipSocial);
        Chip chipMusic = view.findViewById(R.id.chipMusic);
        Chip chipSports = view.findViewById(R.id.chipSports);

        chipAll.setOnClickListener(v -> filterEvents("all"));
        chipSocial.setOnClickListener(v -> filterEvents("social"));
        chipMusic.setOnClickListener(v -> filterEvents("music"));
        chipSports.setOnClickListener(v -> filterEvents("sports"));
    }

    private void filterEvents(String category) {
        filteredEvents.clear();
        if (category.equalsIgnoreCase("all")) {
            filteredEvents.addAll(allEvents);
        } else {
            for (Event event : allEvents) {
                if (event.getTags().toLowerCase().contains(category.toLowerCase())) {
                    filteredEvents.add(event);
                }
            }
        }
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEventClick(int position) {
        // Navigate to single event details when an event card is clicked
        goToSingleEvent(getView());
    }

    private void gotToUserProfile(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_userProfileFragment);
    }

    private void goToBuddySystem(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_buddySystemFragment);
    }

    private void goHome(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_self);
    }

    private void goToEventListing(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_eventListingFragment);
    }
    private void goToEventCreation(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_eventCreationFragment);
    }

    private void goToSingleEvent(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_singleEventFragment);
    }
}