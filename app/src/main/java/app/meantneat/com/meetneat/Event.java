package app.meantneat.com.meetneat;

/**
 * Created by mac on 5/23/15.
 */
public class Event {
    String title;
    String date;
    String time;
    int dishesLeft;
    String eventId;

    public Event(String title, String date, String time, int dishesLeft) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.dishesLeft = dishesLeft;
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
