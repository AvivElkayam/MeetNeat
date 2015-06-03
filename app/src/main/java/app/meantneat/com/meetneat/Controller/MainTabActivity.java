package app.meantneat.com.meetneat.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class MainTabActivity extends ActionBarActivity {
private FragmentTabHost mTabHost;
    Fragment chefFragment;
    Fragment hungryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!MyModel.getInstance().getModel().currentUserConnected())
        {
           Intent intent = new Intent(this,SignInActivity.class);
           startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main_tab);
        chefFragment = new ChefFragment();
        hungryFragment = new HungryFragment();
        Button chefButton = (Button) findViewById(R.id.activity_main_tab_chef_button);
        Button hungryButton = (Button) findViewById(R.id.activity_main_tab_hungry_button);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_tabs_container, hungryFragment, "hungry")
                        // Add this transaction to the back stack
                .addToBackStack("add_hungry")
                .commit();

        chefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.activity_main_tabs_container, chefFragment, "chef")
                                // Add this transaction to the back stack
                        .addToBackStack("replace_to_chef")
                        .commit();
            }


        });

        hungryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_tabs_container, hungryFragment)
                                // Add this transaction to the back stack
                        .addToBackStack("replace_to_hungry")
                        .commit();
            }


        });
        //initTabs();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
//    private void initTabs()
//    {
//
//        mTabHost = (FragmentTabHost)findViewById(R.id.mainTabHost);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.mainTabHostContainer);
//
//
//        mTabHost.addTab(mTabHost.newTabSpec("HungryFragment").setIndicator("Hungry"),
//                HungryFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("ChefFragment").setIndicator("Chef"),ChefFragment.class,null);
//
//        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
//        {
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E0E0E0")); //unselected
//        }
//        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);
//
//        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//
//                for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
//                {
//                    mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#E0E0E0")); //unselected
//                }
//                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);
//            }
//
//
//        });
//
//    }
}
