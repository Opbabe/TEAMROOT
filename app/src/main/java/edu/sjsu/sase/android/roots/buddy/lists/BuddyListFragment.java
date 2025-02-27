package edu.sjsu.sase.android.roots.buddy.lists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.R;

/**
 * A fragment representing the buddy list screen containing a matches list, a friends list,
 * and a button to view friend requests
 */
public class BuddyListFragment extends Fragment {
    ArrayList<String> matchesList = new ArrayList<>();
    ArrayList<String> friendsList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BuddyListFragment() {
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
        // Inflate the layout for this fragment (initializes layout (UI) for fragment)
        View view = inflater.inflate(R.layout.fragment_buddy_list, container, false);

        // matches list: placeholder hardcoded data
        for (int i = 1; i <= 8; i++) {
            matchesList.add("Match " + i);
        }
        MatchFragment matchFragment = (MatchFragment)  getChildFragmentManager().findFragmentById(R.id.matchesFragment);
        matchFragment.setData(matchesList);

        // friends list: placeholder hardcoded data
        for (int i = 1; i <= 10; i++) {
            friendsList.add(String.valueOf(i));
        }
        FriendFragment friendFragment = (FriendFragment) getChildFragmentManager().findFragmentById(R.id.friendsFragment);
        friendFragment.setData(friendsList);
        friendFragment.setNavigation(R.id.action_buddyListFragment_to_userProfileFragment);


        // buttons (retrieve from view)
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button searchBtn = view.findViewById(R.id.searchBtn);
        Button requestBtn = view.findViewById(R.id.requestBtn);
        // TODO: replace with BottomNavigationView
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button buddiesBtn = view.findViewById(R.id.buddiesBtn);
        Button profileBtn = view.findViewById(R.id.userProfileBtn);

        // setOnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);
        searchBtn.setOnClickListener(this::onClickSearch);
        requestBtn.setOnClickListener(this::onClickRequests);
        // TODO: replace with BottomNavigationView
        homeBtn.setOnClickListener(this::onClickHome);
        buddiesBtn.setOnClickListener(this::onClickBuddies);
        profileBtn.setOnClickListener(this::onClickUserProfile);

        return view;
    }

    /**
     * Navigates to Buddy System screen.
     * @param view
     */
    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_buddySystemFragment);
    }

    /**
     * Navigates to User Listing screen.
     * @param view
     */
    private void onClickSearch(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_userListingFragment);
    }

    /**
     * Navigates to Friend Requests screen.
     * @param view
     */
    private void onClickRequests(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_friendRequestsFragment);
    }

    // BOTTOM NAVIGATION BUTTONS

    /**
     * Navigates to Home screen.
     * @param view
     */
    private void onClickHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_homeFragment);
    }

    /**
     * Navigates to Buddy System screen.
     * @param view
     */
    private void onClickBuddies(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_buddySystemFragment);
    }

    /**
     * Navigates to User Profile screen.
     * @param view
     */
    private void onClickUserProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_userProfileFragment);
    }
}