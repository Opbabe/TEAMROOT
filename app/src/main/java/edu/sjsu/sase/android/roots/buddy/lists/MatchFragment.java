package edu.sjsu.sase.android.roots.buddy.lists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.R;

/**
 * A fragment representing a list of matches.
 */
public class MatchFragment extends Fragment {
    ArrayList<String> data = new ArrayList<>();
    MatchRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_match_list, container, false);

        // placeholder hardcoded data
        for (int i = 1; i <= 25; i++) {
            data.add("Match " + i);
        }
        // pass data as argument to construct adapter
        adapter = new MatchRecyclerViewAdapter(data);
        // cast view to RecyclerView and set adapter for RecyclerView
        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);
        return view;
    }
}