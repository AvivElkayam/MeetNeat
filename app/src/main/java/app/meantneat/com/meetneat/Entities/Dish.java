package app.meantneat.com.meetneat.Entities;

import android.graphics.Bitmap;

/**
 * Created by DanltR on 18/05/2015.
 */
public class Dish {

    private String eventId;
    private String name;
    private String descriprion;
    private String title;
    private double price;
    private double quantity;
    private double quantityLeft;
    private boolean isTakeAway;
    private boolean isToSit;
    private byte[] thumbnailImg=null;
    private byte[] fullsizeImg=null;
    private Bitmap thumbnailImage,fullImage;
    private String dishID,chefID;

    public Bitmap getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(Bitmap thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public Bitmap getFullImage() {
        return fullImage;
    }

    public void setFullImage(Bitmap fullImage) {
        this.fullImage = fullImage;
    }

    public String getChefID() {
        return chefID;
    }

    public void setChefID(String chefID) {
        this.chefID = chefID;
    }

    public String getDishID() {
        return dishID;
    }

    public void setDishID(String dishID) {
        this.dishID = dishID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(byte[] thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public byte[] getFullsizeImg() {
        return fullsizeImg;
    }

    public void setFullsizeImg(byte[] fullsizeImg) {
        this.fullsizeImg = fullsizeImg;
    }


    public Dish(String name, String descriprion, double price, double quantity, boolean isTakeAway, boolean isToSit) {
        this.name = name;
        this.descriprion = descriprion;
        this.price = price;
        this.quantity = quantity;
        this.isTakeAway = isTakeAway;
        this.isToSit = isToSit;

    }

    public Dish() {
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


}
