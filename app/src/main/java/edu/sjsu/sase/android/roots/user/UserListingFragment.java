package edu.sjsu.sase.android.roots.user;

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

import edu.sjsu.sase.android.roots.MyApplication;
import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.buddy.lists.RequestFragment;

public class UserListingFragment extends Fragment {
    private FirebaseFirestore db;
    private MyApplication app;
    private ArrayList<User> usersList = new ArrayList<>();
    private String searchQuery = ""; //this string holds the search query from BuddyListFragment, from method

    public UserListingFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve search query from the arguments (passed from BuddyListFragment)
        if (getArguments() != null) {
            searchQuery = getArguments().getString("searchQuery", "").toLowerCase();
        }
        app = MyApplication.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_listing, container, false);

        fetchAllUsers();

        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        backArrow.setOnClickListener(this::onClickBackArrow);

        return view;
    }

    private void fetchAllUsers() {
        usersList.clear();

        // Retrieve all users from Firestore
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        User user = document.toObject(User.class);
                        usersList.add(user);
                    }

                    filterUsersBySearchQuery();

                    updateUserListingFragment();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching users: " + e.getMessage());
                });
    }

    // filters by username
    private void filterUsersBySearchQuery() {
        if (!searchQuery.isEmpty()) {
            ArrayList<User> filteredList = new ArrayList<>();
            //loop thru each user
            for (User user : usersList) {
                if (user.getUsername().toLowerCase().contains(searchQuery)) {
                    filteredList.add(user);
                }
            }
            // set filtered list back to usersList
            usersList = filteredList;
        }
    }


    private void updateUserListingFragment() {
        RequestFragment requestFragment = (RequestFragment) getChildFragmentManager().findFragmentById(R.id.userListingFragment);
        if (requestFragment != null) {
            requestFragment.setUsersList(usersList);
            requestFragment.setNavigationId(R.id.action_userListingFragment_to_userProfileFragment);
        }
    }

    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_userListingFragment_to_buddyListFragment);
    }
}
