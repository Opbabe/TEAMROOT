package edu.sjsu.sase.android.roots.buddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.sjsu.sase.android.roots.R;

import edu.sjsu.sase.android.roots.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuddySystemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuddySystemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuddySystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuddySystemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuddySystemFragment newInstance(String param1, String param2) {
        BuddySystemFragment fragment = new BuddySystemFragment();
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
        View view = inflater.inflate(R.layout.fragment_buddy_system, container, false);

        Button bud = view.findViewById(R.id.button);

        bud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToList(view);
            }
        });

        return view;
    }

    private void goToList(View view){
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_buddySystemFragment_to_buddyListFragment);
    }
}