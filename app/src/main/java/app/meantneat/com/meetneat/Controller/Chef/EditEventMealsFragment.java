package app.meantneat.com.meetneat.Controller.Chef;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Calendar;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Entities.EventMeals;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class EditEventMealsFragment extends android.support.v4.app.Fragment {

    LinearLayout galleryLayout;
    View v;
    private TextView startingTimeTextView,startingDateTextView,endingDateTextView,endingTimeTextView,eventMealsAmountEditText;

    private DatePickerDialog startingDatePickerDialog,endingDatePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int startingYear,startingMonth,startingDay,startingHour,startingMinute;
    private int endingYear,endingMonth,endingDay,endingHour,endingMinute;
    private int mealsAmount;
    private int price;
    private String menu,eventID,title;
    private EditText eventTitleEditText,eventLocationEditText,eventApartmentNumberEditText,priceEditText,menuEditText;
    private Calendar calendar;
    private Button createEventButton;
    private String apartmentNumber,location;
    private boolean isNew;



    public static EditEventMealsFragment newInstance() {
        EditEventMealsFragment fragment = new EditEventMealsFragment();

        return fragment;
    }

    public EditEventMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View insertPhoto(String path){
        Bitmap bm = decodeSampledBitmapFromUri(path, 300, 300);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LinearLayout.LayoutParams(350, 350));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bm);

        layout.addView(imageView);
        return layout;
    }

    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
        Bitmap bm = null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
    }

    public void loadPhotos()
    {
        galleryLayout = (LinearLayout)v.findViewById(R.id.edit_chef_event_meals_pictures_layout);
        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        //String targetPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //Toast.makeText(getActivity(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File[] files = targetDirector.listFiles();
        for (File file : files){
            galleryLayout.addView(insertPhoto(file.getAbsolutePath()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.chef_edit_event_meals_fragment_layout, container, false);
        //loadPhotos();
        initViews(v);
        return v;
    }
    private void getEventDetailsFromBundle()
    {

        startingYear = getArguments().getInt(AppConstants.EVENT_STARTING_YEAR);
        startingMonth = getArguments().getInt(AppConstants.EVENT_STARTING_MONTH);
        startingDay = getArguments().getInt(AppConstants.EVENT_STARTING_DAY);
        startingHour = getArguments().getInt(AppConstants.EVENT_STARTING_HOUR);
        startingMinute = getArguments().getInt(AppConstants.EVENT_STARTING_MINUTE);

        endingYear = getArguments().getInt(AppConstants.EVENT_ENDING_YEAR);
        endingMonth = getArguments().getInt(AppConstants.EVENT_ENDING_MONTH);
        endingDay = getArguments().getInt(AppConstants.EVENT_ENDING_DAY);
        endingHour = getArguments().getInt(AppConstants.EVENT_ENDING_HOUR);
        endingMinute = getArguments().getInt(AppConstants.EVENT_ENDING_MINUTE);

        title = getArguments().getString(AppConstants.EVENT_TITLE);
        apartmentNumber = getArguments().getString(AppConstants.EVENT_APARTMENT_NUMBER);
        location = getArguments().getString(AppConstants.EVENT_LOCATION);
        isNew = getArguments().getBoolean(AppConstants.IS_NEW);
        if(isNew==false)
        {//if it's not new-its in editing mode and it has additional attributes:
            mealsAmount = getArguments().getInt(AppConstants.EVENT_MEALS_LEFT);
            price = getArguments().getInt(AppConstants.EVENT_PRICE);
            menu = getArguments().getString(AppConstants.EVENT_MENU);
            eventID = getArguments().getString(AppConstants.EVENT_MEALS_ID);

        }

        setViewsWithData();
    }
    private void setViewsWithData()
    {
        eventLocationEditText.setText(location);
        eventApartmentNumberEditText.setText(apartmentNumber);
        startingDateTextView.setText(MeetnEatDates.getDateString(startingYear,startingMonth,startingDay));
        startingTimeTextView.setText(startingHour+":"+startingMinute);
        endingTimeTextView.setText(endingHour+":"+endingMinute);
        eventTitleEditText.setText(title);
        if(isNew==false)
        {//if it's not new-its in editing mode and it has additional attributes:
            eventMealsAmountEditText.setText(Integer.toString(mealsAmount));
            priceEditText.setText(Double.toString(price));
            menuEditText.setText(menu);

        }
    }
    private void initViews(View v)
    {
        eventTitleEditText = (EditText)v.findViewById(R.id.edit_chef_event_meals_fragment_title_edit_text_id);
        eventLocationEditText = (EditText)v.findViewById(R.id.edit_chef_event_meals_fragment_location_edit_text_id);
        eventApartmentNumberEditText = (EditText)v.findViewById(R.id.edit_chef_event_meals_apartment_numebr_edit_text_id);
        eventMealsAmountEditText = (EditText)v.findViewById(R.id.edit_chef_event_meals_fragment_meals_amount_edit_text_id);
        calendar= Calendar.getInstance();
        startingTimeTextView = (TextView)v.findViewById(R.id.edit_chef_event_meals_fragment_starting_time_label);
        startingDateTextView = (TextView)v.findViewById(R.id.edit_chef_event_meals_fragment_starting_date_label);
        endingDateTextView = (TextView)v.findViewById(R.id.edit_chef_event_meals_fragment_ending_time_label);
        endingTimeTextView = (TextView)v.findViewById(R.id.edit_chef_event_meals_fragment_ending_time_label);
        priceEditText = (EditText) v.findViewById(R.id.edit_chef_event_meals_fragment_price_edit_text_id);
        menuEditText = (EditText)v.findViewById(R.id.edit_chef_event_meals_fragment_menu_edit_text_id);
        createEventButton = (Button)v.findViewById(R.id.edit_chef_event_meals_fragment_add_event_button_id);

        startingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startingDatePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView textView = (TextView)v;
                        startingYear=year;
                        startingMonth=monthOfYear;
                        startingDay=dayOfMonth;
                        ((TextView) v).setText(MeetnEatDates.getDateString(year,monthOfYear,dayOfMonth));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                startingDatePickerDialog.show();

            }
        });
        startingTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startingHour = hourOfDay;
                        startingMinute = minute;
                        ((TextView) v).setText(MeetnEatDates.getTimeString(hourOfDay,minute));
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });
        endingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                endingDatePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView textView = (TextView)v;
                        endingYear=year;
                        endingMonth=monthOfYear;
                        endingDay=dayOfMonth;
                        ((TextView) v).setText(MeetnEatDates.getDateString(year,monthOfYear,dayOfMonth));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                endingDatePickerDialog.show();

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
                        ((TextView) v).setText(MeetnEatDates.getTimeString(hourOfDay,minute));
                    }
                },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
                timePickerDialog.show();
            }
        });
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNew==true)
                {
                    wrapAllDataToEventAndAddNewEventToServer();
                }
                else
                {
                    wrapAllDataToEventAndUpdateServer();
                }
            }
        });
        getEventDetailsFromBundle();

    }

    public interface SaveToServerCallback
    {
        public void onResult();
    }

    private void wrapAllDataToEventAndAddNewEventToServer()
    {
        EventMeals event = new EventMeals();
        event.setTitle(eventTitleEditText.getText().toString());
        event.setStartingMinute(startingMinute);
        event.setStartingHour(startingHour);
        event.setStartingDay(startingDay);
        event.setStartingMonth(startingMonth);
        event.setStartingYear(startingYear);

        event.setEndingMinute(endingMinute);
        event.setEndingHour(endingHour);
        event.setEndingDay(endingDay);
        event.setEndingMonth(endingMonth);
        event.setEndingYear(endingYear);

        event.setApartmentNumber(eventApartmentNumberEditText.getText().toString());
        event.setLocation(location);
        event.setMealsLeft(Integer.parseInt(eventMealsAmountEditText.getText().toString()));
        event.setPrice(Integer.parseInt(priceEditText.getText().toString()));
        event.setMenu(menuEditText.getText().toString());
        MyModel.getInstance().getModel().addNewEventMealsToServer(event,new SaveToServerCallback() {
            @Override
            public void onResult() {
                Toast.makeText(getActivity(),"Event Saved",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void wrapAllDataToEventAndUpdateServer()
    {
        EventMeals event = new EventMeals();
        event.setTitle(eventTitleEditText.getText().toString());
        event.setStartingMinute(startingMinute);
        event.setStartingHour(startingHour);
        event.setStartingDay(startingDay);
        event.setStartingMonth(startingMonth);
        event.setStartingYear(startingYear);

        event.setEndingMinute(endingMinute);
        event.setEndingHour(endingHour);
        event.setEndingDay(endingDay);
        event.setEndingMonth(endingMonth);
        event.setEndingYear(endingYear);

        event.setApartmentNumber(eventApartmentNumberEditText.getText().toString());
        event.setLocation(location);
        event.setMealsLeft(Integer.parseInt(eventMealsAmountEditText.getText().toString()));
        event.setPrice(Integer.parseInt(priceEditText.getText().toString()));
        event.setMenu(menuEditText.getText().toString());
        event.setEventID(eventID);
        MyModel.getInstance().getModel().editEventMeals(event,new MyModel.EditEventCallback() {
            @Override
            public void eventHasBeenEdited() {
                Toast.makeText(getActivity(),"Event Saved",Toast.LENGTH_SHORT).show();
            }
        });

//        event.setTitle(eventTitleEditText.getText().toString());
//        event.setStartingMinute(startingMinute);
//        event.setStartingHour(startingHour);
//        event.setStartingDay(startingDay);
//        event.setStartingMonth(startingMonth);
//        event.setStartingYear(startingYear);
//
//        event.setEndingMinute(endingMinute);
//        event.setEndingHour(endingHour);
//        event.setEndingDay(endingDay);
//        event.setEndingMonth(endingMonth);
//        event.setEndingYear(endingYear);
//
//        event.setApartmentNumber(eventApartmentNumberEditText.getText().toString());
//        event.setLocation(location);
//        event.setMealsLeft(Integer.parseInt(eventMealsAmountEditText.getText().toString()));
//        event.setPrice(Integer.parseInt(priceEditText.getText().toString()));
//        event.setMenu(menuEditText.getText().toString());
//        MyModel.getInstance().getModel().addNewEventMealsToServer(event,new SaveToServerCallback() {
//            @Override
//            public void onResult() {
//                Toast.makeText(getActivity(),"Event Saved",Toast.LENGTH_SHORT).show();
//            }
//        });
    }







}
