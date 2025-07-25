package com.example.mailassi

import android.content.Context

/**
 * Coordinates fetching emails, analyzing them, and creating events or tasks.
 */
class EmailAnalyzer(private val context: Context) {
    private val gmail = GmailHelper(context)
    private val ai = AiHelper(context)
    private val calendar = CalendarHelper(context)
    private val tasks = TasksHelper(context)
    private val notifications = NotificationHelper(context)

    fun process() {
        val emails = gmail.fetchLatestEmails()
        for (email in emails) {
            val result = ai.analyzeEmail(email)
            val notes = email.body
            result.title?.let { title ->
                if (result.isEvent) {
                    calendar.createEvent(title, notes, result.time)
                }
                if (result.isTask) {
                    tasks.createTask(title, notes)
                    notifications.notifyTask(title)
                }
            }
        }
    }
}
