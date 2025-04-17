package edu.sjsu.sase.android.roots.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.MyApplication;
import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

/**
 * Represents a single event screen
 */
public class SingleEventFragment extends Fragment {

    ArrayList<User> guestList = new ArrayList<>();
    GuestRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    private String eventId;
    private FirebaseFirestore db;

    private MyApplication app;
    private Event currentEvent;

    private TextView eventTitle, startDate, endDate, startTime, endTime, hostName, visibility, tags, location, description, guestTitle;
    private RecyclerView pictureSection;
    private Button editBtn, deleteBtn;
    private ImageButton goingBtn, notGoingBtn;

    public SingleEventFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_event, container, false);

        eventId = null;
        if(getArguments() != null) {
            eventId = getArguments().getString("event");
        }

        db = FirebaseFirestore.getInstance();

        if(eventId != null) {
            fetchEvent(db,eventId,view);
        } else {
            Log.e("SingleEventFragment", "No event ID provided");
        }

        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button editBtn = view.findViewById(R.id.editBtn);

        backArrow.setOnClickListener(this::goToHome);
        editBtn.setOnClickListener(this::goToEventCreation);

        // set data
//        for (int i = 1; i <= 10; i++) {
//            guestList.add(new User(i));
//        }
//        Log.d("single event", "guest list size on create view: " +  guestList.size());

        // pass data as argument to construct adapter
//        adapter = new GuestRecyclerViewAdapter(guestList);
//        adapter.setIsOnSingleEvent(true);
//
//        // cast view to RecyclerView and set adapter for RecyclerView
//        recyclerView = view.findViewById(R.id.guestList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        recyclerView.setAdapter(adapter);
//        recyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                setRecyclerViewHeightBasedOnChildren(recyclerView);
//            }
//        });


        return view;

    }

    private void fetchEvent(FirebaseFirestore db, String eventId, View view) {
        // Assume your events are stored in a "events" collection
        db.collection("events").document(eventId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            // Extract fields from the document (make sure these keys match your Firestore document)
                            String title = documentSnapshot.getString("name");
                            String startDate = documentSnapshot.getString("startDate");
                            String endDate = documentSnapshot.getString("endDate");
                            String startTime = documentSnapshot.getString("startTime");
                            String endTime = documentSnapshot.getString("endTime");
                            String hostName = documentSnapshot.getString("hostName");
                            String visibility = documentSnapshot.getString("visibility");
                            String tags = documentSnapshot.getString("tags");
                            String location = documentSnapshot.getString("location");
                            String description = documentSnapshot.getString("description");

                            // Now, update your UI elements (assume you have already found them by id)
                            TextView eventTitleTextView = view.findViewById(R.id.eventTitle);
                            TextView startDateTextView = view.findViewById(R.id.startDate);
                            TextView endDateTextView = view.findViewById(R.id.endDate);
                            TextView startTimeTextView = view.findViewById(R.id.startTime);
                            TextView endTimeTextView = view.findViewById(R.id.endTime);
                            TextView hostNameTextView = view.findViewById(R.id.hostName);
                            TextView visibilityTextView = view.findViewById(R.id.visibility);
                            TextView tagsTextView = view.findViewById(R.id.tags);
                            TextView locationTextView = view.findViewById(R.id.location);
                            TextView descriptionTextView = view.findViewById(R.id.description);

                            eventTitleTextView.setText(title);
                            startDateTextView.setText(startDate);
                            endDateTextView.setText(endDate);
                            startTimeTextView.setText(startTime);
                            endTimeTextView.setText(endTime);
                            hostNameTextView.setText(hostName);
                            visibilityTextView.setText(visibility);
                            tagsTextView.setText(tags);
                            locationTextView.setText(location);
                            descriptionTextView.setText(description);
                        } else {
                            Log.w("SingleEventFragment", "No event document found for ID: " + eventId);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("SingleEventFragment", "Error retrieving event document", e);
                    }
                });
    }


    /**
     * Retrieve event guests
     */
    private void fetchGuests() {
        guestList.clear();
        // TODO: implement mechanism to keep track of matches and replace hardcoded data
        // guest list: placeholder hardcoded data
        for (int i = 1; i <= 10; i++) {
            guestList.add(new User(i));
        }
        Log.d("event", "guest list size on create view: " +  guestList.size());
    }

    public static void setRecyclerViewHeightBasedOnChildren(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View listItem = recyclerView.getLayoutManager().findViewByPosition(i);
            if (listItem != null) {
                listItem.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.UNSPECIFIED
                );
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight;
        recyclerView.setLayoutParams(params);
    }

    private void goToHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_homeFragment);
    }

    private void goToEventCreation(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_eventCreationFragment);
    }

}