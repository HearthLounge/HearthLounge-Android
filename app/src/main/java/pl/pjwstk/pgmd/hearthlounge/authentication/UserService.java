package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

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

    private ValueEventListener mPostListener;
    private FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
    private DatabaseReference fbRef = fbDb.getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com/users");
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
    public static final String USER_DATA_SP = "pl.pjwstk.pgmd.hearthlounge";

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

        Toast.makeText(getApplicationContext(),"action: " + intent.getStringExtra("action"), Toast.LENGTH_SHORT).show();

        switch (intent.getStringExtra("action")){

            case "login":
            {
                Toast.makeText(getApplicationContext(),"service login", Toast.LENGTH_SHORT).show();
                sUserUid = intent.getStringExtra("uid");
                sUserEmail = fbUser.getEmail();
                getUserData(sUserEmail);
                break;
            }
            case "logout":
            {
                Toast.makeText(getApplicationContext(),"service logout", Toast.LENGTH_SHORT).show();
                userPref.clearUserPref();
                break;
            }
            case "start_0":
            {
                Toast.makeText(getApplicationContext(),"service start 0", Toast.LENGTH_SHORT).show();
//                stopSelf();
                //userPref.clearUserPref();
                break;
            }
            case "start_1":
            {
                Toast.makeText(getApplicationContext(),"service start 1", Toast.LENGTH_SHORT).show();

                break;
            }

        }

        Thread thread = new Thread(new MyThreadUserService(startId));
        thread.start();

        return START_NOT_STICKY;
        //return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"closing service!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public void getUserData(String email){

        Query userQuery = fbRef.orderByChild("email").equalTo(email);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user = singleSnapshot.getValue(User.class);
                    if(user != null) {
                        //TODO userPref.setUserPref(user);
                        Toast.makeText(getApplicationContext(),"Success download " + user.getUsername() + " data", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Your battletag is " + user.getBattletag(), Toast.LENGTH_SHORT).show();
                        userPref.setUserPref(user);
                        Toast.makeText(getApplicationContext(),"Your userPref username is " + userPref.getUsernamePref(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Your userPref battletag is " + userPref.getSingleStringPref("battletag"), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


    }
}


//        fbRef = fbRef.child(uid);
//        ValueEventListener postListener = new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            // Get Post object and use the values to update the UI
//                            //User user = dataSnapshot.getValue(User.class);
//                            // [START_EXCLUDE]
////                            TextView.setText(user.getEmail());
////                            mTitleView.setText(user.getRank());
////                            .setText(user.getUsername());
//                            // [END_EXCLUDE]
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(getApplicationContext(), "Failed to load user.", Toast.LENGTH_SHORT).show();
//                        }
//                    };
//                    fbRef.addValueEventListener(postListener);
//                    // Keep copy of post listener so we can remove it when app stops
//                    mPostListener = postListener;
//        Toast.makeText(getApplicationContext(),/* Hmmm */ , Toast.LENGTH_SHORT).show();

//        fbRef.orderByChild(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // Get user information
//            User user = dataSnapshot.getValue(User.class);
//            String Xusername = user.getUsername();
//
//            // Create new comment object
////            String commentText = mCommentField.getText().toString();
////            Comment comment = new Comment(uid, authorName, commentText);
//
//            // Push the comment, it will appear in the list
//            //mCommentsReference.push().setValue(comment);
//
//            Toast.makeText(getApplicationContext(), Xusername , Toast.LENGTH_SHORT).show();
//
//            // Clear the field
//            //mCommentField.setText(null);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//            Toast.makeText(getApplicationContext(), "Cancel that!" , Toast.LENGTH_SHORT).show();
//
//        }
//    });







//    public UserService(FirebaseUser user){
//
//        this.user = user;
////        Query query = mFirebaseDatabaseReference.child("/user").orderByChild("title").equalTo(user.getUid());
////        query.addValueEventListener(valueEventListener);
////        queryUser = fbRef.orderByChild("/users").equalTo(user.getUid());
////        queryUser.addListenerForSingleValueEvent(vel);
//
//    }

//    public void elo() {
//
//        fbRef.child("/users").addValueEventListener(vel);
//        vel = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//    }