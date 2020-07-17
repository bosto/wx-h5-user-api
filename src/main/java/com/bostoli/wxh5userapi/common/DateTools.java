package com.bostoli.wxh5userapi.common;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DateTools {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date theDayAfterTomorrow() {
        return offSetDate(new Date(), 2);

    }

    public static Date offSetDate(Date date, int dayToOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dayToOffset);
        return calendar.getTime();
    }

    public static Date offSetMin(Date date, int minToOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minToOffset);
        return calendar.getTime();
    }

    public static String millsToDate(long mills) {
        return mills==0? "":dateFormat.format( Date.from(Instant.ofEpochMilli(mills)));
    }

    public static long now() {
        return System.currentTimeMillis();
    }

    public static String long2DateTimeString(long timeMills) {
        return dateTimeFormat.format(new Date(timeMills));
    }

    public static String nowString() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(Date.from(Instant.ofEpochMilli(date.getTime())));
        System.out.println(date);
    }
}
