package com.example.movieevent.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieevent.R;
import com.example.movieevent.model.attendee.Attendee;

import java.util.ArrayList;

public class AttendeeAdapter extends RecyclerView.Adapter <AttendeeAdapter.ViewHolder> {

    private ArrayList<Attendee> attendees;

    public AttendeeAdapter(ArrayList<Attendee> attendees){
        this.attendees = attendees;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView attendeeName, attendeeNumber;
        public ViewHolder(@NonNull View view){
            super(view);

            attendeeName = view.findViewById(R.id.attendee_name);
            attendeeNumber = view.findViewById(R.id.attendee_number);
        }
    }

    @NonNull
    @Override
    public AttendeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contact, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeeAdapter.ViewHolder viewHolder, int i) {
        Attendee attendee = attendees.get(i);

        if(attendee != null){

            viewHolder.attendeeName.setText(attendee.getName());
            viewHolder.attendeeNumber.setText(attendee.getNumber());
        }
    }

    @Override
    public int getItemCount() {
        return attendees.size();
    }
}
