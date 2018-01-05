package pl.pjwstk.pgmd.hearthlounge.locator;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.authentication.UserPreferences;
import pl.pjwstk.pgmd.hearthlounge.model.Localization;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public class Waiter {

        Timer timer;

        public Waiter() {
            timer = new Timer();
        }

        public void setTimer(int seconds){

            this.timer.schedule(new WaiterTask(), seconds * 1000);
            Log.d("WAITER", "waiter start waiting -> "+ seconds);
        }

        class WaiterTask extends TimerTask {
            public void run() {
                Log.d("WAITER", "waiter starting!");
                MyThread xThread = new MyThread(MapsActivity.this, MapsActivity.markerList, timer);
                xThread.run();
//              }
                 //Terminate the timer thread
            }
        }

    }


    class MyThread implements Runnable
    {
        Activity activity;
        Timer timer;
        public MyThread(Activity activity, List<MarkerOptions> list, Timer timer)
        {
            this.activity = activity;
            this.timer = timer;
        }
        @Override
        public void run()
        {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    doItAll();
                    Log.d("SUUUPER", "launch another!");
//                    mMap.clear();
//                    currentLocation();
//                    readLocalizations();
//                    MapsActivity.mMap.clear();
//                    for(MarkerOptions markerOptions: tempList){
//
//                        MapsActivity.mMap.addMarker(markerOptions);
//
//                    }
//            markerList = tempList;
                }

            });
            Log.d("SUUUPER", "leaving!");
            timer.cancel();

            //Reminder rmd  = new Reminder(20);
//            doItAll(10);
        }
    }


//

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
    //Reminder rmd;
    Waiter waiter;

    MapDb mapDb;

    private static FirebaseFirestore fbCloud = FirebaseFirestore.getInstance();
    private CollectionReference fbLocRef = fbCloud.collection("localization");
    private UserPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        checkLocationPermission();

        userPref = new UserPreferences(getApplicationContext());
        mapOfMarkers = new HashMap<>();
        mapDb = new MapDb(getApplicationContext());
        markerList = new ArrayList<>();
        //mapDb.readLocalizations(markerList);
        usersIcon = BitmapDescriptorFactory.fromResource(R.drawable.position_icon);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        waiter = new Waiter();

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
            doItAll();
//            currentLocation();
//            readLocalizations();
//            mapDb.readLocalizations(markerList);
            //getUsersLocation(mapDb.markerOptionsList);


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

    public void doItAll(){

        mMap.clear();
        currentLocation();
        readLocalizations();
        new Waiter().setTimer(10);
    }

    public void onStop(){
        super.onStop();

        mapDb.deleteLocalization();

    }

    public void readLocalizations(){

        fbCloud.collection("/localization").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Localization tempLoc;
                            for (DocumentSnapshot document : task.getResult()) {

                                Log.d("GET ALL LOCAL", document.getId() + " => " + document.getData());
                                tempLoc = new Localization(document.getData());
//                                if(Math.abs(tempLoc.getLat() - userPref.getLanOrLng(userPref.keyLatitude)) != 0 &&
//                                Math.abs(tempLoc.getLng() - userPref.getLanOrLng(userPref.keyLongitude)) != 0){
//
//                                    tempList.add(tempLoc);
//                                }
                                Double x = Double.valueOf(String.valueOf(tempLoc.getRank()));
                                MarkerOptions tempMarker = new MarkerOptions().position(tempLoc.getLatLng()).title(tempLoc.getUsername() + " rank:" + x.intValue());
                                addNewMarker(tempMarker);
                            }
                        } else {
                            Log.d("GET ALL LOCAL", "FAIL FAIL FAIL FAIL ", task.getException());
                        }
                    }
                });
        Log.d("GET ALL LOCAL","Wychodze z pobierania listy" );
    }



    public void addNewMarker(MarkerOptions temp){

        temp.icon(usersIcon);
        temp.visible(true);
        mMap.addMarker(temp);
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("tytul")
                        .setMessage("message")
                        .setPositiveButton("button", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                        //doItAll();
                        onMapReady(mMap);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
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

//public class Reminder {
//        Timer timer;
//
//        public Reminder(int seconds) {
//            timer = new Timer();
//            //timer.schedule(new RemindTask(), seconds * 1000);
//        }
//
//        public void setTimer(int seconds){
//
//            this.timer.schedule(new RemindTask(), seconds * 1000);
//            Log.d("REMINDER", "reminder start waiting -> "+ seconds);
//        }
//
//        class RemindTask extends TimerTask {
//            public void run() {
//                    List<MarkerOptions> listReminder = MapsActivity.markerList;
//                    Log.d("REMINDER", "reminder starting!");
//                    MyThread xThread = new MyThread(MapsActivity.this, listReminder);
//                    xThread.run();
////              }
//                //timer.cancel(); //Terminate the timer thread
//            }
//        }
//    }