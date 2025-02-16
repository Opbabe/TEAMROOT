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
 * Use the {@link EventListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListingFragment newInstance(String param1, String param2) {
        EventListingFragment fragment = new EventListingFragment();
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
        View view = inflater.inflate(R.layout.fragment_event_listing, container, false);

        Button listingToProfile = view.findViewById(R.id.listingToProfile);
        Button listingToBuddy = view.findViewById(R.id.listingToBuddy);
        Button listingToHome = view.findViewById(R.id.listingToHome);

        listingToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfile(view);
            }
        });

        listingToBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBuddy(view);
            }
        });

        listingToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });

        Button listingToCreate = view.findViewById(R.id.listingToCreate);

        listingToCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goCreate(view);
            }
        });

        ImageButton listingToSingle1 = view.findViewById(R.id.listingToSingle1);
        ImageButton listingToSingle2 = view.findViewById(R.id.listingToSingle2);
        ImageButton listingToSingle3 = view.findViewById(R.id.listingToSingle3);

        listingToSingle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSingle1(view);
            }
        });

        listingToSingle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSingle2(view);
            }
        });

        listingToSingle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSingle3(view);
            }
        });
        return view;
    }

    private void goHome(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_homeFragment);

    }

    private void goProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_userProfileFragment);
    }

    private void goBuddy(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_buddySystemFragment);
    }

    private void goSingle1(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_singleEventFragment);
    }

    private void goSingle2(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_singleEventFragment);
    }

    private void goSingle3(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_singleEventFragment);
    }

    private void goCreate(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventListingFragment_to_eventCreationFragment);

    }

}