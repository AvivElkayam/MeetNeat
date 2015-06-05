package app.meantneat.com.meetneat;

import java.util.ArrayList;


public class EventMeals {
    private String title;
    private String date;
    private String time;
    private int mealsLeft;
    private int totalMeals;
    private String eventId;
    //array of photos - ArrayList...
    public EventMeals(String title, String date, String time, int totalDishes,int dishesLeft) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.mealsLeft = dishesLeft;
        this.totalMeals = totalDishes;
     }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
