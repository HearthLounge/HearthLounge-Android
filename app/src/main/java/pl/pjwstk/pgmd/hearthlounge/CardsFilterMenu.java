package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 15.11.2017.
 */

public class CardsFilterMenu extends DrawerMenu {

    private LinearLayout search, all_cards;
    private ImageButton buttonMage, buttonRogue, buttonPaladin, buttonDruid, buttonShaman, buttonWarlock, buttonPriest, buttonWarrior, buttonHunter, buttonNeutral;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.cards_filter_menu, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        all_cards = (LinearLayout) findViewById(R.id.all_cards);
        all_cards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?collectible=1");
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "ALL");
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });


        // TODO SEARCH BUTTON HERE


        buttonMage = (ImageButton) findViewById(R.id.button_mage);
        buttonMage.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Mage");
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

        buttonRogue = (ImageButton) findViewById(R.id.button_rogue);
        buttonRogue.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Rogue");
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

        buttonPaladin = (ImageButton) findViewById(R.id.button_paladin);
        buttonPaladin.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Paladin");
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

        buttonDruid = (ImageButton) findViewById(R.id.button_druid);
        buttonDruid.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Druid");
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

        buttonShaman = (ImageButton) findViewById(R.id.button_shaman);
        buttonShaman.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Shaman");
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

        buttonWarlock = (ImageButton) findViewById(R.id.button_warlock);
        buttonWarlock.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Warlock");
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

        buttonPriest = (ImageButton) findViewById(R.id.button_priest);
        buttonPriest.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Priest");
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

        buttonWarrior = (ImageButton) findViewById(R.id.button_warrior);
        buttonWarrior.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Warrior");
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

        buttonHunter = (ImageButton) findViewById(R.id.button_hunter);
        buttonHunter.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Hunter");
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

        buttonNeutral = (ImageButton) findViewById(R.id.button_neutral);
        buttonNeutral.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent startIntent = new Intent(getApplicationContext(), PlayerClass.class);
                    startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "Neutral");
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
    }
}