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

/**
 * A fragment representing the Login screen
 */
public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 9001;  // Request code for Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private Button loginBtn;

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
        //inflate the layout for fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // back button
        ImageView backArrowBtn = rootView.findViewById(R.id.backArrowBtn);
        backArrowBtn.setOnClickListener(this::onClickBackArrow);

        //find  sign-in button and set up click listener
        SignInButton signInButton = rootView.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> signIn());

        // login button
        loginBtn = rootView.findViewById(R.id.loginBtn);
        loginBtn.setEnabled(false);
        loginBtn.setOnClickListener(this::onClickLogin);

        return rootView;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //result returned from launching the sign-in
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //success
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleSignInSuccess(account);
            } catch (ApiException e) {
                //failure err
                handleSignInFailure(e);
            }
        }
    }

    private void handleSignInSuccess(GoogleSignInAccount account) {
        // TO DO LATER: send info to firebase or make the manual password/email option, wip
        String displayName = account.getDisplayName();
        String email = account.getEmail();
        Toast.makeText(getActivity(), "Signed in as: " + displayName, Toast.LENGTH_SHORT).show();
        loginBtn.setEnabled(true);
    }

    private void handleSignInFailure(ApiException e) {
        int statusCode = e.getStatusCode();
        Toast.makeText(getActivity(), "Sign-in failed: " + statusCode, Toast.LENGTH_SHORT).show();
        Log.e("LoginFragment", "Sign-in failed: " + e.getMessage() + " Status Code: " + statusCode);
    }

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
}
