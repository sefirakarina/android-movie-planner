package com.example.movieevent.http;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.movieevent.model.LoadSharedPref;
import com.example.movieevent.model.Model;
import com.example.movieevent.model.ModelImpl;
import com.example.movieevent.model.event.Event;
import com.example.movieevent.services.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HttpURLConnectionAsyncTask extends AsyncTask<Void, Integer, Void>
{
   private static final String LOG_TAG = HttpURLConnectionAsyncTask.class.getName();
   private String URL = null;
   private StringBuilder htmlStringBuilder = new StringBuilder();
   private Model model;
   private ArrayList<String> dismissedEvents;
   private Alarm alarm;
   private Event event;
   private Context context;


   public void setURLAndEvent(String url, Event event, Context context){
      URL = url;
      this.event = event;
      this.context = context;
   }

   public void setAlarm(Alarm alarm){
      this.alarm = alarm;
   }

   public HttpURLConnectionAsyncTask(){}

   protected Void doInBackground(Void... unused)
   {
      HttpURLConnection connection = null;
      try
      {
         connection = (HttpURLConnection) new URL(URL).openConnection();
         connection.setRequestMethod("GET");
         connection.setRequestProperty("Accept", "text/html");
         connection.setReadTimeout(5000);
         connection.setConnectTimeout(5000);

         int statusCode = connection.getResponseCode();
         if (statusCode != HttpURLConnection.HTTP_OK)
         {
            Log.e(LOG_TAG, "Invalid Response Code: " + statusCode);
            return null;
         }

         readData(connection.getContentLength(), new BufferedReader(new InputStreamReader(connection.getInputStream())));
      }
      catch (Exception e)
      {
         Log.e(LOG_TAG, "Exception caught: " + e.getMessage());
         e.printStackTrace();
      }
      finally
      {
         if (connection != null)
            connection.disconnect();
      }

      return null;
   }

   protected void onPostExecute(Void result)
   {
      model = ModelImpl.getSingletonInstance(context);
      dismissedEvents = model.getDismissedEventId();
      boolean flag = true;

      try {
         JSONObject distance = new JSONObject(htmlStringBuilder.toString());
         JSONArray rows = distance.getJSONArray("rows");
         JSONObject elements = rows.getJSONObject(0);
         JSONArray element = elements.getJSONArray("elements");
         JSONObject dur = element.getJSONObject(0);
         JSONObject duration = dur.getJSONObject("duration");
         String time = duration.getString("value");

         Date today = new Date();

         LoadSharedPref loadSharedPref = LoadSharedPref.getSingletonInstance(context);

         if(event.getStartDate().after(today) && flag){
            Calendar calToday = Calendar.getInstance();
            calToday.setTime(today);
            calToday.add(Calendar.SECOND, Integer.parseInt(time));
            calToday.add(Calendar.MINUTE, loadSharedPref.getNotifThreshold());

            Calendar calEvent = Calendar.getInstance();
            calEvent.setTime(event.getStartDate());

            if(calToday.after(calEvent) || calToday.equals(calEvent)){

               alarm.setTimeAndEventId(time, event.getId());
               alarm.setCheckOrRemind(1);
               alarm.startAlarm();
            }
            else {
               return;
            }
         }


      } catch (JSONException e) {
         e.printStackTrace();
      }

   }

   protected void readData(int length, BufferedReader br) throws Exception
   {
      if (length == -1)
         length = 50000;

      String line;

      while ((line = br.readLine()) != null)
      {
         htmlStringBuilder.append(line);
      }
   }
}
