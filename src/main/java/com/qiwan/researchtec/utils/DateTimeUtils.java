package com.qiwan.researchtec.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <br>类 名: DateTimeUtil 
 * <br>描 述: 日期时间工具类 
 * <br>作 者: shizhenwei 
 * <br>创 建: 2017年5月15日 
 * <br>版 本: v0.0.2 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */
public class DateTimeUtils {
    public static final String DIVIDE_LINE = "-";
    public static final String DIVIDE_COLON = ":";
    
    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long WEEK = 7 * DAY;
    public static final String YEARFORMAT = "yyyy";
    public static final String MONTHFORMAT = "yyyy-MM";
    public static final String SHORTFORMAT = "yyyy-MM-dd";
    public static final String SHORTFORMATNOSPIT = "yyyyMMdd";
    public static final String LONGFORMATNOSPIT = "yyyyMMddHHmmss";
    public static final String LONGFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SUPERLONGFORMAT = "yyyy-MM-dd HH:mm:ss sss";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String SHORTFORMAT1 = "yyyy/MM/dd";
    
    public static final int FIELD_YEAR = Calendar.YEAR;
    public static final int FIELD_MONTH = Calendar.MONTH;
    public static final int FIELD_DATE = Calendar.DATE;
    
    /**
     * 时间戳格式化 yyyy-MM-dd HH:mm:ss
     */
    public static String getTimestampString(java.util.Date date) {
        return sdf.format(date).toString();
    } 
    
    /**
     * 系统当前时间戳
     */
    public static Timestamp getTimestamp(){
        java.text.SimpleDateFormat myFormat = new SimpleDateFormat(LONGFORMAT);
        long stamp = System.currentTimeMillis();
        String timestamp = myFormat.format(stamp);
        return Timestamp.valueOf(timestamp);
    }
    
    
    /**
     * 日期转时间戳
     */
    public static Timestamp getTimestamp(java.util.Date date) {
        return Timestamp.valueOf(getTimestampString(date));
    } 
    
    /**
     * 字符串转时间戳
     */
    public static Timestamp getTimestamp(String timestamp) {
        if(StringUtils.isEmpty(timestamp)){
            return null;
        }
        return Timestamp.valueOf(timestamp);
    } 
    
    /**
     * 时间格式化 yyyy-MM-dd
     */
    public static String getDate() {
        java.text.SimpleDateFormat myFormat = new SimpleDateFormat(SHORTFORMAT);
        return myFormat.format(System.currentTimeMillis());
    }
    
    /**
     * 时间格式化 yyyy-MM-dd
     */
    public static String getDate(java.util.Date date) {
        return getDate(date, SHORTFORMAT);
    }
    
    /**
     * 时间格式化 yyyy-MM-dd
     */
    public static String getDate(java.util.Date date, String format) {
        String result = null;
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(format);
            result = myFormat.format(date);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    /**
     * 时间格式化 yyyy-MM-dd HH:mm:ss
     * */
    public static String getDateTime() {
        return getDateTime(new java.util.Date());
    }
    
    /**
     * 时间格式化 yyyy-MM-dd HH:mm:ss
     * */
    public static String getDateTime(java.util.Date date) {
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat(LONGFORMAT);
        return d.format(date);
    }
    
    /**
     * 时间格式化 yyyy-MM-dd
     * */
    public static String getShortDate(java.util.Date date){
        if(date==null)
            date = new java.util.Date();
        return getDate(date, SHORTFORMAT);
    }
    
    /**
     * 时间格式化 yyyy-MM-dd HH:mm:ss
     * */
    public static String getLongDate(java.util.Date date){
        if(date==null)
            date = new java.util.Date();
        return getDateTime(date);
    }
    
    /**
     * 时间格式化 yyyy-MM-dd HH:mm:ss
     * */
    public static String getTimestampStr(){
        java.text.SimpleDateFormat myFormat = new SimpleDateFormat(LONGFORMAT);
        return myFormat.format(System.currentTimeMillis());
    }
    
    /**
     * 时间格式化 yyyyMMddHHmmss
     * */
    public static String getLongTime(){
        java.text.SimpleDateFormat myFormat = new SimpleDateFormat(LONGFORMATNOSPIT);
        return myFormat.format(System.currentTimeMillis());
    }
    
    /**
     * 时间格式化 yyyyMMddHHmmss
     * */
    public static String getLongTime(long ts){
        java.text.SimpleDateFormat myFormat = new SimpleDateFormat(LONGFORMATNOSPIT);
        return myFormat.format(ts);
    }
    
    /**
     * 时间戳TS
     */
    public static String getTS(){
        long ts = System.currentTimeMillis();
        return Long.toString(ts);
    }
    
    /**
     * N年后
     * */
    public static java.util.Date addYear(java.util.Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }
    
    /**
     * N个月后
     * */
    public static java.util.Date addMonth(java.util.Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }
    
    /**
     * N天后
     * */
    public static java.util.Date addDay(java.util.Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }
    
    /**
     * N（年、月、天）后
     * */
    public static java.util.Date add(java.util.Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }
    
    /**
     * N（年、月、天）后
     * */
    public static java.util.Date add(String datestr, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate(datestr));
        calendar.add(field, amount);
        return calendar.getTime();
    }
        
    /**
     * N（年、月、天）后(字符串形式)
     * */
    public static String add(String datestr, int field, int amount, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate(datestr,format));
        calendar.add(field, amount);
        return getDate(calendar.getTime(), format);  
    }
    
    /***
     * 获取当前时间，不含日期
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
        String time = sdf.format(Calendar.getInstance().getTime());
        return time;
    }
    
    /**
     * 将字符串转为为日期
     */
    public static java.util.Date formatDate(String datestr) {
        if(19==datestr.length()){
            return formatDate(datestr, LONGFORMAT);
        }else{
            return formatDate(datestr, SHORTFORMAT);
        }
    }
    
    /**
     * 将字符串转为为日期
     */
    public static java.util.Date formatDate(String datestr, String format) {
        try {
            if(StringUtils.isEmpty(datestr)){
                return null;
            }
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(format);
            Date date = myFormat.parse(datestr);
            return date;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    /***
     * 月份的开始结束日期
     * @param year
     * @param month
     * @param keys
     * @return
     */
    public static Map<String, String> getMonthStartDateAndEndDate(int year, int month,
            String... keys) {
        SimpleDateFormat d = new SimpleDateFormat(LONGFORMAT);
        Map<String, String> date = new HashMap<String, String>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        date.put(keys[0], d.format(calendar.getTime()));
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date.put(keys[1], d.format(calendar.getTime()));
        return date;
    }
    
    /**
     * 时间格式化
     */
    public static String getTimeString(String time) {
        String[] ti = time.split(":");
        if (ti[1].length() == 1) {
            time = ti[0] + "0" + ti[1];
        } else {
            time = ti[0] + ti[1];
        }
        return time;
    }
    
    /**
     * <br>描 述: 获取计费模式的年月  2017-06-26 至 2017-07-25 为 2017年07月 以此类推
     * <br>作 者: shizhenwei 
     * <br>历 史: (版本) 作者 时间 注释
     * @param 2017-06-19/2016-06-19 15:35:35
     * @return
     */
    public static String getSicdtYearMonth(String datestr){
        String[] fileds = datestr.split(DIVIDE_LINE);
        if(Integer.valueOf(fileds[2])>25){
            Date date = add(datestr, FIELD_MONTH, 1);
            return getDate(date, MONTHFORMAT);
        }
        return datestr.substring(0, 7);
    }
    
    public static String getSicdtYearMonth(){
        return getSicdtYearMonth(getDate());
    }
    
    @SuppressWarnings("deprecation")
    public static String timeString() {
        Date date = new Date();
        return getTimeString(date.getHours() + ":" + date.getMinutes());
    }
    

    /**
     * 月-天数
     * */
    public static int getDayNum(int year, int month) {
        if (month == 2) {
            return year % 400 != 0 && (year % 4 != 0 || year % 100 == 0) ? 28
                    : 29;
        }
        String SmallMonth = ",4,6,9,11,";
        return SmallMonth.indexOf(String.valueOf(String
                .valueOf((new StringBuffer(",")).append(String.valueOf(month))
                        .append(",")))) < 0 ? 31 : 30;
    }
    /***
     * 两个日期之间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int DateDiff(Date date1, Date date2) {
        int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
        return i;
    }
    /**
     * 日期拆分，取年或月或日    Y-年  M-月  D-日
     * @param strDate
     * @param style
     * @return
     */
    public static int getYearMonthDate(String strDate, String style) {
        int year;
        int month;
        int day;
        int firstDash;
        int secondDash;
        if (strDate == null) {
            return 0;
        }
        firstDash = strDate.indexOf('-');
        secondDash = strDate.indexOf('-', firstDash + 1);
        if ((firstDash > 0) & (secondDash > 0)
                & (secondDash < strDate.length() - 1)) {
            year = Integer.parseInt(strDate.substring(0, firstDash));
            month = Integer.parseInt(strDate.substring(firstDash + 1,
                    secondDash));
            day = Integer.parseInt(strDate.substring(secondDash + 1));
        } else {
            return 0;
        }
        if (style.equalsIgnoreCase("Y")) {
            return year;
        } else if (style.equalsIgnoreCase("M")) {
            return month;
        } else if (style.equalsIgnoreCase("D")) {
            return day;
        } else {
            return 0;
        }
    }
    
    /***
     * 是否是年份的第一天
     * @param date
     * @return
     */
    public static boolean isYearFirstDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 1)
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }
    /**
     * 是否是半年的第一天
     * @param date
     * @return
     */
    public static boolean isHalfYearFirstDay(java.sql.Date date) {
        boolean i = false;
        if (((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 1) && (DateTimeUtils
                .getYearMonthDate(date.toString(), "D") == 1))
                || ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 7) && (DateTimeUtils
                        .getYearMonthDate(date.toString(), "D") == 1))) {
            i = true;
        }
        return i;
    }
    /**
     * 取半年的第一天
     * @param date
     * @return
     */
    public static String getHalfYearFirstDay(java.sql.Date date) {
        String month = "01";
        if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "07";
        }
        String day = Integer.toString(DateTimeUtils.getYearMonthDate(
                date.toString(), "Y")) + "-" + month + "-01";
        return day;
    }
    /**
     * 是否是半年最后一天
     * */
    public static boolean isHalfYearLastDay(java.sql.Date date) {
        boolean i = false;
        if (((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 12) && (DateTimeUtils
                .getYearMonthDate(date.toString(), "D") == 31))
                || ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 6) && (DateTimeUtils
                        .getYearMonthDate(date.toString(), "D") == 30))) {
            i = true;
        }
        return i;
    }
    /***
     * 取半年最后一天
     * @param date
     * @return
     */
    public static String getHalfYearLastDay(java.sql.Date date) {
        String month = "-06-30";
        if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "-12-31";
        }
        String day = Integer.toString(getYearMonthDate(date.toString(), "Y"))
                + "-" + month;
        return day;
    }

    /***
     *是否是年度最后一天
     * @param date
     * @return
     */
    public static boolean isYearLastDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 12)
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        return i;
    }

    /**
     * 是否季度第一天
     * */
    public static boolean isQuarterFirstDay(java.sql.Date date) {
        boolean i = false;
        if (((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 1)
                || (DateTimeUtils.getYearMonthDate(date.toString(), "M") == 4)
                || (DateTimeUtils.getYearMonthDate(date.toString(), "M") == 7) || (DateTimeUtils
                .getYearMonthDate(date.toString(), "M") == 10))
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 1)) {
            i = true;
        }
        return i;
    }
    /***
     * 季度第一天
     * @param date
     * @return
     */
    public static String getQuarterFirstDay(java.sql.Date date) {
        String month = "01";
        if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 10) {
            month = "10";
        } else if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "07";
        } else if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 4) {
            month = "04";
        } else if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 1) {
            month = "01";
        }

        String day = Integer.toString(DateTimeUtils.getYearMonthDate(
                date.toString(), "Y")) + "-" + month + "-01";
        return day;
    }


    /**
     * 是否季度最后一天
     * @param date
     * @return
     */
    public static boolean isQuarterLastDay(java.sql.Date date) {
        boolean i = false;
        if ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 3)
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        if ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 6)
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 30)) {
            i = true;
        }
        if ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 9)
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 30)) {
            i = true;
        }
        if ((DateTimeUtils.getYearMonthDate(date.toString(), "M") == 12)
                && (DateTimeUtils.getYearMonthDate(date.toString(), "D") == 31)) {
            i = true;
        }
        return i;
    }
    /**
     * 季度最后一天
     * @param date
     * @return
     */
    public static String getQuarterLastDay(java.sql.Date date) {
        String month = "-03-31";
        if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 10) {
            month = "-12-31";
        } else if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 7) {
            month = "-09-30";
        } else if (DateTimeUtils.getYearMonthDate(date.toString(), "M") >= 4) {
            month = "-06-30";
        }

        String day = Integer.toString(DateTimeUtils.getYearMonthDate(
                date.toString(), "Y")) + "-" + month;
        return day;
    }
    
    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     * 
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = sdf.parse(sdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     * 
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = sdf.parse(sdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /***
     * 是否月份最后一天
     * @param date
     * @return
     */
    public static boolean isMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 是否月份第一天
     * @param date
     * @return
     */
    public static boolean isMonthFisrtDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMinimum(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 月份第一天日期
     * @param date
     * @return
     */
    public static Date getMonthFisrtDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    
    /**
     * 星期的第几天
     * @param strDate  yy-mm-dd
     * @return
     */
    public static int getDateInWeek(String strDate) {
        DateFormat df = DateFormat.getDateInstance();
        try {
            df.parse(strDate);
            java.util.Calendar c = df.getCalendar();
            int day = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            return day;
        } catch (ParseException e) {
            return -1;
        }
    }
    
    
    public static java.sql.Date getSqlDate(java.util.Date date) {
        java.sql.Date result = null;
        try {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(LONGFORMAT);
            String mystrdate = myFormat.format(date);
            result = java.sql.Date.valueOf(mystrdate);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    
    /**
     * 上个月
     * @param dt
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getUpMDate(Date dt) {
        dt.setDate(1);
        dt.setDate(dt.getDate() - 1);
        return dt.toLocaleString();
    }


    public static String getShortDate(String dt) {
        if (dt != null) {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(SHORTFORMAT);
            try {
                Date date = myFormat.parse(dt);
                return getDate(date, SHORTFORMATNOSPIT);
            } catch (ParseException e) {
                return dt;
            }
        } else
            return dt;
    }

    
    public static String getLongDate(String dt) {
        if (dt != null) {
            java.text.SimpleDateFormat myFormat = new SimpleDateFormat(SHORTFORMAT);
            try {
                Date date = myFormat.parse(dt);
                return getDate(date, SHORTFORMAT);
            } catch (ParseException e) {
                return dt;
            }
        } else
            return dt;
    }


    public static boolean isSameYearMonth(String date) {
        try {
            String currdate = getDate();
            currdate = getShortDate(currdate).substring(0, 6);
            String lastdate = getShortDate(date).substring(0, 6);
            if (lastdate.equals(currdate)) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}