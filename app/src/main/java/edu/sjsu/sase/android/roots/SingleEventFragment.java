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
 * Use the {@link SingleEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingleEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleEventFragment newInstance(String param1, String param2) {
        SingleEventFragment fragment = new SingleEventFragment();
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
        View view = inflater.inflate(R.layout.fragment_single_event, container, false);
        Button singleEhome = view.findViewById(R.id.singleEhome);
        Button singleEprofile = view.findViewById(R.id.singleEprofile);
        Button singleEbud = view.findViewById(R.id.singleEbud);
        Button singleElogin = view.findViewById(R.id.singleElogin);

        singleEprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfile(view);
            }
        });

        singleEbud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBuddy(view);
            }
        });

        singleEhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });

        singleElogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin(view);
            }
        });


        return view;

    }

    private void goHome(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_homeFragment);

    }

    private void goProfile(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_userProfileFragment);
    }

    private void goBuddy(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_buddySystemFragment);
    }

    private void goLogin(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_buddySystemFragment);
    }

}