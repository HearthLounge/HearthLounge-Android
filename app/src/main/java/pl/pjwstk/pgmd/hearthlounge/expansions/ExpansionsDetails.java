package pl.pjwstk.pgmd.hearthlounge.expansions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import pl.pjwstk.pgmd.hearthlounge.cards.Cards;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.cards.CardListCache;
import pl.pjwstk.pgmd.hearthlounge.view.TypefaceSpan;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Maciek Dembowski on 28.12.2017.
 */

public class ExpansionsDetails extends YouTubeBaseActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private LinearLayout all_cards;

    private String stringValue;
    private String title;
    private String iconId;

    YouTubePlayerView youTubePlayerView;
    public static final String KEY = "AIzaSyCvRyXnKWGLAm93ZVVYchSN2HXL3xBNisY";
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expansions_details);

        Intent intent = getIntent();
        stringValue = intent.getStringExtra("StringValue");
        title = intent.getStringExtra("Title");
        iconId = intent.getStringExtra("IconID");

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
                    Intent startIntent = new Intent(getApplicationContext(), Cards.class); //Do którego ma iść
                    startIntent.putExtra("StringValue", stringValue);
                    startIntent.putExtra("Title", title);
                    startIntent.putExtra("IconID", iconId);
                    startIntent.putExtra("drawable", getIntent().getExtras().getInt("drawable"));
                    startIntent.putExtra("color", getIntent().getExtras().getInt("color"));
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        getArtUrl();
        getDescriptionText();
        getAboutText();
        getGameBoardUrl();

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(getYouTubeUrl());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(KEY, onInitializedListener);

    }

    public void getArtUrl() {
        ImageView artImage = (ImageView) findViewById(R.id.art_view);
        String artUrl = "";
        if (title.equals("Goblins vs Gnomes")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/goblins-vs-gnomes.jpg";
        } else if (title.equals("The Grand Tournament")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/the-grand-tournament.jpg";
        } else if (title.equals("Whispers of the Old Gods")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/whispers-of-the-old-gods.jpg";
        } else if (title.equals("Mean Streets of Gadgetzan")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/mean-streets-of-gadgetzan.jpg";
        } else if (title.equals("Journey to Un'Goro")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/journey-to-ungoro.jpg";
        } else if (title.equals("Knights of the Frozen Throne")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/knights-of-the-frozen-throne.jpg";
        } else if (title.equals("Kobolds & Catacombs")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/kobolds-and-catacombs.jpg";
        }
        Glide.with(this).load(artUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(artImage);
    }

    public void getDescriptionText() {
        Context context = this;
        TextView descriptionText = (TextView)findViewById(R.id.description_text);
        String description = "";
        int countCrads = CardListCache.getInstance().getCardList(title).size();
        if (title.equals("Goblins vs Gnomes")) {
            description = context.getString(R.string.goblins_vs_gnomes);
        } else if (title.equals("The Grand Tournament")) {
            description = context.getString(R.string.the_grand_tournament);
        } else if (title.equals("Whispers of the Old Gods")) {
            description = context.getString(R.string.whispers_of_the_old_gods);
        } else if (title.equals("Mean Streets of Gadgetzan")) {
            description = context.getString(R.string.mean_streets_of_gadgetzan);
        } else if (title.equals("Journey to Un'Goro")) {
            description = context.getString(R.string.journey_to_ungoro);
        } else if (title.equals("Knights of the Frozen Throne")) {
            description = context.getString(R.string.knights_of_the_frozen_throne);
        } else if (title.equals("Kobolds & Catacombs")) {
            description = context.getString(R.string.kobolds_catacombs);
        }
        descriptionText.setText(description);
    }

    public void getAboutText() {
        TextView aboutText = (TextView)findViewById(R.id.about_text);
        String about = "";
        int countCrads = CardListCache.getInstance().getCardList(stringValue).size();
        if (title.equals("Goblins vs Gnomes")) {
            about = "Announce date: 07.11.2014 \n Release date: 08.12.2014 \n Number of cards: " + countCrads;
        } else if (title.equals("The Grand Tournament")) {
            about = "Announce date: 22.06.2015 \n Release date: 24.08.2015 \n Number of cards: " + countCrads;
        } else if (title.equals("Whispers of the Old Gods")) {
            about = "Announce date: 11.03.2016 \n Release date: 26.04.2016 \n Number of cards: " + countCrads;
        } else if (title.equals("Mean Streets of Gadgetzan")) {
            about = "Announce date: 04.11.2016 \n Release date: 01.12.2016 \n Number of cards: " + countCrads;
        } else if (title.equals("Journey to Un'Goro")) {
            about = "Announce date: 27.02.2017 \n Release date: 06.04.2017 \n Number of cards: " + countCrads;
        } else if (title.equals("Knights of the Frozen Throne")) {
            about = "Announce date: 06.07.2017 \n Release date: 10.08.2017 \n Number of cards: " + countCrads;
        } else if (title.equals("Kobolds & Catacombs")) {
            about = "Announce date: 03.11.2017 \n Release date: 07.12.2017 \n Number of cards: " + countCrads;
        }
        aboutText.setText(about);
    }

    public String getYouTubeUrl() {
        String url = "";
        if (title.equals("Goblins vs Gnomes")) {
            url = "ijyMZPIsj5E";
        } else if (title.equals("The Grand Tournament")) {
            url = "Fe7XDBtlQzg";
        } else if (title.equals("Whispers of the Old Gods")) {
            url = "cKjUgLrDEbI";
        } else if (title.equals("Mean Streets of Gadgetzan")) {
            url = "fTyeE5oK7LQ";
        } else if (title.equals("Journey to Un'Goro")) {
            url = "MG3nb7Oam4k";
        } else if (title.equals("Knights of the Frozen Throne")) {
            url = "CCJq7lE3JQs";
        } else if (title.equals("Kobolds & Catacombs")) {
            url = "zdbnXv8UsVc";
        }
        return url;
    }

    public void getGameBoardUrl() {
        ImageView gameboard = (ImageView)findViewById(R.id.image_viewGameBoard);
        String gameBoardUrl = "";
        if (title.equals("Goblins vs Gnomes")) {
            gameBoardUrl = "http://www.nerdophiles.com/wp-content/uploads/2014/12/gvg-gameboard.jpg";
        } else if (title.equals("The Grand Tournament")) {
            gameBoardUrl = "https://hearthstone-a.akamaihd.net/images/the-grand-tournament/en-us/gameboard-65f997631699c8de8aea11a25826fa883748ec2cf21478ecf3c38fd90d3bbd9c7a257a8c44643ac13582b3b336ed41ac44f58a22de1fd175e3c9e4e2aebda02d.jpg";
        } else if (title.equals("Whispers of the Old Gods")) {
            gameBoardUrl = "http://www.hearthstonetopdecks.com/wp-content/uploads/2016/04/wog-game-board-1024x595.jpg";
        } else if (title.equals("Mean Streets of Gadgetzan")) {
            gameBoardUrl = "http://www.hearthstonetopdecks.com/wp-content/uploads/2016/11/UBQ6EJO9NORH1478042692080.jpg";
        } else if (title.equals("Journey to Un'Goro")) {
            gameBoardUrl = "https://i.ytimg.com/vi/GedVYJR_42U/maxresdefault.jpg";
        } else if (title.equals("Knights of the Frozen Throne")) {
            gameBoardUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/knights-of-the-frozen-throne.jpg";
        } else if (title.equals("Kobolds & Catacombs")) {
            gameBoardUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/expansions/kobolds-and-catacombs.jpg";
        }
        Glide.with(this).load(gameBoardUrl)
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}