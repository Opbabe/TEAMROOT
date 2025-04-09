package edu.sjsu.sase.android.roots.buddy.lists;

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
import android.widget.ImageView;

import java.util.ArrayList;
import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

/**
 * A fragment representing the buddy list screen containing a matches list, a friends list,
 * and a button to view friend requests
 */
public class BuddyListFragment extends Fragment {
    private ArrayList<User> matchesList = new ArrayList<>();
    private ArrayList<User> friendsList = new ArrayList<>();
    private EditText searchInput; // need to capture search query

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

        // matches and friends list
        fetchMatches();
        fetchFriends();

        // buttons (retrieve from view)
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button searchBtn = view.findViewById(R.id.searchBtn);
        Button requestBtn = view.findViewById(R.id.requestBtn);
        // TODO: replace with BottomNavigationView
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button buddiesBtn = view.findViewById(R.id.buddiesBtn);
        Button profileBtn = view.findViewById(R.id.userProfileBtn);
        searchInput = view.findViewById(R.id.searchInput); //taking in input text

        // setOnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);
        searchBtn.setOnClickListener(this::onClickSearch); //search listener
        requestBtn.setOnClickListener(this::onClickRequests);
        // TODO: replace with BottomNavigationView
        homeBtn.setOnClickListener(this::onClickHome);
        buddiesBtn.setOnClickListener(this::onClickBuddies);
        profileBtn.setOnClickListener(this::onClickUserProfile);

        return view;
    }

    /**
     * Retrieve user's matches
     */
    private void fetchMatches() {
        matchesList.clear();
        // TODO: implement mechanism to keep track of matches and replace hardcoded data
        // matches list: placeholder hardcoded data
        for (int i = 1; i <= 10; i++) {
            matchesList.add(new User(i));
        }
        Log.d("buddy list", "matches list size on create view: " + matchesList.size());
        updateMatchFragment();
    }

    /**
     * Update Matches List UI
     */
    private void updateMatchFragment() {
        MatchFragment matchFragment = (MatchFragment) getChildFragmentManager().findFragmentById(R.id.matchesFragment);
        if (matchFragment != null) {
            Log.d("buddy list", "matches list size: " + matchesList.size());
            matchFragment.setUsersList(matchesList);
        }
    }

    /**
     * Retrieve user's friends list
     */
    private void fetchFriends() {
        friendsList.clear();
        // TODO: implement mechanism to keep track of friends and replace hardcoded data
        // friends list: placeholder hardcoded data
        for (int i = 11; i <= 20; i++) {
            friendsList.add(new User(i));
        }
        Log.d("buddy list", "friends list size on create view: " + friendsList.size());
        updateFriendFragment();
    }

    /**
     * Update Friends UI
     */
    private void updateFriendFragment() {
        FriendFragment friendFragment = (FriendFragment) getChildFragmentManager().findFragmentById(R.id.friendsFragment);
        if (friendFragment != null) {
            Log.d("buddy list", "friends list size: " + friendsList.size());
            friendFragment.setUsersList(friendsList);
            friendFragment.setNavigationId(R.id.action_buddyListFragment_to_userProfileFragment);
        }
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
    //search function, will add friend request option here soon
    private void onClickSearch(View view) {
        String query = searchInput.getText().toString().trim(); //grab user input
        Bundle bundle = new Bundle();
        bundle.putString("searchQuery", query); //pass search string to userlisting frag
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_userListingFragment, bundle);
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
