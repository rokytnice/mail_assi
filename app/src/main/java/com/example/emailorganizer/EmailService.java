package com.example.emailorganizer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class EmailService extends Service {

    private static final String TAG = "EmailService";
    private static final long REFRESH_INTERVAL = 5 * 60 * 1000; // 5 minutes

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                fetchEmails();
                mHandler.postDelayed(this, REFRESH_INTERVAL);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.post(mRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void fetchEmails() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            new Thread(() -> {
                GmailHelper gmailHelper = new GmailHelper(EmailService.this, account.getEmail());
                List<com.google.api.services.gmail.model.Message> messages = gmailHelper.getRecentEmails();
                if (messages != null) {
                    for (com.google.api.services.gmail.model.Message message : messages) {
                        com.google.api.services.gmail.model.Message fullMessage = gmailHelper.getMessage(message.getId());
                        if (fullMessage != null) {
                            String snippet = fullMessage.getSnippet();
                            Log.d(TAG, "Email snippet: " + snippet);

                            String date = EmailParser.findDate(snippet);
                            String time = EmailParser.findTime(snippet);
                            String action = EmailParser.findActionItem(snippet);

                            if (date != null && time != null) {
                                CalendarHelper calendarHelper = new CalendarHelper(EmailService.this, account.getEmail());
                                calendarHelper.createEvent("Termin aus E-Mail", snippet, date, time);
                                Log.d(TAG, "Found date: " + date + " and time: " + time);
                            }

                            if (action != null) {
                                TasksHelper tasksHelper = new TasksHelper(EmailService.this, account.getEmail());
                                tasksHelper.createTask(action, snippet);
                                NotificationHelper.showNotification(EmailService.this, "Neue Aufgabe erstellt", action);
                                Log.d(TAG, "Found action: " + action);
                            }
                        }
                    }
                }
            }).start();
        }
    }
}
