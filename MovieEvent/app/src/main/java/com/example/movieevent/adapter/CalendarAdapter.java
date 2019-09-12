package com.example.movieevent.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieevent.R;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter extends ArrayAdapter<Calendar> {

    private LayoutInflater inflater;
    private Model model;
    private int cellMonth;
    private int cellYear;

    public CalendarAdapter(Context context, ArrayList<Calendar> days, int cellMonth, int cellYear) {

        super(context, R.layout.calendar_cells, days);
        this.inflater = LayoutInflater.from(context);
        model = ModelImpl.getSingletonInstance(context);
        this.cellMonth = cellMonth;
        this.cellYear = cellYear;
    }

    @Override
    public View getView(int position,  View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.calendar_cells, parent, false);

        Calendar currentCell = getItem(position);

        SimpleDateFormat year1 = new SimpleDateFormat("dd");
        SimpleDateFormat year2 = new SimpleDateFormat("MM");
        SimpleDateFormat year3 = new SimpleDateFormat("yyyy");

        TextView tvCalendarCells = (TextView) view.findViewById(R.id.tvCalendarCells);
        ImageView imgAlert = (ImageView) view.findViewById(R.id.imgAlert);

        // set today's date
        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        if (view == null)
            view = inflater.inflate(R.layout.calendar_cells, parent, false);

        //show alert for days with event(s)
        for(Date event : model.eventDays()){
            int dd = Integer.parseInt(year1.format(event));
            int mm = Integer.parseInt(year2.format(event));
            int yyyy = Integer.parseInt(year3.format(event));

            if(dd == currentCell.get(Calendar.DATE) && mm == currentCell.get(Calendar.MONTH)+1
                    && yyyy == currentCell.get(Calendar.YEAR)){
                imgAlert.setVisibility(View.VISIBLE);
            }

        }

        //bold the current date
        if(calendarToday.get(Calendar.DATE) == currentCell.get(Calendar.DATE)
                && calendarToday.get(Calendar.MONTH) == currentCell.get(Calendar.MONTH)
            && calendarToday.get(Calendar.YEAR) == currentCell.get(Calendar.YEAR)){

            tvCalendarCells.setTypeface(tvCalendarCells.getTypeface(), Typeface.BOLD);
        }

        //only fill the cells for dates in the current month
        if(currentCell.get(Calendar.MONTH)== cellMonth && currentCell.get(Calendar.YEAR)== cellYear){
            tvCalendarCells.setText(String.valueOf(currentCell.get(Calendar.DATE)));
        }

        return view;
    }


}
