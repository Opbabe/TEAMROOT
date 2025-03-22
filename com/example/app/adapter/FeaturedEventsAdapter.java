package edu.sjsu.sase.android.roots.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.sjsu.sase.android.roots.R;
import edu.sjsu.sase.android.roots.model.Event;

public class FeaturedEventsAdapter extends RecyclerView.Adapter<FeaturedEventsAdapter.ViewHolder> {

    private List<Event> eventList;
    private Context context;

    public FeaturedEventsAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_event_featured, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.titleTextView.setText(event.getTitle());
        if ("Summer Music Festival".equals(event.getTitle())) {
            holder.eventImageView.setImageResource(R.drawable.nycsummer);
        } else if ("Art Gala".equals(event.getTitle())) {
            holder.eventImageView.setImageResource(R.drawable.nyart);
        } else {
            holder.eventImageView.setImageResource(R.drawable.default_event);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImageView;
        TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.ivEventPhoto);
            titleTextView = itemView.findViewById(R.id.tvEventTitle);
        }
    }
} 