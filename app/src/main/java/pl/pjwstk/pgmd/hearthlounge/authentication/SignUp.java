package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;

public class SignUp extends DrawerMenu {

    private Button buttonRegister;
    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private TextView textLogin;

    private static final String TAG = "Sign_up";

    public FirebaseAuth fbAuth;
    private FirebaseDatabase fb_database = FirebaseDatabase.getInstance();
    private DatabaseReference fbDataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com");

    private String nickname;
    private String email;
    private String password;
    private String confirmPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.sign_up, frameLayout);
        navigationView.getMenu().getItem(2).setChecked(true);

        //Firebase configurate
//        fb_database = FirebaseDatabase.getInstance();
        fbAuth = FirebaseAuth.getInstance();

        //Temporary solve
        Toast.makeText(SignUp.this, "--->Loging out", Toast.LENGTH_SHORT).show();

        editName = (EditText) findViewById(R.id.edit_name);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);

        buttonRegister = (Button) findViewById(R.id.button_signup);
        textLogin = (TextView) findViewById(R.id.text_login);

        if(fbAuth.getCurrentUser() != null){
            updateUI();
        }

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nickname = editName.getEditableText().toString();
                email = editEmail.getEditableText().toString();
                password = editPassword.getEditableText().toString();
                confirmPassword = editConfirmPassword.getEditableText().toString();

                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(SignUp.this, "Please enter nickname", Toast.LENGTH_SHORT).show();
                } else if (isValidEmail(email)) {
                    if (isValidPassword(password)) {
                        if (isValidConfirmPassword(password, confirmPassword)) {
                            Toast.makeText(SignUp.this, "Give me a second...", Toast.LENGTH_SHORT).show();
                            create_user(nickname, email, password);
                        }
                    }
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
                        } else {
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

    public void updateUI(){
        findViewById(R.id.edit_name).setVisibility(View.GONE);
        findViewById(R.id.edit_email).setVisibility(View.GONE);
        findViewById(R.id.edit_password).setVisibility(View.GONE);
        findViewById(R.id.button_signup).setVisibility(View.GONE);
        findViewById(R.id.text_to_sign_up).setVisibility(View.GONE);
    }

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            Toast.makeText(SignUp.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            Toast.makeText(SignUp.this, "Your email is incorrect", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final boolean isValidPassword(CharSequence password) {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 8) {
            Toast.makeText(SignUp.this, "Your password is too short, minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
//            {
//            Toast.makeText(SignUp.this, "Correct password", Toast.LENGTH_SHORT).show();
//        }
    }

    public final boolean isValidConfirmPassword(CharSequence password, CharSequence confirmPassword) {
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(SignUp.this, "Confirm your password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (confirmPassword.length() < 8) {
            Toast.makeText(SignUp.this, "Your password is too short, minimum 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUp.this, "Passwords are not matching",Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

}