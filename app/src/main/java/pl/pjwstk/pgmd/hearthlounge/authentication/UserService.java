package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.model.User;

import static android.content.ContentValues.TAG;

/**
 * Created by Froozy on 22.11.2017.
 */


public class UserService extends Service {

    final class MyThreadUserService implements Runnable{

        int service_id;


        MyThreadUserService(int id){

            this.service_id = id;

        }


        @Override
        public void run() {

            //getUserData(sUserUid);
            stopSelf(service_id);

        }

    }

    private String sUserUid;
    private String sUserEmail;
    private User user;


    private FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
    private DatabaseReference fbUserRef = fbDb.getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com/users");
    private FirebaseUser fbUser;
    public static final String USER_DATA_SP = "pl.pjwstk.pgmd.hearthlounge";

    private ChildEventListener fbChildListener;
    private ValueEventListener fbValueListener;

    UserPreferences userPref;

    @Override
    public void onCreate() {
        super.onCreate();
        userPref = new UserPreferences(this.getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "action: " + intent.getStringExtra("action"), Toast.LENGTH_SHORT).show();
        switch (intent.getStringExtra("action")) {

            case "login": {
                Toast.makeText(getApplicationContext(), "ACTION LOGIN", Toast.LENGTH_SHORT).show();
                sUserUid = intent.getStringExtra("uid");
                fbValueListener = fbUserRef.child(sUserUid).addValueEventListener(UserValueListener());
                break;
            }
            case "logout": {
                Toast.makeText(getApplicationContext(), "ACTION LOGOUT", Toast.LENGTH_SHORT).show();
                userPref.clearUserPref();
                Thread thread = new Thread(new MyThreadUserService(startId));
                thread.start();
                break;
            }
            case "start": {
                Toast.makeText(getApplicationContext(), "ACTION START", Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(new MyThreadUserService(startId));
                thread.start();
                break;
            }
            case "update": {
                Toast.makeText(getApplicationContext(), "ACTION UPDATE", Toast.LENGTH_SHORT).show();
                sUserUid = intent.getStringExtra("uid");
                User user = new User((User) intent.getParcelableExtra("updated_user"));
                updateUserData(user);
                break;
            }
            case "update_password":{

                Toast.makeText(getApplicationContext(), "Password changing in progress!", Toast.LENGTH_SHORT).show();
                UpdateUserPassword(intent.getStringExtra("password"));
                break;
            }
            case "delete":{
                Toast.makeText(getApplicationContext(), "ACTION DELETE", Toast.LENGTH_SHORT).show();
                //fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).removeEventListener(fbValueListener);
                //DeleteUser(fbUser);
                DeleteFromDb();
                //fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).removeEventListener(fbValueListener);
                userPref.clearUserPref();
//                Thread thread = new Thread(new MyThreadUserService(startId));
//                thread.start();
                break;
            }

            default: {
//                if(fbUser !=null){
//                    fbValueListener = fbUserRef.child(userPref.getSingleStringPref("uid")).addValueEventListener(UserValueListener());
//                }
                break;
            }

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"closing service!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public ValueEventListener UserValueListener(){

        ValueEventListener uValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if(user != null){
                    userPref.setUserPref(user);
                }
                else{
                    userPref.clearUserPref();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        fbValueListener = uValueListener;
        return uValueListener;
    }

    private void updateUserData(User user){

        DataComparator(user.getEmail(),userPref.keyEmail);
        DataComparator(user.getBattletag(),userPref.keyBattletag);
        DataComparator(user.getFavouriteClass(),userPref.keyFavouriteClass);
        DataComparator(user.getFacebook(),userPref.keyFacebook);
        DataComparator(user.getTwitch(),userPref.keyTwitch);
        DataComparator(user.getTwitter(),userPref.keyTwitter);
        DataComparator(user.getYoutube(),userPref.keyYoutube);
        DataComparator(user.getRegion(),userPref.keyRegion);
        DataComparator(user.getAvatar(),userPref.keyAvatar);

    }

    private void UpdateUserEmail(final String newEmail){

        fbUser.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).child("email").setValue(newEmail);
                            Toast.makeText(getApplicationContext(), "Changed user email with success!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void UpdateUserPassword(String password){

        fbUser.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Changed user password with success!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean DeleteUser(FirebaseUser deleteUser){
        Toast.makeText(getApplicationContext(), "Deleting in progress...", Toast.LENGTH_SHORT).show();

        return false;
    }

    public void DeleteFromDb(){

        String link = "https://hearthlounge-32197.firebaseio.com/users/"+userPref.getSingleStringPref(userPref.keyUid);
        DatabaseReference RefToDelete = fbDb.getReferenceFromUrl(link);
        fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).setValue(null);
    }

    private void DataComparator(String userValue, String key) {

        if(userValue != null) {
            fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).child(key).setValue(userValue);
            Toast.makeText(getApplicationContext(), key + " " + userValue, Toast.LENGTH_SHORT).show();
            if (userValue.isEmpty()) {
                Toast.makeText(getApplicationContext(), key + " delete -> " + userValue, Toast.LENGTH_SHORT).show();
                fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).child(key).setValue(null);
            }
        }
        //if(userPref.getSingleStringPref(key) == "" && userValue == null){ fbUserRef.child(userPref.getSingleStringPref(userPref.keyUid)).child(key).setValue(null); }
    }
}