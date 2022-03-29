package edu.wj.sport.service.utils;

import java.util.Date;

/**
 * 时间格式化
 */
public class RelativeDateFormat {

    static final long ONE_MINUTE = 60000L;
    static final long ONE_HOUR = 3600000L;
    static final long ONE_DAY = 86400000L;
    static final long ONE_WEEK = 604800000L;
    static final String ONE_SECOND_AGO = "秒前";
    static final String ONE_MINUTE_AGO = "分钟前";
    static final String ONE_HOUR_AGO = "小时前";
    static final String ONE_DAY_AGO = "天前";
    static final String ONE_MONTH_AGO = "月前";
    static final String ONE_YEAR_AGO = "年前";


    public static  String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < ONE_MINUTE) {
            long seconds = toSeconds(delta);
            if (seconds <=0){
                return 1 + ONE_SECOND_AGO;
            }else {
                return seconds + ONE_SECOND_AGO;
            }
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            if (minutes <=0){
                return 1 + ONE_MINUTE_AGO;
            }else {
                return minutes + ONE_MINUTE_AGO;
            }
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            if (hours <=0){
                return 1 + ONE_HOUR_AGO;
            }else {
                return hours + ONE_HOUR_AGO;
            }
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            if (days <=0){
                return 1 + ONE_DAY_AGO;
            }else {
                return days + ONE_DAY_AGO;
            }
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            if (months <=0){
                return 1 + ONE_MONTH_AGO;
            }else {
                return months + ONE_MONTH_AGO;
            }
        } else {
            long years = toYears(delta);
            if (years <=0){
                return 1 + ONE_YEAR_AGO;
            }else {
                return years + ONE_YEAR_AGO;
            }
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(Long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
