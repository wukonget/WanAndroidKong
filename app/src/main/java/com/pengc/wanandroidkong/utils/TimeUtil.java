package com.pengc.wanandroidkong.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class TimeUtil {

    public static String getTimeBetweenTwoDate(String dateString1, String dateString2) {
        if (TextUtils.isEmpty(dateString1) || TextUtils.isEmpty(dateString2)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(dateString1);
            date2 = df.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Long diff = Math.abs(date2.getTime() - date1.getTime());
        int days = (int) Math.floor(diff / (24 * 3600 * 1000));
        Long diff1 = diff % (24 * 3600 * 1000);
        int hours = (int) Math.floor(diff1 / (3600 * 1000));
        Long diff2 = diff1 % (3600 * 1000);
        int minutes = (int) Math.floor(diff2 / (60 * 1000));
        Long diff3 = diff2 % ((60 * 1000));
        int seconds = Math.round(diff3 / 1000);
        if (days == 0) {
            if (hours == 0) {
                if (minutes == 0) {
                    return seconds + "s";
                } else {
                    return minutes + "m" + seconds + "s";
                }
            } else {
                return hours + "h" + minutes + "m";
            }
        } else {
            return days + "d" + hours + "h";
        }
    }

    public static String getTimeBetweenTwoDateZH(String dateString1, String dateString2) {
        String resultEn = getTimeBetweenTwoDate(dateString1, dateString2);
        String resultZh = resultEn == null ? "" : resultEn.replace("d", "天").replace("h", "小时")
                .replace("m", "分钟").replace("s", "秒");
        return resultZh;
    }

    public static String getTimeBetweenToNow(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(System.currentTimeMillis());
        String nowString = format.format(now);
        return getTimeBetweenTwoDate(nowString, dateString);
    }

    /**
     * 两个日期之间的差值 ，第一个参数减去第二个参数，来判断大小
     *
     * @param dateString1
     * @param dateString2
     * @return
     */
    public static long getDurationBetween2Date(String dateString1, String dateString2) {
        if (TextUtils.isEmpty(dateString1) || TextUtils.isEmpty(dateString2)) {
            return 0l;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(dateString1);
            date2 = df.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0l;
        }
        return date1.getTime() - date2.getTime();
    }


    /**
     * 两个日期之间的差值 ，第一个参数减去第二个参数，来判断大小
     *
     * @param dateString1
     * @param dateString2
     * @return
     */
    public static long getDurationBetween2Time(String dateString1, String dateString2) {
        if (TextUtils.isEmpty(dateString1) || TextUtils.isEmpty(dateString2)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(dateString1);
            date2 = df.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date1.getTime() - date2.getTime();
    }

    /**
     * 通过年月日拼接为YYYY-MM-DD;
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static String formatToDate(int year, int month, int dayOfMonth) {
        return new StringBuffer().append(year).append("-")
                .append(String.format("%02d", month + 1)).append("-").append(String.format("%02d", dayOfMonth)).toString();
    }

    public static String timeStringToStamp(String date1) {
        if (date1 == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = sdf.parse(date1);
            return String.valueOf(dt1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stampToString(String stamp) {
        Date date = new Date(Long.valueOf(stamp));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 当前年
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获取当前年月
     *
     * @return
     */
    public static String getCurrentYearAndMonth() {
        return getCurrentYearAndMonth("yyyy年MM月");
    }

    /**
     * 获取当前年月
     *
     * @return
     */
    public static String getCurrentYearAndMonth(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获得YYYY年MM月DD日的日期
     *
     * @return
     */
    public static String getNowDataYYYYMMDD_China() {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    /**
     * 获得YYYY-MM-DD的日期
     *
     * @return
     */
    public static String getNowDataYYYYMMDD() {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    public static String getNowTimeHHmm() {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    public static long getLongBetween2TimeyyyMMddHHmm(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return 0;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(startTime);
            date2 = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Long diff = date2.getTime() - date1.getTime();
        return diff;
    }

    public static long getLongBetween2TimeyyyMMdd(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return 0;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(startTime);
            date2 = df.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Long diff = date2.getTime() - date1.getTime();
        return diff;
    }


    public static double getHourFromLongTime(long time) {
        double hours = 0;
        if (time != 0) {
            BigDecimal b1 = new BigDecimal(time);
            BigDecimal b2 = new BigDecimal((3600 * 1000));
            hours = b1.divide(b2, 2, ROUND_HALF_UP).doubleValue();
        }
        return hours;
    }

    public static int getDayFromLongTime(long time) {
        int days = 0;
        if (time != 0) {
            days = (int) Math.floor(time / (24 * 3600 * 1000));
        }
        return days;
    }

    public static long getLongFromStringyyyyMMdd(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Long diff = date.getTime();
        return diff;
    }

    public static long getLongFromStringyyyyMMddHHmm(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Long diff = date.getTime();
        return diff;
    }

    public static String changeHanZiToLine(String date, String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            if (date.contains(str)) {
                if (i == strings.length - 1) {
                    date = date.replace(str, "");
                } else {
                    date = date.replace(str, "-");
                }
            }
        }
        return date;
    }

}