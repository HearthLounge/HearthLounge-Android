package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 15.11.2017.
 */

public class CardsFilterMenu extends DrawerMenu {

    private LinearLayout search, all_cards;
    private ImageButton buttonMage, buttonRogue, buttonPaladin, buttonDruid, buttonShaman, buttonWarlock, buttonPriest, buttonWarrior, buttonHunter, buttonNeutral;
    private TextView textNeutral;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.cards_filter_menu, frameLayout);

        search = (LinearLayout) findViewById(R.id.search);
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.pressed);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.setBackgroundResource(R.drawable.normal);

                    initiatePopupWindow();

                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

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
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class); //Do którego ma iść
                    startIntent.putExtra("Title", "All");
                    startIntent.putExtra("IconID", "All");
                    startIntent.putExtra("drawable", R.drawable.all_cards);
                    startIntent.putExtra("color", R.color.primary_font_color);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });


        buttonMage = (ImageButton) findViewById(R.id.button_mage);
        buttonMage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
                    v.setBackgroundResource(R.color.mage);
                    buttonMage.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonMage.setColorFilter(getResources().getColor(R.color.mage));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Mage");
                    startIntent.putExtra("Title", "Mage");
                    startIntent.putExtra("IconID", "Mage");
                    startIntent.putExtra("drawable", R.drawable.mage);
                    startIntent.putExtra("color", R.color.mage);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonMage.setColorFilter(getResources().getColor(R.color.mage));
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
                    v.setBackgroundResource(R.color.rogue);
                    buttonRogue.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonRogue.setColorFilter(getResources().getColor(R.color.rogue));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Rogue");
                    startIntent.putExtra("Title", "Rogue");
                    startIntent.putExtra("IconID", "Rogue");
                    startIntent.putExtra("drawable", R.drawable.rogue);
                    startIntent.putExtra("color", R.color.rogue);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonRogue.setColorFilter(getResources().getColor(R.color.rogue));
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
                    v.setBackgroundResource(R.color.paladin);
                    buttonPaladin.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonPaladin.setColorFilter(getResources().getColor(R.color.paladin));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Paladin");
                    startIntent.putExtra("Title", "Paladin");
                    startIntent.putExtra("IconID", "Paladin");
                    startIntent.putExtra("drawable", R.drawable.paladin);
                    startIntent.putExtra("color", R.color.paladin);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonPaladin.setColorFilter(getResources().getColor(R.color.paladin));
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
                    v.setBackgroundResource(R.color.druid);
                    buttonDruid.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonDruid.setColorFilter(getResources().getColor(R.color.druid));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Druid");
                    startIntent.putExtra("Title", "Druid");
                    startIntent.putExtra("IconID", "Druid");
                    startIntent.putExtra("drawable", R.drawable.druid);
                    startIntent.putExtra("color", R.color.druid);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonDruid.setColorFilter(getResources().getColor(R.color.druid));
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
                    v.setBackgroundResource(R.color.shaman);
                    buttonShaman.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonShaman.setColorFilter(getResources().getColor(R.color.shaman));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Shaman");
                    startIntent.putExtra("Title", "Shaman");
                    startIntent.putExtra("IconID", "Shaman");
                    startIntent.putExtra("drawable", R.drawable.shaman);
                    startIntent.putExtra("color", R.color.shaman);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonShaman.setColorFilter(getResources().getColor(R.color.shaman));
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
                    v.setBackgroundResource(R.color.warlock);
                    buttonWarlock.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonWarlock.setColorFilter(getResources().getColor(R.color.warlock));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Warlock");
                    startIntent.putExtra("Title", "Warlock");
                    startIntent.putExtra("IconID", "Warlock");
                    startIntent.putExtra("drawable", R.drawable.warlock);
                    startIntent.putExtra("color", R.color.warlock);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonWarlock.setColorFilter(getResources().getColor(R.color.warlock));
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
                    v.setBackgroundResource(R.color.priest);
                    buttonPriest.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonPriest.setColorFilter(getResources().getColor(R.color.priest));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Priest");
                    startIntent.putExtra("Title", "Priest");
                    startIntent.putExtra("IconID", "Priest");
                    startIntent.putExtra("drawable", R.drawable.priest);
                    startIntent.putExtra("color", R.color.priest);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonPriest.setColorFilter(getResources().getColor(R.color.priest));
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
                    v.setBackgroundResource(R.color.warrior);
                    buttonWarrior.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonWarrior.setColorFilter(getResources().getColor(R.color.warrior));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Warrior");
                    startIntent.putExtra("Title", "Warrior");
                    startIntent.putExtra("IconID", "Warrior");
                    startIntent.putExtra("drawable", R.drawable.warrior);
                    startIntent.putExtra("color", R.color.warrior);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonWarrior.setColorFilter(getResources().getColor(R.color.warrior));
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
                    v.setBackgroundResource(R.color.hunter);
                    buttonHunter.setColorFilter(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonHunter.setColorFilter(getResources().getColor(R.color.hunter));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Hunter");
                    startIntent.putExtra("Title", "Hunter");
                    startIntent.putExtra("IconID", "Hunter");
                    startIntent.putExtra("drawable", R.drawable.hunter);
                    startIntent.putExtra("color", R.color.hunter);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonHunter.setColorFilter(getResources().getColor(R.color.hunter));
                    return true;
                }
                return false;
            }
        });

        buttonNeutral = (ImageButton) findViewById(R.id.button_neutral);
        textNeutral = (TextView) findViewById(R.id.text_neutral);
        buttonNeutral.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
                    v.setBackgroundResource(R.color.primary_font_color);
                    buttonNeutral.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textNeutral.setTextColor(getResources().getColor(R.color.dark_grey));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonNeutral.setColorFilter(getResources().getColor(R.color.primary_font_color));
                    textNeutral.setTextColor(getResources().getColor(R.color.primary_font_color));
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                    startIntent.putExtra("StringValue", "Neutral");
                    startIntent.putExtra("Title", "Neutral");
                    startIntent.putExtra("IconID", "Neutral");
                    startIntent.putExtra("drawable", R.drawable.neutral);
                    startIntent.putExtra("color", R.color.primary_font_color);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    v.animate().scaleX(1f).setDuration(1000).start();
                    v.animate().scaleY(1f).setDuration(1000).start();
                    v.setBackgroundResource(R.drawable.normal);
                    buttonNeutral.setColorFilter(getResources().getColor(R.color.primary_font_color));
                    textNeutral.setTextColor(getResources().getColor(R.color.primary_font_color));
                    return true;
                }
                return false;
            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        if (densityDpi < 560) { // SG4 = 480
            int width = 462, height = 462;
            CardView.LayoutParams params = new CardView.LayoutParams(width,height);
            buttonMage.setLayoutParams(params);
            buttonRogue.setLayoutParams(params);
            buttonPaladin.setLayoutParams(params);
            buttonDruid.setLayoutParams(params);
            buttonShaman.setLayoutParams(params);
            buttonWarlock.setLayoutParams(params);
            buttonPriest.setLayoutParams(params);
            buttonWarrior.setLayoutParams(params);
            buttonHunter.setLayoutParams(params);
            buttonNeutral.setLayoutParams(params);
        }
    }

    private PopupWindow initiatePopupWindow() {
        PopupWindow mDropdown = null;
        LayoutInflater mInflater;
        LinearLayout pop_up_search = null;

        try {
            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.popup_search_menu, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            pop_up_search = (LinearLayout) findViewById(R.id.search);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout,FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
            mDropdown.showAtLocation(pop_up_search, Gravity.NO_GRAVITY, 0,pop_up_search.getHeight()*3-70);

            final PopupWindow finalMDropdown = mDropdown;

            final TextView edit_card_name = (EditText) findViewById(R.id.edit_card_name);

            final ImageButton search = (ImageButton) layout.findViewById(R.id.button_search);
            search.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    String card_name;
                    int action = event.getAction();
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

                        card_name = edit_card_name.getText().toString();

                        Intent startIntent = new Intent(getApplicationContext(), Cards.class);
                        startIntent.putExtra("StringValue", card_name);
                        startIntent.putExtra("Title", card_name);
                        startIntent.putExtra("IconID", card_name);
                        startIntent.putExtra("drawable", R.drawable.all_cards);
                        startIntent.putExtra("color", R.color.primary_font_color);
                        startActivity(startIntent);

//                        manaFilter(0);

                        finalMDropdown.dismiss();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDropdown;
    }
}