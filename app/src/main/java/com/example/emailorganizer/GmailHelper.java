package com.example.emailorganizer;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GmailHelper {

    private static final String TAG = "GmailHelper";
    private static final String APPLICATION_NAME = "Email Organizer";

    private Gmail mService;

    public GmailHelper(Context context, String accountName) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                context, Collections.singleton(GmailScopes.GMAIL_READONLY));
        credential.setSelectedAccountName(accountName);

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new Gmail.Builder(AndroidHttp.newCompatibleTransport(), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Message> getRecentEmails() {
        try {
            ListMessagesResponse response = mService.users().messages().list("me").setMaxResults(10L).execute();
            return response.getMessages();
        } catch (IOException e) {
            Log.e(TAG, "Error fetching emails", e);
            return null;
        }
    }

    public Message getMessage(String messageId) {
        try {
            return mService.users().messages().get("me", messageId).execute();
        } catch (IOException e) {
            Log.e(TAG, "Error fetching message", e);
            return null;
        }
    }
}
