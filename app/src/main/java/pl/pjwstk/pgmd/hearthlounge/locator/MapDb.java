package pl.pjwstk.pgmd.hearthlounge.locator;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.authentication.UserPreferences;
import pl.pjwstk.pgmd.hearthlounge.model.Localization;

/**
 * Created by Froozy on 02.01.2018.
 */

public class MapDb {


    private static FirebaseFirestore fbCloud = FirebaseFirestore.getInstance();
    private CollectionReference fbLocRef = fbCloud.collection("localization");
    private UserPreferences userPref;
    private Timer timer;
    BitmapDescriptor usersIcon;
    List<MarkerOptions> markerOptionsList = new LinkedList<>();

    public MapDb(Context context){
        userPref = new UserPreferences(context);
        usersIcon = BitmapDescriptorFactory.fromResource(R.drawable.position_icon);

    }


    public void updateLocalization(final LatLng latLng){

        Localization temp = new Localization(userPref.getSingleStringPref(userPref.keyUid),
                userPref.getSingleStringPref(userPref.keyUsername),userPref.getRankPref(),latLng);

        fbLocRef.document(temp.getUid()).set(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("UPDATED localization1!!", "update your localization success");
                userPref.setLanOrLng(userPref.keyLatitude ,latLng.latitude);
                userPref.setLanOrLng(userPref.keyLongitude,latLng.longitude);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error with Localization", "Error writing document", e);
            }
        });


    }

    public void readLocalizations(final List<MarkerOptions> myMarkers){

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
                                MarkerOptions tempMarker = new MarkerOptions().position(tempLoc.getLatLng()).title(tempLoc.getUsername()).snippet("\n" + "rank: " + Double.toString(tempLoc.getRank()));
                                tempMarker.icon(usersIcon);
                                tempMarker.visible(true);
                                myMarkers.add(tempMarker);
                                LatLng tempLtLng = tempMarker.getPosition();
                                Log.d("readLocalizations","lat: " + tempLtLng.latitude + " lng: " + tempLtLng.longitude);
                            }
                        } else {
                            Log.d("GET ALL LOCAL", "FAIL FAIL FAIL FAIL ", task.getException());
                        }
                    }
                });

        Log.d("PIPA PIPA PIPA","Wychodze z pobierania listy" );
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
}
