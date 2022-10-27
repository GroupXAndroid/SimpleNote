package com.groupx.simplenote.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String ColorIntToString(int colorInt) {
        return "#" + Integer.toHexString(colorInt);
    }

    public static String DateTimeToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM HH:mm", Locale.getDefault());
        return formatter.format(date);
    }
}
