package app.meantneat.com.meetneat.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.EventMeals;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class ChefEventMealsFragment extends Fragment
{
    private ArrayList<EventMeals> eventMealsArrayList;
    private ListView eventsListView;
    private EventRowListAdapter eventsArrayAdapter;
    View view;
    public class EventRowListAdapter extends ArrayAdapter<EventMeals>
    {
        public EventRowListAdapter()
        {
            super(getActivity(), R.layout.chef_event_meals_fragment_list_view_row, eventMealsArrayList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_event_meals_fragment_list_view_row,parent,false);
            }
            EventMeals eventMeal = eventMealsArrayList.get(position);
            String time = eventMeal.getTime();
            String date = eventMeal.getDate();
            String mealsLeft = "Meals left: "+ eventMeal.getDishesLeft();
            String totalMeals = "Total meals: "+eventMeal.getTotalDishes();
            String title = eventMeal.getTitle();



            TextView titleTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_title_text_view);
            titleTextView.setText(title);
            TextView dateTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_date_text_view);
            dateTextView.setText(date);
            TextView timeTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_time_text_view);
            timeTextView.setText(time);
            TextView mealsLeftTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_meals_left_text_view);
            mealsLeftTextView.setText(mealsLeft);
            TextView totalMealsTextView = (TextView)itemView.findViewById(R.id.chef_event_meals_row_total_meals_text_view);
            totalMealsTextView.setText(totalMeals);

            Button editButton = (Button) itemView.findViewById(R.id.chef_event_meals_row_edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chef_event_meals_fragment_container, new EditEventMealsFragment(), "edit_meal_event")
                            // Add this transaction to the back stack
                    .addToBackStack("edit_meal_event")
                    .commit();
                }
            });
            return itemView;
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initViews();
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
        EventMeals eventDishes1 = new EventMeals("חגיגה אסיאתית","21.2.2015","22:00",4,2);
        EventMeals eventDishes2 = new EventMeals("חינגה לובית","17.2.2015","19:00",15,8);

        eventMealsArrayList.add(eventDishes1);
        eventMealsArrayList.add(eventDishes2);

        eventsListView =(ListView)getActivity().findViewById(R.id.chef_events_meals_list_view);
        eventsArrayAdapter = new EventRowListAdapter();
        eventsListView.setAdapter(eventsArrayAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //inflater.inflate(R.menu.chef_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.chef_fragment_menu_add_button)
//        {
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.chef_event_meals_fragment_container,new AddDishEventFragment(), "add_dish_event")
//                            // Add this transaction to the back stack
//                    .addToBackStack("add_dish_event")
//                    .commit();
//        }

        return super.onOptionsItemSelected(item);
    }


}
