package com.example.simhyobin.noti;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by insec on 2018-12-06.
 */

public class insertCalendar extends AsyncTask<String, Void, Void> {


    GoogleAccountCredential credential;
    insertCalendar(GoogleAccountCredential m){
        credential = m;

    }

    public void SetCredential(GoogleAccountCredential m){
        this.credential = m;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    HttpTransport transport;
    JsonFactory jsonFactory;
    Calendar service;
    @Override
    protected Void doInBackground(String... input){

        transport= AndroidHttp.newCompatibleTransport();
        jsonFactory = JacksonFactory.getDefaultInstance();
        try{
            service = new com.google.api.services.calendar.Calendar.Builder(transport, jsonFactory, credential).setApplicationName("Noti").build();
            Event event = new Event()
                    .setSummary(input[0]+input[2]+input[3]+input[4])
                    .setLocation("Noti")
                    .setDescription(input[1]);

            DateTime startDateTime = new DateTime("2018-05-28T09:00:00-07:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2018-05-28T17:00:00-07:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setEnd(end);

            String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=0"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventAttendee[] attendees = new EventAttendee[] {};
            event.setAttendees(Arrays.asList(attendees));

            EventReminder[] reminderOverrides = new EventReminder[] {};
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);

            String calendarId = "primary";
            event = service.events().insert(calendarId, event).execute();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... voids){

    }

    @Override
    protected void onPostExecute(Void voids){
        super.onPostExecute(voids);
    }
}
