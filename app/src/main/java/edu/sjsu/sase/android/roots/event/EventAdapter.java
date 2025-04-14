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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventName.setText(event.getName());
        holder.hostName.setText(event.getHostName());
        holder.eventTags.setText(event.getTags());
        holder.eventImage.setImageResource(event.getImageResourceId());

        // For eventTime, if you need to combine date and time, adjust as appropriate.
        // Here we assume getEventDateStart() and getEventTimeStart() are available.
        String timeDisplay = "";
        if (event.getEventDateStart() != null && event.getEventTimeStart() != null) {
            timeDisplay = event.getEventDateStart() + " - " + event.getEventTimeStart();
        } else if (event.getEventDateStart() != null) {
            timeDisplay = event.getEventDateStart();
        } else if (event.getEventTimeStart() != null) {
            timeDisplay = event.getEventTimeStart();
        }
        holder.eventTime.setText(timeDisplay);

        // Set click listener for the whole card.
        holder.eventCard.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventClick(position);
            }
        });
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

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventCard = itemView.findViewById(R.id.eventCard);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventTime = itemView.findViewById(R.id.eventTime);
            hostName = itemView.findViewById(R.id.hostName);
            eventTags = itemView.findViewById(R.id.eventTags);
        }
    }
}
