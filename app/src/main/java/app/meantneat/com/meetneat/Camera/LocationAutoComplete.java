package app.meantneat.com.meetneat.Camera;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import app.meantneat.com.meetneat.R;

/**
 * Created by DanltR on 10/06/2015.
 */
public class LocationAutoComplete implements AdapterView.OnItemClickListener {

    Context context;
    static ArrayList resultListIds = null;


    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyAoIFsCvxHkeyfQVzktxItuCUbmmyrwv-w";
    private GoogleApiClient mGoogleApiClient;

    private String choosenPlaceid;
    private LatLng choosenPlaceLatLng;
    private String choosenLocationString;
    private AutoCompleteTextView autoCompView;
    public LocationAutoComplete(Context c, GoogleApiClient mGoogleApiClient,int screen) {
        this.context = c;
        this.mGoogleApiClient = mGoogleApiClient;
         autoCompView = null;

        //Screen 1 = EditDishEvent
        if(screen == 2) {
            autoCompView = (AutoCompleteTextView) ((Activity) context).findViewById(R.id.add_event_fragment_auto_location_label);
        }
        //Screen 1 = addDishEvent
        if(screen == 1){
            autoCompView = (AutoCompleteTextView) ((Activity) context).findViewById(R.id.add_dish_event_fragment_location_auto);

        }

        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(context, R.layout.autocomplete_list_item));
        autoCompView.setOnItemClickListener(this);
    }
    public void setAutoCompleteTextView(String location)
    {
        autoCompView.setText(location);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        choosenLocationString=str;

        choosenPlaceid = (String) resultListIds.get(position);

        //Gets Choosen Place Coordinates(LATLNG)
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, choosenPlaceid)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {


                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            final Place myPlace = places.get(0);
                            LatLng l = myPlace.getLatLng() ;
                            LocationAutoComplete.this.choosenPlaceLatLng = l;
                            Log.e("Location Coordinates", "Place found: " + l.latitude + " " + l.longitude);
                        }
                        places.release();
                    }
                });


        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


    public LatLng getChoosenCoordinates(){

        return this.choosenPlaceLatLng;
    }
    public String getChoosenLocationString()
    {
        return choosenLocationString;
    }

    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:il");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                //
                // conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            resultListIds = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println(predsJsonArray.getJSONObject(i).getString("place_id"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                resultListIds.add(predsJsonArray.getJSONObject(i).getString("place_id"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }




    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index).toString();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    public void setChoosenPlaceLatLng(LatLng choosenPlaceLatLng) {
        this.choosenPlaceLatLng = choosenPlaceLatLng;
    }

    public void setChoosenLocationString(String choosenLocationString) {
        this.choosenLocationString = choosenLocationString;
    }
}
