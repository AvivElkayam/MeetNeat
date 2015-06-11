package app.meantneat.com.meetneat;

import java.util.ArrayList;

/**
 * Created by mac on 5/23/15.
 */
public class EventDishes {
    private String title;
//    private String date;
//    private String time;
    int startingHour,startingMinute,endingHour,endingMinute;
    int eventYear,eventMonth,eventDay;
    String location,apartmentNumber;
    private String eventId;
    private ArrayList<Dish> eventsDishes;




    public EventDishes(String title, int startingHour, int startingMinute, int endingHour, int endingMinute, int eventYear, int eventMonth, int eventDay, String location, String apartmentNumber, String eventId, ArrayList<Dish> eventsDishes) {
        this.title = title;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.endingHour = endingHour;
        this.endingMinute = endingMinute;
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
        this.eventDay = eventDay;
        this.location = location;
        this.apartmentNumber = apartmentNumber;
        this.eventId = eventId;
        this.eventsDishes = eventsDishes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public int getStartingMinute() {
        return startingMinute;
    }

    public void setStartingMinute(int startingMinute) {
        this.startingMinute = startingMinute;
    }

    public int getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(int endingHour) {
        this.endingHour = endingHour;
    }

    public int getEndingMinute() {
        return endingMinute;
    }

    public void setEndingMinute(int endingMinute) {
        this.endingMinute = endingMinute;
    }

    public int getEventYear() {
        return eventYear;
    }

    public void setEventYear(int eventYear) {
        this.eventYear = eventYear;
    }

    public int getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(int eventMonth) {
        this.eventMonth = eventMonth;
    }

    public int getEventDay() {
        return eventDay;
    }

    public void setEventDay(int eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public ArrayList<Dish> getEventsDishes() {
        return eventsDishes;
    }

    public void setEventsDishes(ArrayList<Dish> eventsDishes) {
        this.eventsDishes = eventsDishes;
    }
}
