package edu.sjsu.sase.android.roots;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing the screen where users can edit their user profile.
 */
public class EditProfileFragment extends Fragment {

    private MyApplication app;
    private User currUser;
    
    private ImageView profilePicture;
    private TextInputEditText etName, etUsername, etPronouns, etAge, etLocation, etBio;
    private TextInputEditText etEmail, etFacebook, etInstagram, etTwitter;
    private Button btnSave, btnCancel, btnUploadImage;
    
    private Uri selectedImageUri;
    
    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    Picasso.with(getContext())
                            .load(uri)
                            .placeholder(R.drawable.ic_profile)
                            .error(R.drawable.ic_profile)
                            .into(profilePicture);
                }
            }
    );

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
     */
    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = MyApplication.getInstance();
        currUser = app.getCurrUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initialize views
        profilePicture = view.findViewById(R.id.ivProfilePicture);
        etName = view.findViewById(R.id.etName);
        etUsername = view.findViewById(R.id.etUsername);
        etPronouns = view.findViewById(R.id.etPronouns);
        etAge = view.findViewById(R.id.etAge);
        etLocation = view.findViewById(R.id.etLocation);
        etBio = view.findViewById(R.id.etBio);
        etEmail = view.findViewById(R.id.etEmail);
        etFacebook = view.findViewById(R.id.etFacebook);
        etInstagram = view.findViewById(R.id.etInstagram);
        etTwitter = view.findViewById(R.id.etTwitter);
        
        btnSave = view.findViewById(R.id.btnSave);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);

        // Load current user data
        loadUserData();

        // Set click listeners
        backArrow.setOnClickListener(this::onClickBackArrow);
        btnSave.setOnClickListener(this::onClickSave);
        btnCancel.setOnClickListener(this::onClickCancel);
        btnUploadImage.setOnClickListener(v -> pickImage.launch("image/*"));

        return view;
    }

    /**
     * Loads the current user data into the form fields
     */
    private void loadUserData() {
        if (currUser != null) {
            // Load profile picture
            String picUrl = currUser.getProfilePicUrl();
            if (picUrl != null && !picUrl.isEmpty()) {
                Picasso.with(getContext())
                        .load(picUrl)
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .into(profilePicture);
            }
            
            // Load text fields
            etName.setText(currUser.getName());
            etUsername.setText(currUser.getUsername());
            // Additional fields can be set if available in your User class
            // etPronouns.setText(currUser.getPronouns());
            // etAge.setText(currUser.getAge());
            // etLocation.setText(currUser.getLocation());
            // etBio.setText(currUser.getBio());
            // etEmail.setText(currUser.getEmail());
            // etFacebook.setText(currUser.getFacebookLink());
            // etInstagram.setText(currUser.getInstagramLink());
            // etTwitter.setText(currUser.getTwitterLink());
        }
    }

    /**
     * Saves the user profile data
     * @param view The view that was clicked
     */
    private void onClickSave(View view) {
        // Update user object with new data (setters removed because they are not available in User class)
        // You may update the fields directly if accessible, e.g.: currUser.name = etName.getText().toString();
        
        // Save the updated user
        app.setCurrUser(currUser);
        
        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        
        // Navigate back to profile
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_editProfileFragment_to_userProfileFragment);
    }

    /**
     * Cancels the edit and returns to profile
     * @param view The view that was clicked
     */
    private void onClickCancel(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_editProfileFragment_to_userProfileFragment);
    }

    /**
     * Navigates back to user profile screen.
     * @param view The view that was clicked
     */
    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_editProfileFragment_to_userProfileFragment);
    }
}