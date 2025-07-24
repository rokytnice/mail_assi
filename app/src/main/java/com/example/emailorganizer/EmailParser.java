package com.example.emailorganizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailParser {

    // Simple regex to find dates in format YYYY-MM-DD and times in format HH:MM
    private static final Pattern DATE_PATTERN = Pattern.compile("\\b\\d{4}-\\d{2}-\\d{2}\\b");
    private static final Pattern TIME_PATTERN = Pattern.compile("\\b\\d{2}:\\d{2}\\b");

    // Simple regex to find action items starting with "TODO:" or "Action:"
    private static final Pattern ACTION_ITEM_PATTERN = Pattern.compile("\\b(TODO|Action):\\s*(.*)");

    public static String findDate(String text) {
        Matcher matcher = DATE_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static String findTime(String text) {
        Matcher matcher = TIME_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static String findActionItem(String text) {
        Matcher matcher = ACTION_ITEM_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }
}
