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

    private FirebaseAuth fbAuth;
    private UserService userService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.logout, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        fbAuth = FirebaseAuth.getInstance();
        fbAuth.signOut();

        Intent i = new Intent(getApplicationContext(), UserService.class);
        i.putExtra("action","logout");
        startService(i);



        Toast.makeText(LogOut.this," Wylogowany ", Toast.LENGTH_SHORT).show();
        Intent goto_sign_in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goto_sign_in);
    }
}
