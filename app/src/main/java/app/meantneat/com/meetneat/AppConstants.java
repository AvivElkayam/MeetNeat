package app.meantneat.com.meetneat;

/**
 * Created by mac on 5/25/15.
 */
public class AppConstants {
    //USER TABLE
    public static String USER = "User";
    public static String USER_NAME = "username";
    public static String USER_EMAIL = "email";
    public static String USER_RATING = "rating";
    public static String USER_DESCRIPTION = "description";
    public static String USER_PHONE = "phone";
    //EVENT TABLE
    public static String EVENT = "Event";
    public static String EVENT_ID = "objectId";
    public static String EVENT_TITLE = "title";
    public static String EVENT_START_DATE = "start_date";
    public static String EVENT_START_TIME = "start_time";
    public static String EVENT_END_DATE = "end_date";
    public static String EVENT_END_TIME = "end_time";
    public static String EVENT_CHEF_ID = "chef_id";
    public static String EVENT_LOCATION = "location";

    //DISH TABLE
    public static String DISH = "Dish";
    public static String DISH_ID = "objectId";
    public static String DISH_EVENT_ID = "event_id";
    public static String DISH_PRICE = "price";
    public static String DISH_DISHES = "dishes";
    public static String DISH_DISHES_LEFT = "dishes_left";
    public static String DISH_IS_TAKE_AWAY = "is_take_away";
    public static String DISH_IS_TO_SIT = "is_to_sit";
    //ORDERS
    public static String ORDER = "Orders";
    public static String ORDER_ID = "objectId";
    public static String ORDER_USER_ID = "user_id";
    public static String ORDER_DISH_ID = "dish_id";
    public static String ORDER_QUANTITY = "quantity";
    public static String ORDER_IS_TAKE_AWAY = "is_take_away";
    public static String ORDER_IS_TO_SIT = "is_to_sit";
    public static String ORDER_ARRIVAL_TIME = "arrival_time";



}
