package edu.wj.sport.service.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {



    public static String formatDate(long duration){

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return format.format(duration - TimeZone.getDefault().getRawOffset());
    }

    public static long formatMin(long duration){

        long second = duration / 1000;
        long minute = second / 60;
        return minute;
    }

    public static String formatNumber(double value){
        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        return decimal.toString();
    }

    public static String formatNumber(String value){
        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        return decimal.toString();
    }



    public static int getWeekOfDate(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int w = calendar.get(Calendar.DAY_OF_WEEK) -1;
        return w;
    }


    public static int getWeekNumber(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int w = calendar.get(Calendar.WEEK_OF_YEAR);
        return w;
    }

}