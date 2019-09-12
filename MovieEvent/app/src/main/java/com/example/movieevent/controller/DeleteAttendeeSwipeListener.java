package com.example.movieevent.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.movieevent.model.Model;

public class DeleteAttendeeSwipeListener extends ItemTouchHelper.SimpleCallback {

    private RecyclerView.Adapter myAdapter;
    private Model model;

    public DeleteAttendeeSwipeListener(RecyclerView.Adapter myAdapter, Model model){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.myAdapter = myAdapter;
        this.model = model;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        System.out.println(viewHolder.getAdapterPosition());
        model.deleteAttendee(viewHolder.getAdapterPosition());
        myAdapter.notifyDataSetChanged();
    }
}