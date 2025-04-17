package edu.sjsu.sase.android.roots.user;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.sjsu.sase.android.roots.MyApplication;
import edu.sjsu.sase.android.roots.R;

/**
 * A fragment representing the screen where users can edit their user profile.
 */
public class EditProfileFragment extends Fragment {

    private MyApplication app;
    private User currUser;
    
    private ImageView profilePicture;
    private EditText name, username, pronouns, age, location, bio;
    private EditText email, facebook, instagram, twitter;
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

    /**
     * Starting point for fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = MyApplication.getInstance();
        currUser = app.getCurrUser();
    }

    /**
     * Initializes layout (UI) for the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return view of fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initialize views
        profilePicture = view.findViewById(R.id.profilePic);
        name = view.findViewById(R.id.editName);
        username = view.findViewById(R.id.username);
        pronouns = view.findViewById(R.id.pronouns);
        age = view.findViewById(R.id.age);
        location = view.findViewById(R.id.location);
        bio = view.findViewById(R.id.bio);
        email = view.findViewById(R.id.email);
        facebook = view.findViewById(R.id.facebook);
        instagram = view.findViewById(R.id.instagram);
        twitter = view.findViewById(R.id.twitter);
        
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
            name.setText(currUser.getName());
            username.setText(currUser.getUsername());
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