package edu.sjsu.sase.android.roots.buddy.lists;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.databinding.FragmentMatchBinding;

import java.util.List;

/**
 * Creates view for each Match in Matches List of the Buddies List screen
 */
public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {
    // TODO: Create User class and convert mValues into a List<User>
    private final List<String> mValues;

    public MatchRecyclerViewAdapter(List<String> items) {
        mValues = items;
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
        return new ViewHolder(FragmentMatchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        String current = mValues.get(position);
        holder.binding.name.setText(current);
    }

    /**
     * Returns number of data
     * @return the size of the ArrayList
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * A Wrapper around a View that contains the layout (UI) for each row in the list
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // bind with fragment_match.xml
        protected final FragmentMatchBinding binding;

        public ViewHolder(FragmentMatchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // The root represents one row
            // When the row is clicked, navigate to match's user profile
            this.binding.getRoot().setOnClickListener(view -> {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_buddyListFragment_to_userProfileFragment);
            });
        }
    }
}