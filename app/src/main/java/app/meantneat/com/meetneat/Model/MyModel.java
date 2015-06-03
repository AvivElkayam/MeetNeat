package app.meantneat.com.meetneat.Model;

import javax.security.auth.callback.Callback;

import app.meantneat.com.meetneat.Controller.SignInActivity;

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
    }

}
