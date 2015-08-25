package app.meantneat.com.meetneat.Controller.MainAndSettings;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Controller.Chef.ChefFragment;
import app.meantneat.com.meetneat.Controller.Hungry.HungryFragment;
import app.meantneat.com.meetneat.Controller.Login.SignInActivity;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class MainTabActivity extends ActionBarActivity {
    private FragmentTabHost mTabHost;
    private Fragment chefFragment;
    private Fragment hungryFragment;
    private Fragment settingsFragment;
    private CameraBasics cameraBasics;
    private FloatingActionButton actionButton;
    private FloatingActionMenu actionMenu;
    private String[] mPlanetTitles = {"Chef","Hungry","Settings"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<DrawerItem> drawerItems;
    private DrawerRowListAdapter adapter;
    public class DrawerRowListAdapter extends ArrayAdapter<DrawerItem>
    {
        public DrawerRowListAdapter()
        {
            super(MainTabActivity.this, R.layout.main_activity_navigation_drawer__row_layout, drawerItems);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getLayoutInflater().inflate(R.layout.main_activity_navigation_drawer__row_layout,parent,false);
            }
            DrawerItem drawerItem = drawerItems.get(position);
            TextView textView = (TextView)itemView.findViewById(R.id.navigation_drawer_text_view);
            textView.setText(drawerItem.getTitle());
            ImageView imageView = (ImageView)itemView.findViewById(R.id.navigation_drawer_image_view);
            imageView.setBackground(getResources().getDrawable(drawerItem.getDrawable()));
             switch (position)
            {
                case 0:
                {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.activity_main_tabs_container, hungryFragment)
                                            // Add this transaction to the back stack
                                    .addToBackStack("replace_to_hungry")
                                    .commit();
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                    });
                    break;
                }
                case 1:
                {
                  itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          getSupportFragmentManager().beginTransaction()
                                  .replace(
                                          R.id.activity_main_tabs_container, chefFragment, "chef")
                                          // Add this transaction to the back stack
                                  .addToBackStack("replace_to_chef")
                                  .commit();
                          mDrawerLayout.closeDrawer(Gravity.LEFT);
                      }
                  });
                    break;
                }
                case 2:
                {
                    break;
                }
            }

            return itemView;
        }
    }
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
//
//    /** Swaps fragments in the main content view */
//    private void selectItem(int position) {
//        // Create a new fragment and specify the planet to show based on position
//        Fragment fragment = new HungryFragment();
////        Bundle args = new Bundle();
////        args.putInt(HungryFragment.ARG_PLANET_NUMBER, position);
////        fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
//    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!MyModel.getInstance().getModel().currentUserConnected()) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main_tab);
        chefFragment = new ChefFragment();
        hungryFragment = new HungryFragment();
        settingsFragment = new SettingsFragment();
        //initTabsMenu();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.eat_green)));
        getSupportActionBar().setElevation(0);
        initNavigationDrawer();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_tabs_container, hungryFragment)
                        // Add this transaction to the back stack
                .addToBackStack("replace_to_hungry")
                .commit();
        //code for set view as header for action bar

//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setCustomView(R.layout.chef_edit_event_dishes_fragment_dish_row);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
        // getSupportFragmentManager().addOnBackStackChangedListener(getListener());
//        Button chefButton = (Button) findViewById(R.id.activity_main_tab_chef_button);
//        Button hungryButton = (Button) findViewById(R.id.activity_main_tab_hungry_button);
//
//


//        chefButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(
//                                R.id.activity_main_tabs_container, chefFragment, "chef")
//                                // Add this transaction to the back stack
//                        .addToBackStack("replace_to_chef")
//                        .commit();
//            }
//
//
//        });
//
//        hungryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.activity_main_tabs_container, hungryFragment)
//                                // Add this transaction to the back stack
//                        .addToBackStack("replace_to_hungry")
//                        .commit();
//            }
//
//
//        });
        //initTabs();


    }


    private void initTabsMenu() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_tabs_container, hungryFragment, "hungry")
                        // Add this transaction to the back stack
                .addToBackStack("add_hungry")
                .commit();

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.menu_icon));

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageDrawable(getResources().getDrawable(R.drawable.forks_tab_yellow));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_tabs_container, hungryFragment)
                                // Add this transaction to the back stack
                        .addToBackStack("replace_to_hungry")
                        .commit();
                actionMenu.toggle(true);

            }
        });

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.chef_48_yellow));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(
                                R.id.activity_main_tabs_container, chefFragment, "chef")
                                // Add this transaction to the back stack
                        .addToBackStack("replace_to_chef")
                        .commit();
                actionMenu.toggle(true);

            }
        });

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.settings_tab_yellow));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_tabs_container, settingsFragment)
                                // Add this transaction to the back stack
                        .addToBackStack("replace_to_settings")
                        .commit();
                actionMenu.toggle(true);

            }
        });
        actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                        // ...
                .attachTo(actionButton)
                .build();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //cameraBasics.myOnActivityResult(requestCode,resultCode,data);
        // eventImageView.setImageBitmap(b);
//        if (requestCode == REQUEST_IMAGE_CAPTURE) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            eventImageView.setImageBitmap(imageBitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_tab, menu);
        return true;
    }



    @Override
    public void onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then emulate 'onBackPressed' behaviour, because in default, it is not working
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if(frag == null)
                continue;
            if (frag.getTag() == "chef") {
                FragmentManager childFm = frag.getChildFragmentManager();

                int k = childFm.getBackStackEntryCount();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    //call the popped fragment on resume
                    Fragment fragment = childFm.getFragments()
                            .get(childFm.getBackStackEntryCount() - 1);
                    fragment.onResume();
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();
                if (manager != null) {
                    int backStackEntryCount = manager.getBackStackEntryCount();
                    if (backStackEntryCount == 0) {
                        finish();
                    }
                    Fragment fragment = manager.getFragments()
                            .get(backStackEntryCount - 1);
                    fragment.onResume();
                }
            }
        };
        return result;
    }

    private void initNavigationDrawer() {


        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_tab_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.main_tab_drawer_list_view);
        drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem("Hungry",R.drawable.forks_tab_yellow));
        drawerItems.add(new DrawerItem("Chef",R.drawable.chef_48_yellow));
        adapter = new DrawerRowListAdapter();
        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                null, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}

