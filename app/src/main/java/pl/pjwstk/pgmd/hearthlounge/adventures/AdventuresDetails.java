package pl.pjwstk.pgmd.hearthlounge.adventures;

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

import pl.pjwstk.pgmd.hearthlounge.cards.CardListCache;
import pl.pjwstk.pgmd.hearthlounge.cards.Cards;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.view.TypefaceSpan;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Maciek Dembowski on 30.12.2017.
 */

public class AdventuresDetails extends YouTubeBaseActivity {

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
        setContentView(R.layout.adventures_details);

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
        if (title.equals("Curse of Naxxramas")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/adventures/curse-of-naxxramas.jpg";
        } else if (title.equals("Blackrock Mountain")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/adventures/blackrock-mountain.jpg";
        } else if (title.equals("The League of Explorers")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/adventures/the-league-of-explorers.jpg";
        } else if (title.equals("One Night in Karazhan")) {
            artUrl = "https://raw.githubusercontent.com/vFujin/HearthLounge/master/src/images/adventures/one-night-in-karazhan.jpg";
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
        if (title.equals("Curse of Naxxramas")) {
            description = context.getString(R.string.course_of_naxxramas);
        } else if (title.equals("Blackrock Mountain")) {
            description = context.getString(R.string.blackrock_mountain);
        } else if (title.equals("The League of Explorers")) {
            description = context.getString(R.string.the_league_of_explorers);
        } else if (title.equals("One Night in Karazhan")) {
            description = context.getString(R.string.one_night_in_karazhan);
        }
        descriptionText.setText(description);
    }

    public void getAboutText() {
        TextView aboutText = (TextView)findViewById(R.id.about_text);
        String about = "";
        int countCrads = CardListCache.getInstance().getCardList(stringValue).size();
        if (title.equals("Curse of Naxxramas")) {
            about = "Announce date: 11 apr 2014 \n Release date: 22 jul 2014 \n Number of cards: " + countCrads;
        } else if (title.equals("Blackrock Mountain")) {
            about = "Announce date: 06 mar 2015 \n Release date: 02 apr 2015 \n Number of cards: " + countCrads;
        } else if (title.equals("The League of Explorers")) {
            about = "Announce date: 06 nov 2015 \n Release date: 12 nov 2015 \n Number of cards: " + countCrads;
        } else if (title.equals("One Night in Karazhan")) {
            about = "Announce date: 28 jul 2016 \n Release date: 11 aug 2016 \n Number of cards: " + countCrads;
        }
        aboutText.setText(about);
    }

    public String getYouTubeUrl() {
        String url = "";
        if (title.equals("Curse of Naxxramas")) {
            url = "5SDJgW3A_sw";
        } else if (title.equals("Blackrock Mountain")) {
            url = "jsH9w5HW-9w";
        } else if (title.equals("The League of Explorers")) {
            url = "wmu0XXpUYog";
        } else if (title.equals("One Night in Karazhan")) {
            url = "qlHx7YNWYGY";
        }
        return url;
    }

    public void getGameBoardUrl() {
        ImageView gameboard = (ImageView)findViewById(R.id.image_viewGameBoard);
        String gameBoardUrl = "";
        if (title.equals("Curse of Naxxramas")) {
            gameBoardUrl = "https://static.gamespot.com/uploads/original/225/2256286/2504232-curse+of+naxxramas+game+board.jpg";
        } else if (title.equals("Blackrock Mountain")) {
            gameBoardUrl = "http://media-hearth.cursecdn.com/attachments/10/982/635626381068889622.png";
        } else if (title.equals("The League of Explorers")) {
            gameBoardUrl = "http://www.hearthstonetopdecks.com/wp-content/uploads/2015/11/Uldaman_Board.png";
        } else if (title.equals("One Night in Karazhan")) {
            gameBoardUrl = "https://i.ytimg.com/vi/MA7BOB-PjaM/maxresdefault.jpg";
        }
        Glide.with(this).load(gameBoardUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(gameboard);
    }

    public void changeIcon() {
        ImageView iconExpansions = (ImageView)findViewById(R.id.icon_expansions);
        if (iconId.equals("Curse of Naxxramas")) {
            iconExpansions.setImageResource(R.drawable.naxxramas);
        } else if (iconId.equals("Blackrock Mountain")) {
            iconExpansions.setImageResource(R.drawable.blackrock_mountain);
        } else if (iconId.equals("The League of Explorers")) {
            iconExpansions.setImageResource(R.drawable.the_league_of_explorers);
        } else if (iconId.equals("One Night in Karazhan")) {
            iconExpansions.setImageResource(R.drawable.karazhan);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
