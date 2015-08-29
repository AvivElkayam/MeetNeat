package app.meantneat.com.meetneat.Controller.Chef;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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

//import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.makeramen.roundedimageview.RoundedImageView;
import com.melnykov.fab.FloatingActionButton;
import com.rey.material.widget.CheckBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import app.meantneat.com.meetneat.AppConstants;
import app.meantneat.com.meetneat.Camera.CameraBasics;
import app.meantneat.com.meetneat.Camera.LocationAutoComplete;
import app.meantneat.com.meetneat.Entities.Dish;

import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.MeetnEatDates;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class EditEventDishesFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;


    private int PLACE_PICKER_REQUEST = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int REQUEST_PICTURE = 1;
    private static int REQUEST_CROP_PICTURE = 2;
    LocationAutoComplete lAC;
    //FloatingActionButton addNewDishFloatingButton;
    FloatingActionButton addDishButton;
    Bitmap[] bitmapArray;
    CameraBasics cameraBasics = new CameraBasics();
    private TextView startingTimeTextView,startingDateTextView,endingTimeTextView,endingDateTextView;
    //dishes views
    private TextView noMoreEventsOverlay;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int startingYear,startingMonth,startingDay,startingHour,startingMinute;
    private int endingYear,endingMonth,endingDay,endingHour,endingMinute;
    private double latitude,longitude;
    private EditText eventTitleEditText,eventLocationEditText,eventApartmentNumberEditText;
    private Calendar calendar;
    private ListView dishesListView;
    private DishRowListAdapter dishRowListAdapter;
    private ArrayList<Dish> dishArrayList;
    private Button createEventButton;
    private Dialog addDishDialog;
    private String apartmentNumber,location,eventID;
    ArrayList<String> dishesIDArrayList;
    View v1,v2,v3;
    //add dish dialog views
    private LinearLayout dialogBoxLayoutContainer;
    private Button nextButton,backButton,continueButton;
    private EditText addDishTitleEditText,addDishPriceEditText,addDishDishesLeftEditText,addDishDescriptionEditText;
    private ImageView addDishImageView, dishImageView;
    private CheckBox addDishtaCheckBox,addDishseatCheckBox;
    private boolean isNew;
    int dialogBoxIndex=1;
    private Dish newDish;
    private int currentPosition;
    //*** Edit Dish
    private EditDishDialogBox editDishDialogBox;
    private AddDishDialogBox addDishDialogBox;
    private Dish dishInEdit;
    private boolean isAdddishDialogOpened;
    private boolean isEditDishDialogOpened;

    public boolean isEditDishDialogOpened() {
        return isEditDishDialogOpened;
    }

    public void setIsEditDishDialogOpened(boolean isEditDishDialogOpened) {
        this.isEditDishDialogOpened = isEditDishDialogOpened;
    }



    public boolean isAdddishDialogOpened() {
        return isAdddishDialogOpened;
    }

    public void setIsAdddishDialogOpened(boolean isAdddishDialogOpened) {
        this.isAdddishDialogOpened = isAdddishDialogOpened;
    }



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
            dishInEdit = dishArrayList.get(position);
            final String title = dishInEdit.getTitle();
            String price = "Price: "+dishInEdit.getPrice();
            final String dishesLeft = Double.toString(dishInEdit.getQuantityLeft());
            final String description = dishInEdit.getDescriprion();
           // dishImageView = dishInEdit.getThumbnailImg();


            final TextView titleTextView = (TextView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_title_text_view);
            titleTextView.setText(title);

            final TextView priceTextView = (TextView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_price_text_view);
            priceTextView.setText("$"+Double.toString(dishInEdit.getPrice()));

            final TextView quantityTextView = (TextView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_quantity_text_view);
            quantityTextView.setText(Double.toString(dishInEdit.getQuantityLeft()));

            //final ImageView imageView = (ImageView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_image_view);
            //final SelectableRoundedImageView imageView = (SelectableRoundedImageView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_image_view);
            final RoundedImageView imageView = (RoundedImageView)itemView.findViewById(R.id.add_fragment_fragment_dish_row_image_view);
            //imageView.setCornerRadius(imageView.getLayoutParams().width/2);
            if(dishInEdit.getThumbnailImg()==null)
            {
                MyModel.getInstance().getModel().getDishPicture(dishInEdit.getDishID(),new MyModel.PictureCallback() {
                    @Override
                    public void pictureHasBeenFetched(Bitmap bitmap) {
                        dishInEdit.setThumbnailImage(bitmap);
                       // imageView.setBackground(new BitmapDrawable(bitmap));
                        CameraBasics.setImageViewWithFadeAnimation(getActivity(),imageView,bitmap);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        //imageView.setImageDrawable(new BitmapDrawable(bitmap));
                    }
                });
            }
            else {
                //imageView.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(dishInEdit.getThumbnailImg(), 0, dishInEdit.getThumbnailImg().length)));
                imageView.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(dishInEdit.getThumbnailImg(), 0, dishInEdit.getThumbnailImg().length)));
            }



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Edit specific dishInEdit

                    dishInEdit.setDishIndex(position);



                    editDishDialogBox = new EditDishDialogBox(getActivity(),dishInEdit);
                    editDishDialogBox.getCameraBasics().setFragment(EditEventDishesFragment.this);
                    isEditDishDialogOpened = true;
                    editDishDialogBox.show();

                    editDishDialogBox.getDialogBox().setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            dishInEdit.setTitle(editDishDialogBox.getDish().getTitle());
                            dishInEdit.setDescriprion(editDishDialogBox.getDish().getDescriprion());
                            dishInEdit.setPrice(editDishDialogBox.getDish().getPrice());
                            dishInEdit.setQuantityLeft(editDishDialogBox.getDish().getQuantityLeft());
                            dishInEdit.setTakeAway(editDishDialogBox.getDish().isTakeAway());
                            dishInEdit.setToSit(editDishDialogBox.getDish().isToSit());
                            titleTextView.setText(dishInEdit.getTitle());
                            priceTextView.setText("$"+Double.toString(dishInEdit.getPrice()));
                            quantityTextView.setText(Double.toString(dishInEdit.getQuantityLeft()));
                            isEditDishDialogOpened = false;

                          //  dishRowListAdapter.notifyDataSetChanged();
                        }
                    });
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

        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                buildAddDishDiaglog();
//
//                addDishDialog.show();
                buildAddDishDialog();
            }
        });
        addDishButton.attachToListView(dishesListView);
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        lAC  = new LocationAutoComplete(getActivity(),mGoogleApiClient,2);
        lAC.setChoosenLocationString(getArguments().getString("location"));
        lAC.setChoosenPlaceLatLng(new LatLng(getArguments().getDouble("latitude"), getArguments().getDouble("longitude")));
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

        startingDateTextView.setText(MeetnEatDates.getDateString(startingYear, startingMonth, startingDay));
        startingTimeTextView.setText(MeetnEatDates.getTimeString(startingHour, startingMinute));
        endingDateTextView.setText(MeetnEatDates.getDateString(endingYear, endingMonth, endingDay));
        endingTimeTextView.setText(MeetnEatDates.getTimeString(endingHour, endingMinute));
        eventTitleEditText.setText(getArguments().getString(AppConstants.EVENT_TITLE));
        apartmentNumber = getArguments().getString(AppConstants.EVENT_APARTMENT_NUMBER);
        eventApartmentNumberEditText.setText(apartmentNumber);
        location = getArguments().getString(AppConstants.EVENT_LOCATION);
        latitude = getArguments().getDouble(AppConstants.EVENT_LATITUDE);
        longitude = getArguments().getDouble(AppConstants.EVENT_LONGITUDE);
        isNew = getArguments().getBoolean(AppConstants.IS_NEW);
        if(isNew==false)
            eventID = getArguments().getString(AppConstants.EVENT_ID);

//        if(isNew==false)
//        {
//            dishesIDArrayList=getArguments().getStringArrayList("dishes");
//        }
//        eventLocationEditText.setText(location);

    }
    private void initViews()
{


    createEventButton = (Button)getActivity().findViewById(R.id.chef_edit_event_continue_button_id);
    eventTitleEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_title_edit_text_id);
    eventApartmentNumberEditText = (EditText)getActivity().findViewById(R.id.add_event_fragment_apartment_numebr_edit_text_id);
    addDishButton = (FloatingActionButton)getActivity().findViewById(R.id.chef_edit_events_dishes_floating_add_dish_button);
    addDishButton.setColorNormal(getResources().getColor(R.color.eat_green));

    calendar=Calendar.getInstance();

    noMoreEventsOverlay = (TextView)getActivity().findViewById(R.id.add_event_fragment_no_more_events_overlay);

    initDatePickers();
    initTimePickers();
    getEventDetailsFromBundle();
    //initCreateEventButton();
    initListView();

    if(isNew==false)
    {
        getEventsDishes();
    }


}



    private void initTimePickers() {
        startingTimeTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_starting_time_label);
        endingTimeTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_ending_time_label);
        startingTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startingHour = hourOfDay;
                        startingMinute = minute;
                        ((TextView) v).setText(MeetnEatDates.getTimeString(hourOfDay, minute));
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        endingTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endingHour = hourOfDay;
                        endingMinute = minute;
                        ((TextView) v).setText(MeetnEatDates.getTimeString(hourOfDay, minute));
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });
    }

    private void initDatePickers() {
        startingDateTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_starting_date_label);
        endingDateTextView = (TextView)getActivity().findViewById(R.id.add_event_fragment_ending_date_label);
        startingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                datePickerDialog = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TextView textView = (TextView)v;
                        startingYear=year;
                        startingMonth=monthOfYear+1;
                        startingDay=dayOfMonth;
                        ((TextView) v).setText(MeetnEatDates.getDateString(year, monthOfYear, dayOfMonth));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

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
                        endingMonth = monthOfYear+1;
                        endingDay = dayOfMonth;
                        ((TextView) v).setText(MeetnEatDates.getDateString(year, monthOfYear, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }});
    }

    private void initCreateEventButton()
    {
        if(isNew==true) {
            createEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wrapAllDataToEventAndAddDishToServer();
                }
            });
        }
        else
        {
                wrapAllDataAndEditDishInServer();
        }
    }
private void getEventsDishes()
    {
        MyModel.getInstance().getModel().getEventsDishes(getArguments().getString("eventID"), new MyModel.DishesCallback() {
            @Override
            public void dishesAhBeenFetched(ArrayList<Dish> dishes) {
                dishArrayList.clear();
                dishArrayList.addAll(dishes);
                dishRowListAdapter.notifyDataSetChanged();
                if (dishArrayList.size() > 0) {
                    noMoreEventsOverlay.setVisibility(View.GONE);

                } else {
                    noMoreEventsOverlay.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void initListView() {
        dishArrayList = new ArrayList<>();
        dishRowListAdapter = new DishRowListAdapter();
        dishesListView = (ListView)getActivity().findViewById(R.id.add_event_fragment_list_view);
        dishesListView.setAdapter(dishRowListAdapter);
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.edit_dish_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //
    private void buildAddDishDiaglog()
    {
        addDishDialog = new Dialog(getActivity());
        addDishDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDishDialog.setContentView(R.layout.chef_add_dish_dialog_box_pahses_container_layout);
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

    //Add new dish phases
    //3 views - 1 for each phase in the "add dish" part
    private void buildDialogBoxPhases()
    {
        dialogBoxIndex=1;


        // V1 - Dish name and Dish description
        v1 = getActivity().getLayoutInflater().inflate(R.layout.chef_add_dish_phase_one,dialogBoxLayoutContainer,false);
        addDishTitleEditText = (EditText)v1.findViewById(R.id.add_dish_phase_one_title_edit_text_id);
        addDishDescriptionEditText = (EditText)v1.findViewById(R.id.add_dish_phase_one_description_edit_text_id);
        addDishTitleEditText.setText("");
        addDishDescriptionEditText.setText("");
        // V2 - Dish price and Dish description + T\A & To seat
        v2 = getActivity().getLayoutInflater().inflate(R.layout.chef_add_dish_phase_two,dialogBoxLayoutContainer,false);
        addDishPriceEditText = (EditText)v2.findViewById(R.id.add_dish_phase_two_price_edit_text_id);
        addDishDishesLeftEditText = (EditText)v2.findViewById(R.id.add_dish_phase_two_quantity_edit_text_id);
        addDishPriceEditText.setText("");
        addDishDishesLeftEditText.setText("");
        addDishtaCheckBox = (CheckBox)v2.findViewById(R.id.add_dish_phase_two_ta_checkbox_id);
        addDishseatCheckBox = (CheckBox)v2.findViewById(R.id.add_dish_phase_two_seat_checkbox_id);

        // V2 - Dish image
        v3 = getActivity().getLayoutInflater().inflate(R.layout.chef_add_dish_phase_three,dialogBoxLayoutContainer,false);
        addDishImageView = (ImageView)v3.findViewById(R.id.add_dish_phase_three_dish_image_view_id);
        addDishImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();


            }
        });
        dialogBoxLayoutContainer.addView(v1);
        dialogBoxLayoutContainer.addView(v2);
        dialogBoxLayoutContainer.addView(v3);
        newDish = new Dish();
        //Next actions
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dialogBoxIndex)
                {
                    case 1:
                    {
                        dialogBoxIndex=1;

                        if(addDishTitleEditText.getText().toString().equals("") || addDishDescriptionEditText.getText().toString().equals(""))
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
                            hideKeyboard(addDishTitleEditText);
                            hideKeyboard(addDishDescriptionEditText);
                            newDish.setDescriprion(addDishDescriptionEditText.getText().toString());
                            newDish.setTitle(addDishTitleEditText.getText().toString());
                        }
                        break;
                    }
                    case 2:
                    {


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
                            hideKeyboard(addDishPriceEditText);
                            hideKeyboard(addDishDishesLeftEditText);
                            newDish.setPrice(Double.parseDouble(addDishPriceEditText.getText().toString()));
                            newDish.setQuantityLeft(Double.parseDouble(addDishDishesLeftEditText.getText().toString()));
                            newDish.setTakeAway(addDishtaCheckBox.isChecked());
                            newDish.setToSit(addDishseatCheckBox.isChecked());
                        }
                        break;
                    }
                    case 3:
                    {

                        if(newDish.getFullsizeImg()==null)
                        {
                            Toast.makeText(getActivity(),"Please choose a photo for the dish",Toast.LENGTH_SHORT).show();
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
        }
        );
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (dialogBoxIndex)
                {
                    case 1:
                    {
                        break;
                    }
                    case 2:
                    {
                            dialogBoxIndex = 1;
                            v1.setVisibility(View.VISIBLE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.GONE);
                            backButton.setVisibility(View.GONE);
                            hideKeyboard(addDishPriceEditText);
                            hideKeyboard(addDishDishesLeftEditText);


                        break;
                    }
                    case 3:
                    {
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.VISIBLE);
                        v3.setVisibility(View.GONE);
                            dialogBoxIndex=2;

                        break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.edit_event_action_bar_button: {
                if(isNew==true) {
                            wrapAllDataToEventAndAddDishToServer();
                }
                else
                {
                            wrapAllDataAndEditDishInServer();
                }
                break;
            }
            // action with ID action_settings was selected
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        cameraBasics.setFragment(EditEventDishesFragment.this);
        cameraBasics.dispatchTakePictureIntent(getActivity());

    }

    //photo captured in EditDishDialogBox
    public void onActivityResultEditDish(int requestCode, int resultCode, Intent data)
    {
            bitmapArray = editDishDialogBox.getCameraBasics().myOnActivityResult(requestCode, resultCode, data);

            dishInEdit.setFullsizeImg(CameraBasics.bitmapToByteArr(bitmapArray[0]));
            dishInEdit.setThumbnailImg(CameraBasics.bitmapToByteArr(bitmapArray[1]));


            //Log.d("IMAGE_SIZE", String.format("%d ON %d", bitmapArray[0].getWidth(), bitmapArray[0].getHeight()));

            editDishDialogBox.getDishImageView().setImageBitmap(bitmapArray[1]);
            editDishDialogBox.getDishImageView().setScaleType(ImageView.ScaleType.CENTER);
    }


    //photo captured in EditDishDialogBox
    public void onActivityResultAddDish(int requestCode, int resultCode, Intent data)
    {
        bitmapArray = addDishDialogBox.getCameraBasics().myOnActivityResult(requestCode, resultCode, data);

        addDishDialogBox.getDish().setFullsizeImg(CameraBasics.bitmapToByteArr(bitmapArray[0]));
        addDishDialogBox.getDish().setThumbnailImg(CameraBasics.bitmapToByteArr(bitmapArray[1]));


        //Log.d("IMAGE_SIZE", String.format("%d ON %d", bitmapArray[0].getWidth(), bitmapArray[0].getHeight()));

        addDishDialogBox.getDishImageView().setImageBitmap(bitmapArray[1]);
        addDishDialogBox.getDishImageView().setScaleType(ImageView.ScaleType.CENTER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //revoked from edit dish dialog
        if(isEditDishDialogOpened()==true && (requestCode == REQUEST_PICTURE)
                && (resultCode == Activity.RESULT_OK))
        {
            onActivityResultEditDish(requestCode,resultCode,data);
        }

        //revoked from add dish dialog
        if(isAdddishDialogOpened()==true && (requestCode == REQUEST_PICTURE)
                && (resultCode == Activity.RESULT_OK))
        {
            onActivityResultAddDish(requestCode,resultCode,data);
        }

        //revoked from add dish dialog
        if(isAdddishDialogOpened()==true && (requestCode == REQUEST_LOAD_IMAGE)
                && (resultCode == Activity.RESULT_OK))
        {
            onActivityResultAddDish(requestCode, resultCode, data);
        }

//        //Revoked from add dish dialog
//        if (isEditDishDialogOpened()==false && (requestCode == REQUEST_PICTURE) && (resultCode == Activity.RESULT_OK)) {
//
//
//            bitmapArray = cameraBasics.myOnActivityResult(requestCode, resultCode, data);
//
//            newDish.setFullsizeImg(CameraBasics.bitmapToByteArr(bitmapArray[0]));
//            newDish.setThumbnailImg(CameraBasics.bitmapToByteArr(bitmapArray[1]));
//
//
//            Log.d("IMAGE_SIZE", String.format("%d ON %d", bitmapArray[0].getWidth(), bitmapArray[0].getHeight()));
//            addDishImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            addDishImageView.setImageBitmap(bitmapArray[1]);
//
//
//
//
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



    private void wrapAllDataToEventAndAddDishToServer()
    {
        String title = eventTitleEditText.getText().toString();
        LatLng locationCoord = lAC.getChoosenCoordinates();
        String locationString = lAC.getChoosenLocationString();
        //To do: Get string ftom the autoComlete Label;
        //String locationStr =
        EventDishes event = new EventDishes();
        event.setTitle(title);
        event.setStartingYear(startingYear);
        event.setStartingMonth(startingMonth);
        event.setStartingDay(startingDay);
        event.setStartingHour(startingHour);
        event.setStartingMinute(startingMinute);
        event.setEndingYear(endingYear);
        event.setEndingMonth(endingMonth);
        event.setEndingDay(endingDay);
        event.setEndingHour(endingHour);
        event.setEndingMinute(endingMinute);
        event.setApartmentNumber(apartmentNumber);
        event.setEventsDishes(dishArrayList);

        if(locationCoord==null)
        {
            event.setLatitude(latitude);
            event.setLongitude(longitude);
            event.setLocation(location);
        }
        else
        {
            event.setLatitude(locationCoord.latitude);
            event.setLongitude(locationCoord.longitude);
            event.setLocation(locationString);
        }

        event.setEventId(eventID);
        MyModel.getInstance().getModel().addNewEventDishesToServer(event, new SaveToServerCallback() {
            @Override
            public void onResult() {
                //Toast.makeText(getActivity(), "Event Saved", Toast.LENGTH_SHORT).show();
            }
        });

        //Go back to chef Events
        Fragment f = getParentFragment();
        FragmentManager fm = f.getChildFragmentManager();
        fm.popBackStackImmediate();


    }
    private void wrapAllDataAndEditDishInServer()
    {

                String title = eventTitleEditText.getText().toString();
                LatLng locationCoord = lAC.getChoosenCoordinates();
                String locationString = lAC.getChoosenLocationString();
                //To do: Get string ftom the autoComlete Label;
                //String locationStr =
        EventDishes event = new EventDishes();
        event.setTitle(title);
        event.setStartingYear(startingYear);
        event.setStartingMonth(startingMonth);
        event.setStartingDay(startingDay);
        event.setStartingHour(startingHour);
        event.setStartingMinute(startingMinute);
        event.setEndingYear(endingYear);
        event.setEndingMonth(endingMonth);
        event.setEndingDay(endingDay);
        event.setEndingHour(endingHour);
        event.setEndingMinute(endingMinute);
        event.setApartmentNumber(apartmentNumber);
        event.setEventsDishes(dishArrayList);

        if(locationCoord==null)
        {
            event.setLatitude(latitude);
            event.setLongitude(longitude);
            event.setLocation(location);
        }
        else
        {
            event.setLatitude(locationCoord.latitude);
            event.setLongitude(locationCoord.longitude);
            event.setLocation(locationString);
        }

        event.setEventId(eventID);
                MyModel.getInstance().getModel().editEventDishes(event, new MyModel.EditEventCallback() {
                    @Override
                    public void eventHasBeenEdited() {
                        //Toast.makeText(getActivity(), "Even has been edited", Toast.LENGTH_SHORT).show();
                    }
                });

                //Go back to chef Events
                Fragment f = getParentFragment();
                FragmentManager fm = f.getChildFragmentManager();
                fm.popBackStackImmediate();



    }
    public interface SaveToServerCallback
    {
        public void onResult();
    }
    public void hideKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(view!=null)
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
private void buildAddDishDialog()
{
    addDishDialogBox = new AddDishDialogBox(getActivity());
    addDishDialogBox.getCameraBasics().setFragment(EditEventDishesFragment.this);
    isAdddishDialogOpened = true;
    addDishDialogBox.show();

    addDishDialogBox.getDialogBox().setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            Dish newDish = new Dish();
            newDish.setTitle(addDishDialogBox.getDish().getTitle());
            newDish.setDescriprion(addDishDialogBox.getDish().getDescriprion());
            newDish.setPrice(addDishDialogBox.getDish().getPrice());
            newDish.setQuantityLeft(addDishDialogBox.getDish().getQuantityLeft());
            newDish.setTakeAway(addDishDialogBox.getDish().isTakeAway());
            newDish.setToSit(addDishDialogBox.getDish().isToSit());
            //add Photo from dialog

            dishArrayList.add(newDish);
            dishRowListAdapter.notifyDataSetChanged();
            isAdddishDialogOpened = false;

            //  dishRowListAdapter.notifyDataSetChanged();
        }
    });
}
}
