package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.R;

/**
 * Created by Maciek Dembowski on 15.11.2017.
 */

public class LogOut extends DrawerMenu {

    private FirebaseAuth fb_auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO setContentView(R.layout.logout);      Dodać layout

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.logout, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        
        fb_auth = FirebaseAuth.getInstance();
        fb_auth.signOut();


        Toast.makeText(LogOut.this," Wylogowany ", Toast.LENGTH_SHORT).show();
        Intent goto_sign_in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goto_sign_in);
    }
}
