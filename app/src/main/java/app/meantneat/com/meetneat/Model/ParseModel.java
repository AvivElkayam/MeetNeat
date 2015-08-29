package app.meantneat.com.meetneat.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Camera.SpecificEventDishesDialogBox;
import app.meantneat.com.meetneat.Camera.SpecifiecChefEventsDialogBox;
import app.meantneat.com.meetneat.Controller.Chef.ChefEventDishesFragment;
import app.meantneat.com.meetneat.Controller.Chef.ChefEventMealsFragment;
import app.meantneat.com.meetneat.Controller.Chef.EditEventDishesFragment;
import app.meantneat.com.meetneat.Controller.Chef.EditEventMealsFragment;
import app.meantneat.com.meetneat.Controller.Hungry.HungryMapFragment;
import app.meantneat.com.meetneat.Controller.Login.LoginActivity;
import app.meantneat.com.meetneat.Controller.Login.SignInActivity;
import app.meantneat.com.meetneat.Entities.Dish;

import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.Entities.EventMeals;
import app.meantneat.com.meetneat.MeetnEatDates;


/**
 * Created by mac on 5/25/15.
 */
public class ParseModel implements MyModel.ModelInterface {
    @Override
    public void LoginToMeetNeat(String userName, String password, final LoginActivity.LoginCallback callback) {
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    callback.loggedIn();// Hooray! The user is logged in.
                } else {
                    callback.failed(e.getMessage());
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

    @Override
    public void signUpToMeetNeat(final String userName, final String email, final String password,final Bitmap bitmap, final SignInActivity.SignUpCallback callback) {

        new AsyncTask<Void, Void, Void>() {
            String s="Yes";
            @Override
            protected Void doInBackground(Void... params) {
                ParseUser user = new ParseUser();
                user.setUsername(email);
                user.setPassword(password);
                user.put(AppConstants.USER_NICK_NAME,userName);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] data = stream.toByteArray();
                ParseFile imgFile = new ParseFile (userName+".png", data);
                try {
                    imgFile.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                    s=e.getMessage();
                }


                user.put(AppConstants.USER_IMAGE,imgFile);
                try {
                    user.signUp();
                } catch (ParseException e) {
                    e.printStackTrace();
                    s=e.getMessage();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.onResult(s);
            }
        }.execute();




    }

    @Override
    public boolean currentUserConnected() {
        if (ParseUser.getCurrentUser() == null)
            return false;
        else
            return true;
    }

    @Override
    public void addNewEventDishesToServer(final EventDishes event, final EditEventDishesFragment.SaveToServerCallback callback) {

                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        final ParseObject eventObject = new ParseObject(AppConstants.EVENT_DISHES);
                        Date startingDate = MeetnEatDates.getDateWithSpecificTimeZone(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay(), event.getStartingHour(), event.getStartingMinute());
                        Date endingDate = MeetnEatDates.getDateWithSpecificTimeZone(event.getEndingYear(), event.getEndingMonth(), event.getEndingDay(), event.getEndingHour(), event.getEndingMinute());
                        eventObject.put(AppConstants.EVENT_DISHES_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
                        eventObject.put(AppConstants.EVENT_DISHES_CHEF_NAME,ParseUser.getCurrentUser().getUsername());
                        eventObject.put(AppConstants.EVENT_DISHES_START_DATE, startingDate);
                        eventObject.put(AppConstants.EVENT_DISHES_END_DATE, endingDate);
                        eventObject.put(AppConstants.EVENT_DISHES_LOCATION, event.getLocation());
                        eventObject.put(AppConstants.EVENT_DISHES_APARTMENT_NUMBER, event.getApartmentNumber());
                        ParseGeoPoint geoPoint = new ParseGeoPoint(event.getLatitude(), event.getLongitude());
                        eventObject.put(AppConstants.EVENT_DISHES_GEO_POINT, geoPoint);
                        eventObject.put(AppConstants.EVENT_DISHES_TITLE, event.getTitle());
                        try {
                            eventObject.save();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        saveEventsDishesToServer(event,eventObject);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        callback.onResult();
                    }
                }.execute();

    }
private void saveEventsDishesToServer(EventDishes event,ParseObject eventObject)
{
    for (final Dish dish : event.getEventsDishes()) {
        ParseObject dishObject = new ParseObject(AppConstants.DISH);
        dishObject.put(AppConstants.DISH_EVENT_ID, eventObject.getObjectId());
        dishObject.put(AppConstants.DISH_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
        dishObject.put(AppConstants.DISH_IS_TAKE_AWAY, dish.isTakeAway());
        dishObject.put(AppConstants.DISH_IS_TO_SIT, dish.isToSit());
        dishObject.put(AppConstants.DISH_DESCRIPTION, dish.getDescriprion());
        dishObject.put(AppConstants.DISH_TITLE, dish.getTitle());
        dishObject.put(AppConstants.DISH_DISHES_LEFT, dish.getQuantityLeft());
        dishObject.put(AppConstants.DISH_DISHES, dish.getQuantityLeft());
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
}
    @Override
    public void addNewEventMealsToServer(final EventMeals event, final EditEventMealsFragment.SaveToServerCallback callback) {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... params) {
                final ParseObject eventObject = new ParseObject(AppConstants.EVENT_MEALS);
                Date startingDate = MeetnEatDates.getDateWithSpecificTimeZone(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay(), event.getStartingHour(), event.getStartingMinute());
                Date endingDate = MeetnEatDates.getDateWithSpecificTimeZone(event.getEndingYear(), event.getEndingMonth(), event.getEndingDay(), event.getEndingHour(), event.getEndingMinute());
                ParseGeoPoint geoPoint = new ParseGeoPoint(event.getLatitude(), event.getLongitude());
                eventObject.put(AppConstants.EVENT_MEALS_GEO_POINT, geoPoint);
                eventObject.put(AppConstants.EVENt_MEALS_EVENT_TITLE,event.getTitle());
                eventObject.put(AppConstants.EVENT_MEALS_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
                eventObject.put(AppConstants.EVENT_DISHES_CHEF_NAME,ParseUser.getCurrentUser().getUsername());
                eventObject.put(AppConstants.EVENT_MEALS_START_DATE, startingDate);
                eventObject.put(AppConstants.EVENT_MEALS_END_DATE, endingDate);
                eventObject.put(AppConstants.EVENT_MEALS_LOCATION, event.getLocation());
                eventObject.put(AppConstants.EVENT_MEALS_APARTMENT_NUMBER, event.getApartmentNumber());
                eventObject.put(AppConstants.EVENT_MEALS_PRICE, event.getPrice());
                eventObject.put(AppConstants.EVENT_MEALS_MENU,event.getMenu());
                eventObject.put(AppConstants.EVENT_MEALS_QUANTITY, event.getMealsLeft());

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
    public void getChefsEventDishesFromServer(final ChefEventDishesFragment.GetEventDishesCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            ArrayList<EventDishes> eventDishesArrayList = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                //eventQuery.whereEqualTo(AppConstants.EVENT_DISHES_CHEF_ID,ParseUser.getCurrentUser().getObjectId());
                List<ParseObject> tempEventArray;
                try {
                     tempEventArray = eventQuery.find();
                     for(ParseObject object : tempEventArray)
                     {

                         eventDishesArrayList.add(ParseObjectToEventDishes(object));

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

    @Override
    public void getChefsEventMealsFromServer(final ChefEventMealsFragment.GetEventMealsCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            ArrayList<EventMeals> eventMealsArrayList = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_MEALS);
                //eventQuery.whereEqualTo(AppConstants.EVENT_DISHES_CHEF_ID,ParseUser.getCurrentUser().getObjectId());
                List<ParseObject> tempEventArray;
                try {
                    tempEventArray = eventQuery.find();
                    for(ParseObject object : tempEventArray)
                    {

                        eventMealsArrayList.add(ParseObjectToEventMeals(object));

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.done(eventMealsArrayList);
            }
        }.execute();
    }

    @Override
    public void getDishEventDetailsByID(final String eventID, final SpecificEventDishesDialogBox.DishEventCallback callback)
    {
        new AsyncTask<Void, Void, Void>() {
            EventDishes eventDishes;

            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                try {
                    ParseObject object = query.get(eventID);
                    eventDishes = ParseObjectToEventDishes(object);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.eventHasBeenFetched(eventDishes);
            }
        }.execute();

    }

    @Override
    public void getChefPicture(final String chefID, final MyModel.PictureCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            Bitmap bitmap=null;
            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseUser> query = ParseQuery.getUserQuery();
                try
                {
                    ParseUser user = query.get(chefID);
                    ParseFile applicantResume = (ParseFile)user.get(AppConstants.USER_IMAGE);
                    if(applicantResume!=null) {
                        byte[] bytes = applicantResume.getData();

                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    }
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }




                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.pictureHasBeenFetched(bitmap);
            }
        }.execute();
    }

    @Override
    public void getDishPicture(final String dishID,final MyModel.PictureCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            Bitmap bitmap;
            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseUser> query = new ParseQuery<>(AppConstants.DISH);
                try
                {
                    ParseObject dish = query.get(dishID);
                    ParseFile applicantResume = dish.getParseFile(AppConstants.DISH_IMG_THUMBNAIL);//changed to thumbnail

                    if(applicantResume!=null) {
                        byte[] bytes = applicantResume.getData();

                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    }
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }




                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.pictureHasBeenFetched(bitmap);
            }
        }.execute();
    }

    @Override
    public void logOut() {
        ParseUser.logOut();
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

    private EventDishes ParseObjectToEventDishes(ParseObject object) {
        EventDishes eventDishes = new EventDishes();
        eventDishes.setEventId(object.getObjectId());
        eventDishes.setTitle(object.getString(AppConstants.EVENT_DISHES_TITLE));
        eventDishes.setChefID(object.getString(AppConstants.EVENT_DISHES_CHEF_ID));
        eventDishes.setChefName(object.getString(AppConstants.EVENT_DISHES_CHEF_NAME));
        Date startingDate = object.getDate(AppConstants.EVENT_DISHES_START_DATE);
        //startingDate = MeetnEatDates.getDateWithSpecificTimeZone(startingDate.getYear(),startingDate.getMonth(),startingDate.getDay(),startingDate.getHours(),startingDate.getMinutes());
        String s = startingDate.toString();
        Calendar startingDateCalendar = dateToCalendar(startingDate);
        eventDishes.setStartingDay(startingDateCalendar.get(Calendar.DAY_OF_MONTH));

        eventDishes.setStartingMonth(startingDateCalendar.get(Calendar.MONTH));
        eventDishes.setStartingYear(startingDateCalendar.get(Calendar.YEAR));
        eventDishes.setStartingHour(startingDateCalendar.get(Calendar.HOUR_OF_DAY));
        eventDishes.setStartingMinute(startingDateCalendar.get(Calendar.MINUTE));

        Date endingDate = object.getDate(AppConstants.EVENT_DISHES_END_DATE);
        Calendar endingDateCalendar = dateToCalendar(endingDate);

        //endingDate = MeetnEatDates.getDateWithSpecificTimeZone(endingDate.getYear(),endingDate.getMonth(),endingDate.getDay(),endingDate.getHours(),endingDate.getDay());
        eventDishes.setEndingYear(endingDateCalendar.get(Calendar.YEAR));
        eventDishes.setEndingMonth(endingDateCalendar.get(Calendar.MONTH));
        eventDishes.setEndingDay(endingDateCalendar.get(Calendar.DAY_OF_MONTH));
        eventDishes.setEndingHour(endingDateCalendar.get(Calendar.HOUR_OF_DAY));
        eventDishes.setEndingMinute(endingDateCalendar.get(Calendar.MINUTE));

        ParseGeoPoint point = object.getParseGeoPoint(AppConstants.EVENT_DISHES_GEO_POINT);
        eventDishes.setLatitude(point.getLatitude());
        eventDishes.setLongitude(point.getLongitude());
        eventDishes.setLocation(object.getString(AppConstants.EVENT_DISHES_LOCATION));
        eventDishes.setApartmentNumber(object.getString(AppConstants.EVENT_DISHES_APARTMENT_NUMBER));
        //finished the event, now all the event's dishes =\
        //eventDishes.setEventsDishes(getDishesForEvent(eventDishes.getEventId()));


        return eventDishes;
    }
    private EventMeals ParseObjectToEventMeals(ParseObject object) {
        EventMeals eventMeals = new EventMeals();
        eventMeals.setEventId(object.getObjectId());
        eventMeals.setTitle(object.getString(AppConstants.EVENt_MEALS_EVENT_TITLE));
        eventMeals.setChefID(object.getString(AppConstants.EVENT_MEALS_CHEF_ID));
        eventMeals.setChefName(object.getString(AppConstants.EVENT_MEALS_CHEF_NAME));

        Date startingDate = object.getDate(AppConstants.EVENT_MEALS_START_DATE);
        eventMeals.setStartingDay(startingDate.getDay());
        eventMeals.setStartingMonth(startingDate.getMonth());
        eventMeals.setStartingYear(startingDate.getYear());
        eventMeals.setStartingHour(startingDate.getHours());
        eventMeals.setStartingMinute(startingDate.getMinutes());

        Date endingDate = object.getDate(AppConstants.EVENT_MEALS_END_DATE);
        eventMeals.setEndingYear(endingDate.getYear());
        eventMeals.setEndingMonth(endingDate.getMonth());
        eventMeals.setEndingDay(endingDate.getDay());
        eventMeals.setEndingHour(endingDate.getHours());
        eventMeals.setEndingMinute(endingDate.getMinutes());

        ParseGeoPoint point = object.getParseGeoPoint(AppConstants.EVENT_MEALS_GEO_POINT);
        eventMeals.setLatitude(point.getLatitude());
        eventMeals.setLongitude(point.getLongitude());
        eventMeals.setLocation(object.getString(AppConstants.EVENT_MEALS_LOCATION));
        eventMeals.setApartmentNumber(object.getString(AppConstants.EVENT_MEALS_APARTMENT_NUMBER));

        eventMeals.setPrice(object.getInt(AppConstants.EVENT_MEALS_PRICE));
        eventMeals.setMenu(object.getString(AppConstants.EVENT_MEALS_MENU));
        eventMeals.setMealsLeft(object.getInt(AppConstants.EVENT_MEALS_QUANTITY));
        //finished the event, now all the event's dishes =\


        return eventMeals;
    }
    @Override
    public void getClosestChefsRadius(final ChefEventDishesFragment.GetEventDishesCallback callback, final LatLng centerLocation) {

        //TO DO:: load 5 at a time

        new AsyncTask<Void, Void, Void>() {
            ArrayList<EventDishes> eventDishesArrayList = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {


                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                LatLng l = centerLocation;
                eventQuery.whereWithinKilometers(AppConstants.EVENT_DISHES_GEO_POINT,
                        new ParseGeoPoint(centerLocation.latitude,centerLocation.longitude)
                        ,3);




                List<ParseObject> tempEventArray;
                try {
                    if( ! (HungryMapFragment.lastCenterStatic.equals(centerLocation ))  )
                        return null;
                    tempEventArray = eventQuery.find();
                    if( ! (HungryMapFragment.lastCenterStatic.equals(centerLocation ))  )
                        return null;
                    for(ParseObject object : tempEventArray)
                    {

                        eventDishesArrayList.add(ParseObjectToEventDishes(object));

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

    @Override
    public void editEventDishes(final EventDishes event, final MyModel.EditEventCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                ParseObject eventObject = null;
                try {
                    eventObject = query.get(event.getEventId());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date startingDate = MeetnEatDates.getDateWithSpecificTimeZone(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay(), event.getStartingHour(), event.getStartingMinute());
                Date endingDate = MeetnEatDates.getDateWithSpecificTimeZone(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay(), event.getEndingHour(), event.getEndingMinute());
                String s = ParseUser.getCurrentUser().getObjectId();
                eventObject.put(AppConstants.EVENT_DISHES_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
                eventObject.put(AppConstants.EVENT_DISHES_START_DATE, startingDate);
                eventObject.put(AppConstants.EVENT_DISHES_END_DATE, endingDate);
                eventObject.put(AppConstants.EVENT_DISHES_LOCATION, event.getLocation());
                eventObject.put(AppConstants.EVENT_DISHES_APARTMENT_NUMBER, event.getApartmentNumber());
                ParseGeoPoint geoPoint = new ParseGeoPoint(event.getLatitude(), event.getLongitude());
                eventObject.put(AppConstants.EVENT_DISHES_GEO_POINT, geoPoint);
                eventObject.put(AppConstants.EVENT_DISHES_TITLE, event.getTitle());
                editDishesForEvent(event.getEventsDishes(),event.getEventId());
                try {
                    eventObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.eventHasBeenEdited();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    @Override
    public void editEventMeals(final EventMeals event, final MyModel.EditEventCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                ParseObject eventObject = null;
                try {
                    eventObject = query.get(event.getEventId());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date startingDate = new Date(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay(), event.getStartingHour(), event.getStartingMinute());
                Date endingDate = new Date(event.getStartingYear(), event.getStartingMonth(), event.getStartingDay(), event.getEndingHour(), event.getEndingMinute());
                String s = ParseUser.getCurrentUser().getObjectId();
                eventObject.put(AppConstants.EVENT_DISHES_CHEF_ID, ParseUser.getCurrentUser().getObjectId());
                eventObject.put(AppConstants.EVENT_DISHES_START_DATE, startingDate);
                eventObject.put(AppConstants.EVENT_DISHES_END_DATE, endingDate);
                eventObject.put(AppConstants.EVENT_DISHES_LOCATION, event.getLocation());
                eventObject.put(AppConstants.EVENT_DISHES_APARTMENT_NUMBER, event.getApartmentNumber());
                ParseGeoPoint geoPoint = new ParseGeoPoint(event.getLatitude(), event.getLongitude());
                eventObject.put(AppConstants.EVENT_DISHES_GEO_POINT, geoPoint);
                eventObject.put(AppConstants.EVENT_DISHES_TITLE, event.getTitle());
                //event meals attributes:
                eventObject.put(AppConstants.EVENT_MEALS_MENU,event.getMenu());
                eventObject.put(AppConstants.EVENT_MEALS_QUANTITY,event.getMealsLeft());
                eventObject.put(AppConstants.EVENT_MEALS_PRICE, event.getPrice());
                try {
                    eventObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callback.eventHasBeenEdited();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    private void editDishesForEvent(ArrayList<Dish> dishes,String eventID)
    {
        for(Dish dish : dishes)
        {
            ParseQuery query = new ParseQuery(AppConstants.DISH);
            ParseObject object = null;
            try {
               object  = query.get(dish.getDishID());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(object == null)
                object = new ParseObject(AppConstants.DISH);
            object.put(AppConstants.DISH_DISHES_LEFT,dish.getQuantityLeft());
            object.put(AppConstants.DISH_DISHES,dish.getQuantityLeft());
            object.put(AppConstants.DISH_PRICE,dish.getPrice());
            object.put(AppConstants.DISH_TITLE,dish.getTitle());
            object.put(AppConstants.DISH_DESCRIPTION,dish.getDescriprion());
            object.put(AppConstants.DISH_EVENT_ID,eventID);
            object.put(AppConstants.DISH_IS_TAKE_AWAY,dish.isTakeAway());
            object.put(AppConstants.DISH_IS_TO_SIT,dish.isToSit());
            if(dish.getFullsizeImg()!=null) {
                ParseFile file = new ParseFile("aFull.jpeg", dish.getFullsizeImg());

                try {
                    file.save();
                    object.put(AppConstants.DISH_IMG_FULL, file);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                ParseFile fileB = new ParseFile("aThmb.jpeg", dish.getThumbnailImg());
                try {
                    fileB.save();
                    object.put(AppConstants.DISH_IMG_THUMBNAIL, fileB);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                object.save();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }
    @Override
    public void getEventsDishes(String id, final MyModel.DishesCallback callback) {
        new AsyncTask<String, Void, Void>() {
            ArrayList<Dish> dishes;
            @Override
            protected Void doInBackground(String... params) {
                String eventID = params[0];
                dishes = getDishesForEvent(eventID);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                callback.dishesAhBeenFetched(dishes);
            }
        }.execute(id);
    }

    @Override
    public void getSpecifiecChefsEventFromServer(final String chefId, final LatLng coordinates, final SpecifiecChefEventsDialogBox.getEventsByType callback) {
        //Get by ChefId + Coordinates
        new AsyncTask<Void, Void, Void>() {
            ArrayList<EventDishes> eventDishesArrayList = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {
                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>(AppConstants.EVENT_DISHES);
                ParseGeoPoint pGp = new ParseGeoPoint(coordinates.latitude,coordinates.longitude);

                eventQuery.whereEqualTo(AppConstants.EVENT_DISHES_GEO_POINT,pGp);
                eventQuery.whereEqualTo(AppConstants.EVENT_MEALS_CHEF_ID,chefId);

                List<ParseObject> tempEventArray;
                try {
                    tempEventArray = eventQuery.find();
                    for (ParseObject object : tempEventArray) {

                        eventDishesArrayList.add(ParseObjectToEventDishes(object));

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

    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

}
