package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
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
import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.model.DeckListCache;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

public class MainActivity extends DrawerMenu {

    private ImageButton buttonCards, buttonDecks, buttonExpansions;
    //private FirebaseDatabase fbDb;
    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDb = FirebaseDatabase.getInstance();

    private Rect rect;
    private boolean ignore = false;

    private Point mTouchOffsetPoint = new Point();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.main_menu, frameLayout);
        navigationView.getMenu().getItem(5).setChecked(true);

        //fbDb = FirebaseDatabase.getInstance();
        DeckListCache.getInstance();
        fbAuth = FirebaseAuth.getInstance();
        if(fbAuth.getCurrentUser() != null) {
            checkUserLog();
        }

        //checkUserLog();

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
//                mTouchOffsetPoint.x = (int)motionEvent.getX();
//                mTouchOffsetPoint.y = (int)motionEvent.getY();

                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
                    v.setBackgroundResource(R.drawable.pressed);

                    return true;
//                } else if (action == MotionEvent.ACTION_MOVE) {
//                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
//                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
//                    v.setBackgroundResource(R.drawable.pressed);
//                    v.animate().cancel();
//                    return true;
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
//                motionEvent.offsetLocation(-mTouchOffsetPoint.x + v.getWidth()/2, -mTouchOffsetPoint.y + v.getHeight()/2);

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
                }
                return false;
            }
        });
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
