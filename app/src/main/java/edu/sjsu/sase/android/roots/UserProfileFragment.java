package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve global application of app for global variables tied to app's lifecycle
        app = MyApplication.getInstance();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        currUser = app.getCurrUser();
        // profile user info
        TextView name = view.findViewById(R.id.tvFullName);
        name.setText(currUser.getName());
        TextView username = view.findViewById(R.id.tvUsername);
        username.setText(currUser.getUsername());
        ImageView profilePic = view.findViewById(R.id.profilePic);
        String picUrl = currUser.getProfilePicUrl();
        if (picUrl != null && !picUrl.isEmpty()) {
            Picasso.with(getContext())
                    .load(currUser.getProfilePicUrl())
                    .placeholder(R.drawable.ic_profile)
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