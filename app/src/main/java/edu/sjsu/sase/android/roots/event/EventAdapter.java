package edu.sjsu.sase.android.roots.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sase.android.roots.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(int position);
    }

    public EventAdapter(List<Event> eventList, OnEventClickListener listener) {
        this.eventList = (eventList != null) ? eventList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the event card layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent, false);
        return new EventViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // Bind event data and set up the click listener
        Event event = eventList.get(position);
        holder.bind(event, position);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView eventCard;
        ImageView eventImage;
        TextView eventName;
        TextView eventTime;
        TextView hostName;
        TextView eventTags;
        OnEventClickListener listener;

        public EventViewHolder(@NonNull View itemView, OnEventClickListener listener) {
            super(itemView);
            this.listener = listener;
            eventCard = itemView.findViewById(R.id.eventCard);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventTime = itemView.findViewById(R.id.eventTime);
            hostName = itemView.findViewById(R.id.hostName);
            eventTags = itemView.findViewById(R.id.eventTags);
        }

        public void bind(Event event, int position) {
            // Bind the event data to UI elements
            eventName.setText(event.getName());
            hostName.setText(event.getHostName());
            eventTags.setText(event.getTags());
            eventImage.setImageResource(event.getImageResourceId());

            String timeDisplay = "";
            if (event.getEventDateStart() != null && event.getEventTimeStart() != null) {
                timeDisplay = event.getEventDateStart() + " - " + event.getEventTimeStart();
            } else if (event.getEventDateStart() != null) {
                timeDisplay = event.getEventDateStart();
            } else if (event.getEventTimeStart() != null) {
                timeDisplay = event.getEventTimeStart();
            }
            eventTime.setText(timeDisplay);

            // Set click listener for the event card to trigger the callback
            eventCard.setOnClickListener(v -> {
                // Ensure the adapter position is still valid
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onEventClick(position);
                }
            });
        }
    }
}
