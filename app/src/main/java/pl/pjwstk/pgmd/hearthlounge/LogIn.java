package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Froozy on 25.10.2017.
 */

public class LogIn extends AppCompatActivity {

    private Button button_login;
    private EditText edit_email_login;
    private EditText edit_password_login;
    private TextView text_to_sign_up;

    private FirebaseAuth fb_auth;
    FirebaseDatabase fb_database;
    DatabaseReference users;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Firebase configurate
        fb_database = FirebaseDatabase.getInstance();
        users = fb_database.getReference("users");

        edit_password_login = (EditText) findViewById(R.id.edit_password);
        edit_email_login = (EditText) findViewById(R.id.edit_email);

        button_login = (Button) findViewById(R.id.button_login);
        text_to_sign_up = (TextView) findViewById(R.id.text_to_sign_up);
        text_to_sign_up.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent goto_sign_up = new Intent(getApplicationContext(), SignUp.class);
                //Toast.makeText(getApplicationContext(), "Hello, new user!", Toast.LENGTH_SHORT).show();
                startActivity(goto_sign_up);
            }
        });

    }

}
