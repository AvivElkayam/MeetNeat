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

import app.meantneat.com.meetneat.Entities.EventMeals;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class EditEventMealsFragment extends android.support.v4.app.Fragment {

    LinearLayout galleryLayout;
    View v;
    private TextView startingTimeTextView,startingDateTextView,endingTimeTextView,eventMealsAmountEditText;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int startingYear,startingMonth,startingDay,startingHour,startingMinute,mealsAmount;
    private EditText eventTitleEditText,eventLocationEditText,eventApartmentNumberEditText,priceEditText;
    private int endingHour,endingMinute;
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
        loadPhotos();
        initViews(v);
        return v;
    }
    private void getEventDetailsFromBundle()
    {



        startingYear = getArguments().getInt("year");
        startingMonth = getArguments().getInt("month");
        startingDay = getArguments().getInt("day");
        startingHour = getArguments().getInt("starting_hour");
        startingMinute = getArguments().getInt("starting_minute");
        endingHour = getArguments().getInt("ending_hour");
        endingMinute = getArguments().getInt("ending_minute");
        startingDateTextView.setText(startingDay+"."+"."+startingMonth+"."+startingYear);
        startingTimeTextView.setText(startingHour+":"+startingMinute);
        endingTimeTextView.setText(endingHour+":"+endingMinute);
        eventTitleEditText.setText(getArguments().getString("title"));
        apartmentNumber = getArguments().getString("apartment_number");
        eventApartmentNumberEditText.setText(apartmentNumber);
        location = getArguments().getString("location");
        eventLocationEditText.setText(location);
        mealsAmount = getArguments().getInt("meals_amount");
        eventMealsAmountEditText.setText("Meals amount:" + mealsAmount);
        //priceEditText =
        isNew = getArguments().getBoolean("is_new");


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
        endingTimeTextView = (TextView)v.findViewById(R.id.edit_chef_event_meals_fragment_ending_time_label);
        priceEditText = (EditText) v.findViewById(R.id.edit_chef_event_meals_fragment_price_edit_text_id);
        createEventButton = (Button)v.findViewById(R.id.edit_chef_event_meals_fragment_add_event_button_id);

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
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startingHour = hourOfDay;
                        startingMinute = minute;
                        ((TextView) v).setText(hourOfDay + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
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
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapAllDataToEventAndUpdateServer();
            }
        });
        getEventDetailsFromBundle();

    }

    public interface SaveToServerCallback
    {
        public void onResult();
    }

    private void wrapAllDataToEventAndUpdateServer()
    {
        String title = eventTitleEditText.getText().toString();
        EventMeals event = new EventMeals(title,startingHour,startingMinute,
                startingYear,startingMonth,startingDay,location,apartmentNumber,mealsAmount,
                Integer.parseInt(priceEditText.getText().toString()));
        MyModel.getInstance().getModel().addNewEventMealsToServer(event,new SaveToServerCallback() {
            @Override
            public void onResult() {
                Toast.makeText(getActivity(),"Event Saved",Toast.LENGTH_SHORT).show();
            }
        });
    }







}
