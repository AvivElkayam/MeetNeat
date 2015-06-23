package app.meantneat.com.meetneat.Model;

import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Controller.ChefEventDishesFragment;
import app.meantneat.com.meetneat.Controller.EditEventDishesFragment;
import app.meantneat.com.meetneat.Controller.EditEventMealsFragment;
import app.meantneat.com.meetneat.Controller.SignInActivity;
import app.meantneat.com.meetneat.Dish;

import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.EventMeals;


/**
 * Created by mac on 5/25/15.
 */
public class ParseModel implements MyModel.ModelInterface {
    @Override
    public void LoginToMeetNeat(String userName, String password) {

    }

    @Override
    public void signUpToMeetNeat(String userName, String email, String password, final SignInActivity.SignUpCallback callback) {
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        //user.put(AppConstants.USER_NICK_NAME,userName);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null)
                callback.onResult("Yes");
                else
                callback.onResult(e.getMessage());

            }
        });

    }

    @Override
    public boolean currentUserConnected() {
        if(ParseUser.getCurrentUser()==null)
            return false;
        else
            return true;
    }

    @Override
    public void addNewEventDishesToServer(final EventDishes event, final EditEventDishesFragment.SaveToServerCallback callback) {

        //save event first
        final ParseObject eventObject = new ParseObject(AppConstants.EVENT_DISHES);
        Date startingDate = new Date(event.getEventYear(), event.getEventMonth(), event.getEventDay(), event.getStartingHour(), event.getStartingMinute());
        Date endingDate = new Date(event.getEventYear(), event.getEventMonth(), event.getEventDay(), event.getEndingHour(), event.getEndingMinute());
        eventObject.put(AppConstants.EVENT_DISHES_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
        eventObject.put(AppConstants.EVENT_DISHES_START_DATE, startingDate);
        eventObject.put(AppConstants.EVENT_DISHES_END_DATE,endingDate);
        eventObject.put(AppConstants.EVENT_DISHES_LOCATION, event.getLocation());
        eventObject.put(AppConstants.EVENT_DISHES_APARTMENT_NUMBER, event.getApartmentNumber());
        ParseGeoPoint geoPoint = new ParseGeoPoint(event.getLatitude(),event.getLongitude());
        eventObject.put(AppConstants.EVENT_DISHES_GEO_POINT,geoPoint);
        eventObject.put(AppConstants.EVENT_DISHES_TITLE,event.getTitle());

        eventObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                //now lets save the event dishes to the DISHES table
                //need async task???

                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        for (final Dish dish : event.getEventsDishes()) {
                            ParseObject dishObject = new ParseObject(AppConstants.DISH);
                            dishObject.put(AppConstants.DISH_EVENT_ID, eventObject.getObjectId());
                            dishObject.put(AppConstants.DISH_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
                            dishObject.put(AppConstants.DISH_IS_TAKE_AWAY, dish.isTakeAway());
                            dishObject.put(AppConstants.DISH_IS_TO_SIT, dish.isToSit());
                            dishObject.put(AppConstants.DISH_DESCRIPTION, dish.getDescriprion());
                            dishObject.put(AppConstants.DISH_TITLE, dish.getTitle());
                            dishObject.put(AppConstants.DISH_DISHES_LEFT, dish.getQuantity());
                            dishObject.put(AppConstants.DISH_DISHES, dish.getQuantity());
                            dishObject.put(AppConstants.DISH_PRICE, dish.getPrice());
                            ParseFile file = new ParseFile("aFull.jpeg", dish.getFullsizeImg());

                            try {
                                file.save();
                                dishObject.put(AppConstants.DISH_IMG_FULL, file);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            ParseFile fileB = new ParseFile("aThmb.jpeg", dish.getThumbnailImg());
                            try {
                                fileB.save();
                                dishObject.put(AppConstants.DISH_IMG_THUMBNAIL, fileB);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            try {
                                dishObject.save();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        callback.onResult();
                    }
                }.execute();


            }
        });
    }

    @Override
    public void addNewEventMealsToServer(final EventMeals event, final EditEventMealsFragment.SaveToServerCallback callback) {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... params) {
                final ParseObject eventObject = new ParseObject(AppConstants.EVENT_MEALS);
                Date startingDate = new Date(event.getEventYear(), event.getEventMonth(), event.getEventDay(), event.getStartingHour(), event.getStartingMinute());

                eventObject.put(AppConstants.EVENT_MEALS_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
                eventObject.put(AppConstants.EVENT_MEALS_START_DATE, startingDate);
                eventObject.put(AppConstants.EVENT_MEALS_LOCATION, event.getLocation());
                eventObject.put(AppConstants.EVENT_MEALS_APARTMENT_NUMBER, event.getApartmentNumber());
                eventObject.put(AppConstants.EVENT_MEALS_PRICE, event.getPrice());
                eventObject.put(AppConstants.EVENT_MEALS_QUANTITY, event.getTotalDishes());

                try {
                    eventObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return "good";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                callback.onResult();
            }
        }.execute();
    }

    @Override
    public void getChefsEventFromServer(final ChefEventDishesFragment.GetEventDishesCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            ArrayList<EventDishes> eventDishesArrayList = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                eventQuery.whereEqualTo(AppConstants.EVENT_DISHES_CHEF_ID,ParseUser.getCurrentUser().getObjectId());
                List<ParseObject> tempEventArray;
                try {
                     tempEventArray = eventQuery.find();
                     for(ParseObject object : tempEventArray)
                     {
                         EventDishes eventDishes = new EventDishes();
                         eventDishes.setEventId(object.getObjectId());
                         eventDishes.setTitle(object.getString(AppConstants.EVENT_DISHES_TITLE));

                         Date startingDate = object.getDate(AppConstants.EVENT_DISHES_START_DATE);
                         String s = startingDate.toString();
                         eventDishes.setEventDay(startingDate.getDay());
                         eventDishes.setEventMonth(startingDate.getMonth());
                         eventDishes.setEventYear(startingDate.getYear());
                         eventDishes.setStartingHour(startingDate.getHours());
                         eventDishes.setStartingMinute(startingDate.getMinutes());

                         Date endingDate = object.getDate(AppConstants.EVENT_DISHES_END_DATE);
                         eventDishes.setEventYear(endingDate.getYear());
                         eventDishes.setEventMonth(endingDate.getMonth());
                         eventDishes.setEventDay(endingDate.getDay());
                         eventDishes.setEndingHour(endingDate.getHours());
                         eventDishes.setEndingMinute(endingDate.getMinutes());

                         ParseGeoPoint point = object.getParseGeoPoint(AppConstants.EVENT_DISHES_GEO_POINT);
                         eventDishes.setLatitude(point.getLatitude());
                         eventDishes.setLongitude(point.getLongitude());
                         eventDishes.setLocation(object.getString(AppConstants.EVENT_DISHES_LOCATION));
                         eventDishes.setApartmentNumber(object.getString(AppConstants.EVENT_DISHES_APARTMENT_NUMBER));
                         //finished the event, now all the event's dishes =\
                         eventDishes.setEventsDishes(getDishesForEvent(eventDishes.getEventId()));

                         eventDishesArrayList.add(eventDishes);

                     }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.done(eventDishesArrayList);
            }
        }.execute();
    }
    private ArrayList<Dish> getDishesForEvent(String eventID)
    {
        ParseQuery<ParseObject> dishesQuery = new ParseQuery<ParseObject>(AppConstants.DISH);
        dishesQuery.whereEqualTo(AppConstants.DISH_EVENT_ID,eventID);
        ArrayList<Dish> dishesArray = new ArrayList<>();
        try {
            List<ParseObject> tempDishArray = dishesQuery.find();
            for(ParseObject object : tempDishArray)
            {
                Dish dish = new Dish();
                dish.setDishID(object.getObjectId());
                dish.setChefID(object.getString(AppConstants.DISH_CHEF_ID));
                dish.setEventId(object.getString(AppConstants.DISH_EVENT_ID));
                dish.setTitle(object.getString(AppConstants.DISH_TITLE));
                dish.setDescriprion(object.getString(AppConstants.DISH_DESCRIPTION));
                dish.setPrice(object.getDouble(AppConstants.DISH_PRICE));
                dish.setQuantity(object.getDouble(AppConstants.DISH_DISHES));
                dish.setQuantityLeft(object.getDouble(AppConstants.DISH_DISHES_LEFT));
                dish.setTakeAway(object.getBoolean(AppConstants.DISH_IS_TAKE_AWAY));
                dish.setToSit(object.getBoolean(AppConstants.DISH_IS_TO_SIT));
                dish.setFullsizeImg(null);
                dish.setThumbnailImg(null);
                dishesArray.add(dish);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dishesArray;
    }
}
