package app.meantneat.com.meetneat.Entities;

import java.util.ArrayList;

/**
 * Created by mac on 5/23/15.
 */
public class EventDishes {
    private String title;
//    private String date;
//    private String time;
    int startingHour,startingMinute,startingYear, startingMonth, startingDay;

    int endingYear, endingMonth,endingDay,endingHour,endingMinute;
    private String location,apartmentNumber;
    private String eventId;
    private ArrayList<Dish> eventsDishes;
    private double longitude,latitude;
    private String chefID,chefName;

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public int getEndingYear() {
        return endingYear;
    }

    public void setEndingYear(int endingYear) {
        this.endingYear = endingYear;
    }

    public int getEndingMonth() {
        return endingMonth;
    }

    public void setEndingMonth(int endingMonth) {
        this.endingMonth = endingMonth;
    }

    public int getEndingDay() {
        return endingDay;
    }

    public void setEndingDay(int endingDay) {
        this.endingDay = endingDay;
    }

    public String getChefID() {
        return chefID;
    }

    public void setChefID(String chefID) {
        this.chefID = chefID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public EventDishes() {
    }

    public EventDishes(String title, int startingHour, int startingMinute,int endingYear,int endingMonth,int endingDay, int endingHour, int endingMinute, int eventYear, int startingMonth, int eventDay, String location, String apartmentNumber, String eventId, ArrayList<Dish> eventsDishes,double longitude,double latitude) {
        this.title = title;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.endingHour = endingHour;
        this.endingMinute = endingMinute;
        this.startingYear = eventYear;
        this.startingMonth = startingMonth;
        this.startingDay = eventDay;
        this.endingYear=endingYear;
        this.endingMonth=endingMonth;
        this.endingDay=endingDay;
        this.location = location;
        this.apartmentNumber = apartmentNumber;
        this.eventId = eventId;
        this.eventsDishes = eventsDishes;
        this.longitude=longitude;
        this.latitude=latitude;
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

    public int getStartingYear() {
        return startingYear;
    }

    public void setStartingYear(int startingYear) {
        this.startingYear = startingYear;
    }

    public int getStartingMonth() {
        return startingMonth;
    }

    public void setStartingMonth(int startingMonth) {
        this.startingMonth = startingMonth;
    }

    public int getStartingDay() {
        return startingDay;
    }

    public void setStartingDay(int startingDay) {
        this.startingDay = startingDay;
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
