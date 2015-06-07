package app.meantneat.com.meetneat;

import java.util.ArrayList;


public class EventMeals {
    private String title;
    int startingHour,startingMinute,endingHour,endingMinute;
    int eventYear,eventMonth,eventDay;
    String location,apartmentNumber;
    private int mealsLeft;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price;

    public int getEventYear() {
        return eventYear;
    }

    public int getEventMonth() {
        return eventMonth;
    }

    public int getEventDay() {
        return eventDay;
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


    public EventMeals(String title, int startingHour, int startingMinute, int eventYear,
                      int eventMonth, int eventDay, String location, String apartmentNumber, int totalMeals,int price) {
        this.title = title;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
        this.eventDay = eventDay;
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
