package com.kubatov.androidthree.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(String s) {
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
        String formatted = sdf.format(date1);
        return formatted;
    }
}
