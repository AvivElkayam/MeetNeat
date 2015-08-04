package app.meantneat.com.meetneat.Controller.Chef;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.camera.CropImageIntentBuilder;
//import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Camera.LocationAutoComplete;
import app.meantneat.com.meetneat.Entities.Dish;

import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class EditEventDishesFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;


    int PLACE_PICKER_REQUEST = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static int REQUEST_PICTURE = 1;
    private static int REQUEST_CROP_PICTURE = 2;
    LocationAutoComplete lAC;
    //FloatingActionButton addNewDishFloatingButton;
    Bitmap[] bitmapArray;
    CameraBasics cameraBasics = new CameraBasics();
    private TextView startingTimeTextView,startingDateTextView,endingTimeTextView,endingDateTextView;
    //dishes viewas
    private TextView noMoreEventsOverlay;
    private TextView dishTitleEditText,dishPriceEditText,dishQuantityEditText,dishDescriptionEditText,dishTotalOrdersTextView;
    private Button dishTakeAwayButton,dishToSeatButton;
    private boolean isTakeAway=false,isToSeat=false;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int startingYear,startingMonth,startingDay,startingHour,startingMinute;
    private EditText eventTitleEditText,eventLocationEditText,eventApartmentNumberEditText;
    private int endingYear,endingMonth,endingDay,endingHour,endingMinute;
    private Calendar calendar;
    private ListView dishesListView;
    private DishRowListAdapter dishRowListAdapter;
    private ArrayList<Dish> dishArrayList;
    private ListView eventsListView;
    private DishRowListAdapter eventsArrayAdapter;
    private Button createEventButton;
    private Dialog addDishDialog;
    private String apartmentNumber,location;
    ArrayList<String> dishesIDArrayList;
    View v1,v2,v3;
    //add dish dialog views
    private LinearLayout dialogBoxLayoutContainer;
    private Button nextButton,backButton;
    private EditText addDishTitleEditText,addDishPriceEditText,addDishDishesLeftEditText,addDishDescriptionEditText;
    private ImageView addDishImageView, dishImageView;
    private String dishTitle,dishPrice,dishDescription,dishQuantity;
    private boolean isNew;
    int dialogBoxIndex=1;
    private Dish newDish;
    private int currentPosition;
    @Override
    public void onConnected(Bundle bundle) {
        //Log()
        Log.e("LNGLTD", "Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //
    public class DishRowListAdapter extends ArrayAdapter<Dish>
    {
        public DishRowListAdapter()
        {
            super(getActivity(), R.layout.chef_edit_event_dishes_fragment_dish_row, dishArrayList);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if(itemView==null)
            {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.chef_edit_event_dishes_fragment_dish_row,parent,false);
            }
            final Dish dish = dishArrayList.get(position);
            final String title = dish.getTitle();
            String price = "Price: "+dish.getPrice();
            String dishesLeft = "Dishes left: "+dish.getQuantity();
            final String description = dish.getDescriprion();
           // dishImageView = dish.getThumbnailImg();


            TextView titleTextView = (TextView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_title_text_view);
            titleTextView.setText(title);

            final ImageView imageView = (ImageView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_image_view);
            if(dish.getThumbnailImg()==null)
            {
                MyModel.getInstance().getModel().getDishPicture(dish.getDishID(),new MyModel.PictureCallback() {
                    @Override
                    public void pictureHasBeenFetched(Bitmap bitmap) {
                        dish.setThumbnailImage(bitmap);
                        imageView.setBackground(new BitmapDrawable(bitmap));
                    }
                });
            }
            else {
                imageView.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(dish.getThumbnailImg(), 0, dish.getThumbnailImg().length)));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = position;
                    dishTitleEditText.setText(title);
                    dishPriceEditText.setText("Price: "+dish.getPrice());
                    dishQuantityEditText.setText("DishesLeft: "+dish.getQuantityLeft());
                    dishDescriptionEditText.setText(description);
                    dishImageView.setBackground(new BitmapDrawable(dish.getThumbnailImage()));
                }
            });
            return itemView;
        }
    }
    public static EditEventDishesFragment newInstance(String param1, String param2) {
        EditEventDishesFragment fragment = new EditEventDishesFragment();

        return fragment;
    }

    public EditEventDishesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initViews();

//        addNewDishFloatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buildAddDishDiaglog();
//
//                addDishDialog.show();
//            }
//        });
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        lAC  = new LocationAutoComplete(getActivity(),mGoogleApiClient,2);
        lAC.setChoosenLocationString(getArguments().getString("location"));
        lAC.setChoosenPlaceLatLng(new LatLng(getArguments().getDouble("latitude"),getArguments().getDouble("longitude")));
        lAC.setAutoCompleteTextView(location);

//        lac.se


    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chef_edit_event_dishes_fragment, container, false);


        // Inflate the layout for this fragment
        return view;
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
        isNew = getArguments().getBoolean("is_new");
//        if(isNew==false)
//        {
//            dishesIDArrayList=getArguments().getStringArrayList("dishes");
//        }
        //eventLocationEditText.setText(location);

    }
private void initViews()
{
    createEventButton = (Button)getActivity().findViewById(R.id.add_event_fragment_add_event_button_id);
    eventTitleEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_title_edit_text_id);
    eventLocationEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_location_edit_text_id);
    eventApartmentNumberEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_apartment_numebr_edit_text_id);
    //addNewDishFloatingButton = (FloatingActionButton) getActivity().findViewById(R.id.add_event_fragment_add_new_dish_button);
    calendar=Calendar.getInstance();
    startingTimeTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_starting_time_label);
    startingDateTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_starting_date_label);
    endingTimeTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_ending_time_label);
    noMoreEventsOverlay = (TextView)getActivity().findViewById(R.id.add_event_fragment_no_more_events_overlay);
    dishTitleEditText = (TextView)getActivity().findViewById(R.id.add_event_fragment_dish_title_text_view);
    dishTotalOrdersTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_dish_total_orders_text_view);
    dishPriceEditText = (TextView)getActivity().findViewById(R.id.add_event_fragment_dish_price_text_view);
    dishQuantityEditText = (TextView)getActivity().findViewById(R.id.add_event_fragment_dish_dishes_left_text_view);
    dishDescriptionEditText = (TextView)getActivity().findViewById(R.id.add_event_fragment_dish_description_text_view);
    dishTakeAwayButton = (Button)getActivity().findViewById(R.id.add_event_fragment_dish_take_away_button);
    dishTakeAwayButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Button button = (Button)v;
            if(isTakeAway==false)
            {
                button.setBackgroundColor(Color.GREEN);
                isTakeAway=true;
            }
            else
            {
                button.setBackgroundColor(Color.RED);
                isTakeAway=false;
            }
        }
    });
    dishToSeatButton = (Button)getActivity().findViewById(R.id.add_event_fragment_dish_to_seat_button);
    dishToSeatButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            if(isToSeat==false)
            {
                button.setBackgroundColor(Color.GREEN);
                isToSeat=true;
            }
            else
            {
                button.setBackgroundColor(Color.RED);
                isToSeat=false;
            }
        }
    });

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
    eventLocationEditText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


            try {
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
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
    dishImageView = (ImageView)getActivity().findViewById(R.id.add_event_fragment_dish_image_view);
    dishImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dispatchTakePictureIntent();
        }
    });
    if(getArguments().getBoolean("isNew")==true) {
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapAllDataToEventAndUpdateServer();
            }
        });
    }
    else
    {
            createEventButton.setText("Edit event");
            createEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = eventTitleEditText.getText().toString();
                    LatLng locationCoord = lAC.getChoosenCoordinates();
                    String locationString = lAC.getChoosenLocationString();
                    //To do: Get string ftom the autoComlete Label;
                    //String locationStr =
                    EventDishes event = new EventDishes(title
                            ,startingHour
                            ,startingMinute
                            ,endingHour
                            ,endingMinute,
                            startingYear,
                            startingMonth,
                            startingDay,
                            locationString,
                            apartmentNumber,
                            "",
                            dishArrayList,
                            locationCoord.longitude,
                            locationCoord.latitude);
                    event.setEventId(getArguments().getString("eventID"));
                    MyModel.getInstance().getModel().editEvent(event,new MyModel.EditEventCallback() {
                        @Override
                        public void eventHasBeenEdited() {
                            Toast.makeText(getActivity(),"Even has been edited",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


    }
    initListView();
    getEventDetailsFromBundle();
    if(isNew==false)
    {
        MyModel.getInstance().getModel().getEventsDishes(getArguments().getString("eventID"),new MyModel.DishesCallback() {
            @Override
            public void dishesAhBeenFetched(ArrayList<Dish> dishes) {
                dishArrayList.clear();
                dishArrayList.addAll(dishes);
                dishRowListAdapter.notifyDataSetChanged();
                if(dishArrayList.size()>0) {
                    Dish dish = dishArrayList.get(0);
                    dishTitleEditText.setText(dish.getTitle());
                    dishPriceEditText.setText("Price: " + dish.getPrice());
                    dishQuantityEditText.setText("DishesLeft: " + dish.getQuantityLeft());

                    dishDescriptionEditText.setText(dish.getDescriprion());
                    MyModel.getInstance().getModel().getDishPicture(dish.getDishID(),new MyModel.PictureCallback() {
                        @Override
                        public void pictureHasBeenFetched(Bitmap bitmap) {
                            dishImageView.setBackground(new BitmapDrawable(bitmap));
                        }
                    });
                    noMoreEventsOverlay.setVisibility(View.GONE);

                }
                else
                {
                    noMoreEventsOverlay.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}

    private void initListView() {
        dishArrayList = new ArrayList<>();
//        dishArrayList.add(new Dish("פרגית במחבת","מנה טעימה ומשביעה עם טעמים עשירים",34,7,true,true,null));
//        dishArrayList.add(new Dish("שניצל דה דיינר","שניצל קלאסי עם רוטב טעים",27.90,9,true,true,null));
//        dishArrayList.add(new Dish("סלט פלחים","מבחר ירקות העונה חתוכים גס",19,10,true,true,null));
//        dishArrayList.add(new Dish("שרימפס חמאה ושום","מנת שרימפס קלאסי עם רוטב מנצח",46.90,9,true,true,null));

        dishRowListAdapter = new DishRowListAdapter();
        dishesListView = (ListView)getActivity().findViewById(R.id.add_event_fragment_list_view);
        dishesListView.setAdapter(dishRowListAdapter);

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.chef_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void buildAddDishDiaglog()
    {
        addDishDialog = new Dialog(getActivity());
        addDishDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDishDialog.setContentView(R.layout.chef_add_dish_dialog_box_pahses_container_layout);
        nextButton = (Button)addDishDialog.findViewById(R.id.add_dish_dialog_next_button_id);
        dialogBoxLayoutContainer = (LinearLayout)addDishDialog.findViewById(R.id.add_dish_dialog_box_linear_layout_id);
        nextButton = (Button)addDishDialog.findViewById(R.id.add_dish_dialog_box_next_button_id);
        backButton = (Button)addDishDialog.findViewById(R.id.add_dish_dialog_box_back_button_id);
        buildDialogBoxPhases();

//        addDishTitleEditText = (EditText)addDishDialog.findViewById(R.id.add_dish_dialog_title_edit_text);
//        addDishPriceEditText = (EditText)addDishDialog.findViewById(R.id.add_dish_dialog_price_edit_text);
//        addDishDishesLeftEditText = (EditText)addDishDialog.findViewById(R.id.add_dish_dialog_dishes_left_edit_text);
//        addDishDescriptionEditText = (EditText)addDishDialog.findViewById(R.id.add_dish_dialog_decription_edit_text);
//        addDishImageView = (ImageView)addDishDialog.findViewById(R.id.add_dish_dialog_image_view);

    }
    private void buildDialogBoxPhases()
    {
        v1 = getActivity().getLayoutInflater().inflate(R.layout.chef_add_dish_phase_one,dialogBoxLayoutContainer,false);
        addDishTitleEditText = (EditText)v1.findViewById(R.id.add_dish_phase_one_title_edit_text_id);
        addDishDescriptionEditText = (EditText)v1.findViewById(R.id.add_dish_phase_one_description_edit_text_id);
        v2 = getActivity().getLayoutInflater().inflate(R.layout.chef_add_dish_phase_two,dialogBoxLayoutContainer,false);
        addDishPriceEditText = (EditText)v2.findViewById(R.id.add_dish_phase_two_price_edit_text_id);
        addDishDishesLeftEditText = (EditText)v2.findViewById(R.id.add_dish_phase_two_quantity_edit_text_id);

        v3 = getActivity().getLayoutInflater().inflate(R.layout.chef_add_dish_phase_three,dialogBoxLayoutContainer,false);
        addDishImageView = (ImageView)v3.findViewById(R.id.add_dish_phase_three_dish_image_view_id);
        addDishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
//                Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.logo1);
//                addDishImageView.setImageBitmap(b);
//                byte[] ba = bitmapToByteArr(b);
//                newDish.setThumbnailImg(ba);
//                newDish.setFullsizeImg(ba);
            }
        });
        dialogBoxLayoutContainer.addView(v1);
        dialogBoxLayoutContainer.addView(v2);
        dialogBoxLayoutContainer.addView(v3);
        newDish = new Dish();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dialogBoxIndex)
                {
                    case 1:
                    {
                        dialogBoxIndex=1;
                        //dishDescription=addDishDescriptionEditText.getText().toString();
                        newDish.setDescriprion(addDishDescriptionEditText.getText().toString());
                        //dishTitle = addDishTitleEditText.getText().toString();
                        newDish.setTitle(addDishTitleEditText.getText().toString());
                        if(newDish.getTitle().equals("") || newDish.getDescriprion().equals(""))
                        {
                            Toast.makeText(getActivity(),"Please fill in details",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dialogBoxIndex = 2;
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.VISIBLE);
                            v3.setVisibility(View.GONE);
                            backButton.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 2:
                    {
//                        dishPrice = addDishPriceEditText.getText().toString();
//                        dishQuantity = addDishDishesLeftEditText.getText().toString();
                        newDish.setPrice(Double.parseDouble(addDishPriceEditText.getText().toString()));
                        newDish.setQuantityLeft(Double.parseDouble(addDishDishesLeftEditText.getText().toString()));
                        if(addDishPriceEditText.getText().toString().equals("")|| addDishDishesLeftEditText.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"Please fill in details",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dialogBoxIndex = 3;
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.VISIBLE);
                            backButton.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 3:
                    {

                        if(newDish.getFullsizeImg()==null)
                        {
                            Toast.makeText(getActivity(),"Please fill in details",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dishArrayList.add(newDish);
                            dishRowListAdapter.notifyDataSetChanged();
                            noMoreEventsOverlay.setVisibility(View.GONE);

                            addDishDialog.dismiss();
                            dialogBoxIndex=1;
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.chef_fragment_menu_add_button)
//        {
//            addDishDialog.show();
//        }

        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        cameraBasics.setF(EditEventDishesFragment.this);
        cameraBasics.dispatchTakePictureIntent(getActivity());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //New image for a dish
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File croppedImageFile = null;
        try {

             croppedImageFile = File.createTempFile("test",".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ((requestCode == REQUEST_PICTURE) && (resultCode == Activity.RESULT_OK)) {
            // When the user is done picking a picture, let's start the CropImage Activity,
            // setting the output image file and size to 200x200 pixels square.
            Uri croppedImage = Uri.fromFile(croppedImageFile);

            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, croppedImage);
            cropImage.setOutlineColor(0xFF03A9F4);
            cropImage.setSourceImage(data.getData());
            Intent a = cropImage.getIntent(getActivity());



            bitmapArray = cameraBasics.myOnActivityResult(requestCode, resultCode, data);
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... params) {
//                    dishArrayList.get(currentPosition).setFullsizeImg(bitmapToByteArr(bitmapArray[0])); //Full size to Bytearray
//
//                    dishArrayList.get(currentPosition).setThumbnailImg(bitmapToByteArr(bitmapArray[1])); //Thumbnail to Bytearray
//                    newDish.setFullsizeImg(bitmapToByteArr(bitmapArray[0]));
//                    newDish.setThumbnailImg(bitmapToByteArr(bitmapArray[1]));
//                    return null;
//
//                }
//            }.execute();
            dishImageView.setImageBitmap(bitmapArray[1]);

            startActivityForResult(cropImage.getIntent(getActivity()), REQUEST_CROP_PICTURE);


        } else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == Activity.RESULT_OK)) {
            // When we are done cropping, display it in the ImageView.
            dishImageView.setImageBitmap(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
        }



//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==Activity.RESULT_OK) {
           //bitmapArray = cameraBasics.myOnActivityResult(requestCode, resultCode, data);
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... params) {
////                    dishArrayList.get(currentPosition).setFullsizeImg(bitmapToByteArr(bitmapArray[0])); //Full size to Bytearray
////
////                    dishArrayList.get(currentPosition).setThumbnailImg(bitmapToByteArr(bitmapArray[1])); //Thumbnail to Bytearray
//                    newDish.setFullsizeImg(bitmapToByteArr(bitmapArray[0]));
//                    newDish.setThumbnailImg(bitmapToByteArr(bitmapArray[1]));
//                    return null;
//
//                }
//            }.execute();
//            addDishImageView.setImageBitmap(bitmapArray[1]);
//        }
        //Location from google picker
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }


        }

    }


    private byte[] bitmapToByteArr(Bitmap b)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,80,baos);
        return baos.toByteArray();

    }
    private void wrapAllDataToEventAndUpdateServer()
    {
        String title = eventTitleEditText.getText().toString();
        LatLng locationCoord = lAC.getChoosenCoordinates();
        String locationString = lAC.getChoosenLocationString();
        //To do: Get string ftom the autoComlete Label;
        //String locationStr =
        EventDishes event = new EventDishes(title
                ,startingHour
                ,startingMinute
                ,endingHour
                ,endingMinute,
                startingYear,
                startingMonth,
                startingDay,
                locationString,
                apartmentNumber,
                "",
                dishArrayList,
                locationCoord.longitude,
                locationCoord.latitude);

        MyModel.getInstance().getModel().addNewEventDishesToServer(event, new SaveToServerCallback() {
            @Override
            public void onResult() {
                Toast.makeText(getActivity(), "Event Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public interface SaveToServerCallback
    {
        public void onResult();
    }

}
