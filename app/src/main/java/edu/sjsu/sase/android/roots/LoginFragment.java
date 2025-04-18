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

        // Sign-in button setup
        SignInButton signInButton = rootView.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> signIn());

        // Initialize leaves container and start leaf animations
//        leavesContainer = rootView.findViewById(R.idz.leavesContainer);
//        startLeafAnimations();

        return rootView;
    }

//    @Override
//    public void onViewCreated(View view, @javax.annotation.Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        animateGrass();
//        setupParallaxEffect();
//    }

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
