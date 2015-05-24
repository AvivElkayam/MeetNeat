package app.meantneat.com.meetneat;

import android.graphics.Bitmap;

/**
 * Created by DanltR on 18/05/2015.
 */
public class Dish {

    private String eventId;
    private String name;
    private String descriprion;

    private double price;
    private double quantity;
    private double quantityLeft;
    private boolean isTakeAway;
    private boolean isToSit;
    private Bitmap picture;

    public Dish(String name, String descriprion, double price, double quantity, boolean isTakeAway, boolean isToSit, Bitmap picture) {
        this.name = name;
        this.descriprion = descriprion;
        this.price = price;
        this.quantity = quantity;
        this.isTakeAway = isTakeAway;
        this.isToSit = isToSit;
        this.picture = picture;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriprion() {
        return descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(double quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public boolean isTakeAway() {
        return isTakeAway;
    }

    public void setTakeAway(boolean isTakeAway) {
        this.isTakeAway = isTakeAway;
    }

    public boolean isToSit() {
        return isToSit;
    }

    public void setToSit(boolean isToSit) {
        this.isToSit = isToSit;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
