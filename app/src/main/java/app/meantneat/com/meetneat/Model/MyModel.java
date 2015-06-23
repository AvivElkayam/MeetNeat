package app.meantneat.com.meetneat.Model;

import javax.security.auth.callback.Callback;

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
    public ModelInterface getModel()
    {
        return  model;
    }
    public  interface ModelInterface
    {
        public void LoginToMeetNeat(String userName, String password);
        public void signUpToMeetNeat(String userName,String email,String password,SignInActivity.SignUpCallback callback);
        public boolean currentUserConnected();
        public void addNewEventDishesToServer(EventDishes event,EditEventDishesFragment.SaveToServerCallback callback);
        public void addNewEventMealsToServer(EventMeals event,EditEventMealsFragment.SaveToServerCallback callback);
        public void getChefsEventFromServer(ChefEventDishesFragment.GetEventDishesCallback callback);
    }

}
