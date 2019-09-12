package com.example.movieevent.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.example.movieevent.model.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarClickListener implements AdapterView.OnItemClickListener {

    private Model model;
    private RecyclerView.Adapter myAdapter;

    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date;

    public CalendarClickListener(Model model, RecyclerView.Adapter myAdapter){

        this.model = model;
        this.myAdapter = myAdapter;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Calendar cal = (Calendar) parent.getItemAtPosition(position);
        cal.get(Calendar.DATE);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);

        String rawDate = cal.get(Calendar.DAY_OF_MONTH) + "/" + month +
                "/" + cal.get(Calendar.YEAR);

        try {
            date = formatter.parse(rawDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        model.setEventOnDate(day,month,year);

        myAdapter.notifyDataSetChanged();
    }
}
