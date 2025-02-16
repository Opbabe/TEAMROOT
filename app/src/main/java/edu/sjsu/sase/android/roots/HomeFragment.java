package edu.sjsu.sase.android.roots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button profileBttn = view.findViewById(R.id.profileBttn);
        Button buddyBttn = view.findViewById(R.id.buddyBttn);
        Button homeButtn = view.findViewById(R.id.homeButtn);
        Button loginBttn = view.findViewById(R.id.loginBttn);
        Button createEventBttn = view.findViewById(R.id.createBttn);
        ImageButton listingBttn1 = view.findViewById(R.id.eventListing1);
        ImageButton listingBttn2 = view.findViewById(R.id.eventListing2);
        ImageButton listingBttn3 = view.findViewById(R.id.eventListing3);
        ImageButton myEventsBttn = view.findViewById(R.id.myEventCard);

        profileBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotToUserProfile(view);
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

        createEventBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventCreation(view);
            }
        });

        listingBttn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventListing(view);
            }
        });

        listingBttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventListing(view);
            }
        });

        listingBttn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventListing(view);
            }
        });

        myEventsBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSingleEvent(view);
            }
        });

        return view;
    }



    private void gotToUserProfile(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_userProfileFragment);
    }

    private void goToBuddySystem(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_buddySystemFragment);
    }

    private void goHome(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_self);
    }

    private void goToLogin(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_loginFragment);
    }

    private void goToEventListing(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_eventListingFragment);
    }
    private void goToEventCreation(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_eventCreationFragment);
    }

    private void goToSingleEvent(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_homeFragment_to_singleEventFragment);
    }


}