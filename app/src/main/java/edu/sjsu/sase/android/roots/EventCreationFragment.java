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
 */
public class EventCreationFragment extends Fragment {

    // TODO: Rename parameter arguments if needed
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters
    private String mParam1;
    private String mParam2;

    public EventCreationFragment() {
        // Required empty public constructor
    }

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
        // Inflate the new layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_creation, container, false);

        // Find the new Post Event button and attach a click listener
        Button btnPostEvent = view.findViewById(R.id.btnPostEvent);
        btnPostEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSingleEvent(view);
            }
        });

        return view;
    }

    private void goToSingleEvent(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_eventCreationFragment_to_singleEventFragment);
    }
}

