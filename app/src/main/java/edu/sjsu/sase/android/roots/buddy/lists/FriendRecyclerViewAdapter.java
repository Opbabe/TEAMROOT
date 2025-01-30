package edu.sjsu.sase.android.roots.buddy.lists;

import androidx.annotation.IdRes;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.databinding.FragmentFriendBinding;

import java.util.List;

/**
 * Creates view for each Friend in Friends List of the Buddies List screen.
 */
public class FriendRecyclerViewAdapter extends RecyclerView.Adapter<FriendRecyclerViewAdapter.ViewHolder> {
    // TODO: Create User class and convert mValues into a List<User>
    private final List<String> mValues;
    private int navigationId;
    private boolean navigationSet = false;

    public FriendRecyclerViewAdapter(List<String> items) {
        mValues = items;
    }

    /**
     * Creates a new ViewHolder object for a row when needed. Only initializes the ViewHolder, with no data filled in.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return a new ViewHolder for a row
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentFriendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * Displays the view holder's layout (UI) using the corresponding data at the specified position.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set the image as the launcher icon of Android
        holder.binding.profilePic.setImageResource(R.mipmap.ic_launcher);
        // Get the current data from the arraylist based on the position
        String currentName = "Name " + mValues.get(position);
        holder.binding.name.setText(currentName);
        String currentUsername = "username " + mValues.get(position);
        holder.binding.username.setText(currentUsername);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // bind with fragment_friend.xml
        protected final FragmentFriendBinding binding;

        public ViewHolder(FragmentFriendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // The root represents one row
            // When the row is clicked, navigate to the appropriate page
            this.binding.getRoot().setOnClickListener(view -> {
                if (navigationSet == true){
                    NavController controller = Navigation.findNavController(view);
                    controller.navigate(navigationId);
                }
            });
        }
    }

    /**
     * Set the navigation id to the specified value.
     * @param resId an action id or destination id to navigation to
     */
    public void setNavigationId(@IdRes int resId) {
        this.navigationId = resId;
        navigationSet = true;
    }
}