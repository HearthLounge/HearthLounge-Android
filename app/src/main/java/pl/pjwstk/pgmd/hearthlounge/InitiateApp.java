package pl.pjwstk.pgmd.hearthlounge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import pl.pjwstk.pgmd.hearthlounge.authentication.LogIn;
import pl.pjwstk.pgmd.hearthlounge.authentication.UserPreferences;
import pl.pjwstk.pgmd.hearthlounge.authentication.UserService;

/**
 * Created by Froozy on 08.12.2017.
 */

public class InitiateApp extends Activity {

    FirebaseAuth fbAuth;
    UserPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiate_app);

        fbAuth = FirebaseAuth.getInstance();
        checkUserLog();
        //TODO add card and decks downloader


        goToMainActivity();
    }


    public void goToMainActivity(){
        Intent goToMA = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToMA);
    }


    public void checkUserLog(){

        Intent i = new Intent(getApplicationContext(), UserService.class);
        if(!userPref.contain("uid"))
        {
            i.putExtra("action", "start_0");
            startService(i);
        }
        else
        {
            i.putExtra("action", "start_1");
            startService(i);
        }
    }
}
