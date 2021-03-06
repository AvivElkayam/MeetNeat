package app.meantneat.com.meetneat.Controller.Chef;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/1/15.
 */
public class AddMealsEventFragment extends Fragment {
    TextView dateTextView,startingTime;
    EditText location,apartmentNumber,titleEditText,mealsAmountText;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    int startingHour,startingMinute,endingHour,endingMinute;
    int eventYear,eventMonth,eventDay;
    int mealsAmount;
    Calendar calendar;
    View v;
    Button continueButton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chef_add_event_meals_fragment_layout, container, false);
        calendar=Calendar.getInstance();
        dateTextView = (TextView)v.findViewById(R.id.add_meals_event_fragment_date_text_view_id);
        startingTime = (TextView)v.findViewById(R.id.add_meals_event_fragment_starting_time_text_view_id);
        mealsAmountText = (EditText) v.findViewById(R.id.add_meals_event_fragment_meals_amount_text_view_id);
        location = (EditText)v.findViewById(R.id.add_meals_event_fragment_location_text_view_id);
        apartmentNumber = (EditText)v.findViewById(R.id.add_meals_event_fragment_appartment_number_text_view_id);
        titleEditText = (EditText)v.findViewById(R.id.add_meals_event_fragment_title_edit_text_id);
        continueButton = (Button)v.findViewById(R.id.buttadd_meal_event_fragment_continue_button_idon3);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    packBundleAndOpenFragment();
            }
        });
        initTimePicker();



        return v;
    }
    private boolean validateFields()
    {
        if(dateTextView.equals("Date")
                || startingTime.equals("Starting Time")

                || location.getText().toString().equals("")
                || apartmentNumber.getText().toString().equals(""))
        {
            return false;
        }
        else
        { return true;}
    }
    private void initTimePicker() {
        startingTime.setOnClickListener(new View.OnClickListener() {
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

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView textView = (TextView)v;
                        eventYear=year;
                        eventMonth=monthOfYear;
                        eventDay=dayOfMonth;
                        ((TextView) v).setText(dayOfMonth+"."+monthOfYear+"."+year);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void packBundleAndOpenFragment()
    {
        if(validateFields()) {
            Bundle bundle = new Bundle();
            bundle.putInt("year", eventYear);
            bundle.putInt("month", eventMonth);
            bundle.putInt("day", eventDay);
            bundle.putInt("starting_hour", startingHour);
            bundle.putInt("starting_minute", startingMinute);
            bundle.putInt("ending_hour", endingHour);
            bundle.putInt("ending_minute", endingMinute);
            bundle.putString("title", titleEditText.getText().toString());
            bundle.putString("location", location.getText().toString());
            bundle.putString("apartment_number", apartmentNumber.getText().toString());


            int meals = Integer.parseInt(mealsAmountText.getText().toString());
            bundle.putInt("meals_amount", Integer.parseInt(mealsAmountText.getText().toString()));
            bundle.putBoolean("is_new", true);
            //set Fragmentclass Arguments
            EditEventMealsFragment fragment = new EditEventMealsFragment();

            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chef_event_meals_fragment_container, fragment, "edit_meal_event")
                    .commit();
        }
        else
        {
            Toast.makeText(getActivity(),"Please fill all event details",Toast.LENGTH_SHORT).show();
        }
    }
}
