package edu.sjsu.sase.android.roots.buddy.lists;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
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
import edu.sjsu.sase.android.roots.databinding.FragmentBudBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates view for each Friend in Friends List of the Buddies List screen.
 */
public class BudRecyclerViewAdapter extends RecyclerView.Adapter<BudRecyclerViewAdapter.ViewHolder> {
    private List<User> usersList;
    private int navigationId;

    public BudRecyclerViewAdapter(ArrayList<User> items) {
        usersList = items;
    }

    /**
     * Creates a new ViewHolder object for a row when needed. Only initializes the ViewHolder, with no data filled in.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a new ViewHolder for a row
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentBudBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * Displays the view holder's layout (UI) using the corresponding data at the specified position.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("friend adapter", "onbindviewholder: " + position);
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

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // bind with fragment_friend.xml
        protected final FragmentBudBinding binding;

        public ViewHolder(FragmentBudBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // The root represents one row
            // When the row is clicked, navigate to the appropriate page
            this.binding.getRoot().setOnClickListener(view -> {
                int position = getLayoutPosition();
                if (navigationId != 0 && position != RecyclerView.NO_POSITION){
                    // user clicked on
                    User user = usersList.get(position);
                    // pass user as data to the appropriate page
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(this.binding.name.getContext().getString(R.string.user_argument_key), user);
                    NavController controller = Navigation.findNavController(view);
                    controller.navigate(navigationId, bundle);
                    navigationId = 0;
                }
            });
        }
    }

    /**
     * Set the navigation id to the specified value.
     * Use this method because multiple pages use the FriendsFragment class,
     *  so they have different resID.
     * @param resId an action id or destination id to navigation to
     */
    public void setNavigationId(@IdRes int resId) {
        this.navigationId = resId;
    }

    /**
     * Set the list of users to the specified list and refresh the UI to display the list
     * @param usersList the list of users to display
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
        Log.d("friend adapter", "friends list size: " + this.usersList.size());
        notifyDataSetChanged(); // Notify the RecyclerView to refresh the UI
    }
}