package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 30.12.2017.
 */

public class Adventures extends DrawerMenu {

    private LinearLayout curseOfNaxxramas, blackrockMountain, theLeageOfExplorers, oneNightInKarazhan;

    private ImageView imageCurseOfNaxxramas, imageBlackrockMountain, imageTheLeageOfExplorers, imageOneNightInKarazhan;

    private TextView textCurseOfNaxxramas, textBlackrockMountain, textTheLeageOfExplorers, textOneNightInKarazhan;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.adventures, frameLayout);


        curseOfNaxxramas = (LinearLayout) findViewById(R.id.curse_of_naxxramas);
        imageCurseOfNaxxramas = (ImageView) findViewById(R.id.image_curse_of_naxxramas);
        textCurseOfNaxxramas = (TextView) findViewById(R.id.text_curse_of_naxxramas);
        curseOfNaxxramas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageCurseOfNaxxramas.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageCurseOfNaxxramas.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textCurseOfNaxxramas.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textCurseOfNaxxramas.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageCurseOfNaxxramas.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textCurseOfNaxxramas.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_con);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageCurseOfNaxxramas.animate().scaleX(1f).setDuration(1000).start();
                    imageCurseOfNaxxramas.animate().scaleY(1f).setDuration(1000).start();
                    textCurseOfNaxxramas.animate().scaleX(1f).setDuration(1000).start();
                    textCurseOfNaxxramas.animate().scaleY(1f).setDuration(1000).start();
                    imageCurseOfNaxxramas.setColorFilter(getResources().getColor(R.color.naxx));
                    textCurseOfNaxxramas.setTextColor(getResources().getColor(R.color.naxx));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), AdventuresDetails.class);
                    startIntent.putExtra("StringValue", "Naxxramas");
                    startIntent.putExtra("Title", "Curse of Naxxramas");
                    startIntent.putExtra("IconID", "Curse of Naxxramas");
                    startIntent.putExtra("drawable", R.drawable.naxxramas);
                    startIntent.putExtra("color", R.color.naxx);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageCurseOfNaxxramas.animate().scaleX(1f).setDuration(1000).start();
                    imageCurseOfNaxxramas.animate().scaleY(1f).setDuration(1000).start();
                    textCurseOfNaxxramas.animate().scaleX(1f).setDuration(1000).start();
                    textCurseOfNaxxramas.animate().scaleY(1f).setDuration(1000).start();
                    imageCurseOfNaxxramas.setColorFilter(getResources().getColor(R.color.naxx));
                    textCurseOfNaxxramas.setTextColor(getResources().getColor(R.color.naxx));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        blackrockMountain = (LinearLayout) findViewById(R.id.blackrock_mountain);
        imageBlackrockMountain = (ImageView) findViewById(R.id.image_blackrock_mountain);
        textBlackrockMountain = (TextView) findViewById(R.id.text_blackrock_mountain);
        blackrockMountain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageBlackrockMountain.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageBlackrockMountain.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textBlackrockMountain.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textBlackrockMountain.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageBlackrockMountain.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textBlackrockMountain.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_bm);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageBlackrockMountain.animate().scaleX(1f).setDuration(1000).start();
                    imageBlackrockMountain.animate().scaleY(1f).setDuration(1000).start();
                    textBlackrockMountain.animate().scaleX(1f).setDuration(1000).start();
                    textBlackrockMountain.animate().scaleY(1f).setDuration(1000).start();
                    imageBlackrockMountain.setColorFilter(getResources().getColor(R.color.bm));
                    textBlackrockMountain.setTextColor(getResources().getColor(R.color.bm));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), AdventuresDetails.class);
                    startIntent.putExtra("StringValue", "Blackrock Mountain");
                    startIntent.putExtra("Title", "Blackrock Mountain");
                    startIntent.putExtra("IconID", "Blackrock Mountain");
                    startIntent.putExtra("drawable", R.drawable.blackrock_mountain);
                    startIntent.putExtra("color", R.color.bm);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageBlackrockMountain.animate().scaleX(1f).setDuration(1000).start();
                    imageBlackrockMountain.animate().scaleY(1f).setDuration(1000).start();
                    textBlackrockMountain.animate().scaleX(1f).setDuration(1000).start();
                    textBlackrockMountain.animate().scaleY(1f).setDuration(1000).start();
                    imageBlackrockMountain.setColorFilter(getResources().getColor(R.color.bm));
                    textBlackrockMountain.setTextColor(getResources().getColor(R.color.bm));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        theLeageOfExplorers = (LinearLayout) findViewById(R.id.the_leage_of_explorers);
        imageTheLeageOfExplorers = (ImageView) findViewById(R.id.image_the_leage_of_explorers);
        textTheLeageOfExplorers = (TextView) findViewById(R.id.text_the_leage_of_explorers);
        theLeageOfExplorers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageTheLeageOfExplorers.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageTheLeageOfExplorers.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textTheLeageOfExplorers.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textTheLeageOfExplorers.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageTheLeageOfExplorers.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textTheLeageOfExplorers.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_tloe);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageTheLeageOfExplorers.animate().scaleX(1f).setDuration(1000).start();
                    imageTheLeageOfExplorers.animate().scaleY(1f).setDuration(1000).start();
                    textTheLeageOfExplorers.animate().scaleX(1f).setDuration(1000).start();
                    textTheLeageOfExplorers.animate().scaleY(1f).setDuration(1000).start();
                    imageTheLeageOfExplorers.setColorFilter(getResources().getColor(R.color.loe));
                    textTheLeageOfExplorers.setTextColor(getResources().getColor(R.color.loe));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), AdventuresDetails.class);
                    startIntent.putExtra("StringValue", "The League of Explorers");
                    startIntent.putExtra("Title", "The League of Explorers");
                    startIntent.putExtra("IconID", "The League of Explorers");
                    startIntent.putExtra("drawable", R.drawable.the_league_of_explorers);
                    startIntent.putExtra("color", R.color.loe);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageTheLeageOfExplorers.animate().scaleX(1f).setDuration(1000).start();
                    imageTheLeageOfExplorers.animate().scaleY(1f).setDuration(1000).start();
                    textTheLeageOfExplorers.animate().scaleX(1f).setDuration(1000).start();
                    textTheLeageOfExplorers.animate().scaleY(1f).setDuration(1000).start();
                    imageTheLeageOfExplorers.setColorFilter(getResources().getColor(R.color.loe));
                    textTheLeageOfExplorers.setTextColor(getResources().getColor(R.color.loe));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        oneNightInKarazhan = (LinearLayout) findViewById(R.id.one_night_in_karazhan);
        imageOneNightInKarazhan = (ImageView) findViewById(R.id.image_one_night_in_karazhan);
        textOneNightInKarazhan = (TextView) findViewById(R.id.text_one_night_in_karazhan);
        oneNightInKarazhan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    imageOneNightInKarazhan.animate().scaleXBy(0.2f).setDuration(5000).start();
                    imageOneNightInKarazhan.animate().scaleYBy(0.2f).setDuration(5000).start();
                    textOneNightInKarazhan.animate().scaleXBy(0.2f).setDuration(5000).start();
                    textOneNightInKarazhan.animate().scaleYBy(0.2f).setDuration(5000).start();
                    imageOneNightInKarazhan.setColorFilter(getResources().getColor(R.color.dark_grey));
                    textOneNightInKarazhan.setTextColor(getResources().getColor(R.color.dark_grey));
                    v.setBackgroundResource(R.drawable.pressed_onik);
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    v.animate().cancel();
                    imageOneNightInKarazhan.animate().scaleX(1f).setDuration(1000).start();
                    imageOneNightInKarazhan.animate().scaleY(1f).setDuration(1000).start();
                    textOneNightInKarazhan.animate().scaleX(1f).setDuration(1000).start();
                    textOneNightInKarazhan.animate().scaleY(1f).setDuration(1000).start();
                    imageOneNightInKarazhan.setColorFilter(getResources().getColor(R.color.karazhan));
                    textOneNightInKarazhan.setTextColor(getResources().getColor(R.color.karazhan));
                    v.setBackgroundResource(R.drawable.normal);
                    Intent startIntent = new Intent(getApplicationContext(), AdventuresDetails.class);
                    startIntent.putExtra("StringValue", "One Night in Karazhan");
                    startIntent.putExtra("Title", "One Night in Karazhan");
                    startIntent.putExtra("IconID", "One Night in Karazhan");
                    startIntent.putExtra("drawable", R.drawable.karazhan);
                    startIntent.putExtra("color", R.color.karazhan);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.animate().cancel();
                    imageOneNightInKarazhan.animate().scaleX(1f).setDuration(1000).start();
                    imageOneNightInKarazhan.animate().scaleY(1f).setDuration(1000).start();
                    textOneNightInKarazhan.animate().scaleX(1f).setDuration(1000).start();
                    textOneNightInKarazhan.animate().scaleY(1f).setDuration(1000).start();
                    imageOneNightInKarazhan.setColorFilter(getResources().getColor(R.color.karazhan));
                    textOneNightInKarazhan.setTextColor(getResources().getColor(R.color.karazhan));
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });
    }
}
