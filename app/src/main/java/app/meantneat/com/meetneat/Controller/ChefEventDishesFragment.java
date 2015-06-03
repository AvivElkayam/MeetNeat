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

import app.meantneat.com.meetneat.Event;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class ChefEventDishesFragment extends Fragment
{
    private ArrayList<Event> eventArrayList;
    private ListView eventsListView;
    private EventRowListAdapter eventsArrayAdapter;
    View view;
    public class EventRowListAdapter extends ArrayAdapter<Event>
    {
        public EventRowListAdapter()
        {
            super(getActivity(), R.layout.chef_event_dishes_fragment_list_view_row, eventArrayList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_event_dishes_fragment_list_view_row,parent,false);
            }
            Event event = eventArrayList.get(position);
            String time = event.getTime();
            String date = event.getDate();
            String dishesLeft = "Dishes left: "+event.getDishesLeft();
            String title = event.getTitle();


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

        eventArrayList = new ArrayList<>();
        Event event1 = new Event("חומוס פול","21.2.2015","22:00",4);
        Event event2 = new Event("קובה סלק","17.2.2015","19:00",3);
        Event event3 = new Event("מוקפץ תאילנדי","17.2.2015","14:00",8);
        Event event4 = new Event("ספגטי בולונז","17.2.2015","19:00",5);
        eventArrayList.add(event1);
        eventArrayList.add(event2);
        eventArrayList.add(event3);
        eventArrayList.add(event4);
        eventsListView =(ListView)getActivity().findViewById(R.id.chef_fragment_events_list_view);
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
                    .replace(R.id.chef_fragment_container,new AddDishEventFragment(), "add_dish_event")
                            // Add this transaction to the back stack
                    .addToBackStack("add_dish_event")
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }


}
