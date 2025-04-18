package edu.sjsu.sase.android.roots.event;

import static edu.sjsu.sase.android.roots.event.SingleEventFragment.setRecyclerViewHeightBasedOnChildren;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import edu.sjsu.sase.android.roots.MyApplication;
import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

public class EventCreationFragment extends Fragment {

    private FirebaseFirestore db;
    private MyApplication app;

    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar endDateCalendar   = Calendar.getInstance();
    private Calendar startTimeCalendar = Calendar.getInstance();
    private Calendar endTimeCalendar   = Calendar.getInstance();

    private TextInputEditText eventTitleInput, eventDescriptionInput;
    private EditText          eventLocationInput;

    private Button btnStartDate, btnEndDate, btnStartTime, btnEndTime;
    private Button btnSave, btnDiscard, btnUploadImage;

    private CheckBox   cbOption1, cbOption2, cbOption3, cbOption4;
    private RadioGroup rgVisibility;
    private RadioButton rbPrivate, rbPublic;

    private RecyclerView recyclerView;
    private GuestRecyclerViewAdapter adapter;
    private ArrayList<User> userList = new ArrayList<>();

    private FirebaseAuth mAuth;

    // Firebase Storage
    private FirebaseStorage storage;
    private StorageReference storageRef;

    // Image upload state
    private ImageView ivEventImage;
    private Uri       selectedImageUri;
    private String    uploadedImageUrl;

    // Activity‑Result launcher for picking image
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    public EventCreationFragment() { /* Required empty constructor */ }

    public static EventCreationFragment newInstance(String param1, String param2) {
        EventCreationFragment fragment = new EventCreationFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = MyApplication.getInstance();

        // Firebase setup
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(requireContext());
        if (firebaseApps.isEmpty()) {
            Log.e("Firebase", "No Firebase apps are initialized!");
        }
        db         = FirebaseFirestore.getInstance();
        storage    = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth      = FirebaseAuth.getInstance();

        // Register image‐picker callback
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK &&
                            result.getData() != null)
                    {
                        selectedImageUri = result.getData().getData();
                        ivEventImage.setImageURI(selectedImageUri);
                        uploadImageToFirebase(selectedImageUri);
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);

        // Text inputs
        eventTitleInput       = view.findViewById(R.id.eventName);
        eventDescriptionInput = view.findViewById(R.id.description);
        eventLocationInput    = view.findViewById(R.id.etLocation);

        // Date/time buttons
        btnStartDate = view.findViewById(R.id.btnStartDate);
        btnEndDate   = view.findViewById(R.id.btnEndDate);
        btnStartTime = view.findViewById(R.id.btnStartTime);
        btnEndTime   = view.findViewById(R.id.btnEndTime);

        // Action buttons
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        btnSave        = view.findViewById(R.id.btnSave);
        btnDiscard     = view.findViewById(R.id.btnDiscard);

        // Visibility
        rgVisibility = view.findViewById(R.id.rgVisibility);
        rbPrivate    = view.findViewById(R.id.rbPrivate);
        rbPublic     = view.findViewById(R.id.rbPublic);

        // Image preview
        ivEventImage = view.findViewById(R.id.ivEventImage);

        // Guests RecyclerView (unchanged)
        for (int i = 1; i <= 5; i++) userList.add(new User(i));
        adapter = new GuestRecyclerViewAdapter(userList);
        adapter.setIsOnSingleEvent(false);
        recyclerView = view.findViewById(R.id.guestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().registerAdapterDataObserver(
                new RecyclerView.AdapterDataObserver() {
                    @Override public void onChanged() {
                        setRecyclerViewHeightBasedOnChildren(recyclerView);
                    }
                }
        );

        // Date/time picker wiring
        setupDateTimeButtons();

        // Upload image button launches picker
        btnUploadImage.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pick.setType("image/*");
            imagePickerLauncher.launch(pick);
        });

        // Save / Discard
        btnSave.setOnClickListener(v -> publishEvent(v));
        btnDiscard.setOnClickListener(v -> discardEvent());

        return view;
    }

    private void setupDateTimeButtons() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a",      Locale.US);

        btnStartDate.setText(dateFormat.format(startDateCalendar.getTime()));
        btnEndDate.setText(  dateFormat.format(endDateCalendar.getTime()));
        btnStartTime.setText(timeFormat.format(startTimeCalendar.getTime()));
        btnEndTime.setText(  timeFormat.format(endTimeCalendar.getTime()));

        btnStartDate.setOnClickListener(v -> new DatePickerDialog(
                getContext(),
                (dp, y, m, d) -> {
                    startDateCalendar.set(y, m, d);
                    btnStartDate.setText(dateFormat.format(startDateCalendar.getTime()));
                },
                startDateCalendar.get(Calendar.YEAR),
                startDateCalendar.get(Calendar.MONTH),
                startDateCalendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        btnEndDate.setOnClickListener(v -> new DatePickerDialog(
                getContext(),
                (dp, y, m, d) -> {
                    endDateCalendar.set(y, m, d);
                    btnEndDate.setText(dateFormat.format(endDateCalendar.getTime()));
                },
                endDateCalendar.get(Calendar.YEAR),
                endDateCalendar.get(Calendar.MONTH),
                endDateCalendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        btnStartTime.setOnClickListener(v -> new TimePickerDialog(
                getContext(),
                (tp, h, min) -> {
                    startTimeCalendar.set(Calendar.HOUR_OF_DAY, h);
                    startTimeCalendar.set(Calendar.MINUTE, min);
                    btnStartTime.setText(timeFormat.format(startTimeCalendar.getTime()));
                },
                startTimeCalendar.get(Calendar.HOUR_OF_DAY),
                startTimeCalendar.get(Calendar.MINUTE),
                false
        ).show());

        btnEndTime.setOnClickListener(v -> new TimePickerDialog(
                getContext(),
                (tp, h, min) -> {
                    endTimeCalendar.set(Calendar.HOUR_OF_DAY, h);
                    endTimeCalendar.set(Calendar.MINUTE, min);
                    btnEndTime.setText(timeFormat.format(endTimeCalendar.getTime()));
                },
                endTimeCalendar.get(Calendar.HOUR_OF_DAY),
                endTimeCalendar.get(Calendar.MINUTE),
                false
        ).show());
    }

    private void uploadImageToFirebase(Uri uri) {
        String path = "event_images/" + UUID.randomUUID();
        StorageReference imgRef = storageRef.child(path);

        imgRef.putFile(uri)
                .addOnSuccessListener(task -> imgRef.getDownloadUrl()
                        .addOnSuccessListener(downloadUri -> {
                            uploadedImageUrl = downloadUri.toString();
                            Toast.makeText(getContext(),
                                    "Image uploaded successfully!",
                                    Toast.LENGTH_SHORT).show();
                        }))
                .addOnFailureListener(e -> Toast.makeText(
                        getContext(),
                        "Upload failed: " + e.getMessage(),
                        Toast.LENGTH_LONG).show()
                );
    }

    private void publishEvent(View view) {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(),
                    "User not authenticated",
                    Toast.LENGTH_SHORT).show();
            return;
        }



        // Build list of guest IDs
        List<String> guestIds = new ArrayList<>();
        for (User u : userList) {
            guestIds.add(u.getId());
        }

        // New 13‑arg constructor: id, name, host, tags, imageUrl,
        // startDate, endDate, startTime, endTime, description,
        // visibility, location, guestList
        Event event = new Event(
                generateEventID(),
                eventTitleInput.getText().toString(),
                app.getCurrUser().getId(),
                getEventTags(),
                uploadedImageUrl,
                getEventStartDate(),
                getEventEndDate(),
                getEventStartTime(),
                getEventEndTime(),
                eventDescriptionInput.getText().toString(),
                getEventVisibility(),
                eventLocationInput.getText().toString(),
                guestIds
        );

        db.collection("events")
                .document(event.getId())
                .set(event)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(),
                            "Event created!",
                            Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("event", event.getId());
                    Navigation.findNavController(view)
                            .navigate(R.id.action_eventCreationFragment_to_singleEventFragment,
                                    bundle);
                })
                .addOnFailureListener(e -> {
                    Log.e("EventCreation", "Error saving event", e);
                    Toast.makeText(getContext(),
                            "Save failed: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }

    private void discardEvent() {
        Toast.makeText(getContext(), "Event discarded", Toast.LENGTH_SHORT).show();
        requireActivity().onBackPressed();
    }

    // Helpers for formatting
    private String getEventStartDate() {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.US)
                .format(startDateCalendar.getTime());
    }
    private String getEventEndDate() {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.US)
                .format(endDateCalendar.getTime());
    }
    private String getEventStartTime() {
        return new SimpleDateFormat("h:mm a", Locale.US)
                .format(startTimeCalendar.getTime());
    }
    private String getEventEndTime() {
        return new SimpleDateFormat("h:mm a", Locale.US)
                .format(endTimeCalendar.getTime());
    }
    private String getEventVisibility() {
        return rbPrivate.isChecked() ? "private" : "public";
    }
    private String getEventTags() {
        // TODO: collect your checkbox/tag logic here
        return "";
    }
    private String generateEventID() {
        return System.currentTimeMillis()
                + app.getCurrUser().getId()
                + eventTitleInput.getText().toString();
    }
}
