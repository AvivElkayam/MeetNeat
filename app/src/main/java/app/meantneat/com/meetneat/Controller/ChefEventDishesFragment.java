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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class ChefEventDishesFragment extends Fragment
{
    private ArrayList<EventDishes> eventDishesArrayList;
    private ListView eventsListView;
    private EventRowListAdapter eventsArrayAdapter;
    View view;
    public class EventRowListAdapter extends ArrayAdapter<EventDishes>
    {
        public EventRowListAdapter()
        {
            super(getActivity(), R.layout.chef_event_dishes_fragment_list_view_row, eventDishesArrayList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_event_dishes_fragment_list_view_row,parent,false);
            }
            EventDishes eventDishes = eventDishesArrayList.get(position);
            String time = eventDishes.getTime();
            String date = eventDishes.getDate();
            String dishesLeft = "Dishes left: "+ eventDishes.getDishesLeft();
            String title = eventDishes.getTitle();


            TextView titleTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_title_text_view);
            titleTextView.setText(title);
            TextView dateTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_date_text_view);
            dateTextView.setText(date);
            TextView timeTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_time_text_view);
            timeTextView.setText(time);
            TextView dishesLeftTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_dishes_left_text_view);
            dishesLeftTextView.setText(dishesLeft);
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
            view = inflater.inflate(R.layout.chef_event_dishes_fragment_layout,container,false);
        return view;
    }
    private void initViews()
    {

        eventDishesArrayList = new ArrayList<>();
        EventDishes eventDishes1 = new EventDishes("חומוס פול","21.2.2015","22:00",4);
        EventDishes eventDishes2 = new EventDishes("קובה סלק","17.2.2015","19:00",3);
        EventDishes eventDishes3 = new EventDishes("מוקפץ תאילנדי","17.2.2015","14:00",8);
        EventDishes eventDishes4 = new EventDishes("ספגטי בולונז","17.2.2015","19:00",5);
        eventDishesArrayList.add(eventDishes1);
        eventDishesArrayList.add(eventDishes2);
        eventDishesArrayList.add(eventDishes3);
        eventDishesArrayList.add(eventDishes4);
        eventsListView =(ListView)getActivity().findViewById(R.id.chef_events_dishes_list_view);
        eventsArrayAdapter = new EventRowListAdapter();
        eventsListView.setAdapter(eventsArrayAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.chef_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.chef_fragment_menu_add_button)
        {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chef_event_dishes_fragment_container,new AddDishEventFragment(), "add_dish_event")
                            // Add this transaction to the back stack
                    .addToBackStack("add_dish_event")
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }


}
