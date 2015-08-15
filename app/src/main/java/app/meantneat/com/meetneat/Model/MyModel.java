package app.meantneat.com.meetneat.Model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import app.meantneat.com.meetneat.Camera.SpecificEventDishesDialogBox;
import app.meantneat.com.meetneat.Camera.SpecifiecChefEventsDialogBox;
import app.meantneat.com.meetneat.Controller.Chef.ChefEventDishesFragment;
import app.meantneat.com.meetneat.Controller.Chef.ChefEventMealsFragment;
import app.meantneat.com.meetneat.Controller.Chef.EditEventDishesFragment;
import app.meantneat.com.meetneat.Controller.Chef.EditEventMealsFragment;
import app.meantneat.com.meetneat.Controller.Login.LoginActivity;
import app.meantneat.com.meetneat.Controller.Login.SignInActivity;

import app.meantneat.com.meetneat.Entities.Dish;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.Entities.EventMeals;

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
        public void LoginToMeetNeat(String userName, String password,LoginActivity.LoginCallback callback);

        public void signUpToMeetNeat(String userName, String email, String password,Bitmap bitmap, SignInActivity.SignUpCallback callback);

        public boolean currentUserConnected();

        public void addNewEventDishesToServer(EventDishes event, EditEventDishesFragment.SaveToServerCallback callback);

        public void addNewEventMealsToServer(EventMeals event, EditEventMealsFragment.SaveToServerCallback callback);

        public void getChefsEventDishesFromServer(ChefEventDishesFragment.GetEventDishesCallback callback);
        public void getChefsEventMealsFromServer(ChefEventMealsFragment.GetEventMealsCallback callback);

        public void getDishEventDetailsByID(String eventID, SpecificEventDishesDialogBox.DishEventCallback callback);

        public void getChefPicture(String chefID, PictureCallback callback);

        public void getDishPicture(String dishID, PictureCallback callback);
        public void logOut();
        void getClosestChefsRadius(ChefEventDishesFragment.GetEventDishesCallback callback, LatLng centerLocation);
        public void editEvent(EventDishes event,EditEventCallback callback);
        public void getEventsDishes(String id,DishesCallback callback);
        //Hungry - OnClick chef-Map
        public void getSpecifiecChefsEventFromServer(String chefId, LatLng coordinates,
                                                     SpecifiecChefEventsDialogBox.getEventsByType callback);


    }
    public interface EditEventCallback
    {
        public void eventHasBeenEdited();
    }
    public interface PictureCallback
    {
        public void pictureHasBeenFetched(Bitmap bitmap);
    }
    public interface DishesCallback
    {
        public void dishesAhBeenFetched(ArrayList<Dish> dishes);
    }

}
