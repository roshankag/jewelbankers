package com.jewelbankers.Utility;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatterUtil {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    public static String formatTo12Hour(LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : null;
    }
}
