package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
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

import pl.pjwstk.pgmd.hearthlounge.model.MyApplicationContext;
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
    private DatabaseReference fbRef = fbDb.getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com/users");
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    private FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
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

        Toast.makeText(getApplicationContext(),"action: " + intent.getStringExtra("action"), Toast.LENGTH_SHORT).show();

        switch (intent.getStringExtra("action")){

            case "login":
            {
                Toast.makeText(getApplicationContext(),"ACTION LOGIN", Toast.LENGTH_SHORT).show();
                sUserUid = intent.getStringExtra("uid");
                sUserEmail = fbUser.getEmail();
                //fbRef.orderByChild("users").equalTo(sUserUid).addChildEventListener(UserChildListener(sUserUid));
                fbRef.child(sUserUid).addValueEventListener(UserValueListener(sUserUid));
                break;
            }
            case "logout":
            {
                Toast.makeText(getApplicationContext(),"ACTION LOGOUT", Toast.LENGTH_SHORT).show();
                userPref.clearUserPref();
                Thread thread = new Thread(new MyThreadUserService(startId));
                thread.start();
                break;
            }
            case "start_1":
            {
                Toast.makeText(getApplicationContext(),"ACTION START", Toast.LENGTH_SHORT).show();
//                Thread thread = new Thread(new MyThreadUserService(startId));
//                thread.start();
                break;
            }
            case "update":
            {
                Toast.makeText(getApplicationContext(),"ACTION UPDATE", Toast.LENGTH_SHORT).show();
                sUserUid = intent.getStringExtra("uid");
                sUserEmail = fbUser.getEmail();
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


    public ChildEventListener UserChildListener(String uid){

        Toast.makeText(getApplicationContext(), "DZIECI!!!!", Toast.LENGTH_SHORT).show();
        ChildEventListener temp_fbChildListener = new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Toast.makeText(getApplicationContext(), "Added user ChildEventListener", Toast.LENGTH_SHORT).show();
                User userDb = dataSnapshot.getValue(User.class);
                userPref.setUserPref(userDb);
                Toast.makeText(getApplicationContext(), "Read " + userDb.getUsername() + " data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Toast.makeText(getApplicationContext(), "Changed user ChildEventListener", Toast.LENGTH_SHORT).show();
                User userdb = dataSnapshot.getValue(User.class);
                userPref.setUserPref(userdb);
                Toast.makeText(getApplicationContext(), "" + userdb.getUsername() + " data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                //TODO
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                //Ignore that for a while
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Cancelled user ChildEventListener", Toast.LENGTH_SHORT).show();
            }
        };

        //fbRef.orderByChild("users").equalTo("uid").addChildEventListener(temp_fbChildListener);
        fbChildListener = temp_fbChildListener;

        return temp_fbChildListener;
    }


    public ValueEventListener UserValueListener(String uid){

        ValueEventListener uValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                userPref.setUserPref(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        fbValueListener = uValueListener;

        return uValueListener;
        // Listen for comments
//        mAdapter = new CommentAdapter(this, mCommentsReference);
//        mCommentsRecycler.setAdapter(mAdapter);

    }


    public void UserDataConnector(String email,final String action){

        Query userQuery = fbRef.orderByChild("email").equalTo(email);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user = singleSnapshot.getValue(User.class);
                    Toast.makeText(getApplicationContext(), "Success download " + user.getUsername() + " data", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Your battletag is " + user.getBattleTag(), Toast.LENGTH_SHORT).show();
                    userPref.setUserPref(user);
                    Toast.makeText(getApplicationContext(), "Your userPref username is " + userPref.getUsernamePref(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


    }

}

























//    @Override
//    public int onStartCommand(final Intent intent, int flags, int startId) {
//
//        String action = intent.getStringExtra("action");
//        Toast.makeText(getApplicationContext(), "start service -> action = "+ action, Toast.LENGTH_SHORT).show();
//        ValueEventListener UserListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    User userDB = dataSnapshot.getValue(User.class);
//                    userPref.setUserPref(userDB);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                    Toast.makeText(getApplicationContext(), "Failed to load User", Toast.LENGTH_SHORT).show();
//                }
//
//
//        };
//        //fbRef.orderByChild("uid").equalTo(userPref.getSingleStringPref("uid")).addValueEventListener(UserListener);
//        fbRef.child(userPref.getSingleStringPref("uid")).addValueEventListener(UserListener);
//
////        else {
//////            Thread thread = new Thread(new MyThreadUserService(startId));
//////            thread.start();
////        }
//
//
//        return START_STICKY;
//    }
//
//
//
//    @Override
//    public void onDestroy() {
//        Toast.makeText(getApplicationContext(),"closing service!", Toast.LENGTH_SHORT).show();
//        super.onDestroy();
//    }
//
//}




//        mPostReference.addValueEventListener(postListener);
//
//        mPostListener = postListener;
//        // Listen for comments
//        mAdapter = new CommentAdapter(this, mCommentsReference);
//        mCommentsRecycler.setAdapter(mAdapter);

//        Query userQuery = fbRef.orderByChild("email").equalTo(email);
//        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    user = singleSnapshot.getValue(User.class);
//                    if(user != null) {
//
////                        if(action == "login") {
////                            //TODO userPref.setUserPref(user);
////                            Toast.makeText(getApplicationContext(), "Success download " + user.getUsername() + " data", Toast.LENGTH_SHORT).show();
////                            Toast.makeText(getApplicationContext(), "Your battletag is " + user.getBattleTag(), Toast.LENGTH_SHORT).show();
////                            userPref.setUserPref(user);
////                            Toast.makeText(getApplicationContext(), "Your userPref username is " + userPref.getUsernamePref(), Toast.LENGTH_SHORT).show();
////                        }
////                        else if(action == "update"){
////
////
////
////
////                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAG, "onCancelled", databaseError.toException());
//            }
//        });






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


// Toast.makeText(getApplicationContext(),"action: " + intent.getStringExtra("action"), Toast.LENGTH_SHORT).show();
//fbRef = fbRef intent.getStringArrayExtra("uid");
//        switch (intent.getStringExtra("action")){
//
//            case "login":
//            {
//                Toast.makeText(getApplicationContext(),"ACTION LOGIN", Toast.LENGTH_SHORT).show();
//                sUserUid = intent.getStringExtra("uid");
//                sUserEmail = fbUser.getEmail();
//                UserDataConnector(sUserEmail,intent.getStringExtra("action"));
//                break;
//            }
//            case "logout":
//            {
//                Toast.makeText(getApplicationContext(),"ACTION LOGOUT", Toast.LENGTH_SHORT).show();
//                userPref.clearUserPref();
//                break;
//            }
//            case "start":
//            {
//                Toast.makeText(getApplicationContext(),"ACTION START", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            //TODO add it to UserAccount
//            case "update":
//            {
//                Toast.makeText(getApplicationContext(),"ACTION UPDATE", Toast.LENGTH_SHORT).show();
//                sUserUid = intent.getStringExtra("uid");
//                sUserEmail = fbUser.getEmail();
//                UserDataConnector(sUserEmail,intent.getStringExtra("action"));
//                break;
//            }
//
//        }