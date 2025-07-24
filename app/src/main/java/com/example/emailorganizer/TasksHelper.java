package com.example.emailorganizer;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;

import java.io.IOException;
import java.util.Collections;

public class TasksHelper {

    private static final String TAG = "TasksHelper";
    private static final String APPLICATION_NAME = "Email Organizer";

    private Tasks mService;

    public TasksHelper(Context context, String accountName) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                context, Collections.singleton(TasksScopes.TASKS));
        credential.setSelectedAccountName(accountName);

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new Tasks.Builder(AndroidHttp.newCompatibleTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void createTask(String title, String notes) {
        try {
            Task task = new Task().setTitle(title).setNotes(notes);
            mService.tasks().insert("@default", task).execute();
            Log.d(TAG, "Task created: " + task.getTitle());
        } catch (IOException e) {
            Log.e(TAG, "Error creating task", e);
        }
    }
}
