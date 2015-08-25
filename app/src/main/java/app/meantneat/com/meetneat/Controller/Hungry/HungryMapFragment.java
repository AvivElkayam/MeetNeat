package app.meantneat.com.meetneat.Controller.Hungry;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import app.meantneat.com.meetneat.Camera.SpecifiecChefEventsDialogBox;
import app.meantneat.com.meetneat.Controller.Chef.ChefEventDishesFragment;
import app.meantneat.com.meetneat.Entities.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.*;



/**
 * Created by mac on 5/17/15.
 */

 public class HungryMapFragment extends Fragment implements OnMapReadyCallback , LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static View view;
    private Map<Marker, EventDishes> allMarkersMap = new ConcurrentHashMap<Marker,EventDishes>();
    private ArrayList<EventDishes> cuurentEventsArray = new ArrayList<>();
    private ArrayList<EventDishes> newEventsArray = new ArrayList<>();
    private ArrayList<LatLng> coordinatesArray = new ArrayList<>();
    private GoogleMap googleMapHungry;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    ViewGroup fragmentViewGroup;
    public LatLng lastCenter;
    public static LatLng lastCenterStatic;
    boolean firstTime = true;
    Marker lastMarker;
    BitmapDescriptor chefMarker;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        this.fragmentViewGroup=container;
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.hungry_map_fragment, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }


        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        android.support.v4.app.FragmentManager fragmentManager = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        mGoogleApiClient.connect();

        //set properties of location request - to run look in OnConnected
//        mLocationRequest = new LocationRequest();
//
//        mLocationRequest.setInterval(10000);
//        mLocationRequest.setFastestInterval(5000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {


        this.googleMapHungry = googleMap;
        googleMapHungry.setMyLocationEnabled(true);

        //restaurant marker init
        Drawable dr = getResources().getDrawable(R.drawable.chef_48_green);
        final Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        chefMarker = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, 96, 96, true));


        googleMapHungry.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            //TO DO:: check best practice to identify camera stopped changing
            //TO DO:: check how to stop parse query/function - we will want to stop last restaurants load from server
            // if camera changed too far(^^)
            //TO DO:: center changed -> clear only the events that not in a 3km radius+check their availability
            // -> get closest 3km from server except those we already have(if not null) ->draw them
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {


                if(firstTime == true) {
                    lastCenter = new LatLng(50, 50);
                    lastCenterStatic = lastCenter;
                    firstTime = false;

                }


                float[] results = new float[10]; //10 - random
                Location.distanceBetween(
                        lastCenter.latitude,
                        lastCenter.longitude,
                        cameraPosition.target.latitude,
                        cameraPosition.target.longitude,
                        results);

                //results[0] = distance in Meters
                if(results[0] > 0)

                    getClosestCoordinatesFromServer();

               //temp Mechanism to stop model from downloading if while downloading the center is changed
                lastCenter = googleMapHungry.getCameraPosition().target;
                lastCenterStatic = lastCenter;
            }

        });

        googleMapHungry.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                EventDishes eventByMarker = allMarkersMap.get(marker);
                final SpecifiecChefEventsDialogBox dialogBox = new SpecifiecChefEventsDialogBox(
                        getActivity(), eventByMarker.getChefID(), eventByMarker.getChefName(), marker.getPosition());


                dialogBox.show();
                return false;
            }
        });

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.073776, 34.781890), 16));


    }

    private void getClosestCoordinatesFromServer() {

        MyModel.getInstance().getModel().getClosestChefsRadius(
                new ChefEventDishesFragment.GetEventDishesCallback() { //Callback
                    @Override
                    public void done(ArrayList<EventDishes> eventDisheses) {

                        //currentEventsArray = eventDisheses;
                        newEventsArray = eventDisheses;
//                        ArrayList<LatLng> coordinatesArrayTmp = new ArrayList<>();
//                        for (EventDishes e : eventsArray) {
//
//                            LatLng tmp = new LatLng(e.getLatitude(), e.getLongitude());
//                            coordinatesArrayTmp.add(tmp);
//
//
//                        }
//
//
//                        coordinatesArray = coordinatesArrayTmp;
                        showClosestEvents();
                    }
                }
                , googleMapHungry.getCameraPosition().target); //MapCenter

    }

    private void mergeEventsArray()
    {
        for (int i = 0;i< newEventsArray.size(); i++) {



            for(int j=0;j<cuurentEventsArray.size();j++)
            {
                if(newEventsArray.get(i).getEventId() == cuurentEventsArray.get(j).getEventId()) {

                    newEventsArray.remove(i);
                    break; // continue to next new event

                }
            }


        }

    }

    private void showClosestEvents()
    {
        //make hashmap of markers and coordinates - if there is
        // 2 diffrent chefs in the same coordinate rotate the marker <-- TO DO
        // if there same chef in the same location diffrent events - add only one event...

        //TO DO:: remove events in the same location+chef --> server should do it
        removeFarEvents(); // Remove Events far than 3 km
        mergeEventsArray(); //Remove existing events that downloaded from the server - TEMP...
        //addClosestUniqueEvents();

        //Add new markers to GoogleMap
        for (int i = 0;i< newEventsArray.size(); i++) {

        EventDishes event = newEventsArray.get(i);
        LatLng coordinate = new LatLng(event.getLatitude(), event.getLongitude());
        final Marker m = googleMapHungry.addMarker(new MarkerOptions()
                            .position(coordinate)
                            .title("Marker")
                             .alpha(0)
                            //.rotation((float) 90.0)
                            .icon(chefMarker)



        );
        //Marker m = setUpMap(coordinatesArray.get(i), BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.chef_hat_yellow));
        allMarkersMap.put(m, newEventsArray.get(i));
        cuurentEventsArray.add(newEventsArray.get(i));

            //marker fade in
            ValueAnimator ani = ValueAnimator.ofFloat(0, 1); //change for (1,0) if you want a fade out
            ani.setDuration(3000);
            ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    m.setAlpha((float) animation.getAnimatedValue());
                }
            });
            ani.start();
        }
    }

    private void removeFarEvents()
    {
        //Clear(from GoogleMap) only the ones not in the 3 km radius + remove it from allMarkersMap
        float results[] = new float[10];
        for (Map.Entry<Marker,EventDishes> entry : allMarkersMap.entrySet()) {
            final Marker marker = entry.getKey();
            EventDishes event = entry.getValue();



            Location.distanceBetween(
                    lastCenter.latitude,
                    lastCenter.longitude,
                    marker.getPosition().latitude,
                    marker.getPosition().longitude,
                    results);

            //results[0] = distance in Meters
            //There is an option just to hide -  marker.setVisibility
            if(results[0] > 3000) {
                //marker fade in
                ValueAnimator ani = ValueAnimator.ofFloat(1, 0); //change for (1,0) if you want a fade out and reverse
                ani.setDuration(3000);
                ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        marker.setAlpha((float) animation.getAnimatedValue());
                        
                    }
                });
                ani.start();


                allMarkersMap.remove(marker);
                //remove from current events
                cuurentEventsArray.remove(event);
            }
        }
    }

    private void addClosestUniqueEvents() {
               //TO DO:: download from server events that not already on map

    }
    @Override
    public void onConnected(Bundle bundle) {
        Log.e("LNGLTD", "Connected");

        // on location update by time - (listener in the class)
        //LocationServices.FusedLocationApi.requestLocationUpdates(
           //    mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(),"Location Updated",Toast.LENGTH_SHORT).show();
        googleMapHungry.clear();
        getClosestCoordinatesFromServer();
        //googleMapHungry.clear();
    }
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.map_activity);
//
//            MapFragment mapFragment = (MapFragment) getFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//        }
//
//        @Override
//        public void onMapReady(GoogleMap map) {
//            LatLng sydney = new LatLng(-33.867, 151.206);
//
//            map.setMyLocationEnabled(true);
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//            map.addMarker(new MarkerOptions()
//                    .title("Sydney")
//                    .snippet("The most populous city in Australia.")
//                    .position(sydney));
//        }
//    }


private Bitmap createChefTemplate(Bitmap chefImage)
{
    View v = getActivity().getLayoutInflater().inflate(R.layout.chef_template,fragmentViewGroup,false);
    ImageView imageView = new ImageView(getActivity());
    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
    //int px = Math.round(256 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(1048,1048);
    params.gravity= Gravity.CENTER;
    imageView.setLayoutParams(params);
    imageView.setBackground(getResources().getDrawable(R.drawable.eyal_shani));
    FrameLayout layout = (FrameLayout)v.findViewById(R.id.chef_template_container);
    layout.addView(imageView);

   // if the view wasn't displayed before the size of it will be zero. Its possible to measure it like this:
    Bitmap b;
    if (v.getMeasuredHeight() <= 0) {
        v.measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        b = Bitmap.createBitmap(1048, 1512, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.draw(c);
        return b;
    }
    else
    {
         b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
    }


return b;

}

    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }
//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the
//        // map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
//    }

    private Marker setUpMap(final LatLng markerLatLng,Bitmap bitmap) {

        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chef_template, null);
        ImageView imageView = (ImageView) marker.findViewById(R.id.chef_template_image_view);
        imageView.setImageBitmap(bitmap);
        Marker m = googleMapHungry.addMarker(new MarkerOptions()
                .position(markerLatLng)
                .title("Title")
                .snippet("Description")
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));
        Fragment fragment = this;
        final View mapView = fragment.getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @SuppressLint("NewApi")
                // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    LatLngBounds bounds = new LatLngBounds.Builder().include(markerLatLng).build();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    googleMapHungry.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                }
            });
        }
        return m;
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


}
