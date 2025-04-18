package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
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
    private HashSet<Button> buttons = new HashSet<>();
    private EditText searchInput; // need to capture search query
    Button allTab;
    Button socialTab;
    Button musicTab;
    Button sportsTab;

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

        Button searchBtn = view.findViewById(R.id.searchBtn);
        searchInput = view.findViewById(R.id.searchInput); //taking in input text

        // Initialize views
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        FloatingActionButton createEventBtn = view.findViewById(R.id.createBttn);
        //MaterialCardView searchCard = view.findViewById(R.id.searchCard);

        // Set up RecyclerView with grid layout (2 columns)
        eventsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // initialize event lists
        allEvents = new ArrayList<>();
        filteredEvents = new ArrayList<>(allEvents);

        // Set up adapter
        eventAdapter = new EventAdapter(filteredEvents, this);
        eventsRecyclerView.setAdapter(eventAdapter);

        // Set up category chips click listeners
        setupCategoryChips(view);

        // Set up button click listeners
        createEventBtn.setOnClickListener(v -> goToEventCreation(v));

        // search bar
        searchBtn.setOnClickListener(this::onClickSearch); //search listener

        // Fetch events from Firebase
        fetchEvents();



        return view;
    }

    /**
     * Navigates to User Listing screen.
     * @param view
     */
    //search function, will add friend request option here soon
    private void onClickSearch(View view) {
        unClickTabs();
        String query = searchInput.getText().toString().trim().toLowerCase(); //grab user input
        if (query.isEmpty()){return;}
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    allEvents.clear(); // clear any existing data
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Event event = doc.toObject(Event.class);
                        event.setId(doc.getId());
                        String eventName = doc.getString("name").toLowerCase();
                        if (eventName.contains(query)) {
                            allEvents.add(event);
                        }
                    }
                    // Refresh the filteredEvents, here we're simply showing all events
                    filteredEvents.clear();
                    filteredEvents.addAll(allEvents);
                    eventAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("HomeFragment", "Error fetching events", e);
                });
//        Bundle bundle = new Bundle();
//        bundle.putString("searchQuery", query); //pass search string to userlisting frag
//        NavController controller = Navigation.findNavController(view);
//        controller.navigate(R.id.action_buddyListFragment_to_userListingFragment, bundle);
    }

    private void fetchEvents() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    allEvents.clear(); // clear any existing data
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Event event = doc.toObject(Event.class);
                        event.setId(doc.getId());
                        allEvents.add(event);
                    }
                    // Refresh the filteredEvents, here we're simply showing all events
                    filteredEvents.clear();
                    filteredEvents.addAll(allEvents);
                    eventAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("HomeFragment", "Error fetching events", e);
                });
    }

    private void setupCategoryChips(View view) {
        allTab = view.findViewById(R.id.allTab);
        socialTab = view.findViewById(R.id.socialTab);
        musicTab = view.findViewById(R.id.musicTab);
        sportsTab = view.findViewById(R.id.sportsTab);
        buttons.add(allTab);
        buttons.add(socialTab);
        buttons.add(musicTab);
        buttons.add(sportsTab);

//        allTab.setOnClickListener(v -> filterEvents("all"));
//        socialTab.setOnClickListener(v -> filterEvents("social"));
//        musicTab.setOnClickListener(v -> filterEvents("music"));
//        sportsTab.setOnClickListener(v -> filterEvents("sports"));

        for (Button tab : buttons) {
            tab.setOnClickListener(v -> onClickTab(tab));
        }
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

    private void unClickTabs() {
        for (Button tab: buttons) {
            tab.setBackground(ContextCompat.getDrawable(tab.getContext(), R.drawable.rounded_tab));
        }
    }

    private void onClickTab(Button button) {
        for (Button tab: buttons) {
            if (tab == button) {
                tab.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.rounded_tab_pressed));
                if (tab == allTab) {
                    fetchEvents();
                }
                else{
                    filterEvents(tab.getText().toString());
                }
            }
            else {
                tab.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.rounded_tab));
            }
        }
    }

    @Override
    public void onEventClick(int position) {
        // Get the selected event.
        Event selectedEvent = allEvents.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("event", selectedEvent.getId());

        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_singleEventFragment, bundle);
    }

    private void goToEventCreation(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_eventCreationFragment);
    }
}