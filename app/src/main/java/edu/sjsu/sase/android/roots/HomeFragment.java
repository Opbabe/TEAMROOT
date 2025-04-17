package edu.sjsu.sase.android.roots;

import android.os.Bundle;

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

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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



        // Fetch events from Firebase
        fetchEvents();



        return view;
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