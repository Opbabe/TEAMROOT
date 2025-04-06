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
import edu.sjsu.sase.android.roots.User;

public class BuddyListFragment extends Fragment {
    private ArrayList<User> matchesList = new ArrayList<>();
    private ArrayList<User> friendsList = new ArrayList<>();
    private EditText searchInput; // need to capture search query

    public BuddyListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buddy_list, container, false);

        fetchMatches();
        fetchFriends();

        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button searchBtn = view.findViewById(R.id.searchBtn);
        Button requestBtn = view.findViewById(R.id.requestBtn);
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button buddiesBtn = view.findViewById(R.id.buddiesBtn);
        Button profileBtn = view.findViewById(R.id.userProfileBtn);
        searchInput = view.findViewById(R.id.searchInput); //taking in input text

        // Set OnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);
        searchBtn.setOnClickListener(this::onClickSearch); //search listener
        requestBtn.setOnClickListener(this::onClickRequests);
        homeBtn.setOnClickListener(this::onClickHome);
        buddiesBtn.setOnClickListener(this::onClickBuddies);
        profileBtn.setOnClickListener(this::onClickUserProfile);

        return view;
    }

    private void fetchMatches() {
        matchesList.clear();
        // Placeholder hardcoded data
        for (int i = 1; i <= 10; i++) {
            matchesList.add(new User(i));
        }
        updateMatchFragment();
    }

    private void updateMatchFragment() {
        MatchFragment matchFragment = (MatchFragment) getChildFragmentManager().findFragmentById(R.id.matchesFragment);
        if (matchFragment != null) {
            matchFragment.setUsersList(matchesList);
        }
    }

    private void fetchFriends() {
        friendsList.clear();
        // Placeholder hardcoded data
        for (int i = 11; i <= 20; i++) {
            friendsList.add(new User(i));
        }
        updateFriendFragment();
    }

    private void updateFriendFragment() {
        FriendFragment friendFragment = (FriendFragment) getChildFragmentManager().findFragmentById(R.id.friendsFragment);
        if (friendFragment != null) {
            friendFragment.setUsersList(friendsList);
            friendFragment.setNavigationId(R.id.action_buddyListFragment_to_userProfileFragment);
        }
    }

    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_buddySystemFragment);
    }

    //search function, will add friend request option here soon
    private void onClickSearch(View view) {
        String query = searchInput.getText().toString().trim(); //grab user input
        Bundle bundle = new Bundle();
        bundle.putString("searchQuery", query); //pass search string to userlisting frag
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_userListingFragment, bundle);
    }

    private void onClickRequests(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_friendRequestsFragment);
    }

    private void onClickHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_homeFragment);
    }

    private void onClickBuddies(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_buddySystemFragment);
    }

    private void onClickUserProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_userProfileFragment);
    }
}
