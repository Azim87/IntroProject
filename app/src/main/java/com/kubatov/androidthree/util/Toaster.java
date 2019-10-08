package com.kubatov.androidthree.util;

import android.widget.Toast;

import com.kubatov.androidthree.App;

public class Toaster {

    public static void shortMessage(String message) {
        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
    }

    public static void longMessage(String message) {
        Toast.makeText(App.context, message, Toast.LENGTH_LONG).show();
    }
}
