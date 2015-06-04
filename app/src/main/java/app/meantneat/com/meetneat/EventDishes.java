package app.meantneat.com.meetneat;

import java.util.ArrayList;

/**
 * Created by mac on 5/23/15.
 */
public class EventDishes {
    private String title;
    private String date;
    private String time;
    private int dishesLeft;
    private String eventId;
    private ArrayList<Dish> eventsDishes;
    public EventDishes(String title, String date, String time, int dishesLeft) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.dishesLeft = dishesLeft;
        eventsDishes = new ArrayList<>();
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
        return dishesLeft;
    }

    public void setDishesLeft(int dishesLeft) {
        this.dishesLeft = dishesLeft;
    }
}
