package edu.sjsu.sase.android.roots.buddy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.sjsu.sase.android.roots.R;

public class BuddySystemFragment extends Fragment {

    private ImageView buddyImage;
    private TextView buddyName, tvInterests, tvBio;
    private CardView profileCard, skipBtn, digDeeperBtn, beBudsBtn;
    private ImageButton buddiesBtn, nextBtn;
    private ImageView likeIndicator, skipIndicator;
    private FrameLayout cardContainer;

    // Touch handling variables
    private float initialX;
    private float initialY;
    private boolean isCardMoving = false;
    private boolean isAnimating = false;
    private static final float SWIPE_THRESHOLD = 150f;
    private static final float ROTATION_COEFFICIENT = 0.1f;

    // Profile data
    private String[] names = {"Sandra Le", "Alex Kim", "Jordan Taylor", "Casey Morgan", "Riley Johnson"};
    private String[] interests = {
            "social, food, music, outdoor",
            "hiking, photography, travel",
            "gaming, anime, tech",
            "cooking, reading, yoga",
            "sports, music, art"
    };
    private String[] bios = {
            "erm this is my user bio for the profile :0",
            "Adventure seeker looking for hiking buddies!",
            "Gamer by night, coder by day. Let's talk tech!",
            "Foodie who loves trying new recipes and restaurants.",
            "Sports enthusiast who also plays in a band."
    };
    private int currentProfileIndex = 0;

    public BuddySystemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buddy_system, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        cardContainer = view.findViewById(R.id.cardContainer);
        profileCard = view.findViewById(R.id.profileCard);
        buddyImage = view.findViewById(R.id.buddyImage);
        buddyName = view.findViewById(R.id.buddyName);
        tvInterests = view.findViewById(R.id.tvInterests);
        tvBio = view.findViewById(R.id.tvBio);
        likeIndicator = view.findViewById(R.id.likeIndicator);
        skipIndicator = view.findViewById(R.id.skipIndicator);

        // Initialize buttons
        skipBtn = view.findViewById(R.id.skipBtn);
        digDeeperBtn = view.findViewById(R.id.digDeeperBtn);
        beBudsBtn = view.findViewById(R.id.beBudsBtn);
        buddiesBtn = view.findViewById(R.id.buddiesBtn);
        nextBtn = view.findViewById(R.id.nextBtn);

        // Set up click listeners
        skipBtn.setOnClickListener(v -> {
            if (!isAnimating) {
                skipProfile();
            }
        });
        digDeeperBtn.setOnClickListener(v -> viewProfileDetails());
        beBudsBtn.setOnClickListener(v -> {
            if (!isAnimating) {
                connectWithBuddy();
            }
        });
        buddiesBtn.setOnClickListener(v -> navigateToMyBuddies());
        nextBtn.setOnClickListener(v -> {
            if (!isAnimating) {
                showNextProfile();
            }
        });

        // Set up swipe detection
        setupSwipeDetection();

        // Load initial profile data
        loadProfileData();

        // Ensure card starts with no rotation or translation
        resetCardPosition();
    }

    private void resetCardPosition() {
        profileCard.setRotation(0f);
        profileCard.setTranslationX(0f);
        profileCard.setTranslationY(0f);
        profileCard.setAlpha(1f);
    }

    private void setupSwipeDetection() {
        profileCard.setOnTouchListener((v, event) -> {
            if (isAnimating) {
                return false;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Record initial touch position
                    initialX = event.getRawX();
                    initialY = event.getRawY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    // Calculate how far the card has moved
                    float deltaX = event.getRawX() - initialX;
                    float deltaY = event.getRawY() - initialY;

                    // Only start moving if the movement is significant
                    if (!isCardMoving && Math.abs(deltaX) > 10) {
                        isCardMoving = true;
                    }

                    if (isCardMoving) {
                        // Move the card
                        profileCard.setTranslationX(deltaX);

                        // Add a slight rotation based on horizontal movement
                        float rotation = deltaX * ROTATION_COEFFICIENT;
                        profileCard.setRotation(rotation);

                        // Show like/skip indicators based on swipe direction
                        if (deltaX > SWIPE_THRESHOLD) {
                            likeIndicator.setAlpha(Math.min(deltaX / (SWIPE_THRESHOLD * 3), 1f));
                            skipIndicator.setAlpha(0f);
                        } else if (deltaX < -SWIPE_THRESHOLD) {
                            skipIndicator.setAlpha(Math.min(Math.abs(deltaX) / (SWIPE_THRESHOLD * 3), 1f));
                            likeIndicator.setAlpha(0f);
                        } else {
                            likeIndicator.setAlpha(0f);
                            skipIndicator.setAlpha(0f);
                        }
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    if (isCardMoving) {
                        float deltaEndX = event.getRawX() - initialX;

                        // Determine if the card should be swiped away or returned
                        if (Math.abs(deltaEndX) > SWIPE_THRESHOLD * 2) {
                            // Swipe away
                            boolean isLiked = deltaEndX > 0;
                            completeSwipeAnimation(isLiked);
                        } else {
                            // Return to center
                            returnCardToCenter();
                        }

                        isCardMoving = false;
                    }
                    return true;
                }
            }
            return false;
        });
    }

    private void loadProfileData() {
        // Load the current profile data
        buddyName.setText(names[currentProfileIndex]);
        tvInterests.setText(interests[currentProfileIndex]);
        tvBio.setText(bios[currentProfileIndex]);

        // Load image (using placeholder for now)
        buddyImage.setImageResource(R.drawable.placeholder_image);
    }

    private void skipProfile() {
        Toast.makeText(getContext(), "Skipped profile", Toast.LENGTH_SHORT).show();
        // Animate the skip action
        animateSwipe(false);
    }

    private void viewProfileDetails() {
        Toast.makeText(getContext(), "Viewing profile details", Toast.LENGTH_SHORT).show();
        // In a real app, navigate to detailed profile view
    }

    private void connectWithBuddy() {
        Toast.makeText(getContext(), "Connection request sent!", Toast.LENGTH_SHORT).show();
        // Animate the like action
        animateSwipe(true);
    }

    private void navigateToMyBuddies() {
        NavController controller = Navigation.findNavController(requireView());
        controller.navigate(R.id.action_buddySystemFragment_to_buddyListFragment);
    }

    private void showNextProfile() {
        // Move to the next profile with animation
        animateSwipe(false);
    }

    private void animateSwipe(boolean isLiked) {
        if (isAnimating) return;

        isAnimating = true;

        // Show the appropriate indicator
        if (isLiked) {
            likeIndicator.setAlpha(1f);
            skipIndicator.setAlpha(0f);
        } else {
            likeIndicator.setAlpha(0f);
            skipIndicator.setAlpha(1f);
        }

        completeSwipeAnimation(isLiked);
    }

    private void completeSwipeAnimation(boolean isLiked) {
        final CardView oldCard = profileCard;
        final String currentName = names[currentProfileIndex];

        // Determine the direction to swipe
        float targetX = isLiked ? cardContainer.getWidth() * 1.5f : -cardContainer.getWidth() * 1.5f;
        float rotation = isLiked ? 30f : -30f;

        // Animate the old card off screen
        ObjectAnimator translationX = ObjectAnimator.ofFloat(oldCard, "translationX", oldCard.getTranslationX(), targetX);
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(oldCard, "rotation", oldCard.getRotation(), rotation);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(oldCard, "alpha", 1f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX, rotationAnim, alpha);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Remove the old card
                cardContainer.removeView(oldCard);

                // Increment profile index here
                currentProfileIndex = (currentProfileIndex + 1) % names.length;

                // Create new card with updated profile data
                createNewCard();

                // Show appropriate message
                if (isLiked) {
                    Toast.makeText(getContext(), "You connected with " + currentName + "!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Loading next profile...", Toast.LENGTH_SHORT).show();
                }

                // Reset indicators
                likeIndicator.setAlpha(0f);
                skipIndicator.setAlpha(0f);
                isAnimating = false;
            }
        });

        animatorSet.start();
    }

    private void createNewCard() {
        // Inflate a new card (currentProfileIndex is already updated)
        View newCardView = getLayoutInflater().inflate(R.layout.buddy_profile_card, cardContainer, false);

        // Find views in the new card
        CardView newCard = newCardView.findViewById(R.id.profileCard);
        ImageView newBuddyImage = newCardView.findViewById(R.id.buddyImage);
        TextView newBuddyName = newCardView.findViewById(R.id.buddyName);
        TextView newTvInterests = newCardView.findViewById(R.id.tvInterests);
        TextView newTvBio = newCardView.findViewById(R.id.tvBio);

        // Set data for the new card
        newBuddyName.setText(names[currentProfileIndex]);
        newTvInterests.setText(interests[currentProfileIndex]);
        newTvBio.setText(bios[currentProfileIndex]);
        newBuddyImage.setImageResource(R.drawable.placeholder_image);

        // Add the new card to the container
        cardContainer.addView(newCardView);

        // Update references to the new card
        profileCard = newCard;
        buddyImage = newBuddyImage;
        buddyName = newBuddyName;
        tvInterests = newTvInterests;
        tvBio = newTvBio;

        // Set up swipe detection for the new card
        setupSwipeDetection();

        // Start with the card slightly below and fade it in
        profileCard.setTranslationY(50f);
        profileCard.setAlpha(0f);

        // Animate the new card in
        ObjectAnimator translateY = ObjectAnimator.ofFloat(profileCard, "translationY", 50f, 0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(profileCard, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateY, fadeIn);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
    }

    private void returnCardToCenter() {
        if (isAnimating) return;

        isAnimating = true;

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(profileCard, "translationX", profileCard.getTranslationX(), 0f);
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(profileCard, "rotation", profileCard.getRotation(), 0f);

        translationAnimator.setDuration(200);
        rotationAnimator.setDuration(200);

        translationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.setInterpolator(new DecelerateInterpolator());

        translationAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Reset indicators
                likeIndicator.setAlpha(0f);
                skipIndicator.setAlpha(0f);
                isAnimating = false;
            }
        });

        translationAnimator.start();
        rotationAnimator.start();
    }
}