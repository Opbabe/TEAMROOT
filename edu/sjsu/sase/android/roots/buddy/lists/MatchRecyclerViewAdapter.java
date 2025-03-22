package edu.sjsu.sase.android.roots.buddy.lists;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.sjsu.sase.android.roots.databinding.FragmentMatchBinding;

public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {

    // TODO: Define your data source (e.g., List<Match> matches)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentMatchBinding binding = FragmentMatchBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: Bind your data to the view here
    }

    @Override
    public int getItemCount() {
        // TODO: Return your data set size
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentMatchBinding binding;

        public ViewHolder(FragmentMatchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 