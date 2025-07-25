package com.example.mailassi

import android.content.Context

/**
 * This class represents a stub for the AI model that analyzes an email and
 * determines if it contains an event or a task.
 */
class AiHelper(private val context: Context) {
    data class AnalysisResult(
        val isEvent: Boolean,
        val isTask: Boolean,
        val title: String?,
        val time: Long?
    )

    fun analyzeEmail(email: GmailHelper.Email): AnalysisResult {
        // TODO: Replace with actual AI/LLM call (e.g., Google Gemini)
        return AnalysisResult(isEvent = false, isTask = false, title = null, time = null)
    }
}
