package edu.sjsu.sase.android.roots.buddy.lists;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.user.User;
import edu.sjsu.sase.android.roots.databinding.FragmentBlossomBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates view for each Blossom in Blossoms List of the Buddies List screen
 */
public class BlossomsRecyclerViewAdapter extends RecyclerView.Adapter<BlossomsRecyclerViewAdapter.ViewHolder> {
    private List<User> usersList;
    private int navigationId;
    private boolean navigationSet = false;

    public BlossomsRecyclerViewAdapter(ArrayList<User> items) {
        usersList = items;
    }

    /** Creates a new ViewHolder object for a row when needed. Only initializes the ViewHolder, with no data filled in.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position. The whole list.
     * @param viewType The view type of the new View.
     * @return a new ViewHolder for a row
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentBlossomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * Displays the view holder's layout (UI) using the corresponding data at the specified position.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // profile user info
        holder.binding.name.setText(usersList.get(position).getName());
        String picUrl = usersList.get(position).getProfilePicUrl();
        if (picUrl != null && !picUrl.isEmpty()) {
            Picasso.with(holder.binding.name.getContext())
                    .load(usersList.get(position).getProfilePicUrl())
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into( holder.binding.profilePic);
        }
        else {
            holder.binding.profilePic.setImageResource(R.drawable.ic_profile);
        }
    }

    /**
     * Returns number of data
     * @return the size of the ArrayList
     */
    @Override
    public int getItemCount() {
        return usersList.size();
    }

    /**
     * A Wrapper around a View that contains the layout (UI) for each row in the list
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // bind with fragment_match.xml
        protected final FragmentBlossomBinding binding;

        public ViewHolder(FragmentBlossomBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // The root represents one row
            // When the row is clicked, navigate to match's user profile
            this.binding.getRoot().setOnClickListener(view -> {
                int position = getLayoutPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // user clicked on
                    User user = usersList.get(position);
                    // pass user as data to the appropriate page
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(this.binding.name.getContext().getString(R.string.user_argument_key), user);
                    NavController controller = Navigation.findNavController(view);
                    controller.navigate(R.id.action_buddyListFragment_to_userProfileFragment, bundle);
                }
            });
        }
    }

    /**
     * Set the list of users to the specified list and refresh the UI to display the list
     * @param usersList the list of users to display
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setUsersList(ArrayList<User> usersList) {
        Log.d("blossom adapter", "blossoms list size: " + usersList.size());
        this.usersList = usersList;
        notifyDataSetChanged(); // Notify the RecyclerView to refresh the UI
    }
}