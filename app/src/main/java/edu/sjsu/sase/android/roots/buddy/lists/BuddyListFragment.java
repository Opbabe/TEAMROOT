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

import edu.sjsu.sase.android.roots.R;

/**
 * A fragment representing the buddy list screen containing a matches list, a friends list,
 * and a button to view friend requests
 */
public class BuddyListFragment extends Fragment {
    private RecyclerView matchesRecyclerView;

    /**
     * Mandatory empty contructor for the fragment manager to instantiate the
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
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
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

        // buttons (retrieve from view)
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button requestBtn = view.findViewById(R.id.requestBtn);
        Button homeBtn = view.findViewById(R.id.homeBtn);

        // setOnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);
        requestBtn.setOnClickListener(this::onClickRequests);
        homeBtn.setOnClickListener(this::onClickHome);

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
     * Navigates to Friend Requests screen.
     * @param view
     */
    private void onClickRequests(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_friendRequestsFragment);
    }

    /**
     * Navigates to Home screen.
     * @param view
     */
    private void onClickHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddyListFragment_to_homeFragment);
    }
}