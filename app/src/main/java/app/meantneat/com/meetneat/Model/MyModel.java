package app.meantneat.com.meetneat.Model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import javax.security.auth.callback.Callback;

import app.meantneat.com.meetneat.Camera.SpecificEventDishesDialogBox;
import app.meantneat.com.meetneat.Camera.SpecifiecChefEventsDialogBox;
import app.meantneat.com.meetneat.Controller.ChefEventDishesFragment;
import app.meantneat.com.meetneat.Controller.EditEventDishesFragment;
import app.meantneat.com.meetneat.Controller.EditEventMealsFragment;
import app.meantneat.com.meetneat.Controller.SignInActivity;

import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.EventMeals;

/**
 * Created by mac on 5/25/15.
 */
public class MyModel {
    private ModelInterface model;

    private static MyModel ourInstance = new MyModel();

    public static MyModel getInstance() {
        return ourInstance;
    }

    private MyModel() {
        model = new ParseModel();
    }

    public ModelInterface getModel() {
        return model;
    }

    public interface ModelInterface {
        public void LoginToMeetNeat(String userName, String password);

        public void signUpToMeetNeat(String userName, String email, String password, SignInActivity.SignUpCallback callback);

        public boolean currentUserConnected();

        public void addNewEventDishesToServer(EventDishes event, EditEventDishesFragment.SaveToServerCallback callback);

        public void addNewEventMealsToServer(EventMeals event, EditEventMealsFragment.SaveToServerCallback callback);

        public void getChefsEventFromServer(ChefEventDishesFragment.GetEventDishesCallback callback);

        public void getDishEventDetailsByID(String eventID, SpecificEventDishesDialogBox.DishEventCallback callback);

        public void getChefPicture(String chefID, PictureCallback callback);

        public void getDishPicture(String dishID, PictureCallback callback);

        //Hungry - OnClick chef-Map
        public void getSpecifiecChefsEventFromServer(String chefId, LatLng coordinates,
                                                     SpecifiecChefEventsDialogBox.getEventsByType callback);


        //    public void getClosestChefsRadius(final HungryMapFragment.GetEventDishesCallback callback) {
        //        new AsyncTask<Void, Void, Void>() {
        //            ArrayList<EventDishes> eventDishesArrayList = new ArrayList<>();
        //            @Override
        //            protected Void doInBackground(Void... params) {
        //                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
        //                eventQuery.whereEqualTo(AppConstants.EVENT_DISHES_CHEF_ID,ParseUser.getCurrentUser().getObjectId());
        //                List<ParseObject> tempEventArray;
        //                try {
        //                    tempEventArray = eventQuery.find();
        //                    for(ParseObject object : tempEventArray)
        //                    {
        //                        EventDishes eventDishes = new EventDishes();
        //                        eventDishes.setEventId(object.getObjectId());
        //                        eventDishes.setTitle(object.getString(AppConstants.EVENT_DISHES_TITLE));
        //
        //                        Date startingDate = object.getDate(AppConstants.EVENT_DISHES_START_DATE);
        //                        String s = startingDate.toString();
        //                        eventDishes.setEventDay(startingDate.getDay());
        //                        eventDishes.setEventMonth(startingDate.getMonth());
        //                        eventDishes.setEventYear(startingDate.getYear());
        //                        eventDishes.setStartingHour(startingDate.getHours());
        //                        eventDishes.setStartingMinute(startingDate.getMinutes());
        //
        //                        Date endingDate = object.getDate(AppConstants.EVENT_DISHES_END_DATE);
        //                        eventDishes.setEventYear(endingDate.getYear());
        //                        eventDishes.setEventMonth(endingDate.getMonth());
        //                        eventDishes.setEventDay(endingDate.getDay());
        //                        eventDishes.setEndingHour(endingDate.getHours());
        //                        eventDishes.setEndingMinute(endingDate.getMinutes());
        //
        //                        ParseGeoPoint point = object.getParseGeoPoint(AppConstants.EVENT_DISHES_GEO_POINT);
        //                        eventDishes.setLatitude(point.getLatitude());
        //                        eventDishes.setLongitude(point.getLongitude());
        //                        eventDishes.setLocation(object.getString(AppConstants.EVENT_DISHES_LOCATION));
        //                        eventDishes.setApartmentNumber(object.getString(AppConstants.EVENT_DISHES_APARTMENT_NUMBER));
        //                        //finished the event, now all the event's dishes =\
        //                        eventDishes.setEventsDishes(getDishesForEvent(eventDishes.getEventId()));
        //
        //                        eventDishesArrayList.add(eventDishes);
        //
        //                    }
        //                } catch (ParseException e) {
        //                    e.printStackTrace();
        //                }
        //                return null;
        //            }
        //
        //            @Override
        //            protected void onPostExecute(Void aVoid) {
        //                super.onPostExecute(aVoid);
        //                callback.done(eventDishesArrayList);
        //            }
        //        }.execute();
        //    }


        //    public void getClosestChefsRadius(final HungryMapFragment.GetEventDishesCallback callback) {
        //        new AsyncTask<Void, Void, Void>() {
        //            ArrayList<EventDishes> eventDishesArrayList = new ArrayList<>();
        //            @Override
        //            protected Void doInBackground(Void... params) {
        //                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
        //                eventQuery.whereEqualTo(AppConstants.EVENT_DISHES_CHEF_ID,ParseUser.getCurrentUser().getObjectId());
        //                List<ParseObject> tempEventArray;
        //                try {
        //                    tempEventArray = eventQuery.find();
        //                    for(ParseObject object : tempEventArray)
        //                    {
        //                        EventDishes eventDishes = new EventDishes();
        //                        eventDishes.setEventId(object.getObjectId());
        //                        eventDishes.setTitle(object.getString(AppConstants.EVENT_DISHES_TITLE));
        //
        //                        Date startingDate = object.getDate(AppConstants.EVENT_DISHES_START_DATE);
        //                        String s = startingDate.toString();
        //                        eventDishes.setEventDay(startingDate.getDay());
        //                        eventDishes.setEventMonth(startingDate.getMonth());
        //                        eventDishes.setEventYear(startingDate.getYear());
        //                        eventDishes.setStartingHour(startingDate.getHours());
        //                        eventDishes.setStartingMinute(startingDate.getMinutes());
        //
        //                        Date endingDate = object.getDate(AppConstants.EVENT_DISHES_END_DATE);
        //                        eventDishes.setEventYear(endingDate.getYear());
        //                        eventDishes.setEventMonth(endingDate.getMonth());
        //                        eventDishes.setEventDay(endingDate.getDay());
        //                        eventDishes.setEndingHour(endingDate.getHours());
        //                        eventDishes.setEndingMinute(endingDate.getMinutes());
        //
        //                        ParseGeoPoint point = object.getParseGeoPoint(AppConstants.EVENT_DISHES_GEO_POINT);
        //                        eventDishes.setLatitude(point.getLatitude());
        //                        eventDishes.setLongitude(point.getLongitude());
        //                        eventDishes.setLocation(object.getString(AppConstants.EVENT_DISHES_LOCATION));
        //                        eventDishes.setApartmentNumber(object.getString(AppConstants.EVENT_DISHES_APARTMENT_NUMBER));
        //                        //finished the event, now all the event's dishes =\
        //                        eventDishes.setEventsDishes(getDishesForEvent(eventDishes.getEventId()));
        //
        //                        eventDishesArrayList.add(eventDishes);
        //
        //                    }
        //                } catch (ParseException e) {
        //                    e.printStackTrace();
        //                }
        //                return null;
        //            }
        //
        //            @Override
        //            protected void onPostExecute(Void aVoid) {
        //                super.onPostExecute(aVoid);
        //                callback.done(eventDishesArrayList);
        //            }
        //        }.execute();
        //    }

    }


    public interface PictureCallback
    {
        public void pictureHasBeenFetched(Bitmap bitmap);
    }

}
