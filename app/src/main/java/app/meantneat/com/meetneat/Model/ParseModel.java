package app.meantneat.com.meetneat.Model;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Controller.SignInActivity;

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

}
