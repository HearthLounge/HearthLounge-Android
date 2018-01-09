package pl.pjwstk.pgmd.hearthlounge.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import pl.pjwstk.pgmd.hearthlounge.MainActivity;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.authentication.LogIn;
import pl.pjwstk.pgmd.hearthlounge.authentication.LogOut;
import pl.pjwstk.pgmd.hearthlounge.authentication.SignUp;
import pl.pjwstk.pgmd.hearthlounge.authentication.UserAccount;

import pl.pjwstk.pgmd.hearthlounge.authentication.UserPreferences;

/**
 * Created by Maciek Dembowski on 16.11.2017.
 */

public class DrawerMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;
    protected Toolbar toolbar;

    private Menu drawerMenu;

    protected FrameLayout frameLayout;

    // Dodane
    private View navHeader;
    private ImageView imgProfile;
    private TextView userName, userEmail;
    private UserPreferences userPref;
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    private static final String urlProfileImg = "https://cdn.pixabay.com/photo/2016/12/13/16/17/dancer-1904467_1280.png";
    public static int navItemIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        userPref = new UserPreferences(this.getApplicationContext());

        // ZMIANA CZCIONKI DLA PASKA "TOOLBAR"
        SpannableString s = new SpannableString(getTitle());
        s.setSpan(new TypefaceSpan(this, "belwe_medium.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        toolbar = (Toolbar) findViewById(R.id.toolbar); //PASEK U GÓRY Z NAZWĄ APLIKACJI
        toolbar.setTitle(s);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        //toggle.setDrawerIndicatorEnabled(false); // wyłączenie drawer menu na guzik tylko wyciąganie w lewej krawędzi

        // Zmiana ikonki HAMBURGERA na naszą
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.hl_launcher);
//        toolbar.setNavigationIcon(R.mipmap.hl_launcher); // onPostCreate psuje to :(

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //dodane
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        //navigationView.getMenu().getItem(4).setActionView(R.layout.menu_dot); // ustawia kropke koło notifications

        navigationView.setNavigationItemSelectedListener(this);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        userName = (TextView) navHeader.findViewById(R.id.user_name);
        userEmail = (TextView) navHeader.findViewById(R.id.user_email);

        // load nav menu header data
        loadNavHeader();

        if (savedInstanceState == null) {
            navItemIndex = 0;
        }

//        drawerMenu = navigationView.getMenu();
//        for(int i = 0; i < drawerMenu.size(); i++) {
//            drawerMenu.getItem(i).setOnMenuItemClickListener(this);

        navigationView.getMenu().getItem(4).setVisible(false);  //Chowa notifications
        modifyNav(navigationView);
//        }
    }

    public void modifyNav(NavigationView navView){

        if(fbAuth.getCurrentUser() == null){

            navView.getMenu().getItem(1).setVisible(true);
            navView.getMenu().getItem(2).setVisible(true);

            navView.getMenu().getItem(0).setVisible(false);
            navView.getMenu().getItem(3).setVisible(false);
        }
        else{
            navView.getMenu().getItem(1).setVisible(false);
            navView.getMenu().getItem(2).setVisible(false);

            navView.getMenu().getItem(0).setVisible(true);
            navView.getMenu().getItem(3).setVisible(true);

//            findViewById(R.id.nav_account).setVisibility(View.VISIBLE);
//            findViewById(R.id.nav_log_out).setVisibility(View.VISIBLE);
//            findViewById(R.id.nav_log_in).setVisibility(View.GONE);
//            findViewById(R.id.nav_sign_up).setVisibility(View.GONE);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
        toolbar.setNavigationIcon(R.mipmap.hl_launcher); // kolejność ma zanczenie
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (frameLayout != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View frame = inflater.inflate(layoutResID, frameLayout, false);
            frameLayout.addView(frame, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (frameLayout != null) {
            frameLayout.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNavHeader() {
        userName.setText(userPref.getSingleStringPref(userPref.keyUsername));
        userEmail.setText(userPref.getSingleStringPref(userPref.keyEmail));

        // Loading profile image    // user.getAvatar czy cos
        String urlAvatar;
        if (userPref.contain(userPref.keyAvatar)) { urlAvatar = userPref.getSingleStringPref(userPref.keyAvatar); }
        else { urlAvatar = urlProfileImg; }
            Glide.with(this).load(urlAvatar)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new CircleTransform(this))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);
        }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    // TO JEST MENU Z PRAWEJ STRONY TE TRZY KROPKI
//    @Override
//    public boolean onCreateOptionsMenu(Menu nav_bar) {
//        getMenuInflater().inflate(R.menu.navigation_bar, nav_bar);
//        return true;
//    }

    // DZIEKI TEMU WYSUWA SIE MENU DRAWERLAYOUT BEZ TEGO NIC SIE NIE DZIEJE

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Toast.makeText(this, "Your account", Toast.LENGTH_SHORT).show();
            navItemIndex = 0;

            Intent account = new Intent(getApplicationContext(), UserAccount.class);
            startActivity(account);

        } else if (id == R.id.nav_log_in) {
            Toast.makeText(this, "Log In", Toast.LENGTH_SHORT).show();
            navItemIndex = 1;

            Intent logIn = new Intent(getApplicationContext(), LogIn.class);
            startActivity(logIn);

        } else if (id == R.id.nav_sign_up) {
            Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show();
            navItemIndex = 2;

            Intent signUp = new Intent(getApplicationContext(), SignUp.class);
            startActivity(signUp);

        } else if (id == R.id.nav_log_out) {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
            navItemIndex = 3;

            Intent logOut = new Intent(getApplicationContext(), LogOut.class);
            startActivity(logOut);

        } else if (id == R.id.nav_notifications) {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();
            navItemIndex = 4;

            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            //item.getIcon().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.DST_ATOP);
            navItemIndex = 5;

            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        return true;
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.item1:
//                // handle it
//                break;
//            case R.id.item2:
//                // do whatever
//                break;
//            // and so on...
//        }
//        return false;
//    }

    private void animateHamburgerToArrow() {
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(drawer, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        // You can change this duration to more closely match that of the default animation.
        anim.setDuration(500);
        anim.start();
    }

//    public void checkUserLog(){
//
//        Intent i = new Intent(getApplicationContext(), UserService.class);
//        if(fbAuth.getUid() == null){ Toast.makeText(getApplicationContext(),"Hello, stranger...", Toast.LENGTH_SHORT).show(); }
//        else{String uid = fbAuth.getUid(); i.putExtra("uid", uid); startService(i); }
//    }
}
