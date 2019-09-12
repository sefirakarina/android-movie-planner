package com.example.movieevent.controller;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class TimePickerListener implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private String requestCode;

    public TimePickerListener(FragmentManager fragmentManager, String requestCode) {
        this.fragmentManager = fragmentManager;
        this.requestCode = requestCode;
    }

    @Override
    public void onClick(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(fragmentManager, requestCode);
    }
}
