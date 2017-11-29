package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.app.Application;
import android.app.Service;
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

    @Override
    public void onCreate() {
        super.onCreate();
        //Sprawdzanie czy jest użytkowanik
        //if true
        //if false
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {



        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //sUserUid = intent.getStringExtra("uid");
        sUserUid = intent.getStringExtra("uid");
        sUserEmail = fbUser.getEmail(); //Niby null
        if(sUserUid != null){
            //Pobieranie danych z bazy?
            Toast.makeText(getApplicationContext(),"Pobieranie danych...(LIE!!!)", Toast.LENGTH_SHORT).show();
            getUserData(sUserUid,sUserEmail);
        }

        Toast.makeText(getApplicationContext(),"service w toku!", Toast.LENGTH_SHORT).show();


        Thread thread = new Thread(new MyThreadUserService(startId));
        thread.start();

        //Hmmmm


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //Czy ten service kiedyś niszczyć?
        Toast.makeText(getApplicationContext(),"zamykam service!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public void getUserData(String uid,String email){

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

        Query phoneQuery = fbRef.orderByChild("email").equalTo(email);
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user = singleSnapshot.getValue(User.class);
                    Toast.makeText(getApplicationContext(),user.getUsername(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });



    }
}






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