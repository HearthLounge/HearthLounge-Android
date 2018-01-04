package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;

/**
 * Created by Froozy on 03.10.2017.
 */

public class SignUp extends DrawerMenu /*implements View.OnClickListener */ {

    private Button buttonRegister;
    private EditText edit_name;
    private EditText edit_email;
    private EditText edit_password;
    private TextView textLogin;

    private static final String TAG = "Sign_up";

    private FirebaseAuth fbAuth;
    private FirebaseDatabase fb_database = FirebaseDatabase.getInstance();
    private DatabaseReference fbDataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.sign_up, frameLayout);
        navigationView.getMenu().getItem(2).setChecked(true);

        //Firebase configurate
        fb_database = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        //Temporary solve
        Toast.makeText(SignUp.this, "--->Loging out", Toast.LENGTH_SHORT).show();

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_password = (EditText) findViewById(R.id.edit_password);


        buttonRegister = (Button) findViewById(R.id.button_signup);
        textLogin = (TextView) findViewById(R.id.text_login);

        if(fbAuth.getCurrentUser() != null){
            updateUI();
        }

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(TextUtils.isEmpty(edit_name.getEditableText().toString())){
                    Toast.makeText(SignUp.this, "Please enter nickname", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(edit_email.getEditableText().toString())){
                    Toast.makeText(SignUp.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(edit_password.getEditableText().toString())){
                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUp.this, "Test Toast 01", Toast.LENGTH_SHORT).show();
                    create_user(edit_name.getText().toString(), edit_email.getText().toString(), edit_password.getText().toString());
                }
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent goto_sign_in = new Intent(getApplicationContext(), LogIn.class);
                startActivity(goto_sign_in);
            }
        });
    }

    public void onStart() {
        super.onStart();
        Toast.makeText(SignUp.this, "Sprawdzę czy jesteś zalogowany", Toast.LENGTH_SHORT).show();
        check_logged_in(fbAuth);

    }

    public void onStop() {
        super.onStop();
//        if (fb_auth_listener != null) {
//            fbAuth.removeAuthStateListener(fb_auth_listener);
//        }
    }

    public void create_user(final String nickname, final String email, final String password){

        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task){
                        FirebaseUser user = fbAuth.getCurrentUser();

                        if(task.isSuccessful()){

                            Toast.makeText(SignUp.this,"registration is successful!", Toast.LENGTH_SHORT).show();
                            add_new_user(nickname,email, fbAuth.getCurrentUser().getUid());
                            fbAuth.signOut();
                            go_to_log_in();
                        }
                        else {
                            Toast.makeText(SignUp.this, "Something goes wrong :(", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }

                });
    }

    public void add_new_user(String nickname, String email, String uid){

        User user_db = new User(nickname, email, uid);
        fbDataRef.child("/users").child(user_db.getUid()).setValue(user_db);
    }


    public void go_to_log_in(){
        Intent goto_sign_in = new Intent(getApplicationContext(), LogIn.class);
        startActivity(goto_sign_in);
    }

    public void check_logged_in(FirebaseAuth fb_auth){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(SignUp.this, "You're already logged!" + user.getEmail(), Toast.LENGTH_SHORT).show();
            updateUI();
        }
    }


    //TODO
    public void updateUI(){
        findViewById(R.id.edit_name).setVisibility(View.GONE);
        findViewById(R.id.edit_email).setVisibility(View.GONE);
        findViewById(R.id.edit_password).setVisibility(View.GONE);
        findViewById(R.id.button_signup).setVisibility(View.GONE);
        findViewById(R.id.text_to_sign_up).setVisibility(View.GONE);
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