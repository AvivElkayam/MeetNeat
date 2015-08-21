package app.meantneat.com.meetneat.Controller.Chef;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.Entities.EventMeals;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class ChefEventMealsFragment extends Fragment
{
    private ArrayList<EventMeals> eventMealsArrayList;
    private ListView eventsListView;
    private EventRowListAdapter eventsArrayAdapter;
    private View view;
    private FloatingActionButton floatingAddButton;

    public class EventRowListAdapter extends ArrayAdapter<EventMeals>
    {
        public EventRowListAdapter()
        {
            super(getActivity(), R.layout.chef_event_meals_fragment_list_view_row, eventMealsArrayList);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_event_meals_fragment_list_view_row,parent,false);
            }
            EventMeals eventMeal = eventMealsArrayList.get(position);
            //String time = eventMeal.getTime();
            //String date = eventMeal.getDate();
            //String mealsLeft = "Meals left: "+ eventMeal.getDishesLeft();
            String totalMeals = "Total meals: "+eventMeal.getMealsLeft();



            TextView titleTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_title_text_view);
            titleTextView.setText(eventMeal.getTitle());
            TextView dateTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_date_text_view);
            dateTextView.setText(MeetnEatDates.getDateString(eventMeal.getStartingYear(),eventMeal.getStartingMonth(),eventMeal.getStartingDay()));

            TextView timeTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_time_text_view);
            timeTextView.setText(MeetnEatDates.getTimeString(eventMeal.getStartingHour(),eventMeal.getStartingMinute()));
            TextView mealsLeftTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_meals_left_text_view);
            mealsLeftTextView.setText(totalMeals);
//            TextView totalMealsTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_total_meals_text_view);
//            totalMealsTextView.setText("Total meals: "+totalMeals);

            Button editButton = (Button) itemView.findViewById(R.id.chef_event_meals_row_edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                packDataToBundleAndPassToEditScreen(position);
                }
            });
            return itemView;
        }
    }
    private void initAddEventButton()
    {
        floatingAddButton = (FloatingActionButton) getActivity().findViewById(R.id.chef_events_meals_floating_add_button);
        floatingAddButton.attachToListView(eventsListView);
        floatingAddButton.setColorNormal(getResources().getColor(R.color.eat_orange));
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragment().getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddDishEventFragment fragment = new AddDishEventFragment();
                Bundle bundle = new Bundle();
                bundle.putString("whereToGo",AddDishEventFragment.goToMeals);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.chef_event_meals_fragment_container, fragment).addToBackStack("addNewEventMeals");
                fragmentTransaction.commit();
                floatingAddButton.hide();
            }
        });
    }
    private void getEventMealsFromServer()
    {
        MyModel.getInstance().getModel().getChefsEventMealsFromServer(new GetEventMealsCallback() {
            @Override
            public void done(ArrayList<EventMeals> eventDisheses) {
                eventMealsArrayList.clear();
                eventMealsArrayList.addAll(eventDisheses);
                eventsArrayAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
//                progressBar.setVisibility(View.GONE);

            }
        });

    }
    public interface GetEventMealsCallback
    {
        public void done(ArrayList<EventMeals> eventMeals);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initViews();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEventMealsFromServer();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(view==null)
            view = inflater.inflate(R.layout.chef_event_meals_fragment_layout,container,false);
        return view;
    }
    private void initViews()
    {

        eventMealsArrayList = new ArrayList<>();

        eventsListView =(ListView)getActivity().findViewById(R.id.chef_events_meals_list_view);
        eventsArrayAdapter = new EventRowListAdapter();
        eventsListView.setAdapter(eventsArrayAdapter);
        initAddEventButton();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.chef_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.chef_fragment_menu_add_button)
//        {
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.chef_event_meals_fragment_container,new AddMealsEventFragment(), "add_meal_event")
//                            // Add this transaction to the back stack
//                    .addToBackStack("add_meal_event")
//                    .commit();
//        }

        return super.onOptionsItemSelected(item);
    }

    private void packDataToBundleAndPassToEditScreen(int index)
    {
        EventMeals eventMeals = eventMealsArrayList.get(index);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.EVENT_STARTING_YEAR, eventMeals.getStartingYear());
        bundle.putInt(AppConstants.EVENT_STARTING_MONTH, eventMeals.getStartingMonth());
        bundle.putInt(AppConstants.EVENT_STARTING_DAY, eventMeals.getStartingDay());
        bundle.putInt(AppConstants.EVENT_STARTING_HOUR, eventMeals.getStartingHour());
        bundle.putInt(AppConstants.EVENT_STARTING_MINUTE, eventMeals.getStartingMinute());
        bundle.putInt(AppConstants.EVENT_ENDING_YEAR, eventMeals.getEndingYear());
        bundle.putInt(AppConstants.EVENT_ENDING_MONTH, eventMeals.getEndingMonth());
        bundle.putInt(AppConstants.EVENT_ENDING_DAY, eventMeals.getEndingDay());
        bundle.putInt(AppConstants.EVENT_ENDING_HOUR, eventMeals.getEndingHour());
        bundle.putInt(AppConstants.EVENT_ENDING_MINUTE, eventMeals.getEndingMinute());
        bundle.putString(AppConstants.EVENT_TITLE, eventMeals.getTitle());
        bundle.putString(AppConstants.EVENT_LOCATION,eventMeals.getLocation());
        bundle.putString(AppConstants.EVENT_APARTMENT_NUMBER, eventMeals.getApartmentNumber());
        bundle.putBoolean(AppConstants.IS_NEW, false);
        bundle.putString(AppConstants.EVENT_ID,eventMeals.getEventId());
        //these fields are only for EVENT MEALS
        bundle.putInt(AppConstants.EVENT_PRICE,eventMeals.getPrice());
        bundle.putInt(AppConstants.EVENT_MEALS_LEFT,eventMeals.getMealsLeft());
        bundle.putString(AppConstants.EVENT_MENU,eventMeals.getMenu());
//        ArrayList<String> dishesIDArrayList = new ArrayList<>();
//        for(Dish dish : eventMeals.getEventsDishes())
//        dishesIDArrayList.add(dish.getDishID());
//        bundle.putStringArrayList("dishes",dishesIDArrayList);
//        EditEventDishesFragment fragment = new EditEventDishesFragment();
//        fragment.setArguments(bundle);
//        getParentFragment().getChildFragmentManager().beginTransaction()
//                .add(R.id.chef_event_dishes_fragment_container, fragment, "add_event")
//                .addToBackStack("1")
//                .commit();
        EditEventMealsFragment fragment = new EditEventMealsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.chef_event_meals_fragment_container, fragment, "edit_meal_event")
                        // Add this transaction to the back stack
                .addToBackStack("edit_meal_event")
                .commit();
    }
}
