package com.example.rickandmorty.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private TimeUtils() {}
    public static LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        return LocalDateTime.parse(dateTime, format);
    }
}
