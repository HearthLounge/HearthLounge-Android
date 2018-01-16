package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.initiateApp.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.R;

/**
 * Created by Maciek Dembowski on 15.11.2017.
 */

public class LogOut extends DrawerMenu {

    private FirebaseAuth fbAuth;
    private UserService userService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.logout, frameLayout);
        navigationView.getMenu().getItem(3).setChecked(true);

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
