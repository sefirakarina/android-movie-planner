package com.example.movieevent.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.movieevent.R;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

public class DeleteEventListener implements View.OnLongClickListener{

    private String eventId;
    private Context context;
    private Model model;
    private RecyclerView.Adapter myAdapter;

    //Toast toast = Toast.makeText(context, text, duration);

    public DeleteEventListener(Context context, String eventId, RecyclerView.Adapter myAdapter) {
        this.context = context;
        this.eventId = eventId;
        this.myAdapter = myAdapter;
        model = ModelImpl.getSingletonInstance(context);
    }



    @Override
    public boolean onLongClick(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(context.getString(R.string.delete_entry));
        alert.setMessage(context.getString(R.string.delete_message));
        alert.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(model.deleteEvent(eventId)){
                    myAdapter.notifyDataSetChanged();
                }

            }
        });
        alert.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();

        return true;
    }
}
