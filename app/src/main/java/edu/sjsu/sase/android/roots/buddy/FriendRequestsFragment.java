package edu.sjsu.sase.android.roots.buddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.R;

/**
 * A fragment representing the friend requests screen
 */
public class FriendRequestsFragment extends Fragment {
    ArrayList<String> usersList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendRequestsFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_requests, container, false);

        // requests list: placeholder hardcoded data
//        for (int i = 0; i < 12; i++) {
//            usersList.add(String.valueOf(i));
//        }
//        FriendFragment friendFragment = (FriendFragment) getChildFragmentManager().findFragmentById(R.id.userListingFragment);
//        friendFragment.setData(usersList);
//        friendFragment.setNavigation(R.id.action_friendRequestsFragment_to_userProfileFragment);

        // buttons (retrieve from view)
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);

        // setOnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);

        return view;
    }

    /**
     * Navigates to Buddy List screen.
     * @param view
     */
    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_friendRequestsFragment_to_buddyListFragment);
    }
}