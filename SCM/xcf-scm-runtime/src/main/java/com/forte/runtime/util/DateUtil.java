package com.forte.runtime.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @desc
 * @author wangbin
 * @date 2015/8/17
 */
public class DateUtil {
	
	public static String YYYY_MM_DD = "yyyy-MM-dd";
	
    public static Date toDate(String dateStr)
    {
        if (dateStr==null ||dateStr.isEmpty()) return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    public static Date toDate(String dateStr,String format)
    {
        if (dateStr==null ||dateStr.isEmpty()) return null;
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(Date date)
    {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    public static void main(String[] arg) {
        System.out.println(formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    public static double subtract(Date startTime, Date endTime)
    {
        long start = startTime.getTime();
        long end = endTime.getTime();

        double difference = end - start;
        double days = difference / 86400000.0D;
        return days;
    }

    public static boolean isTimeOut(Date startTime, Date endTime, String minitues) {
        long str = 0L;
        try {
            str = (endTime.getTime() - startTime.getTime()) / 1000L;
            if (str > Long.parseLong(minitues) * 60L) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean isTimeOut(Date startTime, Date endTime, long seconds){
        long str = 0L;
        try {
            str = (endTime.getTime() - startTime.getTime()) / 1000L;
            if (str > seconds) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }
}
