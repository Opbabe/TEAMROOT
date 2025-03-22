package edu.sjsu.sase.android.roots;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;
    private int layoutResId;

    public EventAdapter(List<Event> events, int layoutResId) {
        this.events = events;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView locationTextView;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvEventName);
            dateTextView = itemView.findViewById(R.id.tvEventDate);
            locationTextView = itemView.findViewById(R.id.tvEventLocation);
        }

        void bind(Event event) {
            titleTextView.setText(event.getName());
            dateTextView.setText(event.getDate());
            locationTextView.setText(event.getLocation());
        }
    }
} 