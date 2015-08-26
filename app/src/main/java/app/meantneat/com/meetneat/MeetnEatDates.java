package app.meantneat.com.meetneat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mac on 8/13/15.
 */
public class MeetnEatDates {

    public static String getDateString(int year,int month, int day)
    {
        String s="";
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int monthOfYear = calendar.get(Calendar.MONTH);
        String dayString="";
        String monthString="";
        switch (dayOfWeek)
        {
            case Calendar.SUNDAY:
            {
                dayString = "Sun";
                break;
            }
            case Calendar.MONDAY:
            {
                dayString = "Mon";
                break;
            }
            case Calendar.TUESDAY:
            {
                dayString = "Tue";
                break;
            }
            case Calendar.WEDNESDAY:
            {
                dayString = "Wed";
                break;
            }
            case Calendar.THURSDAY:
            {
                dayString = "Thu";
                break;
            }
            case Calendar.FRIDAY:
            {
                dayString = "Fri";
                break;
            }
            case Calendar.SATURDAY:
            {
                dayString = "Sat";
                break;
            }
        }

        switch (monthOfYear)
        {
            case Calendar.JANUARY:
            {
                monthString = "Jan";
                break;
            }
            case Calendar.FEBRUARY:
            {
                monthString = "Feb";
                break;
            }
            case Calendar.MARCH:
            {
                monthString = "Mar";
                break;
            }
            case Calendar.APRIL:
            {
                monthString = "Apr";
                break;
            }
            case Calendar.MAY:
            {
                monthString = "May";
                break;
            }
            case Calendar.JUNE:
            {
                monthString = "Jun";
                break;
            }
            case Calendar.JULY:
            {
                monthString = "Jul";
                break;
            }
            case Calendar.AUGUST:
            {
                monthString = "Aug";
                break;
            }
            case Calendar.SEPTEMBER:
            {
                monthString = "Sep";
                break;
            }
            case Calendar.OCTOBER:
            {
                monthString = "Oct";
                break;
            }
            case Calendar.NOVEMBER:
            {
                monthString = "Nov";
                break;
            }
            case Calendar.DECEMBER:
            {
                monthString = "Dec";
                break;
            }

        }
        s = dayString+", "+monthString+" "+Integer.toString(day)+" "+Integer.toString(year);
        //NumberFormat f = new DecimalFormat("00");//makes sure always number are displayed with two digits

        //s = f.format(day)+"."+f.format(month)+"."+Integer.toString(year);
        return s;
    }
    public static String getTimeString(int hour, int minute)
    {
        NumberFormat f = new DecimalFormat("00");//makes sure always number are displayed with two digits

        String s=f.format(hour) + ":" + f.format(minute);

        return s;
    }
    public static Date getDateWithSpecificTimeZone(int year,int month,int day,int hour, int minute) {
        String yearS = Integer.toString(year);
        String monthS = Integer.toString(month);
        String dayS = Integer.toString(day);
        String hourS = Integer.toString(hour);
        String minuteS = Integer.toString(minute);
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");

        isoFormat.setTimeZone(TimeZone.getDefault());//local time zone
        Date date1 = null;
        try {
             date1 = isoFormat.parse(yearS+"-"+monthS+"-"+dayS+","+hourS+":"+minuteS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    //Convert Date to Calendar
    public static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    //Convert Calendar to Date
    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
