package app.meantneat.com.meetneat;

import java.util.Calendar;

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
                dayString = "Sunday";
                break;
            }
            case Calendar.MONDAY:
            {
                dayString = "Monday";
                break;
            }
            case Calendar.TUESDAY:
            {
                dayString = "Tuesday";
                break;
            }
            case Calendar.WEDNESDAY:
            {
                dayString = "Wednesday";
                break;
            }
            case Calendar.THURSDAY:
            {
                dayString = "Thursday";
                break;
            }
            case Calendar.FRIDAY:
            {
                dayString = "Friday";
                break;
            }
            case Calendar.SATURDAY:
            {
                dayString = "Saturday";
                break;
            }
        }

        switch (monthOfYear)
        {
            case Calendar.JANUARY:
            {
                monthString = "January";
                break;
            }
            case Calendar.FEBRUARY:
            {
                monthString = "February";
                break;
            }
            case Calendar.MARCH:
            {
                monthString = "March";
                break;
            }
            case Calendar.APRIL:
            {
                monthString = "April";
                break;
            }
            case Calendar.MAY:
            {
                monthString = "May";
                break;
            }
            case Calendar.JUNE:
            {
                monthString = "June";
                break;
            }
            case Calendar.JULY:
            {
                monthString = "July";
                break;
            }
            case Calendar.AUGUST:
            {
                monthString = "August";
                break;
            }
            case Calendar.SEPTEMBER:
            {
                monthString = "September";
                break;
            }
            case Calendar.OCTOBER:
            {
                monthString = "October";
                break;
            }
            case Calendar.NOVEMBER:
            {
                monthString = "November";
                break;
            }
            case Calendar.DECEMBER:
            {
                monthString = "December";
                break;
            }

        }
        s = dayString+", "+monthString+" "+Integer.toString(day)+" "+Integer.toString(year);
        return s;
    }
    public static String getTimeString(int hour, int minute)
    {
        String s=hour + ":" + minute;

        return s;
    }

}
