package edu.sjsu.sase.android.roots.buddy.lists;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.buddy.lists.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of friends.
 */
public class FriendFragment extends Fragment {
    ArrayList<String> data = new ArrayList<>();
    FriendRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendFragment() {
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
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        // pass data as argument to construct adapter
        adapter = new FriendRecyclerViewAdapter(data);
        // cast view to RecyclerView and set adapter for RecyclerView
        recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);
        return view;
    }

    /**
     * Sets the friend list data to the specified ArrayList of data
     * @param data ArrayList of data
     */
    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}