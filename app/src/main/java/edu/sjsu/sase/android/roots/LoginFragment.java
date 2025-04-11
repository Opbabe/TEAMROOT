package edu.sjsu.sase.android.roots;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import edu.sjsu.sase.android.roots.user.User;
import android.widget.FrameLayout;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import java.util.Random;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;

/**
 * A fragment representing the Login screen
 */
public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 9001;  // Request code for Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private Button loginBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MyApplication app;
    private FrameLayout leavesContainer;
    private Random random = new Random();
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LoginFragment() {
        //required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
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

        // Firebase
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(requireContext());

        if (firebaseApps.isEmpty()) {
            Log.e("Firebase", "No Firebase apps are initialized!");
        } else {
            Log.d("Firebase", "Firebase Apps Initialized: " + firebaseApps.size());
            for (FirebaseApp app : firebaseApps) {
                Log.d("Firebase", "App Name: " + app.getName());
            }
        }

        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        FirebaseFirestore.setLoggingEnabled(true);

        //initialize Google Sign-In options, later ill add manual password and email
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) //this pulls from default webclient id, i put the client id in strings.xml
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
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
        // Inflate the layout for fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Back button
        ImageView backArrowBtn = rootView.findViewById(R.id.backArrowBtn);
        backArrowBtn.setOnClickListener(this::onClickBackArrow);

        // Sign-in button setup
        SignInButton signInButton = rootView.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> signIn());

        // Initialize leaves container and start leaf animations
        leavesContainer = rootView.findViewById(R.id.leavesContainer);
        startLeafAnimations();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @javax.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateGrass();
        setupParallaxEffect();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //result returned from launching the sign-in
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //success
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                //failure err
                Log.e("LoginFragment", "Google sign-in failed" + e.getMessage());
                Toast.makeText(getActivity(), "Google sign-in failed", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                saveUserToFirestore(account);
            } else {
                Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Create user object from specified account and set the appropriate variables
     * @param account the google sign in account
     */
    private void setCurrUser(GoogleSignInAccount account){
        // get user information from account
        String uid = mAuth.getCurrentUser().getUid();
        String name = account.getDisplayName();
        String email = account.getEmail();
        String profilePicUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";
        String username = email.substring(0,email.indexOf("@"));
        // store as global variable in MyApplication
        // TODO: store currUser using DataStore to minimize user calls to Firestore
        User currUser = new User(uid, name, email, profilePicUrl, username);
        app.setCurrUser(currUser);
    }

    /**
     * Save account to Firestore as a User
     * @param account the google sign in account
     */
    private void saveUserToFirestore(GoogleSignInAccount account) {
        // set user variable in MyApplication and LoginFragment
        setCurrUser(account);
        User currUser = app.getCurrUser();
        // save user to Firestore
        db.collection("users").document(currUser.getId()).set(currUser)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Signed in as: " + currUser.getName(), Toast.LENGTH_SHORT).show();
                    navigateToHome();
                })
                .addOnFailureListener(e -> Log.e("LoginFragment", "Error writing document", e));

    }

    private void navigateToHome() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_loginFragment_to_homeFragment);
    }


//    private void handleSignInSuccess(GoogleSignInAccount account) {
//        // TO DO LATER: send info to firebase or make the manual password/email option, wip
//        String displayName = account.getDisplayName();
//        String email = account.getEmail();
//        Toast.makeText(getActivity(), "Signed in as: " + displayName, Toast.LENGTH_SHORT).show();
//        loginBtn.setEnabled(true);
//    }
//
//    private void handleSignInFailure(ApiException e) {
//        int statusCode = e.getStatusCode();
//        Toast.makeText(getActivity(), "Sign-in failed: " + statusCode, Toast.LENGTH_SHORT).show();
//        Log.e("LoginFragment", "Sign-in failed: " + e.getMessage() + " Status Code: " + statusCode);
//    }

    /**
     * Navigates to Start screen.
     * @param view
     */
    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_loginFragment_to_startFragment);
    }

    /**
     * Navigates to Home screen.
     * @param view
     */
    private void onClickLogin(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_loginFragment_to_homeFragment);
    }

    /**
     * Creates and animates falling leaves in the background with enhanced movement
     */
    private void startLeafAnimations() {
        // Create and animate 15 leaves (increased from 10)
        for (int i = 0; i < 15; i++) {
            // Stagger the creation of leaves for more natural appearance
            final int delay = i * 500; // 500ms delay between each leaf
            new android.os.Handler().postDelayed(() -> createFallingLeaf(), delay);
        }

        // Continue creating leaves periodically
        new android.os.Handler().postDelayed(() -> {
            if (isAdded() && getContext() != null) {
                createFallingLeaf();
                // Schedule next leaf creation
                new android.os.Handler().postDelayed(this::startLeafAnimations, 3000);
            }
        }, 15000);
    }

    /**
     * Creates a single animated falling leaf with enhanced movement
     */
    private void createFallingLeaf() {
        if (getContext() == null || leavesContainer == null || !isAdded()) return;

        // Create leaf image
        ImageView leaf = new ImageView(getContext());

        // Randomly choose between different leaf shapes for variety
        int[] leafDrawables = {
            R.drawable.leaf_decoration,
            R.drawable.leaf_decoration_2,
            R.drawable.leaf_decoration_3
        };
        leaf.setImageResource(leafDrawables[random.nextInt(leafDrawables.length)]);

        // Randomize leaf size (between 20dp and 50dp for more variety)
        int sizeDp = random.nextInt(30) + 20;
        int sizePx = (int) (sizeDp * getResources().getDisplayMetrics().density);

        // Set layout parameters
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizePx, sizePx);

        // Get container width (or estimated screen width if not measured yet)
        int containerWidth = leavesContainer.getWidth() > 0 ? 
            leavesContainer.getWidth() : getResources().getDisplayMetrics().widthPixels;

        // Randomize starting horizontal position
        params.leftMargin = random.nextInt(containerWidth - sizePx);
        params.topMargin = -sizePx; // Start above the screen

        leaf.setLayoutParams(params);

        // Randomize opacity for depth effect
        leaf.setAlpha(random.nextFloat() * 0.4f + 0.4f); // Between 0.4 and 0.8

        // Random initial rotation
        leaf.setRotation(random.nextInt(360));

        // Add to container
        leavesContainer.addView(leaf);

        // Create falling animation with enhanced properties
        int duration = random.nextInt(8000) + 12000; // 12-20 seconds for smoother fall
        ValueAnimator fallAnimator = ValueAnimator.ofFloat(0f, 1f);
        fallAnimator.setDuration(duration);
        fallAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // Record starting horizontal position
        final float startX = params.leftMargin;

        // Generate random wind parameters for natural swaying
        final float windStrength = random.nextFloat() * 200 + 100; // 100-300px max deviation
        final float windFrequency = random.nextFloat() * 3 + 2; // 2-5 oscillations
        final float windPhase = random.nextFloat() * (float)Math.PI * 2; // Random phase

        // Generate random spin parameters
        final float spinSpeed = random.nextFloat() * 720 - 360; // -360 to +360 degrees

        fallAnimator.addUpdateListener(animation -> {
            if (getContext() == null || !isAdded()) return;

            float value = (float) animation.getAnimatedValue();
            int screenHeight = leavesContainer.getHeight() > 0 ? 
                leavesContainer.getHeight() : getResources().getDisplayMetrics().heightPixels;

            // Smooth vertical fall: quadratic easing for natural acceleration
            float verticalProgress = value * value;
            params.topMargin = (int) (verticalProgress * (screenHeight + sizePx));

            // Enhanced horizontal movement using two sine waves for natural swaying
            float windEffect = (float) Math.sin(value * windFrequency * Math.PI * 2 + windPhase) * windStrength;
            float breezeEffect = (float) Math.sin(value * (windFrequency * 2.5) * Math.PI * 2) * (windStrength * 0.3f);
            float totalWindEffect = windEffect + breezeEffect;
            params.leftMargin = (int) (startX + totalWindEffect);

            // Rotation: combine constant spin with wind-influenced rotation
            float baseRotation = value * spinSpeed;
            float windRotation = totalWindEffect * 0.2f;
            leaf.setRotation(baseRotation + windRotation);

            leaf.setLayoutParams(params);

            // Remove leaf when animation completes
            if (value >= 0.99f) {
                leavesContainer.removeView(leaf);
                // No need to spawn a new one here as periodic creation is running
            }
        });

        fallAnimator.start();
    }

    /**
     * Creates a subtle grass animation effect on the forest silhouette.
     */
    private void animateGrass() {
        View forestView = getView().findViewById(R.id.forestSilhouette);
        if (forestView == null) return;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(forestView, "scaleX", 1.0f, 1.005f);
        scaleX.setDuration(3000);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator translationY = ObjectAnimator.ofFloat(forestView, "translationY", 0f, -2f);
        translationY.setDuration(2000);
        translationY.setRepeatCount(ObjectAnimator.INFINITE);
        translationY.setRepeatMode(ObjectAnimator.REVERSE);
        translationY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, translationY);
        animatorSet.start();
    }

    /**
     * Sets up a parallax effect based on device tilting.
     */
    private void setupParallaxEffect() {
        final View forestView = getView().findViewById(R.id.forestSilhouette);
        final View logoContainer = getView().findViewById(R.id.logoContainer);
        if (forestView == null || logoContainer == null) return;
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = Math.min(Math.max(event.values[0], -3), 3) / 3;
                float y = Math.min(Math.max(event.values[1], -3), 3) / 3;
                forestView.setTranslationX(-x * 15);
                logoContainer.setTranslationX(-x * 5);
                forestView.setTranslationY(y * 10);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // No action needed
            }
        };
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null && sensorEventListener != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null && sensorEventListener != null) {
            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }
}
