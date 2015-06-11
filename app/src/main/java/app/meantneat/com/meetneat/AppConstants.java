package app.meantneat.com.meetneat;

/**
 * Created by mac on 5/25/15.
 */
public class AppConstants {
    //USER TABLE
    public static String USER = "User";
    public static String USER_ID = "objectId";
    public static String USER_EMAIL = "username";
    public static String USER_NICK_NAME = "nick_name";
    public static String USER_RATING = "rating";
    public static String USER_DESCRIPTION = "description";
    public static String USER_PHONE = "phone";
    public static String USER_PRICE_MIN = "min_price";
    public static String USER_PRICE_MAX = "max_price";
    //EVENT_DISHES TABLE
    public static String EVENT_DISHES = "EventDishes";
    public static String EVENT_DISHES_ID = "objectId";
    public static String EVENT_DISHES_START_DATE = "start_date";
    public static String EVENT_DISHES_END_DATE = "end_date";
    public static String EVENT_DISHES_CHEF_ID = "chef_id";
    public static String EVENT_DISHES_LOCATION = "location";
    public static String EVENT_DISHES_APARTMENT_NUMBER = "apartment_number";

    //EVENT_MEALS TABLE
    public static String EVENT_MEALS = "EventMeals";
    public static String EVENT_MEALS_ID = "objectId";
    public static String EVENT_MEALS_START_DATE = "start_date";
    public static String EVENT_MEALS_END_DATE = "end_date";
    public static String EVENT_MEALS_CHEF_ID = "chef_id";
    public static String EVENT_MEALS_LOCATION = "location";
    public static String EVENT_MEALS_PRICE = "price";
    public static String EVENT_MEALS_QUANTITY = "quantity";
    public static String EVENT_MEALS_APARTMENT_NUMBER = "apartment_number";
    //DISH TABLE
    public static String DISH = "Dish";
    public static String DISH_ID = "objectId";
    public static String DISH_CHEF_ID = "chef_id";
    public static String DISH_EVENT_ID = "event_id";
    public static String DISH_TITLE = "title";
    public static String DISH_DESCRIPTION = "description";
    public static String DISH_PRICE = "price";
    public static String DISH_DISHES = "dishes";
    public static String DISH_DISHES_LEFT = "dishes_left";
    public static String DISH_IS_TAKE_AWAY = "is_take_away";
    public static String DISH_IS_TO_SIT = "is_to_sit";
    public static String DISH_IMG_FULL = "dish_img_full";
    public static String DISH_IMG_THUMBNAIL = "dish_img_thmb";
    //DISHES_OREDERS
    public static String DISHES_ORDER = "DishesOrders";
    public static String DISHES_ORDER_ID = "objectId";
    public static String DISHES_ORDER_USER_ID = "user_id";
    public static String DISHES_ORDER_DISH_ID = "dish_id";
    public static String DISHES_ORDER_EVENT_ID = "event_id";
    public static String DISHES_ORDER_QUANTITY = "quantity";
    public static String DISHES_ORDER_IS_TAKE_AWAY = "is_take_away";
    public static String DISHES_ORDER_IS_TO_SIT = "is_to_sit";
    public static String DISHES_ORDER_ARRIVAL_TIME = "arrival_time";
    //MEALS_OREDERS
    public static String MEALS_ORDER = "MealsOrders";
    public static String MEALS_ORDER_ID = "objectId";
    public static String MEALS_ORDER_USER_ID = "user_id";
    public static String MEALS_ORDER_EVENT_ID = "event_id";
    public static String MEALS_ORDER_QUANTITY = "quantity";
    public static String MEALS_ORDER_ARRIVAL_TIME = "arrival_time";




}
