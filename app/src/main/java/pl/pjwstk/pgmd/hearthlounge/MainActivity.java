package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import pl.pjwstk.pgmd.hearthlounge.authentication.UserService;
import pl.pjwstk.pgmd.hearthlounge.locator.MapsActivity;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

public class MainActivity extends DrawerMenu {

    private ImageButton buttonCards, buttonDecks, buttonExpansions, buttonAdventures, buttonReddit, buttonMap;
    //private FirebaseDatabase fbDb;
    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDb = FirebaseDatabase.getInstance();

    private Point mTouchOffsetPoint = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.main_menu, frameLayout);
        navigationView.getMenu().getItem(5).setChecked(true);

        fbAuth = FirebaseAuth.getInstance();
        if(fbAuth.getCurrentUser() != null) {
            checkUserLog();
        }

        buttonDecks = (ImageButton) findViewById(R.id.button_decks);
        buttonDecks.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), Decks.class);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
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
                    Intent startIntent = new Intent(getApplicationContext(), CardsFilterMenu.class); //CardsFilterMenu
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
////                if(ignore && motionEvent.getAction()!=MotionEvent.ACTION_UP)
////                    return true;
//                switch(motionEvent.getActionMasked()) {
//                    case MotionEvent.ACTION_DOWN:
//                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
//                        v.animate().scaleXBy(0.2f).setDuration(5000).start();
//                        v.animate().scaleYBy(0.2f).setDuration(5000).start();
//                        v.setBackgroundResource(R.drawable.pressed);
//                        return true;
//
//                    case MotionEvent.ACTION_MOVE:
////                        if(rect.contains(v.getLeft() + (int) motionEvent.getX(), v.getTop() + (int) motionEvent.getY())){
//                            v.animate().scaleXBy(0.2f).setDuration(5000).start();
//                            v.animate().scaleYBy(0.2f).setDuration(5000).start();
//                            v.setBackgroundResource(R.drawable.pressed);
//                            v.animate().cancel();
//
////                        } else {
//
////                            ignore = true;
////                        }
//                        return true;
//
//                    case MotionEvent.ACTION_OUTSIDE:
//                        v.animate().scaleX(1f).setDuration(1000).start();
//                        v.animate().scaleY(1f).setDuration(1000).start();
//                        v.setBackgroundResource(R.drawable.normal);
//                        v.animate().cancel();
//
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        v.animate().cancel();
//                        v.animate().scaleX(1f).setDuration(1000).start();
//                        v.animate().scaleY(1f).setDuration(1000).start();
//                        v.setBackgroundResource(R.drawable.normal);
////                        ignore = false;
//                        Intent startIntent = new Intent(getApplicationContext(),CardsFilterMenu.class); //Do którego ma iść
//                        startActivity(startIntent);
//                        return true;
//                    default:
//                        return false;
//                }
//            }
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
                    Intent startIntent = new Intent(getApplicationContext(), Expansions.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        buttonAdventures = (ImageButton)findViewById(R.id.button_adventures);
        buttonAdventures.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), Adventures.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        buttonReddit = (ImageButton)findViewById(R.id.button_reddit);
        buttonReddit.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), Reddit.class); //Do którego ma iść
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        buttonMap = (ImageButton) findViewById(R.id.button_position);
        buttonMap.setOnTouchListener(new View.OnTouchListener() {
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
                    if(fbAuth.getCurrentUser() != null){
                        Intent startIntent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(startIntent);}
                    else { Toast.makeText(MainActivity.this, "Log in to use this!", Toast.LENGTH_SHORT).show(); }
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        if (densityDpi < 560) { // SG4 = 480
            int width = 468, height = 468;
            CardView.LayoutParams params = new CardView.LayoutParams(width,height);
            buttonDecks.setLayoutParams(params);
            buttonCards.setLayoutParams(params);
            buttonExpansions.setLayoutParams(params);
            buttonAdventures.setLayoutParams(params);
            buttonReddit.setLayoutParams(params);
            buttonMap.setLayoutParams(params);
        }
    }

    public void checkUserLog(){

        Intent i = new Intent(getApplicationContext(), UserService.class);
        String uid = fbAuth.getUid();
        i.putExtra("action", "login");
        i.putExtra("uid", uid);
        startService(i);
    }

    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }
}
