package app.meantneat.com.meetneat.Entities;

import java.util.ArrayList;


public class EventMeals {
    private String title;
    int startingHour,startingMinute,startingYear,startingMonth,startingDay;
    int endingYear,endingMonth,endingDay,endingHour,endingMinute;
    String location,apartmentNumber,eventID,chefID,chefName,menu;
    private int mealsLeft;
    private double longitude,latitude;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price;

    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public void setStartingMinute(int startingMinute) {
        this.startingMinute = startingMinute;
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

    public int getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(int endingHour) {
        this.endingHour = endingHour;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public String getChefID() {
        return chefID;
    }

    public void setChefID(String chefID) {
        this.chefID = chefID;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public int getMealsLeft() {
        return mealsLeft;
    }

    public void setMealsLeft(int mealsLeft) {
        this.mealsLeft = mealsLeft;
    }

    public int getTotalMeals() {
        return totalMeals;
    }

    public void setTotalMeals(int totalMeals) {
        this.totalMeals = totalMeals;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getStartingMinute() {
        return startingMinute;
    }

    public int getStartingHour() {
        return startingHour;
    }

    public String getLocation() {
        return location;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    private int totalMeals;
    private String eventId;
    //array of photos - ArrayList...


    public EventMeals() {
    }

    public EventMeals(String title, int startingHour, int startingMinute, int eventYear,
                      int eventMonth, int eventDay, String location, String apartmentNumber, int totalMeals,int price) {
        this.title = title;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;

        this.location = location;
        this.apartmentNumber = apartmentNumber;
        this.totalMeals = totalMeals;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }





    public int getDishesLeft() {
        return mealsLeft;
    }

    public void setDishesLeft(int mealsLeft) {
        this.mealsLeft = mealsLeft;
    }

    public int getTotalDishes() {
        return totalMeals;
    }

    public void setTotalDishes(int totalMeals) {
        this.totalMeals = totalMeals;
    }
}
