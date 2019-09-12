package com.example.movieevent.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.movieevent.adapter.EventAdapter;
import com.example.movieevent.model.event.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortEventListener implements MenuItem.OnMenuItemClickListener {

    private Context context;
    private RecyclerView.Adapter myAdapter;
    private ArrayList<Event> events;
    private RecyclerView recyclerView;
    boolean makeAscending = false;

    public SortEventListener(Context context, RecyclerView.Adapter myAdapter, ArrayList<Event> events, RecyclerView recyclerView) {
        this.context = context;
        this.myAdapter = myAdapter;
        this.events = events;
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        // if the current order is not ascending, make it ascending
        if(!makeAscending){
            Collections.sort(events, new SortDate());
            myAdapter = new EventAdapter(context, events);
            recyclerView.setAdapter(myAdapter);

            makeAscending = true;
        }
        //if the current order is ascending, sort it backwards
        else{
            Collections.sort(events, new SortDate(
                    SortDate.REVERSE_ORDER));
            myAdapter = new EventAdapter(context, events);
            recyclerView.setAdapter(myAdapter);

            makeAscending = false;
        }
        return true;
    }

    public static class SortDate implements Comparator<Event> {

        public static final int REVERSE_ORDER = 1;
        private int order;

        public SortDate(int order) {
            this.order = order;
        }

        public SortDate() {
            this(0);
        }

        @Override
        public int compare(Event event1, Event event2) {
            if(order == REVERSE_ORDER) {
                return event2.getStartDate().compareTo(event1.getStartDate());
            } else {
                return event1.getStartDate().compareTo(event2.getStartDate());
            }
        }
    }
}
