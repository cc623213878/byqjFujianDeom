package com.byqj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeUtil {
    public static final int ms = 1000;
    public static final int limitTimeSeconds = 2 * 24 * 60 * 60;

    public static String getCurrentDataTimeString() {
        Date currentDataTime = new Date();
        String currentDataTimeString = getDateTimeString(currentDataTime);
        return currentDataTimeString;
    }

    public static String getTimeIntervalStringByFormatHourSecondMinute(Date startTime, Date endTime) {
        long startTimeLong = startTime.getTime();
        long endtimeLong = endTime.getTime();
        long timeIntervalLong = (endtimeLong - startTimeLong) / 1000;

        long minute = timeIntervalLong % 60;
        long second = (timeIntervalLong / 60) % 60;
        long hour = timeIntervalLong / 3600;

        String timeInterval = hour + "时" + second + "分" + minute + "秒";
        return timeInterval;
    }

    public static String getDateTimeString(Date dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = simpleDateFormat.format(dateTime);
        return dateTimeString;
    }

    /**
     * 当传入时间为null时，返回null，不为null时返回转换以后的值
     *
     * @param dateTime
     * @return
     */
    public static String getDateTimeStringOrNull(Date dateTime) {
        if (dateTime == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = simpleDateFormat.format(dateTime);
        return dateTimeString;
    }

    public static Date changeStringToDate(String dateTimeString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateTimeString);
        return date;
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Date getTodeyStartTime() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static Date getTodetEndTime() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return currentDate.getTime();
    }

    /**
     * 获取剩余时间
     *
     * @param questionDate 某会话最新的提问时间
     * @return
     */
    public static Integer getRestSeconds(Date questionDate) {
        Integer restSeconds = null;
        Date presentDate = new Date();
        if (questionDate != null) {
            long secondsLong = (presentDate.getTime() - questionDate.getTime()) / ms;
            restSeconds = limitTimeSeconds - (int) secondsLong;
        }
        return restSeconds;
    }

    /**
     * 得到签到开始或者结束时间
     */
    public static Date getDateAddMinutes(Date Date, Integer minutes) {
        Calendar calendar = Calendar.getInstance();
        if (Date != null) {
            calendar.setTime(Date);
            calendar.add(Calendar.MINUTE, minutes);
        }
        return calendar.getTime();
    }

}
