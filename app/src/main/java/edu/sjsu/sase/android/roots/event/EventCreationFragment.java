package edu.sjsu.sase.android.roots.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;

import java.util.List;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.sjsu.sase.android.roots.MyApplication;
import edu.sjsu.sase.android.roots.R;

import edu.sjsu.sase.android.roots.event.Event;



/**
 * A simple {@link Fragment} subclass.
 */
public class EventCreationFragment extends Fragment {

    // TODO: Rename parameter arguments if needed
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db;
    private MyApplication app;
    
    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar endDateCalendar = Calendar.getInstance();
    private Calendar startTimeCalendar = Calendar.getInstance();
    private Calendar endTimeCalendar = Calendar.getInstance();

    private EditText eventTitleInput;
    
    private Button btnStartDate, btnEndDate, btnStartTime, btnEndTime;
    private Button btnSave, btnDiscard, btnUploadImage;

    private FirebaseAuth mAuth;

    public EventCreationFragment() {
        // Required empty public constructor
    }

    public static EventCreationFragment newInstance(String param1, String param2) {
        EventCreationFragment fragment = new EventCreationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = MyApplication.getInstance();

        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(requireContext());

        if (firebaseApps.isEmpty()) {
            Log.e("Firebase", "No Firebase apps are initialized!");
        } else {
            Log.d("Firebase", "Firebase Apps Initialized: " + firebaseApps.size());
            for (FirebaseApp app : firebaseApps) {
                Log.d("Firebase", "App Name: " + app.getName());
            }
        }
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        FirebaseFirestore.setLoggingEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the new layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);

        // Initialize text input
        eventTitleInput = view.findViewById(R.id.etEventName);

        // Initialize date and time buttons
        btnStartDate = view.findViewById(R.id.btnStartDate);
        btnEndDate = view.findViewById(R.id.btnEndDate);
        btnStartTime = view.findViewById(R.id.btnStartTime);
        btnEndTime = view.findViewById(R.id.btnEndTime);
        
        // Initialize action buttons
        btnSave = view.findViewById(R.id.btnSave);
        btnDiscard = view.findViewById(R.id.btnDiscard);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);

        // Set up date and time pickers
        setupDateTimeButtons();
        
        // Set up action buttons
        btnSave.setOnClickListener(v -> publishEvent(v));
        btnDiscard.setOnClickListener(v -> discardEvent());
        btnUploadImage.setOnClickListener(v -> uploadImage());

        return view;
    }


    // event info getters
    private String generateEventID() {
        return String.valueOf(System.currentTimeMillis()) + app.getCurrUser().getId() + getEventName();
    }
    public String getEventName() {
        return eventTitleInput.getText().toString();
    }

    public String getEventDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US);
        return dateFormat.format(startDateCalendar.getTime()) + " - " + dateFormat.format(endDateCalendar.getTime());
    }

    public String getEventHostUid() {
        return app.getCurrUser().getId();
    }

    public String getEventTags() {
        return "";
    }

    public int getImageResourceId() {
        return 0;
    }

    private void setupDateTimeButtons() {
        // Format for displaying dates and times
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        
        // Set initial text
        btnStartDate.setText(dateFormat.format(startDateCalendar.getTime()));
        btnEndDate.setText(dateFormat.format(endDateCalendar.getTime()));
        btnStartTime.setText(timeFormat.format(startTimeCalendar.getTime()));
        btnEndTime.setText(timeFormat.format(endTimeCalendar.getTime()));
        
        // Set up date pickers
        btnStartDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year, month, dayOfMonth) -> {
                        startDateCalendar.set(Calendar.YEAR, year);
                        startDateCalendar.set(Calendar.MONTH, month);
                        startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        btnStartDate.setText(dateFormat.format(startDateCalendar.getTime()));
                    },
                    startDateCalendar.get(Calendar.YEAR),
                    startDateCalendar.get(Calendar.MONTH),
                    startDateCalendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
        
        btnEndDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year, month, dayOfMonth) -> {
                        endDateCalendar.set(Calendar.YEAR, year);
                        endDateCalendar.set(Calendar.MONTH, month);
                        endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        btnEndDate.setText(dateFormat.format(endDateCalendar.getTime()));
                    },
                    endDateCalendar.get(Calendar.YEAR),
                    endDateCalendar.get(Calendar.MONTH),
                    endDateCalendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
        
        // Set up time pickers
        btnStartTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (view, hourOfDay, minute) -> {
                        startTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        startTimeCalendar.set(Calendar.MINUTE, minute);
                        btnStartTime.setText(timeFormat.format(startTimeCalendar.getTime()));
                    },
                    startTimeCalendar.get(Calendar.HOUR_OF_DAY),
                    startTimeCalendar.get(Calendar.MINUTE),
                    false
            );
            timePickerDialog.show();
        });
        
        btnEndTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (view, hourOfDay, minute) -> {
                        endTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        endTimeCalendar.set(Calendar.MINUTE, minute);
                        btnEndTime.setText(timeFormat.format(endTimeCalendar.getTime()));
                    },
                    endTimeCalendar.get(Calendar.HOUR_OF_DAY),
                    endTimeCalendar.get(Calendar.MINUTE),
                    false
            );
            timePickerDialog.show();
        });
    }

    private void publishEvent(View view) {
        // check if user is authenticated
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new event object=
        Event event = new Event(generateEventID(), getEventName(),
                getEventDateTime(), getEventHostUid(), getEventTags(),getImageResourceId());

        db.collection("events").document(event.getId()).set(event)
                .addOnSuccessListener(aVoid -> {
                    Log.d("EventCreationFragment", "Event " + getEventName() + "saved to successfully");
                    goToSingleEvent(view);
                })
                .addOnFailureListener(e -> Log.e("EventCreationFragment", "Error writing document", e));
        // Here you would validate and save the event
        Toast.makeText(getContext(), "Event saved" + app.getCurrUser().getId(), Toast.LENGTH_SHORT).show();

        // Navigate to single event

    }

    private void discardEvent() {
        // Navigate back or clear form
        Toast.makeText(getContext(), "Event discarded", Toast.LENGTH_SHORT).show();
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    private void uploadImage() {
        // Implement image upload functionality
        Toast.makeText(getContext(), "Image upload clicked", Toast.LENGTH_SHORT).show();
    }

    private void goToSingleEvent(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_singleEventFragment);
    }
}

