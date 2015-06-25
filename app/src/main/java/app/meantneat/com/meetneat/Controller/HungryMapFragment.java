package app.meantneat.com.meetneat.Controller;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.meantneat.com.meetneat.Camera.SpecifiecChefEventsDialogBox;
import app.meantneat.com.meetneat.EventDishes;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.Camera.SpecificEventDishesDialogBox;
import app.meantneat.com.meetneat.R;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.*;



/**
 * Created by mac on 5/17/15.
 */

 public class HungryMapFragment extends Fragment implements OnMapReadyCallback , LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static View view;
    private Map<Marker, EventDishes> allMarkersMap = new HashMap<Marker, EventDishes>();
    private ArrayList<EventDishes> eventsArray = new ArrayList<>();
    private ArrayList<LatLng> coordinatesArray = new ArrayList<>();
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

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
       SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
        mGoogleApiClient.connect();
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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

        //Google_Map = supportMapFragment.getMap();
//               SupportMapFragment mapFragment = (SupportMapFragment) (getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map));

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

//                final SpecifiecChefEventsDialogBox dialogBox = new SpecifiecChefEventsDialogBox(
//                        getActivity(),"pFubDWWXGT",marker.getPosition());
                EventDishes eventByMarker =  allMarkersMap.get(marker);
                final SpecifiecChefEventsDialogBox dialogBox = new SpecifiecChefEventsDialogBox(
                        getActivity(),eventByMarker.getChefID(),marker.getPosition());


//                final SpecificEventDishesDialogBox dialogBox = new SpecificEventDishesDialogBox(getActivity(),"7k60BVnPPQ","Jonathan Roshfeld","01.08.2004 - 03.09.2014","Italian Party");
//                dialogBox.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        //dialogBox.getDialog().show();
//                    }
//                });

                dialogBox.show();
                return false;
            }
        });
        //googleMap.animateCamera(Cmera);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(32.073776, 34.781890), 16));
        //ArrayList<LatLng> coordinatesArr = getClosestCoordinatesFromServer();
        getClosestCoordinatesFromServer();

    }

    private void getClosestCoordinatesFromServer() {
        MyModel.getInstance().getModel().getChefsEventFromServer(new ChefEventDishesFragment.GetEventDishesCallback() {
            @Override
            public void done(ArrayList<EventDishes> eventDisheses) {
                eventsArray = eventDisheses;
                ArrayList<LatLng> coordinatesArrayTmp = new ArrayList<>();
                for (EventDishes e : eventsArray) {
                    LatLng tmp = new LatLng(e.getLatitude(), e.getLongitude());
                    coordinatesArrayTmp.add(tmp);



                }
                coordinatesArray = coordinatesArrayTmp;
                showClosestEvents();
            }
        });
    }

    private void showClosestEvents()
    {
        //make hashmap of markers and coordinates - if there is
        // 2 diffrent chefs in the same coordinate rotate the marker
        // if there same chef in the same location diffrent events - add only one event...


        Drawable dr = getResources().getDrawable(R.drawable.logo1);
        final Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        int i=0;
        for (i = 0;i< coordinatesArray.size(); i++) {
            Marker m = googleMap.addMarker(new MarkerOptions()
                            .position(coordinatesArray.get(i))
                            .title("Marker")
                            .rotation((float)90.0)
                            .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, true)))
            );
            allMarkersMap.put(m, eventsArray.get(i));
        }
    }

        
    @Override
    public void onConnected(Bundle bundle) {
        Log.e("LNGLTD", "Connected");
        LocationServices.FusedLocationApi.requestLocationUpdates(
               mGoogleApiClient, mLocationRequest, this);
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
        //googleMap.clear();
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



}
