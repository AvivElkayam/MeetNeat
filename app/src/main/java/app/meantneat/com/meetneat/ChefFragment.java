package app.meantneat.com.meetneat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mac on 5/17/15.
 */
public class ChefFragment extends Fragment
{   private Button addEvent;
    private ArrayList<Event> eventArrayList;
    private ListView eventsListView;
    private EventRowListAdapter eventsArrayAdapter;
    public class EventRowListAdapter extends ArrayAdapter<Event>
    {
        public EventRowListAdapter()
        {
            super(getActivity(),R.layout.chef_fragment_list_view_row, eventArrayList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_fragment_list_view_row,parent,false);
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
        initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        View view = inflater.inflate(R.layout.chef_fragment_layout,container,false);

        return view;
    }
    private void initViews()
    {
        addEvent =(Button)getActivity().findViewById(R.id.chef_fragment_add_event_button);
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
}
