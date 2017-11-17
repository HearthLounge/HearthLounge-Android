package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import pl.pjwstk.pgmd.hearthlounge.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.R;

/**
 * Created by Maciek Dembowski on 15.11.2017.
 */

public class LogOut extends DrawerMenu {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
//        getLayoutInflater().inflate(R.layout.sign_up, contentFrameLayout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);
    }
}
