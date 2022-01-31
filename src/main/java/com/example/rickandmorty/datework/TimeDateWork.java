package com.example.rickandmorty.datework;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDateWork {
    private TimeDateWork() {}
    public static LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        return LocalDateTime.parse(dateTime, format);
    }
}
