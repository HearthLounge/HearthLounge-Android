package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import pl.pjwstk.pgmd.hearthlounge.view.TypefaceSpan;

/**
 * Created by Maciek Dembowski on 28.12.2017.
 */

public class ExpansionsDetails extends YouTubeBaseActivity {

    String iconId;
    String title;
    private android.support.v7.widget.Toolbar toolbar;
    private LinearLayout all_cards;


    YouTubePlayerView youTubePlayerView;
    public static final String KEY = "AIzaSyCvRyXnKWGLAm93ZVVYchSN2HXL3xBNisY";
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansions_details);

        Intent intent = getIntent();
        iconId = intent.getStringExtra("IconID");
        title = intent.getStringExtra("Title");
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(this, "belwe_medium.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar2); //PASEK U GÓRY Z NAZWĄ APLIKACJI
        toolbar.setTitle(s);

        changeIcon();


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
                    startIntent.putExtra("StringValue", title);
                    startIntent.putExtra("IconID", iconId);
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });



        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("ijyMZPIsj5E");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(KEY, onInitializedListener);


        ImageView gameboard = (ImageView)findViewById(R.id.image_viewGameBoard);
        // dodać jakiś prosty if i na sztywno linki lub drowable
        String URL = "http://www.nerdophiles.com/wp-content/uploads/2014/12/gvg-gameboard.jpg";
        Glide.with(this).load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(gameboard);
    }

    public void changeIcon() {
        ImageView iconExpansions = (ImageView)findViewById(R.id.icon_expansions);
        if (iconId.equals("Goblins vs Gnomes")) {       // EXPANSIONS ICON
            iconExpansions.setImageResource(R.drawable.goblins_vs_gnomes);
        } else if (iconId.equals("The Grand Tournament")) {
            iconExpansions.setImageResource(R.drawable.the_grand_tournament);
        } else if (iconId.equals("Whispers of the Old Gods")) {
            iconExpansions.setImageResource(R.drawable.whispers_of_the_old_gods);
        } else if (iconId.equals("Mean Streets of Gadgetzan")) {
            iconExpansions.setImageResource(R.drawable.mean_streets_of_gadgetzan);
        } else if (iconId.equals("Journey to Un'Goro")) {
            iconExpansions.setImageResource(R.drawable.journey_to_ungoro);
        } else if (iconId.equals("Knights of the Frozen Throne")) {
            iconExpansions.setImageResource(R.drawable.knights_of_the_frozen_throne);
        } else if (iconId.equals("Kobolds & Catacombs")) {
            iconExpansions.setImageResource(R.drawable.kobolds_catacombs);
        }
    }
}
