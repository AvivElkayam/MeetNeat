package app.meantneat.com.meetneat.Controller.MainAndSettings;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Controller.Chef.ChefFragment;
import app.meantneat.com.meetneat.Controller.Hungry.HungryFragment;
import app.meantneat.com.meetneat.Controller.Login.SignInActivity;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class MainTabActivity extends ActionBarActivity {
private FragmentTabHost mTabHost;
    private Fragment chefFragment;
    private Fragment hungryFragment;
    private Fragment settingsFragment;
    private CameraBasics cameraBasics;
    private FloatingActionButton actionButton;
    FloatingActionMenu actionMenu;
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
        settingsFragment = new SettingsFragment();
        initTabsMenu();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.eat_green)));
        getSupportActionBar().setElevation(0);


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

    @Override
    public void onBackPressed() {
        // if there is a fragment and the back stack of this fragment is not empty,
        // then emulate 'onBackPressed' behaviour, because in default, it is not working
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.getTag() == "chef"){
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
    }}



