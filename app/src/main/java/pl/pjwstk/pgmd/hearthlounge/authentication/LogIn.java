package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.R;

public class LogIn extends DrawerMenu {

    private Button buttonLogin;
    private EditText editEmailLogin;
    private EditText editPasswordLogin;
    private TextView textToSignUp;
    private String email,password;

    private FirebaseAuth fbAuth;
    FirebaseDatabase fb_database;
    private DatabaseReference fb_data_ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.login, frameLayout);
        navigationView.getMenu().getItem(1).setChecked(true);


        fb_database = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        editEmailLogin = (EditText) findViewById(R.id.edit_email);
        editPasswordLogin = (EditText) findViewById(R.id.edit_password);
        buttonLogin = (Button) findViewById(R.id.button_login);
        textToSignUp = (TextView) findViewById(R.id.text_to_sign_up);

        buttonLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if(fbAuth.getCurrentUser() == null) {
                    //log_user(editEmailLogin.getText().toString(),editPasswordLogin.getText().toString());
                    email = editEmailLogin.getText().toString();
                    password = editPasswordLogin.getText().toString();
                    Toast.makeText(LogIn.this, "E:" + email + " H:" + password, Toast.LENGTH_SHORT).show();
                    log_user(email, password);
                }
                else{ Toast.makeText(getApplicationContext(),"Something went wrong :|", Toast.LENGTH_SHORT).show();}
            }
        });
        textToSignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent goto_sign_up = new Intent(getApplicationContext(), SignUp.class);
                startActivity(goto_sign_up);
            }
        });
    }
    public void log_user(String email,String password){
        fbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Hello user!", Toast.LENGTH_SHORT).show();
                            toUserService(fbAuth.getCurrentUser().getUid());
                            go_to_main_menu();
                        } else {
                            Toast.makeText(LogIn.this, "Something goes wrong..."+"\n"+"Check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void go_to_main_menu(){
        Intent menu_intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(menu_intent);
    }
    public void toUserService(String uid){    //User Data Service
        Intent i = new Intent(getApplicationContext(), UserService.class);
        i.putExtra("action", "login");
        i.putExtra("uid", uid);
        startService(i);
    }
}
