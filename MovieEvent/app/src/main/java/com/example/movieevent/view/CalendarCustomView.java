package com.example.movieevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieevent.R;
import com.example.movieevent.adapter.CalendarAdapter;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarCustomView extends LinearLayout {

    GridView calendarGrid;
    TextView tvMonth;
    ImageView btnPrev, btnNext;
    Calendar today ;
    Model model;

    public CalendarCustomView(Context context)
    {
        super(context);
        model = ModelImpl.getSingletonInstance(context);
        loadComponents(context);
    }

    public CalendarCustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        model = ModelImpl.getSingletonInstance(context);
        loadComponents(context);
    }

    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        model = ModelImpl.getSingletonInstance(context);
        loadComponents(context);
    }


    private void loadComponents(Context context){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        today = Calendar.getInstance();

        inflater.inflate(R.layout.activity_calendar_custom_view, this);

        calendarGrid = findViewById(R.id.calendarGrid);
        tvMonth = findViewById(R.id.tvMonth);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        // refresh calendar whens user click prev/next btn
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                model.clearEventOnDate();
                today.add(Calendar.MONTH, 1);
                refreshCalendar();
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                model.clearEventOnDate();
                today.add(Calendar.MONTH, -1);
                refreshCalendar();
            }
        });

    }

    public void refreshCalendar() {

        ArrayList<Calendar> cells = new ArrayList<>();
        Calendar calendar = (Calendar)today.clone();

        int cellMonth = today.get(Calendar.MONTH);
        int cellYear = today.get(Calendar.YEAR);

        // position of in which cell the first day of week will be placed
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthStartingCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.add(Calendar.DAY_OF_MONTH, -monthStartingCell);

        // fill the cells
        while (cells.size() < 35)
        {
            cells.add((Calendar)calendar.clone());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        calendarGrid.setAdapter(new CalendarAdapter(getContext(), cells, cellMonth, cellYear));

        // update the month and year header
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        tvMonth.setText(simpleDateFormat.format(today.getTime()));

    }



}
