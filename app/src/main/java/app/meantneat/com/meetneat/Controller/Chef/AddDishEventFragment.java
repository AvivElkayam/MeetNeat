package app.meantneat.com.meetneat.Controller.Chef;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Camera.LocationAutoComplete;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.R;

/**
 * Created by mac on 6/1/15.
 */
public class AddDishEventFragment extends Fragment implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    TextView endingDateTextView,startingDateTextView,startingTime,endingTime;
    EditText location,apartmentNumber,titleEditText;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    int startingHour,startingMinute,startingYear,startingMonth,startingDay;
    int endingHour,endingMinute,endingYear,endingMonth,endingDay;
    LocationAutoComplete lAC;
    Calendar calendar;
    View v;
    Button continueButton;

    String whereToGo;
    public static String goToMeals = "meals";
    public static String goToDishes = "dishes";

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    //String s = ParseUser.getCurrentUser().getObjectId();
        whereToGo = getArguments().getString(AppConstants.WHERE_TO_GO);
        v = inflater.inflate(R.layout.chef_add_event_dishes_fragment_layout,container,false);
        calendar=Calendar.getInstance();
        startingDateTextView = (TextView)v.findViewById(R.id.add_dish_event_fragment_starting_date_text_view_id);
        endingDateTextView = (TextView)v.findViewById(R.id.add_dish_event_fragment_ending_date_text_view_id);
        startingTime = (TextView)v.findViewById(R.id.add_dish_event_fragment_starting_time_text_view_id);
        endingTime = (TextView)v.findViewById(R.id.add_dish_event_fragment_ending_time_text_view_id);
        //location = (EditText)v.findViewById(R.id.add_dish_event_fragment_location_text_view_id);
        apartmentNumber = (EditText)v.findViewById(R.id.add_dish_event_fragment_appartment_number_text_view_id);
        apartmentNumber.setPadding(5,0,0,0);
        titleEditText = (EditText)v.findViewById(R.id.add_dish_event_fragment_title_edit_text_id);
        titleEditText.setPadding(5,0,0,0);
        continueButton = (Button)v.findViewById(R.id.buttadd_dish_event_fragment_continue_button_idon3);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFields()) {
                        //Location Coordinates an
                    LatLng locationCoord = lAC.getChoosenCoordinates();
                    String locationString = lAC.getChoosenLocationString();

                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.EVENT_STARTING_YEAR,startingYear);
                    bundle.putInt(AppConstants.EVENT_STARTING_MONTH, startingMonth);
                    bundle.putInt(AppConstants.EVENT_STARTING_DAY, startingDay);
                    bundle.putInt(AppConstants.EVENT_STARTING_HOUR, startingHour);
                    bundle.putInt(AppConstants.EVENT_STARTING_MINUTE, startingMinute);

                    bundle.putInt(AppConstants.EVENT_ENDING_YEAR,endingYear);
                    bundle.putInt(AppConstants.EVENT_ENDING_MONTH, endingMonth);
                    bundle.putInt(AppConstants.EVENT_ENDING_DAY, endingDay);
                    bundle.putInt(AppConstants.EVENT_ENDING_HOUR, endingHour);
                    bundle.putInt(AppConstants.EVENT_ENDING_MINUTE, endingMinute);

                    bundle.putString(AppConstants.EVENT_TITLE, titleEditText.getText().toString());
                    bundle.putString(AppConstants.EVENT_LOCATION, locationString);
                    bundle.putDouble(AppConstants.EVENT_LATITUDE,locationCoord.latitude);
                    bundle.putDouble(AppConstants.EVENT_LONGITUDE,locationCoord.longitude);
                    bundle.putString(AppConstants.EVENT_APARTMENT_NUMBER, apartmentNumber.getText().toString());

                    bundle.putBoolean(AppConstants.IS_NEW, true);
                    //set Fragmentclass Arguments
                    if(whereToGo.equals(goToDishes))
                    {
                        wrapInputsAndGoToNewDishEvent(bundle);
                    }
                    if(whereToGo.equals(goToMeals))
                    {
                        wrapInputsAndGoToNewMealsEvent(bundle);
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please fill all event details",Toast.LENGTH_SHORT).show();
                }

            }
        });
        initTimePicker();



        return v;
    }
    private boolean validateFields()
    {
        if(startingDateTextView.equals("Date")
                || startingTime.equals("Starting Time")
                || endingTime.equals("EndingTime")
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
        endingTime.setOnClickListener(new View.OnClickListener() {
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
        endingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView textView = (TextView) v;
                        endingYear = year;
                        endingMonth = monthOfYear;
                        endingDay = dayOfMonth;
                        ((TextView) v).setText(MeetnEatDates.getDateString(year, monthOfYear, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });
        startingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView textView = (TextView) v;
                        startingYear = year;
                        startingMonth = monthOfYear;
                        startingDay = dayOfMonth;
                        ((TextView) v).setText(MeetnEatDates.getDateString(year, monthOfYear, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        lAC  = new LocationAutoComplete(getActivity(),mGoogleApiClient,1);

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    public void wrapInputsAndGoToNewDishEvent(Bundle bundle)
    {
        EditEventDishesFragment fragment = new EditEventDishesFragment();
        fragment.setArguments(bundle);
        Fragment f = getParentFragment();
        FragmentManager fragmentManager = f.getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.chef_event_dishes_fragment_container, fragment, "added_event").addToBackStack("added_new")
                .commit();//different container ID
    }
    public void wrapInputsAndGoToNewMealsEvent(Bundle bundle)
    {
        EditEventMealsFragment fragment = new EditEventMealsFragment();
        fragment.setArguments(bundle);
        Fragment f = getParentFragment();
        FragmentManager fragmentManager = f.getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.chef_event_meals_fragment_container, fragment, "added_event").addToBackStack("added_new")
                .commit();//different container ID
    }
}
