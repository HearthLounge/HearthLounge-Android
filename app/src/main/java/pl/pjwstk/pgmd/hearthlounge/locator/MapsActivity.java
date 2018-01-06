package pl.pjwstk.pgmd.hearthlounge.locator;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        MyThread xThread;

        public Waiter() {
            timer = new Timer();
        }

        public void setTimer(boolean xActive, int seconds){

            if(xActive) {
                this.timer.schedule(new WaiterTask(), seconds * 1000);
                Log.d("WAITER", "waiter start waiting -> " + seconds);
                xThread = new MyThread(MapsActivity.this, MapsActivity.markerList, timer);
            }
        }

        public void closeMyThread(){

            Thread.interrupted();
        }

        class WaiterTask extends TimerTask {
            public void run() {
                Log.d("WAITER", "waiter starting!");

                xThread.run(timer);
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
        public void run(){}
        public void run(Timer timer)
        {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    doItAll();
                    Log.d("SUUUPER", "launch another!");
                }
            });
            Log.d("SUUUPER", "leaving!");

        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private HashMap<String,MarkerOptions> mapOfMarkers;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private BitmapDescriptor usersIcon;
    public static List<MarkerOptions> markerList;
    public LatLng userPosition;
    //Reminder rmd;
    Waiter waiter;


    private ClosingAppService closingAppService;
    private Boolean firstUpdate;
    private Boolean addUser;
    private Boolean active;

    private static FirebaseFirestore fbCloud = FirebaseFirestore.getInstance();
    private CollectionReference fbLocRef = fbCloud.collection("localization");
    private UserPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        firstUpdate = true;
        addUser = true;
        active = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        closingAppService = new ClosingAppService(this);
        checkLocationPermission();
        userPref = new UserPreferences(getApplicationContext());
        mapOfMarkers = new HashMap<>();
        markerList = new ArrayList<>();
        usersIcon = BitmapDescriptorFactory.fromResource(R.drawable.position_icon);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        waiter = new Waiter();
        startService(new Intent(getApplicationContext(), ClosingAppService.class));
    }
    public void onPause(){
        super.onPause();
        addUser = false;
        deleteLocalization();
    }
    public void onResume(){
        super.onResume();
        if(mMap != null){
            addUser = true;
            doItAll();
        }

    }
    public void onStop(){
        super.onStop();
        addUser = false;
        deleteLocalization();
        waiter.timer.cancel();

    }
    public void OnDestroy(){
        super.onDestroy();

    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
//          mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.setMinZoomPreference(14);
            doItAll();
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
                        userPosition = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        if(firstUpdate){
                        firstUpdate = false;
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                                new LatLng(userPosition.latitude,userPosition.longitude)));
                        }
                        updateLocalization(userPosition);
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
    public void doItAll(){

        mMap.clear();
        currentLocation();
        readLocalizations();
        new Waiter().setTimer(addUser,15);
    }
    public void readLocalizations(){

        fbCloud.collection("/localization").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Localization tempLoc;
                            for (DocumentSnapshot document : task.getResult()) {

                                if(!userPref.getSingleStringPref(userPref.keyUid).equals(document.getId())) {
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
                            }
                        } else {
                            Log.d("GET ALL LOCAL", "FAIL FAIL FAIL FAIL ", task.getException());
                        }
                    }
                });
        Log.d("GET ALL LOCAL","Wychodze z pobierania listy" );
    }
    public void updateLocalization(final LatLng latLng){

        Localization temp = new Localization(userPref.getSingleStringPref(userPref.keyUid),
                userPref.getSingleStringPref(userPref.keyUsername),userPref.getRankPref(),latLng);
        if(addUser) {
            fbLocRef.document(temp.getUid()).set(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("UPDATED localization1!!", "update your localization success");
                    userPref.setLanOrLng(userPref.keyLatitude, latLng.latitude);
                    userPref.setLanOrLng(userPref.keyLongitude, latLng.longitude);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Error with Localization", "Error writing document", e);
                }
            });
        }
    }
    public void deleteLocalization(){

        fbLocRef.document(userPref.getSingleStringPref(userPref.keyUid))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Delete my position!!!", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Delete my position!!!", "Error deleting document", e);
                    }
                });
    }
    public void addNewMarker(MarkerOptions temp){

        temp.icon(usersIcon);
        temp.visible(true);
        mMap.addMarker(temp);
    }
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
