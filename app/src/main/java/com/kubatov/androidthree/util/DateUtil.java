package com.kubatov.androidthree.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public static String convertUnixToHour(String s) {
        SimpleDateFormat inputFormmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.getDefault());
        SimpleDateFormat outputFormmat = new SimpleDateFormat("HH:mm:SS", Locale.getDefault());

        Date date = null;
        try {
            date = inputFormmat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormmat.format(date);
    }
}