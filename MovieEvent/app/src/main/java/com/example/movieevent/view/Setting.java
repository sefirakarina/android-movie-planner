package com.example.movieevent.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieevent.R;
import com.example.movieevent.controller.SaveSettingListener;
import com.example.movieevent.model.LoadSharedPref;

public class Setting extends AppCompatActivity {

    EditText etNotifThreshold, etRemindMe, etNotifPeriod;
    Button btnSave;
    int notifThreshold, remindMe, notifPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LoadSharedPref loadSharedPref = LoadSharedPref.getSingletonInstance(this);

        etNotifPeriod = findViewById(R.id.etNotifPeriod);
        etNotifThreshold = findViewById(R.id.etNotifThreshold);
        etRemindMe = findViewById(R.id.etRemindMe);

        btnSave = findViewById(R.id.btnSave);

        notifThreshold = loadSharedPref.getNotifThreshold();
        notifPeriod = loadSharedPref.getNotifPeriod();
        remindMe = loadSharedPref.getRemindMe();

        etNotifThreshold.setText(Integer.toString(notifThreshold));
        etNotifPeriod.setText(Integer.toString(notifPeriod));
        etRemindMe.setText(Integer.toString(remindMe));

        btnSave.setOnClickListener(new SaveSettingListener(this, etNotifThreshold.getText(), etNotifPeriod.getText(),
                etRemindMe.getText()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
