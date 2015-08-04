package app.meantneat.com.meetneat.Controller.MainAndSettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.meantneat.com.meetneat.Controller.Login.SignInActivity;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/28/15.
 */
public class SettingsFragment extends Fragment {
    Button logOutButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment,container,false);
        logOutButton = (Button)v.findViewById(R.id.settings_activity_logout_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyModel.getInstance().getModel().logOut();
                Intent intent = new Intent(getActivity(),SignInActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
