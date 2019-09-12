package com.example.movieevent.controller;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class DatePickerListener implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private String requestCode;

    public DatePickerListener(FragmentManager fragmentManager, String requestCode) {
        this.fragmentManager = fragmentManager;
        this.requestCode = requestCode;
    }

    @Override
    public void onClick(View v) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(fragmentManager, requestCode);
    }
}
