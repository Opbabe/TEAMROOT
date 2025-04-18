package edu.sjsu.sase.android.roots.buddy.lists;

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
 * A fragment representing a list of matches.
 */
public class BlossomsFragment extends Fragment {
    ArrayList<User> usersList = new ArrayList<>();
    ArrayList<User> pendingList;
    BlossomsRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BlossomsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_blossom_list, container, false);

        // pass data as argument to construct adapter
        adapter = new BlossomsRecyclerViewAdapter(usersList);
        // cast view to RecyclerView and set adapter for RecyclerView
        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);

        // set adapter's data to the pending list
        if (pendingList != null) {
            adapter.setUsersList(pendingList);
            pendingList = null;
        }

        return view;
    }

    /**
     * Sets the matches list data to the specified ArrayList of data
     * @param usersList ArrayList of data
     */
    public void setUsersList(ArrayList<User> usersList) {
        if (adapter != null) {
            Log.d("match frag", "match list size: " + usersList.size());
            adapter.setUsersList(usersList);
        }
        else {
            // temporarily store list until adapter is created
            pendingList = usersList;
        }
    }
}