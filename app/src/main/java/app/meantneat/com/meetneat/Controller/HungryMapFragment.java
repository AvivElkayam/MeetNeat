package app.meantneat.com.meetneat.Controller;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTabHost;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.meantneat.com.meetneat.R;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.*;



/**
 * Created by mac on 5/17/15.
 */

 public class HungryMapFragment extends Fragment implements OnMapReadyCallback {

    private static View view;
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Google_Map = supportMapFragment.getMap();
//               SupportMapFragment mapFragment = (SupportMapFragment) (getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.map));

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        Drawable dr = getResources().getDrawable(R.drawable.logo1);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(32.073776, 34.781890))
                .title("Marker")
                .icon(BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, 200, 200, true)))
        );
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
