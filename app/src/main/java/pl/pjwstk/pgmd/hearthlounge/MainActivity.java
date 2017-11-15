package pl.pjwstk.pgmd.hearthlounge;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pl.pjwstk.pgmd.hearthlounge.authentication.LogIn;
import pl.pjwstk.pgmd.hearthlounge.authentication.LogOut;
import pl.pjwstk.pgmd.hearthlounge.authentication.SignUp;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    private ImageButton buttonCards, buttonDecks, buttonExpansions;
    private ListView listViewCards;
    private TextView textCards;

    private CardView cardDecks;
    private boolean isRunning;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);

        toolbar = (Toolbar) findViewById(R.id.toolbar); //PASEK U GÓRY Z NAZWĄ APLIKACJI
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //toggle.setDrawerIndicatorEnabled(false); // wyłączenie drawer menu na guzik tylko wyciąganie w lewej krawędzi

        // Zmiana ikonki HAMBURGERA na naszą
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.hl_launcher);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //OLD ONCLICK
//        buttonCards = (ImageButton)findViewById(R.id.button_cards);
//        buttonCards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent startIntent = new Intent(getApplicationContext(),CardsJSON.class); //Do którego ma iść
//                startActivity(startIntent);
//                //new CardsJSON().execute("https://omgvamp-hearthstone-v1.p.mashape.com/cards");
//            }
//        });

        buttonDecks = (ImageButton) findViewById(R.id.button_decks);
        buttonDecks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
                    v.setBackgroundResource(R.drawable.pressed);
                    //v.startAnimation(animationScale); // druga metoda
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(),Decks.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                }
                return false;
            }
        });

        buttonCards = (ImageButton)findViewById(R.id.button_cards);
        buttonCards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(),CardsFilterMenu.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                }
                return false;
            }
        });

        buttonExpansions = (ImageButton)findViewById(R.id.button_expansions);
        buttonExpansions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(),Main2Activity.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                }
                return false;
            }
        });
    }
// NIE DZIAŁA SHIT
//    public void setScreenSize() {
//        Display display = getWindowManager().getDefaultDisplay();
//        FrameLayout layout = (FrameLayout) findViewById(R.id.frame_decks);
//        int screen_height = display.getHeight();
//        screen_height = (int) (0.60*screen_height);
//        DrawerLayout.LayoutParams parms = (DrawerLayout.LayoutParams) layout.getLayoutParams();
//        parms.height = screen_height;
//        // Set it back.
//        layout.setLayoutParams(parms);
//    }

// NIE WIEM CO TO NAWET
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    // TO JEST MENU Z PRAWEJ STRONY TE TRZY KROPKI
//    @Override
//    public boolean onCreateOptionsMenu(Menu nav_bar) {
//        getMenuInflater().inflate(R.menu.navigation_bar, nav_bar);
//        return true;
//    }

    // DZIEKI TEMU WYSUWA SIE MENU DRAWERLAYOUT BEZ TEGO NIC SIE NIE DZIEJE
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log_in) {
            Toast.makeText(this, "Log In", Toast.LENGTH_SHORT).show();

            Intent logIn = new Intent(getApplicationContext(), LogIn.class);
            startActivity(logIn);

        } else if (id == R.id.nav_sign_up) {
            Toast.makeText(this, "Sign Up", Toast.LENGTH_SHORT).show();

            Intent signUp = new Intent(getApplicationContext(), SignUp.class);
            startActivity(signUp);

        } else if (id == R.id.nav_log_out) {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();

            Intent logOut = new Intent(getApplicationContext(), LogOut.class);
            startActivity(logOut);

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();

            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
}
