package app.meantneat.com.meetneat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddEventFragment extends Fragment {
    private TextView startingTimeTextView,startingDateTextView,endingTimeTextView,endingDateTextView;
    private EditText titleEditText,priceEditText,quantityEditText,descriptionEditText;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int startingYear,startingMonth,startingDay,startingHour,startingMinute;
    private int endingYear,endingMonth,endingDay,endingHour,endingMinute;
    private Calendar calendar;
    private ListView dishesListView;
    private DishRowListAdapter dishRowListAdapter;
    private ArrayList<Dish> dishArrayList;
    private ListView eventsListView;
    private DishRowListAdapter eventsArrayAdapter;
    public class DishRowListAdapter extends ArrayAdapter<Dish>
    {
        public DishRowListAdapter()
        {
            super(getActivity(),R.layout.add_event_fragment_dish_row, dishArrayList);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.add_event_fragment_dish_row,parent,false);
            }
            final Dish dish = dishArrayList.get(position);
            final String title = dish.getName();
            String price = "Price: "+dish.getPrice();
            String dishesLeft = "Dishes left: "+dish.getQuantity();
            final String description = dish.getDescriprion();


            TextView titleTextView = (TextView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_title_text_view);
            titleTextView.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titleEditText.setText(title);
                    priceEditText.setText(Double.toString(dish.getPrice()));
                    quantityEditText.setText(Double.toString(dish.getQuantity()));
                    descriptionEditText.setText(description);
                }
            });
            return itemView;
        }
    }
    public static AddEventFragment newInstance(String param1, String param2) {
        AddEventFragment fragment = new AddEventFragment();

        return fragment;
    }

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event_fragment, container, false);

        // Inflate the layout for this fragment
        return view;
    }
private void initViews()
{
    calendar=Calendar.getInstance();
    startingTimeTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_starting_time_label);
    startingDateTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_starting_date_label);
    endingTimeTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_ending_time_label);
    endingDateTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_ending_date_label);

    titleEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_title_edit_text);
    priceEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_price_edit_text);
    quantityEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_dishes_left_edit_text);
    descriptionEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_description_edit_text);

    startingDateTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    TextView textView = (TextView)v;
                    startingYear=year;
                    startingMonth=monthOfYear;
                    startingDay=dayOfMonth;
                    ((TextView) v).setText(dayOfMonth+"."+monthOfYear+"."+year);
                }
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }
    });
    startingTimeTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            timePickerDialog = new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startingHour = hourOfDay;
                    startingMinute=minute;
                    ((TextView) v).setText(hourOfDay+":"+minute);
                }
            },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
            timePickerDialog.show();
        }
    });
    endingDateTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    TextView textView = (TextView)v;
                    endingYear=year;
                    endingMonth=monthOfYear;
                    endingDay=dayOfMonth;
                    ((TextView) v).setText(dayOfMonth+"."+monthOfYear+"."+year);
                }
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }
    });
    endingTimeTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            timePickerDialog = new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    endingHour = hourOfDay;
                    endingMinute=minute;
                    ((TextView) v).setText(hourOfDay+":"+minute);
                }
            },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
            timePickerDialog.show();
        }
    });
    initListView();

}

    private void initListView() {
        dishArrayList = new ArrayList<>();
        dishArrayList.add(new Dish("פרגית במחבת","מנה טעימה ומשביעה עם טעמים עשירים",34,7,true,true,null));
        dishArrayList.add(new Dish("שניצל דה דיינר","שניצל קלאסי עם רוטב טעים",27.90,9,true,true,null));
        dishArrayList.add(new Dish("סלט פלחים","מבחר ירקות העונה חתוכים גס",19,10,true,true,null));
        dishArrayList.add(new Dish("שרימפס חמאה ושום","מנת שרימפס קלאסי עם רוטב מנצח",46.90,9,true,true,null));

        dishRowListAdapter = new DishRowListAdapter();
        dishesListView = (ListView)getActivity().findViewById(R.id.add_event_fragment_list_view);
        dishesListView.setAdapter(dishRowListAdapter);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.chef_fragment_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
}
