package edu.sjsu.sase.android.roots.buddy;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

public class FriendRequestsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<User> requestList = new ArrayList<>();
    private String currentUserId;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_requests, container, false);

        recyclerView = view.findViewById(R.id.requestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new FriendRequestAdapter(requestList, currentUserId);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        fetchFriendRequests();

        return view;
    }

    private void fetchFriendRequests() {
        db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    requestList.clear();
                    //pull all fruebdreqs
                    List<String> incomingRequests = (List<String>) documentSnapshot.get("incomingRequests");

                    //ccyle thru each req and fetch id of requester
                    if (incomingRequests != null) {
                        for (String requesterId : incomingRequests) {
                            fetchRequesterInfo(requesterId);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // handle error here if you want idk im going to sleep
                });
    }

    private void fetchRequesterInfo(String requesterId) {
        db.collection("users").document(requesterId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User requester = snapshot.toObject(User.class);
                        if (requester != null) {
                            requester.setId(requesterId);
                            requestList.add(requester);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
