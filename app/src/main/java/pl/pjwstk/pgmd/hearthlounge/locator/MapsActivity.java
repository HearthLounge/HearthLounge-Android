package pl.pjwstk.pgmd.hearthlounge.locator;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.Localization;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    class MyThread implements Runnable
    {
        Activity activity;
        List<MarkerOptions> tempList;
        public MyThread(Activity activity, List<MarkerOptions> list)
        {
            this.activity = activity;
            tempList = list;
        }
        @Override
        public void run()
        {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    MapsActivity.mMap.clear();
                    for(MarkerOptions markerOptions: tempList){


                        MapsActivity.mMap.addMarker(markerOptions);

                    }
            markerList = tempList;
                }
            });

            Log.d("MyThread", ""+tempList.size());
            //Reminder rmd  = new Reminder(20);
            doItAll(10);
        }
    }


    public class Reminder {
        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            //timer.schedule(new RemindTask(), seconds * 1000);
            Log.d("REMINDER", "reminder start waiting -> "+ seconds);
        }

        public void setTimer(int seconds){

            this.timer.schedule(new RemindTask(), seconds * 1000);
        }

        class RemindTask extends TimerTask {
            public void run() {
                    List<MarkerOptions> listReminder = MapsActivity.markerList;
                    Log.d("REMINDER", "reminder starting!");
                    MyThread xThread = new MyThread(MapsActivity.this, listReminder);
                    xThread.run();
//              }
                timer.cancel(); //Terminate the timer thread
            }
        }
    }

//    runOnUiThread(new Runnable() {
//
//        public void run(){
//
//        }
//    });

    private static GoogleMap mMap;
    SupportMapFragment mapFragment;
    HashMap<String,MarkerOptions> mapOfMarkers;
    FusedLocationProviderClient mFusedLocationProviderClient;
    BitmapDescriptor usersIcon;
    public static List<MarkerOptions> markerList;
    Reminder rmd;

    MapDb mapDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapOfMarkers = new HashMap<>();
        mapDb = new MapDb(getApplicationContext());
        markerList = new ArrayList<>();
        //mapDb.readLocalizations(markerList);
        usersIcon = BitmapDescriptorFactory.fromResource(R.drawable.position_icon);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
//            LatLng sydney = new LatLng(54.31, 18.59675);
//            Marker marker01 = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker01"));
//            marker01.setIcon(usersIcon);
//          mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.setMinZoomPreference(14);
//            rmd = new Reminder(7);
//            currentLocation();
//            mapDb.readLocalizations(markerList);
            //getUsersLocation(mapDb.markerOptionsList);
            doItAll(120);

        } else {
            Toast.makeText(MapsActivity.this, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(false);
            }
        }



    }

    public void currentLocation(){

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location mLastKnownLocation = (Location) task.getResult();
                        LatLng myPosition = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(
                                new LatLng(myPosition.latitude,myPosition.longitude)));
                        mapDb.updateLocalization(myPosition);
                        Log.d("currentLocation", "Current location WORKING");
                        //mapDb.readLocalizations();
                    } else {
                        Log.d("Hello there...", "Current location is null. Using defaults.");
                        Log.e("Hello there...", "Exception: %s", task.getException());
//                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });
        }

    }


    public void getUsersLocation(List<MarkerOptions> tempList){
        Log.d("getUserLocations", "Starting..." + tempList.size());

        for(MarkerOptions markerOptions: tempList){

            mMap.addMarker(markerOptions);

        }


//        for(Marker marker: tempList){
//
//            tempMarker = new MarkerOptions().position(localization.getLatLng()).title(localization.getUsername()).snippet("\n" + "rank: " + Double.toString(localization.getRank()));
//            mapOfMarkers.put(localization.getUsername(),tempMarker);
//            tempMarker.icon(usersIcon);
//            tempMarker.visible(true);
//            LatLng tempLtLng = tempMarker.getPosition();
//            Log.d("getUsersLocation","lat: " + tempLtLng.latitude + " lng: " + tempLtLng.longitude);
//            mMap.addMarker(tempMarker);
//
//        }



    }

    public void doItAll(int seconds){

        try {
            rmd = new Reminder(seconds);
            currentLocation();
            mapDb.readLocalizations(markerList, rmd);
            markerList.clear();
        }
        catch(ConcurrentModificationException e){

            Log.d("Concurrent exception", "Error");
        }

    }

    public void onStop(){
        super.onStop();

        mapDb.deleteLocalization();

    }

}

/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */

//    if (mMap != null) {
//
//            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//
//@Override
//public void onMyLocationChange(Location arg0) {
//        // TODO Auto-generated method stub
//
//        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
//        CameraUpdate zoom=CameraUpdateFactory.zoomTo(12);
//
//        mMap.moveCamera(center);
//        mMap.animateCamera(zoom);
//        }
//        });
//
//        }