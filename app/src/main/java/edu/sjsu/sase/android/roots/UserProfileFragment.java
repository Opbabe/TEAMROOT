package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A fragment representing a user's profile screen.
 */
public class UserProfileFragment extends Fragment {
    private MyApplication app;
    private User currUser;
    private User userToDisplay;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve global application of app for global variables tied to app's lifecycle
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
        }
        else {
            userToDisplay = currUser;
            Log.d("Profile", "display curr user: " + currUser.getName());
        }

        // profile user info
        TextView name = view.findViewById(R.id.tvFullName);
        name.setText(userToDisplay.getName());
        TextView username = view.findViewById(R.id.tvUsername);
        username.setText(userToDisplay.getUsername());
        ImageView profilePic = view.findViewById(R.id.profilePic);
        String picUrl = userToDisplay.getProfilePicUrl();
        if (picUrl != null && !picUrl.isEmpty()) {
            Picasso.with(getContext())
                    .load(userToDisplay.getProfilePicUrl())
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(profilePic);
        }

        // buttons (retrieve from view)
        Button editProfileBtn = view.findViewById(R.id.btnBuddyProfile);
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        // TODO: replace with BottomNavigationView
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button buddiesBtn = view.findViewById(R.id.buddySystemBtn);

        // setOnClickListeners
        editProfileBtn.setOnClickListener(this::onClickEditProfile);
        logoutBtn.setOnClickListener(this::onClickLogout);
        // TODO: replace with BottomNavigationView
        homeBtn.setOnClickListener(this::onClickHome);
        buddiesBtn.setOnClickListener(this::onClickBuddySystem);

        return view;
    }

    /**
     * Navigates to edit profile screen.
     * @param view
     */
    private void onClickEditProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_editProfileFragment);
    }

    /**
     * Navigates to start screen.
     * @param view
     */
    private void onClickLogout(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_startFragment);
    }

    // BOTTOM NAVIGATION BUTTONS

    /**
     * Navigates to Home screen.
     * @param view
     */
    private void onClickHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_homeFragment);
    }

    /**
     * Navigates to Buddy System screen.
     * @param view
     */
    private void onClickBuddySystem(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userProfileFragment_to_buddySystemFragment);
    }

}