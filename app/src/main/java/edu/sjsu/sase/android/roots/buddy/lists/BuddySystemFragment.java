package edu.sjsu.sase.android.roots.buddy.lists;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import edu.sjsu.sase.android.roots.R;

public class BuddySystemFragment extends Fragment {

    public BuddySystemFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_buddy_system, container, false);

        // Initialize the "My Buddies" button.
        Button buddiesBtn = view.findViewById(R.id.buddiesBtn);
        buddiesBtn.setOnClickListener(this::goToList);

        return view;
    }

    /**
     * Navigate to the Buddy List fragment.
     */
    private void goToList(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_buddySystemFragment_to_buddyListFragment);
    }
} 