package edu.sjsu.sase.android.roots.buddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {
    private List<User> requestList;
    private String currentUserId;

    public FriendRequestAdapter(List<User> requestList, String currentUserId) {
        this.requestList = requestList;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User requester = requestList.get(position);
        holder.usernameText.setText(requester.getUsername());

            //retrieve requester...debugging
        holder.acceptBtn.setOnClickListener(v -> {
            acceptRequest(requester.getId());
        });

        holder.declineBtn.setOnClickListener(v -> {
            declineRequest(requester.getId());
        });
    }

    private void acceptRequest(String requesterId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //accept means add requesters id to ur friends list and adding ur id to the requesters list, NO UNFRIENDING HAHAHAHAA
        db.collection("users").document(currentUserId)
                //update friends field
                .update("friends", FieldValue.arrayUnion(requesterId))
                .addOnSuccessListener(aVoid -> {
                    db.collection("users").document(requesterId)
                            .update("friends", FieldValue.arrayUnion(currentUserId))
                            .addOnSuccessListener(aVoid2 -> {
                                //remove th friend request from both sides
                                db.collection("users").document(currentUserId)
                                        .update("incomingRequests", FieldValue.arrayRemove(requesterId));

                                db.collection("users").document(requesterId)
                                        .update("outgoingRequests", FieldValue.arrayRemove(currentUserId));

                                //remove req from buddylist client side holy that was more annoying than i thoughut
                                removeFromList(requesterId);
                            });
                });
    }



    private void declineRequest(String requesterId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentUserId)
                //update incoming req and outgoing req fields ONLY! since decline
                .update("incomingRequests", FieldValue.arrayRemove(requesterId))
                .addOnSuccessListener(aVoid -> {
                    db.collection("users").document(requesterId)
                            .update("outgoingRequests", FieldValue.arrayRemove(currentUserId))
                            .addOnSuccessListener(aVoid2 -> {
                                //again remove from display client side
                                removeFromList(requesterId);
                            });
                });
    }

    private void removeFromList(String requesterId) {
        for (int i = 0; i < requestList.size(); i++) {
            if (requestList.get(i).getId().equals(requesterId)) {
                requestList.remove(i);
                //i had to call this method to remove from recylcler (client side view)
                notifyItemRemoved(i);
                break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        Button acceptBtn, declineBtn;

        public ViewHolder(View view) {
            super(view);
            usernameText = view.findViewById(R.id.usernameText);
            acceptBtn = view.findViewById(R.id.acceptBtn);
            declineBtn = view.findViewById(R.id.declineBtn);
        }
    }
}

