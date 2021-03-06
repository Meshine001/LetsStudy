package com.meshine.letsstudyclient.tools;

import android.content.Context;
import android.util.Log;

import com.meshine.letsstudyclient.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.jpush.android.Configs;

/**
 * Created by Ming on 2016/5/2.
 */
public class TimeFormat {

//    public static TimeFormat timeFormat = new TimeFormat();
//
//    public static TimeFormat getInstance(){
//        return timeFormat;
//    }

    private long mTimeStamp;
    private Context mContext;

    public TimeFormat(Context context, long timeStamp) {
        this.mContext = context;
        this.mTimeStamp = timeStamp;
        SimpleDateFormat format = new SimpleDateFormat(mContext.getString(R.string.time_format_accuracy),
                Locale.CHINA);
        String date = format.format(timeStamp);
    }

    //用于显示会话时间
    public String getTime() {
        long currentTime = Configs.getReportTime();
        Date date1 = new Date(currentTime);
        Date date2 = new Date(mTimeStamp);
        SimpleDateFormat format = new SimpleDateFormat(mContext.getString(R.string.time_format_hours),
                Locale.CHINA);
        SimpleDateFormat format1 = new SimpleDateFormat(mContext.getString(R.string.time_format_accuracy),
                Locale.CHINA);
        String date = format.format(mTimeStamp);
        int hour = Integer.parseInt(date.substring(0, 2));
        //今天
        if (date1.getDate() - date2.getDate() == 0) {
            if (hour < 6) {
                return mContext.getString(R.string.before_dawn) + " " + date;
            } else if (hour < 12) {
                return mContext.getString(R.string.morning) + " " + date;
            } else if (hour < 18) {
                return mContext.getString(R.string.afternoon) + " " + date;
            } else {
                return mContext.getString(R.string.night) + " " + date;
            }
            //昨天
        } else if (date1.getDate() - date2.getDate() == 1) {
            return mContext.getString(R.string.yesterday);
        } else if (date1.getDay() - date2.getDay() > 0) {
            switch (date2.getDay()) {
                case 1:
                    return mContext.getString(R.string.monday);
                case 2:
                    return mContext.getString(R.string.tuesday);
                case 3:
                    return mContext.getString(R.string.wednesday);
                case 4:
                    return mContext.getString(R.string.thursday);
                case 5:
                    return mContext.getString(R.string.friday);
                case 6:
                    return mContext.getString(R.string.saturday);
                default:
                    return mContext.getString(R.string.sunday);
            }
        } else if (date1.getYear() == date2.getYear()) {
            return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                    + mContext.getString(R.string.day);
        } else {
            return format1.format(mTimeStamp);
        }
    }

    //用于显示消息具体时间
    public String getDetailTime() {
        long currentTime = Configs.getReportTime();
        Date date1 = new Date(currentTime);
        Date date2 = new Date(mTimeStamp);
        SimpleDateFormat format = new SimpleDateFormat(mContext.getString(R.string.time_format_hours),
                Locale.CHINA);
        String date = format.format(mTimeStamp);
        SimpleDateFormat format1 = new SimpleDateFormat(mContext.getString(R.string.time_format_year_month_day),
                Locale.CHINA);
        String date3 = format1.format(mTimeStamp);
        int hour = Integer.parseInt(date.substring(0, 2));
        if (date1.getDate() - date2.getDate() == 0) {
            if (hour < 6) {
                return mContext.getString(R.string.before_dawn) + date;
            } else if (hour < 12) {
                return mContext.getString(R.string.morning) + date;
            } else if (hour < 18) {
                return mContext.getString(R.string.afternoon) + date;
            } else {
                return mContext.getString(R.string.night) + date;
            }
        } else if (date1.getDate() - date2.getDate() == 1) {
            if (hour < 6) {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.before_dawn) + date;
            } else if (hour < 12) {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.morning) + date;
            } else if (hour < 18) {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.afternoon) + date;
            } else {
                return mContext.getString(R.string.yesterday) + " " + mContext.getString(R.string.night) + date;
            }
        } else if (date1.getYear() == date2.getYear()) {
            if (hour < 6) {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.before_dawn) + date;
            } else if (hour < 12) {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.morning) + date;
            } else if (hour < 18) {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.afternoon) + date;
            } else {
                return date2.getMonth() + 1 + mContext.getString(R.string.month) + date2.getDate()
                        + mContext.getString(R.string.day) + " " + mContext.getString(R.string.night) + date;
            }
        } else if (hour < 6) {
            return date3 + " " + mContext.getString(R.string.before_dawn) + date;
        } else if (hour < 12) {
            return date3 + " " + mContext.getString(R.string.morning) + date;
        } else if (hour < 18) {
            return date3 + " " + mContext.getString(R.string.afternoon) + date;
        } else {
            return date3 + " " + mContext.getString(R.string.night) + date;
        }
    }

    private static final String TIME_PATTERN = "yyyy年MM月dd日 HH:mm";

    public static long getTimeStamp(String timeStr){
        try {
            SimpleDateFormat df = new SimpleDateFormat(TIME_PATTERN);
            Date date = df.parse(timeStr);
            return date.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String unixTime2Local(Long time,String pattern){
        Date date = new Date(time*1000);
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
}
