package com.example.emailorganizer;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.util.Collections;
import java.util.TimeZone;

public class CalendarHelper {

    private static final String TAG = "CalendarHelper";
    private static final String APPLICATION_NAME = "Email Organizer";

    private Calendar mService;

    public CalendarHelper(Context context, String accountName) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                context, Collections.singleton(CalendarScopes.CALENDAR));
        credential.setSelectedAccountName(accountName);

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new Calendar.Builder(AndroidHttp.newCompatibleTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void createEvent(String summary, String description, String date, String time) {
        try {
            Event event = new Event()
                    .setSummary(summary)
                    .setDescription(description);

            String dateTimeString = date + "T" + time + ":00";
            DateTime startDateTime = new DateTime(dateTimeString);
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());
            event.setStart(start);

            // Set end time to one hour after start time
            long ONE_HOUR_IN_MILLIS = 60 * 60 * 1000;
            DateTime endDateTime = new DateTime(startDateTime.getValue() + ONE_HOUR_IN_MILLIS);
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());
            event.setEnd(end);

            String calendarId = "primary";
            mService.events().insert(calendarId, event).execute();
            Log.d(TAG, "Event created: " + event.getHtmlLink());
        } catch (IOException e) {
            Log.e(TAG, "Error creating event", e);
        }
    }
}
