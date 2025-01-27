package edu.sjsu.sase.android.roots;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.common.SignInButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 9001;  // Request code for Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate the layout for fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        //find  sign-in button and set up click listener
        SignInButton signInButton = rootView.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(view -> signIn());

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

    }

    private void handleSignInFailure(ApiException e) {
        int statusCode = e.getStatusCode();
        Toast.makeText(getActivity(), "Sign-in failed: " + statusCode, Toast.LENGTH_SHORT).show();
        Log.e("LoginFragment", "Sign-in failed: " + e.getMessage() + " Status Code: " + statusCode);
    }
}
