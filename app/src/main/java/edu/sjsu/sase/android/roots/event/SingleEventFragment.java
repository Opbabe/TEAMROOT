package edu.sjsu.sase.android.roots.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

/**
 * Represents a single event screen
 */
public class SingleEventFragment extends Fragment {

    ArrayList<User> guestList = new ArrayList<>();
    GuestRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    public SingleEventFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_event, container, false);

        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        Button editBtn = view.findViewById(R.id.editBtn);

        backArrow.setOnClickListener(this::goToHome);
        editBtn.setOnClickListener(this::goToEventCreation);

        // set data
        for (int i = 1; i <= 10; i++) {
            guestList.add(new User(i));
        }
        Log.d("single event", "guest list size on create view: " +  guestList.size());

        // pass data as argument to construct adapter
        adapter = new GuestRecyclerViewAdapter(guestList);
        adapter.setIsOnSingleEvent(true);

        // cast view to RecyclerView and set adapter for RecyclerView
        recyclerView = view.findViewById(R.id.guestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setRecyclerViewHeightBasedOnChildren(recyclerView);
            }
        });


        return view;

    }

    /**
     * Retrieve event guests
     */
    private void fetchGuests() {
        guestList.clear();
        // TODO: implement mechanism to keep track of matches and replace hardcoded data
        // guest list: placeholder hardcoded data
        for (int i = 1; i <= 10; i++) {
            guestList.add(new User(i));
        }
        Log.d("event", "guest list size on create view: " +  guestList.size());
    }

    public static void setRecyclerViewHeightBasedOnChildren(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View listItem = recyclerView.getLayoutManager().findViewByPosition(i);
            if (listItem != null) {
                listItem.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.UNSPECIFIED
                );
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight;
        recyclerView.setLayoutParams(params);
    }

    private void goToHome(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_homeFragment);
    }

    private void goToEventCreation(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_singleEventFragment_to_eventCreationFragment);
    }

}