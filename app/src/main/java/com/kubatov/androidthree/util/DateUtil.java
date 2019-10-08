package com.kubatov.androidthree.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.EEE.MM.yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        date = sdf.format(new Date());
        return date;
    }
}
