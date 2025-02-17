package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A fragment representing the start screen containing buttons to navigate to the
 * Login and Signup screens
 */
public class StartFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StartFragment() {
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
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // buttons (retrieve from view)
        Button loginBtn = view.findViewById(R.id.loginBtn);
        Button signupBtn = view.findViewById(R.id.signupBtn);

        // setOnClickListeners
        loginBtn.setOnClickListener(this::onClickLogin);
        signupBtn.setOnClickListener(this::onClickSignup);
        return view;
    }

    /**
     * Navigates to Login screen.
     * @param view
     */
    private void onClickLogin(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_startFragment_to_loginFragment);
    }

    /**
     * Navigates to Signup screen.
     * @param view
     */
    private void onClickSignup(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_startFragment_to_signupFragment);
    }
}