package com.example.movieevent.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;

import java.util.Map;

public class CancelDialog extends AppCompatActivity  implements
        DialogInterface.OnCancelListener, DialogInterface.OnDismissListener{


    Intent intent = getIntent();
    Model model = ModelImpl.getSingletonInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        model = ModelImpl.getSingletonInstance(this);
        final Map<String, Integer> notifications = model.getNotifId();
        final String eventId = intent.getStringExtra("eventId");
        Event event = model.getEventById(eventId);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(event.getTitle())
                .setMessage("Are you sure you want to cancel this event?")
                .setOnCancelListener(this)
                .setOnDismissListener(this)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.deleteEvent(intent.getStringExtra("eventId"));
                        notifications.remove(eventId);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        dialog.show();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if(!CancelDialog.this.isFinishing()){
            finish();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(!CancelDialog.this.isFinishing()){
            finish();
        }
    }

}