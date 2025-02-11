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
 * A simple {@link Fragment} subclass.
 * Use the {@link EventCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventCreationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventCreationFragment newInstance(String param1, String param2) {
        EventCreationFragment fragment = new EventCreationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);
        Button profileBttn = view.findViewById(R.id.profileBttn2);
        Button buddyBttn = view.findViewById(R.id.buddyBttn2);
        Button homeButtn = view.findViewById(R.id.homeButtn2);
        Button loginBttn = view.findViewById(R.id.loginBttn);

        profileBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(view);
            }
        });

        buddyBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBuddySystem(view);
            }
        });

        homeButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });

        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin(view);
            }
        });

        Button postEvent = view.findViewById(R.id.postBttn);

        postEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSingleEvent(view);
            }
        });

        return view;
    }
//    TODO add nav from a "Post Event Button" to a single event page
//    private goToSingleEvent(View view){
//        NavController controller = Navigation.findNavController(view);
//        controller.navigate(R.id.ac);
//
//    }

    private void goToProfile(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_userProfileFragment);
    }

    private void goToLogin(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_loginFragment);
    }

    private void goToBuddySystem(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_buddySystemFragment);

    }

    private void goHome(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_homeFragment);

    }

    private void goToSingleEvent(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_singleEventFragment);
    }
}