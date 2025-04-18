package edu.sjsu.sase.android.roots.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.sase.android.roots.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<Event> eventList;
    private final OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(int position);
    }

    public EventAdapter(List<Event> eventList, OnEventClickListener listener) {
        this.eventList = (eventList != null) ? eventList : new ArrayList<>();
        this.listener  = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent, false);
        return new EventViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(eventList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView eventCard;
        private final ImageView        eventImage;
        private final TextView         eventName;
        private final TextView         eventTime;
        private final TextView         hostName;
        private final TextView         eventTags;
        private final OnEventClickListener listener;

        public EventViewHolder(@NonNull View itemView, OnEventClickListener listener) {
            super(itemView);
            this.listener   = listener;
            eventCard       = itemView.findViewById(R.id.eventCard);
            eventImage      = itemView.findViewById(R.id.eventImage);
            eventName       = itemView.findViewById(R.id.eventName);
            eventTime       = itemView.findViewById(R.id.eventTime);
            hostName        = itemView.findViewById(R.id.hostName);
            eventTags       = itemView.findViewById(R.id.eventTags);
        }

        public void bind(Event event, int position) {
            // 1) Text fields
            eventName.setText(event.getName());
            hostName .setText(event.getHostName());
            eventTags.setText(event.getTags());

            // 2) Load image from URL (fallback to placeholder)
            String url = event.getImageUrl();
            if (url != null && !url.isEmpty()) {
                Glide.with(eventImage.getContext())
                        .load(url)
                        .centerCrop()
                        .into(eventImage);
            } else {
                eventImage.setImageDrawable(null);
            }

            // 3) Build time display
            String timeDisplay = "";
            if (event.getEventDateStart() != null && event.getEventTimeStart() != null) {
                timeDisplay = event.getEventDateStart() + " • " + event.getEventTimeStart();
            } else if (event.getEventDateStart() != null) {
                timeDisplay = event.getEventDateStart();
            } else if (event.getEventTimeStart() != null) {
                timeDisplay = event.getEventTimeStart();
            }
            eventTime.setText(timeDisplay);

            // 4) Card click
            eventCard.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onEventClick(pos);
                }
            });
        }
    }
}
