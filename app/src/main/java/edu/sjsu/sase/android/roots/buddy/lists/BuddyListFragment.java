package edu.sjsu.sase.android.roots.buddy.lists;

import static edu.sjsu.sase.android.roots.R.*;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
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
import java.util.HashMap;
import java.util.Objects;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

/**
 * A fragment representing the buddy list screen containing a matches list, a friends list,
 * and a button to view friend requests
 */
public class BuddyListFragment extends Fragment {
    private ArrayList<User> blossomsList = new ArrayList<>();
    private ArrayList<User> budsList = new ArrayList<>();
    private ArrayList<User> requestsList = new ArrayList<>();
    private EditText searchInput; // need to capture search query
    // tabs
    Button budsTab, blossomsTab, requestsTab;
    FragmentContainerView budsFragment, blossomsFragment, requestsFragment;
    HashMap<Button, FragmentContainerView> tabToFragmentMap = new HashMap<>();

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
        fetchBlossoms();
        fetchBuds();
        fetchRequests();

        // buttons (retrieve from view)
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button searchBtn = view.findViewById(R.id.searchBtn);
        searchInput = view.findViewById(R.id.searchInput); //taking in input text

        // tabs
        budsTab = view.findViewById(R.id.budsTab);
        blossomsTab = view.findViewById(R.id.blossomsTab);
        requestsTab = view.findViewById(R.id.requestsTab);

        // list fragments
        budsFragment = view.findViewById(R.id.budsFragment);
        blossomsFragment = view.findViewById(R.id.blossomsFragment);
        requestsFragment = view.findViewById(R.id.requestsFragment);

        // tab to fragment hash map
        tabToFragmentMap.put(budsTab, budsFragment);
        tabToFragmentMap.put(blossomsTab,blossomsFragment);
        tabToFragmentMap.put(requestsTab, requestsFragment);

        // setOnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);
        searchBtn.setOnClickListener(this::onClickSearch); //search listener

        // setOnClickListeners for each tab
        for (Button tab : tabToFragmentMap.keySet()) {
            tab.setOnClickListener(v -> onClickTab(tab));
        }

        return view;
    }

    /**
     * Retrieve user's blossoms (steaks)
     */
    private void fetchBlossoms() {
        blossomsList.clear();
        // TODO: implement mechanism to keep track of matches and replace hardcoded data
        // blossoms list: placeholder hardcoded data
        for (int i = 1; i <= 10; i++) {
            blossomsList.add(new User(i));
        }
        Log.d("buddy list", "blossoms list size on create view: " + blossomsList.size());
        updateBlossomsFragment();
    }

    /**
     * Update Blossoms (streaks) List UI
     */
    private void updateBlossomsFragment() {
        BlossomsFragment blossomsFragment = (BlossomsFragment) getChildFragmentManager().findFragmentById(R.id.blossomsFragment);
        if (blossomsFragment != null) {
            Log.d("buddy list", "blossoms list size: " + blossomsList.size());
            blossomsFragment.setUsersList(blossomsList);
        }
    }

    /**
     * Retrieve user's buds (friends) list
     */
    private void fetchBuds() {
        budsList.clear();
        // TODO: implement mechanism to keep track of friends and replace hardcoded data
        // buds list: placeholder hardcoded data
        for (int i = 11; i <= 20; i++) {
            budsList.add(new User(i));
        }
        Log.d("buddy list", "buds list size on create view: " + budsList.size());
        updateBudFragment();
    }

    /**
     * Update Buds (Friends) fragment UI
     */
    private void updateBudFragment() {
        BudFragment budFragment = (BudFragment) getChildFragmentManager().findFragmentById(R.id.budsFragment);
        if (budFragment != null) {
            Log.d("buddy list", "friends list size: " + budsList.size());
            budFragment.setUsersList(budsList);
            budFragment.setNavigationId(R.id.action_buddyListFragment_to_userProfileFragment);
        }
    }

    /**
     * Retrieve user's incoming requests list
     */
    private void fetchRequests() {
        requestsList.clear();
        // TODO: implement mechanism to keep track of friends and replace hardcoded data
        // buds list: placeholder hardcoded data
        for (int i = 11; i <= 20; i++) {
            requestsList.add(new User(i));
        }
        Log.d("buddy list", "buds list size on create view: " + requestsList.size());
        updateRequestsFragment();
    }

    /**
     * Update Requests fragment UI
     */
    private void updateRequestsFragment() {
        RequestFragment requestFragment = (RequestFragment) getChildFragmentManager().findFragmentById(R.id.requestsFragment);
        if (requestFragment != null) {
            Log.d("buddy list", "friends list size: " + requestsList.size());
            requestFragment.setUsersList(requestsList);
            requestFragment.setNavigationId(R.id.action_buddyListFragment_to_userProfileFragment);
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

    private void onClickTab(Button button) {
        for (Button tab: tabToFragmentMap.keySet()) {
            if (tab == button) {
                tab.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.rounded_tab_pressed));
                Objects.requireNonNull(tabToFragmentMap.get(tab)).setVisibility(View.VISIBLE);
            }
            else {
                tab.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.rounded_tab));
                Objects.requireNonNull(tabToFragmentMap.get(tab)).setVisibility(View.GONE);
            }
        }
    }
}
