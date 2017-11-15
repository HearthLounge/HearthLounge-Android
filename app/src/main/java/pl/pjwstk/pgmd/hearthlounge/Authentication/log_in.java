package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by Froozy on 25.10.2017.
 */

public class log_in extends AppCompatActivity {

    private Button button_login;
    private EditText edit_email_login;
    private EditText edit_password_login;
    private TextView text_to_sign_up;

    private FirebaseAuth fb_auth;
    FirebaseDatabase fb_database;
    private DatabaseReference fb_data_ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Firebase configurate
        fb_database = FirebaseDatabase.getInstance();
        fb_auth = FirebaseAuth.getInstance();

        edit_password_login = (EditText) findViewById(R.id.edit_password);
        edit_email_login = (EditText) findViewById(R.id.edit_email);

        button_login = (Button) findViewById(R.id.button_login);
        text_to_sign_up = (TextView) findViewById(R.id.text_to_sign_up);


        //Log in mechanic
        button_login.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                log_user(edit_email_login.getText().toString(),edit_password_login.getText().toString());
            }
        });


        text_to_sign_up.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent goto_sign_up = new Intent(getApplicationContext(), sign_up.class);
                //Toast.makeText(getApplicationContext(), "Hello, new user!", Toast.LENGTH_SHORT).show();
                startActivity(goto_sign_up);
            }
        });
    }




        public void log_user(String email,String password){

        fb_auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Intent go_after_log_in = new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(getApplicationContext(), "Hello, new user!", Toast.LENGTH_SHORT).show();
                            startActivity(go_after_log_in);
                            //go_after_log_in

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(log_in.this, "Something went wrong with log in", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
        }

}
