package ru.practicum.util;

import java.time.format.DateTimeFormatter;

public class UtilConstant {

    public static final int MIN_EVENT_TIME = 2;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

}
