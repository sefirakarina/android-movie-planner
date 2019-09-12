package com.example.movieevent.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieevent.R;
import com.example.movieevent.controller.DeleteEventListener;
import com.example.movieevent.controller.EditEventListener;
import com.example.movieevent.model.event.Event;

import java.util.ArrayList;


public class EventAdapter extends RecyclerView.Adapter <EventAdapter.ViewHolder>{

    private ArrayList<Event> events;
    private Context context;


    public EventAdapter(Context context, ArrayList<Event> eventList){
        events = eventList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvEventTitle, tvStartDate, tvVenue, tvEndDate, tvLocation, tvMovie,
                tvAttendees;

        //item view refer to list_events layout
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvVenue = itemView.findViewById(R.id.tvVenue);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvMovie = itemView.findViewById(R.id.movie_title);
            tvAttendees = itemView.findViewById(R.id.total_attendees);

        }

    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_events, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int i) {

        Event event = events.get(i);

        if(event != null){
            viewHolder.itemView.setTag(event);

            viewHolder.tvEventTitle.setText(event.getTitle());
            viewHolder.tvStartDate.setText(event.getStartDate().toString());
            viewHolder.tvEndDate.setText(event.getEndDate().toString());
            viewHolder.tvVenue.setText(event.getVenue());
            viewHolder.tvLocation.setText(event.getLocation());

            if(event.getMovie() != null){
                viewHolder.tvMovie.setText(event.getMovie().getTitle());
            }

            if(event.getTotalAttendees() > 0){
                viewHolder.tvAttendees.setText("Attendees: "+event.getTotalAttendees());
            } else {
                viewHolder.tvAttendees.setText("No Attendee");
            }

            viewHolder.itemView.setOnClickListener(new EditEventListener(context, event.getId()));
            viewHolder.itemView.setOnLongClickListener(new DeleteEventListener(context, event.getId(),this));
        }
        else {
        }

    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
