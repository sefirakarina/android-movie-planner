package com.example.movieevent.controller;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;

public class AddAttendeeListener implements View.OnClickListener {
    private Activity activity;

    static final int REQUEST_CODE =1;

    public AddAttendeeListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v){
        Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }
}


