package app.meantneat.com.meetneat.Controller;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import app.meantneat.com.meetneat.Camera.SpecificEventDishesDialogBox;
import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 5/17/15.
 */
public class ChefEventDishesFragment extends Fragment
{
    private ArrayList<EventDishes> eventDishesArrayList;
    private ListView eventsListView;
    private EventRowListAdapter eventsArrayAdapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    FloatingActionButton floatingAddButton;
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




            TextView titleTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_title_text_view);
            titleTextView.setText(title);

            TextView dateTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_date_text_view);
            dateTextView.setText(event.getEventDay()+"."+event.getEventMonth()+"."+event.getEventYear());

            TextView timeTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_time_text_view);
            timeTextView.setText(event.getStartingHour()+":"+event.getStartingMinute()+" - "+event.getEndingHour()+":"+event.getEndingMinute());

            TextView locationTextView = (TextView)itemView.findViewById(R.id.chef_fragment_row_location_text_view);
            locationTextView.setText(event.getLocation());

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
        initSwipeRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(view==null)
            view = inflater.inflate(R.layout.chef_event_dishes_fragment_layout,container,false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEventDishesFromServer();
    }
    private void getEventDishesFromServer()
    {
        MyModel.getInstance().getModel().getChefsEventFromServer(new GetEventDishesCallback() {
            @Override
            public void done(ArrayList<EventDishes> eventDisheses) {
                eventDishesArrayList.clear();
                eventDishesArrayList.addAll(eventDisheses);
                eventsArrayAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);

            }
        });

    }
    public interface GetEventDishesCallback
    {
        public void done(ArrayList<EventDishes> eventDisheses);
    }
    private void initViews()
    {

        eventDishesArrayList = new ArrayList<>();
//        EventDishes event1 = new EventDishes("שישי בשכונה",12,00,16,00,2015,3,12,"תל אביב, אבן גבירול","22","",null,0,0);
//        EventDishes event2 = new EventDishes("שאריות משבת",8,00,13,00,2015,3,12,"תל אביב,שאול המלך","11","",null,0,0);
//
//        eventDishesArrayList.add(event1);
//        eventDishesArrayList.add(event2);
        eventsListView =(ListView)getActivity().findViewById(R.id.chef_events_dishes_list_view);
        eventsArrayAdapter = new EventRowListAdapter();
        eventsListView.setAdapter(eventsArrayAdapter);

        progressBar = (ProgressBar)getActivity().findViewById(R.id.chef_events_dishes_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        floatingAddButton = (FloatingActionButton) getActivity().findViewById(R.id.chef_events_dishes_floating_add_button);
        floatingAddButton.attachToListView(eventsListView);
        floatingAddButton.setColorNormal(getResources().getColor(R.color.eat_orange));
        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddDishEventFragment fragment = new AddDishEventFragment();
                fragmentTransaction.add(R.id.chef_event_dishes_fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.chef_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


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
        getChildFragmentManager().beginTransaction()
                .replace(R.id.chef_event_dishes_fragment_container, fragment, "add_event")
                .commit();
    }
    private void initSwipeRefresh()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.chef_events_dishes_swipe_to_refresh);
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                getEventDishesFromServer();
            }
        });
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(R.color.eat_black,
                R.color.eat_orange,
                R.color.eat_white
                );


    }
}
