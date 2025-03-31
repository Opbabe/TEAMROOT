package edu.sjsu.sase.android.roots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FeaturedEventsAdapter extends RecyclerView.Adapter<FeaturedEventsAdapter.ViewHolder> {

    private final List<Event> eventList;
    private final Context context;

    public FeaturedEventsAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_featured, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.titleTextView.setText(event.getName());

        // Set the appropriate image based on the event title
        if ("Summer Music Festival".equals(event.getName())) {
            // Make sure the resource name is all lowercase in your project;
            // if the file was originally "nycSummer.png", it must be renamed to "nycsummer.png"
            holder.eventImageView.setImageResource(R.drawable.nycsummer);
        } else if ("Art Gala".equals(event.getName())) {
            holder.eventImageView.setImageResource(R.drawable.nyart);
        } else {
            // Optionally, set a default image for other events
            holder.eventImageView.setImageResource(R.drawable.default_image);
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
//            eventImageView = itemView.findViewById(R.id.ivEventPhoto);
            titleTextView = itemView.findViewById(R.id.tvEventTitle);
        }
    }
} 