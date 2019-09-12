package com.example.movieevent.model.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.attendee.Attendee;

import java.util.ArrayList;

public class AttendeeViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Attendee>> attendeeLiveData;

    public AttendeeViewModel(Application application){
        super(application);
    }

    public MutableLiveData<ArrayList<Attendee>> getAttendees(String eventId) {

        if(attendeeLiveData == null){
            attendeeLiveData = new MutableLiveData<>();
            Model model = ModelImpl.getSingletonInstance(getApplication());
            attendeeLiveData.setValue(model.getAllAttendee(eventId));
        }

        return attendeeLiveData;
    }
}