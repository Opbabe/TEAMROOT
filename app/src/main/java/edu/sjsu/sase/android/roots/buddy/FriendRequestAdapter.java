package edu.sjsu.sase.android.roots.buddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.sjsu.sase.android.roots.R;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.RequestViewHolder> {
    private List<DocumentSnapshot> requests;
    private OnRequestActionListener listener;

    public interface OnRequestActionListener {
        void onAccept(DocumentSnapshot request);
        void onDecline(DocumentSnapshot request);
    }

    public FriendRequestAdapter(List<DocumentSnapshot> requests, OnRequestActionListener listener) {
        this.requests = requests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_request, parent, false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        DocumentSnapshot request = requests.get(position);
        String fromUserId = request.getString("from");

        // this will load username/pass it with the request data, confirm once others tested it
        FirebaseFirestore.getInstance().collection("users")
                .document(fromUserId)
                .get()
                .addOnSuccessListener(userDoc -> {
                    String username = userDoc.getString("username");
                    holder.usernameText.setText(username);
                });

        holder.acceptBtn.setOnClickListener(v -> listener.onAccept(request));
        holder.declineBtn.setOnClickListener(v -> listener.onDecline(request));
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        Button acceptBtn, declineBtn;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.request_username);
            acceptBtn = itemView.findViewById(R.id.btn_accept);
            declineBtn = itemView.findViewById(R.id.btn_decline);
        }
    }
}
