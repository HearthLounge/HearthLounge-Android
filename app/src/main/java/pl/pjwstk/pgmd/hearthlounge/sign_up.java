package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.pjwstk.pgmd.hearthlounge.model.User;

/**
 * Created by Froozy on 03.10.2017.
 */

public class sign_up extends AppCompatActivity /*implements View.OnClickListener */ {

    private Button button_register;
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_password;
    private TextView text_to_login;

    private static final String TAG = "Sign_up";

    private FirebaseAuth fb_auth;
    private static FirebaseAuth.AuthStateListener fb_auth_listener;
    private FirebaseDatabase fb_database;
    private DatabaseReference users;
    private DatabaseReference users_uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

//        fb_auth_listener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };

        //Firebase configurate
        fb_database = FirebaseDatabase.getInstance();
        fb_auth = FirebaseAuth.getInstance();
        users = fb_database.getReference("users");

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_email = (EditText) findViewById(R.id.edit_email);

        button_register = (Button) findViewById(R.id.button_signup);
        text_to_login = (TextView) findViewById(R.id.text_login);

        button_register.setOnClickListener(new View.OnClickListener() {
            //Todo Still not working register
            public void onClick(View view) {

//                if(TextUtils.isEmpty(edit_name)){
//                    Toast.makeText(sign_up.this, "Please enter nickname", Toast.LENGTH_SHORT).show();
//                }
//                if(TextUtils.isEmpty(edit_email)){
//                    Toast.makeText(sign_up.this, "Please enter email", Toast.LENGTH_SHORT).show();
//                }
//                if(TextUtils.isEmpty(edit_password)){
//                    Toast.makeText(sign_up.this, "Please enter password", Toast.LENGTH_SHORT).show();
//                }
//                else{
                create_user(edit_name.getText().toString(), edit_email.getText().toString(), edit_password.getText().toString());
            }
        });
//                fb_auth.createUserWithEmailAndPassword(edit_email.getText().toString(), edit_password.getText().toString())
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
////                                        if (task.isSuccessful()) {
////                                            // Sign in success, update UI with the signed-in user's information
////                                            FirebaseUser user = fb_auth.getCurrentUser();
////                                            updateUI(user);
////                                        }
//                                    }
//                                };

        text_to_login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent goto_log_in = new Intent(getApplicationContext(), log_in.class);
                //Toast.makeText(getApplicationContext(), "Hello, new user!", Toast.LENGTH_SHORT).show();
                startActivity(goto_log_in);
            }
        });


//
    }

    public void onStart() {
        super.onStart();
        //fb_auth.addAuthStateListener(fb_auth_listener);
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = fb_auth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void create_user(final String nickname, final String email, final String password){

        fb_auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Toast.makeText(sign_up.this,"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful()){
                            FirebaseUser user = fb_auth.getCurrentUser();
                            User user0 = new User(nickname, email, password, user.getUid());


                            //users_uid = users.child(user0.getUid());

                            //users.child(user0.getUid()).setValue(user0.getUsername());
                            //alternative
                            //users.child("users/"+user.getUid()).child("username").setValue(user0.getUsername());

                            //Firebase userRef = rootRef.child("users/" + rootRef.getAuth().getUid());
                            //userRef.child("message1").setValue("Hello World");

                            //users_uid = users.child("users/" + user0.getUid());
                            //add_user_db(users_uid, user0);

                            //users.child(user0.getUid()).setValue(user0.getUsername());

                            users.push().child(user.getUid()).setValue(user0);
                        }
                    }
                });
        go_to_log_in();
    }

    public void onStop() {
        super.onStop();
//        if (fb_auth_listener != null) {
//            fb_auth.removeAuthStateListener(fb_auth_listener);
//        }
    }


    public void go_to_log_in(){
        Intent goto_sign_in = new Intent(getApplicationContext(), log_in.class);
        startActivity(goto_sign_in);
    }

      public void add_user_db(DatabaseReference dr,User user){
          dr.child("uid").setValue(user.getUid());
          dr.child("username").setValue(user.getUsername());
          dr.child("email").setValue(user.getEmail());
          dr.child("role").setValue(user.getRole());
          dr.child("rank").setValue(user.getRank());
          dr.child("updatedProfile").setValue(user.getUpdatedProfile());
    }


}

/* private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    } */

//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sign_up);
//
//        fb_database = FirebaseDatabase.getInstance();
//        users = fb_database.getReference("Users");
//        fb_auth = FirebaseAuth.getInstance();
//
//        edit_name = (EditText) findViewById(R.id.edit_name);
//        edit_email = (EditText) findViewById(R.id.edit_email);
//        edit_password = (EditText) findViewById(R.id.edit_name);
//
//        button_register = (Button) findViewById(R.id.button_signup);
//        text_to_login = (TextView) findViewById(R.id.text_login);
//
//        button_register.setOnClickListener(this);
//        //button_register.setOnClickListener(findViewById(R.layout.class_activity));
//        text_to_login.setOnClickListener(this);

