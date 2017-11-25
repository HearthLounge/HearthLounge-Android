package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

public class MainActivity extends DrawerMenu {

    private ImageButton buttonCards, buttonDecks, buttonExpansions;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.main_menu, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        final Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);

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
}
