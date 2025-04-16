package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
        //MaterialCardView searchCard = view.findViewById(R.id.searchCard);

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
        Button allTab = view.findViewById(R.id.allTab);
        Button socialTab = view.findViewById(R.id.socialTab);
        Button musicTab = view.findViewById(R.id.musicTab);
        Button sportsTab = view.findViewById(R.id.sportsTab);
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

    private void onClickTab(Button button) {
        for (Button tab: buttons) {
            if (tab == button) {
                tab.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.rounded_tab_pressed));
                filterEvents(tab.getText().toString());
            }
            else {
                tab.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.rounded_tab));
            }
        }
    }

    @Override
    public void onEventClick(int position) {
        // Navigate to single event details when an event card is clicked
        goToSingleEvent(getView());
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