package app.meantneat.com.meetneat.Model;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by mac on 5/25/15.
 */
public class MeenNeatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Px7JepEgOgDBQkQHKqxXXkChkwb6SxSUyEp9ZFks", "XJxLvJFHVXOjtnNYWrNPP8b66JLvakZ9VrfNufVz");
    }
}
