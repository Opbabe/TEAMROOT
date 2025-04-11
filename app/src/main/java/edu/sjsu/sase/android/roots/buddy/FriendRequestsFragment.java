package edu.sjsu.sase.android.roots.buddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sase.android.roots.R;

public class FriendRequestsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FriendRequestAdapter adapter;
    private List<DocumentSnapshot> requestList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String currentUserId = FirebaseAuth.getInstance().getUid();

    public FriendRequestsFragment() {
        //required construct
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_requests, container, false);

        ImageView backArrow = view.findViewById(R.id.backArrowBtn);
        backArrow.setOnClickListener(this::onClickBackArrow);

        recyclerView = view.findViewById(R.id.friend_requests_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FriendRequestAdapter(requestList, new FriendRequestAdapter.OnRequestActionListener() {
            @Override
            public void onAccept(DocumentSnapshot request) {
                handleAccept(request);
            } //need sandra or nick to test this

            @Override
            public void onDecline(DocumentSnapshot request) {
                db.collection("friendRequests").document(request.getId()).delete();
                loadFriendRequests(); // refresh reqs after decline
            }
        });

        recyclerView.setAdapter(adapter);
        loadFriendRequests();

        return view;
    }

    private void onClickBackArrow(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_friendRequestsFragment_to_buddyListFragment);
    }

    private void loadFriendRequests() {
        db.collection("friendRequests")
                .whereEqualTo("to", currentUserId)
                .get()
                .addOnSuccessListener(query -> {
                    requestList.clear();
                    requestList.addAll(query.getDocuments());
                    adapter.notifyDataSetChanged();
                });
    }

    private void handleAccept(DocumentSnapshot request) {
        String fromUserId = request.getString("from");

        WriteBatch batch = db.batch();
        DocumentReference currentUserRef = db.collection("users").document(currentUserId);
        DocumentReference fromUserRef = db.collection("users").document(fromUserId);

        batch.update(currentUserRef, "friends", FieldValue.arrayUnion(fromUserId));
        batch.update(fromUserRef, "friends", FieldValue.arrayUnion(currentUserId));
        batch.delete(db.collection("friendRequests").document(request.getId()));

        batch.commit().addOnSuccessListener(unused -> {
            Toast.makeText(getContext(), "Friend request accepted!", Toast.LENGTH_SHORT).show();
            loadFriendRequests();
        });
    }
}

