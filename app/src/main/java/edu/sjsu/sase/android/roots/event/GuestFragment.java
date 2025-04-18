package edu.sjsu.sase.android.roots.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;


/**
 * A fragment representing a list of guests.
 */
public class GuestFragment extends Fragment {
    ArrayList<User> guestList = new ArrayList<>();
    GuestRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GuestFragment() {
    }

    /**
     * Starting point for the fragment.
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
     * @return view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_list, container, false);

        // set data
        fetchGuests();

        // pass data as argument to construct adapter
        adapter = new GuestRecyclerViewAdapter(guestList);

        // cast view to RecyclerView and set adapter for RecyclerView
        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);


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

    /**
     * Set which screen the guest fragment is on.
     * @param state true is single event screen, otherwise false
     */
    public void setIsOnSingleEvent(boolean state) {
        adapter.setIsOnSingleEvent(state);

    }
}