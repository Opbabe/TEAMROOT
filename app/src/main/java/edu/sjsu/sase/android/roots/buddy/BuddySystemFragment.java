package edu.sjsu.sase.android.roots.buddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.sjsu.sase.android.roots.R;

public class BuddySystemFragment extends Fragment {

    private CardView swipeCard;
    private float x1, x2, y1, y2;
    private static final int MIN_DISTANCE = 150;

    public BuddySystemFragment() {
        // Required empty public constructor
    }

    public static BuddySystemFragment newInstance(String param1, String param2) {
        BuddySystemFragment fragment = new BuddySystemFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve and process any arguments if needed.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_buddy_system, container, false);

        // Initialize the swipe card and bottom elements.
        swipeCard = view.findViewById(R.id.swipeCard);
        Button buddiesBtn = view.findViewById(R.id.buddiesBtn);
        Button homeBtn = view.findViewById(R.id.homeBtn);
        Button buddiesSystemBtn = view.findViewById(R.id.buddiesSystemBtn);
        Button profileBtn = view.findViewById(R.id.userProfileBtn);

        // Set onClickListeners for the buttons.
        buddiesBtn.setOnClickListener(this::goToList);
        homeBtn.setOnClickListener(this::onClickHome);
        profileBtn.setOnClickListener(this::onClickUserProfile);
        buddiesSystemBtn.setOnClickListener(v -> {
            // Already in Buddy System; you can opt to show a toast message if necessary.
        });

        // Setup swipe functionality on the CardView.
        setupSwipeCard();

        return view;
    }

    /**
     * Sets up a touch listener on the CardView to detect swipes.
     */
    private void setupSwipeCard() {
        swipeCard.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    return true; // Consume this event.
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    float deltaX = x2 - x1;
                    float deltaY = y2 - y1;

                    if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > MIN_DISTANCE) {
                        if (deltaX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    return true;
            }
            return false;
        });
    }

    /**
     * Handles a left swipe on the card (for example, to reject a buddy).
     */
    private void onSwipeLeft() {
        // Implement your left swipe behavior here.
        loadNextBuddy();
    }

    /**
     * Handles a right swipe on the card (for example, to accept a buddy).
     */
    private void onSwipeRight() {
        // Implement your right swipe behavior here.
        loadNextBuddy();
    }

    /**
     * Loads the data for the next buddy and updates the CardView.
     */
    private void loadNextBuddy() {
        // Retrieve child views from the CardView.
        TextView buddyName = swipeCard.findViewById(R.id.buddyName);
        TextView buddyInfo = swipeCard.findViewById(R.id.buddyInfo);
        ImageView buddyImage = swipeCard.findViewById(R.id.buddyImage);

        // For demonstration, simply update texts (replace with your data-loading logic).
        buddyName.setText("New Buddy Name");
        buddyInfo.setText("New Buddy Information");
        // Optionally update the image resource.
        buddyImage.setImageResource(R.drawable.placeholder_image);
    }

    /**
     * Navigates to the Buddy List fragment.
     */
    private void goToList(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddySystemFragment_to_buddyListFragment);
    }

    /**
     * Navigates to the Home fragment.
     */
    private void onClickHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddySystemFragment_to_homeFragment);
    }

    /**
     * Navigates to the User Profile fragment.
     */
    private void onClickUserProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddySystemFragment_to_userProfileFragment);
    }
}