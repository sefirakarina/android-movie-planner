package com.example.movieevent.model.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.view.CalendarActivity;
import com.example.movieevent.view.MainActivity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class EventViewModel extends AndroidViewModel implements PropertyChangeListener {

    private MutableLiveData<ArrayList<Event>> eventsLiveData;
    private Model model;

    public EventViewModel(Application application) {
        super(application);
        model = ModelImpl.getSingletonInstance(getApplication());
        model.addPropertyChangeListener(EventViewModel.this);

    }

    public MutableLiveData<ArrayList<Event>> getEvents(Activity activity) {
        if(eventsLiveData == null) {
            eventsLiveData = new MutableLiveData<>();
        }

        Model model = ModelImpl.getSingletonInstance(getApplication());

        if(activity.getClass() == MainActivity.class){
            eventsLiveData.setValue(model.getAllEvents());
        }

        else if(activity.getClass() == CalendarActivity.class){
            eventsLiveData.setValue(model.getEventOnDate());
        }

        return eventsLiveData;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("remove event")) {
            eventsLiveData.setValue(model.getAllEvents());
        }
        if (evt.getPropertyName().equals("changing event")) {
            eventsLiveData.setValue(model.getAllEvents());
        }
    }
}
