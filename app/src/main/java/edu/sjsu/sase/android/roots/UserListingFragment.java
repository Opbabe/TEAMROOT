package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.buddy.lists.FriendFragment;

/**
 * A fragment representing the user listing screen where all users can be found
 */
public class UserListingFragment extends Fragment {
    private FirebaseFirestore db;
    private MyApplication app;
    ArrayList<User> usersList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListingFragment() {
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
        // Retrieve global application of app for global variables tied to app's lifecycle
        app = MyApplication.getInstance();
        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();
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
        View view = inflater.inflate(R.layout.fragment_user_listing, container, false);
        // Retrieve all users to display on user listing
        fetchAllUsers();
        // buttons (retrieve from view)
        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        // setOnClickListeners
        backArrow.setOnClickListener(this::onClickBackArrow);

        return view;
    }

    /**
     * Retrieve all users to display on user listing
     * TODO: filter/sort users
     */
    private void fetchAllUsers() {
        // clear list
        usersList.clear();
        // placeholder user
        User testUser = new User("id", "name", "username@gmail.com", "", "username");
        usersList.add(testUser);
        // retrieve all users
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        User user = document.toObject(User.class);
                        usersList.add(user);
                    }
                    updateFriendFragment();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching users: " + e.getMessage());
                });
    }

    /**
     * Update UI
     */
    private void updateFriendFragment() {
        FriendFragment friendFragment = (FriendFragment) getChildFragmentManager().findFragmentById(R.id.userListingFragment);
        if (friendFragment != null) {
            friendFragment.setData(usersList);
            friendFragment.setNavigation(R.id.action_userListingFragment_to_userProfileFragment);
        }
    }

    /**
     * Navigates to Buddy List screen.
     * @param view
     */
    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userListingFragment_to_buddyListFragment);
    }
}