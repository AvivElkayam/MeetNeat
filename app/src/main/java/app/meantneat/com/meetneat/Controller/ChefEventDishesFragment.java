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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_event_dishes_fragment_list_view_row,parent,false);
            }
            EventDishes event = eventDishesArrayList.get(position);

            String title = event.getTitle();
            EventDishes eventDishes = eventDishesArrayList.get(position);




            TextView titleTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_title_text_view);
            titleTextView.setText(title);
            TextView dateTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_date_text_view);
            dateTextView.setText(event.getEventDay()+"."+event.getEventMonth()+"."+event.getEventYear());
            TextView timeTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_time_text_view);
            //timeTextView.setText(time);

            timeTextView.setText(event.getStartingHour()+":"+event.getStartingMinute()+" - "+event.getEndingHour()+":"+event.getEndingMinute());
            TextView dishesLeftTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_location_text_view);
            dishesLeftTextView.setText(event.getLocation());
            Button editButton = (Button)itemView.findViewById(R.id.chef_fragment_row_edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                packDataToBundleAndPassToEditScreen(position);

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
            view = inflater.inflate(R.layout.chef_event_dishes_fragment_layout,container,false);
        return view;
    }
    private void initViews()
    {

        eventDishesArrayList = new ArrayList<>();
//        Event event1 = new Event("חומוס פול","21.2.2015","22:00",4);
//        Event event2 = new Event("קובה סלק","17.2.2015","19:00",3);
//        Event event3 = new Event("מוקפץ תאילנדי","17.2.2015","14:00",8);
//        Event event4 = new Event("ספגטי בולונז","17.2.2015","19:00",5);
//        eventArrayList.add(event1);
        EventDishes event1 = new EventDishes("שישי בשכונה",12,00,16,00,2015,3,12,"תל אביב, אבן גבירול","22","",null);
        EventDishes event2 = new EventDishes("שאריות משבת",8,00,13,00,2015,3,12,"תל אביב,שאול המלך","11","",null);

        eventDishesArrayList.add(event1);
        eventDishesArrayList.add(event2);

//        eventArrayList.add(event2);
//        eventArrayList.add(event3);
//        eventArrayList.add(event4);
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
    private void packDataToBundleAndPassToEditScreen(int index)
    {
        EventDishes eventDishes = eventDishesArrayList.get(index);
        Bundle bundle = new Bundle();
        bundle.putInt("year", eventDishes.getEventYear());
        bundle.putInt("month", eventDishes.getEventMonth());
        bundle.putInt("day", eventDishes.getEventDay());
        bundle.putInt("starting_hour", eventDishes.getStartingHour());
        bundle.putInt("starting_minute", eventDishes.getStartingMinute());
        bundle.putInt("ending_hour", eventDishes.getEndingHour());
        bundle.putInt("ending_minute", eventDishes.getEndingMinute());
        bundle.putString("title", eventDishes.getTitle());
        bundle.putString("location",eventDishes.getLocation());
        bundle.putString("apartment_number", eventDishes.getApartmentNumber());
        bundle.putBoolean("is_new", false);
        EditEventDishesFragment fragment = new EditEventDishesFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.chef_event_dishes_fragment_container, fragment, "add_event")
                .commit();
    }

}
