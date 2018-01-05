package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import pl.pjwstk.pgmd.hearthlounge.model.DeckFull;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 02.01.2018.
 */

public class SelectedDeck extends DrawerMenu {

    private LinearLayout all_cards;

    private ImageView image_view_player_class;
    private TextView text_up_vote, text_down_vote;
    private TextView text_title;
    private TextView text_author;
    private TextView text_archetyp;
    private TextView text_created;
    private TextView text_description;
    private TextView text_hero, text_minio, text_spell, text_weapon;

    private TextView text_count0, text_count1, text_count2, text_count3, text_count4, text_count5, text_count6, text_count7;
    private ProgressBar progressBar0, progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6, progressBar7;

    private String stringValue;
    private String deckTitle;
    private String iconId;
    private int drawable;
    private int color;

    public LinearLayout getAll_cards() {
        return all_cards;
    }

    public void setAll_cards(LinearLayout all_cards) {
        this.all_cards = all_cards;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getDeckTitle() {
        return deckTitle;
    }

    public void setDeckTitle(String deckTitle) {
        this.deckTitle = deckTitle;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.selected_deck, frameLayout);

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
                    startIntent.putExtra("StringValue", getStringValue());// String.valueOf(getStringValue())
                    startIntent.putExtra("Title", getDeckTitle());
                    startIntent.putExtra("IconID", getIconId());
                    startIntent.putExtra("drawable", getDrawable());
                    startIntent.putExtra("color", getColor());
                    startActivity(startIntent);
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.normal);
                    return true;
                }
                return false;
            }
        });

        // Showing and Enabling clicks on the Home/Up button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // setting up text views and stuff
        setUpUIViews();

        // recovering data from MainActivity, sent via intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("deckModel"); // getting the model from MainActivity send via extras
            DeckFull deckModel = new Gson().fromJson(json, DeckFull.class);

//            String json2 = bundle.getString("deck"); // getting the model from MainActivity send via extras
//            Deck deck = new Gson().fromJson(json, Deck.class);
//            Deck deck = new Deck();

            String mDrawableName = deckModel.getPlayerClass();
            String playerClassName = mDrawableName.substring(0,1).toUpperCase() + mDrawableName.substring(1).toLowerCase();
            setIconId(playerClassName);
            int drawable = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
            setDrawable(drawable);
            int color = getResources().getIdentifier(mDrawableName , "color", getPackageName());
            setColor(color);

            image_view_player_class.setImageResource(drawable);
            text_up_vote.setText(""+deckModel.getUpvotes());
            text_down_vote.setText(""+deckModel.getDownvotes());

            setDeckTitle(deckModel.getTitle());
            text_title.setText(deckModel.getTitle());
            text_author.setText(deckModel.getAuthor());
            text_archetyp.setText(deckModel.getArchetype());

            Long created = Long.parseLong(deckModel.getCreated());
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(created);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.setCalendar(calendar);
            String result = format.format(calendar.getTime());
            text_created.setText(result);

            text_description.setText(deckModel.getDescription());

            int[] array = new int[8];
            for (int i = 0; i <= 7; i++) {
                array[i] = deckModel.getDeck().getManaCurve().get(String.valueOf(i));
            }
            text_count0.setText(""+array[0]);
            progressBar0.setProgress(array[0]);
            progressBar0.setMax((int) deckModel.getDeck().getMax());
            text_count1.setText(""+array[1]);
            progressBar1.setProgress(array[1]);
            progressBar1.setMax((int) deckModel.getDeck().getMax());
            text_count2.setText(""+array[2]);
            progressBar2.setProgress(array[2]);
            progressBar2.setMax((int) deckModel.getDeck().getMax());
            text_count3.setText(""+array[3]);
            progressBar3.setProgress(array[3]);
            progressBar3.setMax((int) deckModel.getDeck().getMax());
            text_count4.setText(""+array[4]);
            progressBar4.setProgress(array[4]);
            progressBar4.setMax((int) deckModel.getDeck().getMax());
            text_count5.setText(""+array[5]);
            progressBar5.setProgress(array[5]);
            progressBar5.setMax((int) deckModel.getDeck().getMax());
            text_count6.setText(""+array[6]);
            progressBar6.setProgress(array[6]);
            progressBar6.setMax((int) deckModel.getDeck().getMax());
            text_count7.setText(""+array[7]);
            progressBar7.setProgress(array[7]);
            progressBar7.setMax((int) deckModel.getDeck().getMax());

            int progressBarDrawable = 0;
            if (playerClassName.equals("Mage")) {
                progressBarDrawable = R.drawable.progress_bar_mage;
            } else if (playerClassName.equals("Rogue")) {
                progressBarDrawable = R.drawable.progress_bar_rogue;
            } else if (playerClassName.equals("Paladin")) {
                progressBarDrawable = R.drawable.progress_bar_paladin;
            } else if (playerClassName.equals("Druid")) {
                progressBarDrawable = R.drawable.progress_bar_druid;
            } else if (playerClassName.equals("Shaman")) {
                progressBarDrawable = R.drawable.progress_bar_shaman;
            } else if (playerClassName.equals("Warlock")) {
                progressBarDrawable = R.drawable.progress_bar_warlock;
            } else if (playerClassName.equals("Priest")) {
                progressBarDrawable = R.drawable.progress_bar_priest;
            } else if (playerClassName.equals("Warrior")) {
                progressBarDrawable = R.drawable.progress_bar_warrior;
            } else if (playerClassName.equals("Hunter")) {
                progressBarDrawable = R.drawable.progress_bar_hunter;
            }
            progressBar0.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar1.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar2.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar3.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar4.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar5.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar6.setProgressDrawable(getResources().getDrawable(progressBarDrawable));
            progressBar7.setProgressDrawable(getResources().getDrawable(progressBarDrawable));

            if (deckModel.getDeck().getTypes().containsKey("Hero")) {
                text_hero.setText("Hero: " + deckModel.getDeck().getTypes().get("Hero"));
            } else { text_hero.setVisibility(View.GONE); }
            if (deckModel.getDeck().getTypes().containsKey("Minion")) {
                text_minio.setText("Minion: " + deckModel.getDeck().getTypes().get("Minion"));
            } else { text_minio.setVisibility(View.GONE); }
            if (deckModel.getDeck().getTypes().containsKey("Spell")) {
                text_spell.setText("Spell: " + deckModel.getDeck().getTypes().get("Spell"));
            } else { text_spell.setVisibility(View.GONE); }
            if (deckModel.getDeck().getTypes().containsKey("Weapon")) {
                text_weapon.setText("Weapon: " + deckModel.getDeck().getTypes().get("Weapon"));
            } else { text_weapon.setVisibility(View.GONE); }


            setStringValue(deckModel.getDeckId());
        }
    }

    private void setUpUIViews() {
        image_view_player_class = (ImageView)findViewById(R.id.image_view_player_class);
        text_up_vote = (TextView)findViewById(R.id.text_up_vote);
        text_down_vote = (TextView)findViewById(R.id.text_down_vote);
        text_title = (TextView)findViewById(R.id.text_title);
        text_author = (TextView)findViewById(R.id.text_author);
        text_archetyp = (TextView)findViewById(R.id.text_archetyp);
        text_created = (TextView)findViewById(R.id.text_created);
        text_description = (TextView)findViewById(R.id.text_description);

        // MANA CURVE
        text_count0 = (TextView)findViewById(R.id.text_count_0);
        text_count1 = (TextView)findViewById(R.id.text_count_1);
        text_count2 = (TextView)findViewById(R.id.text_count_2);
        text_count3 = (TextView)findViewById(R.id.text_count_3);
        text_count4 = (TextView)findViewById(R.id.text_count_4);
        text_count5 = (TextView)findViewById(R.id.text_count_5);
        text_count6 = (TextView)findViewById(R.id.text_count_6);
        text_count7 = (TextView)findViewById(R.id.text_count_7);
        progressBar0 = (ProgressBar)findViewById(R.id.progressBar_0);
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar_1);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar_2);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar_3);
        progressBar4 = (ProgressBar)findViewById(R.id.progressBar_4);
        progressBar5 = (ProgressBar)findViewById(R.id.progressBar_5);
        progressBar6 = (ProgressBar)findViewById(R.id.progressBar_6);
        progressBar7 = (ProgressBar)findViewById(R.id.progressBar_7);

        text_hero = (TextView)findViewById(R.id.text_hero);
        text_minio = (TextView)findViewById(R.id.text_minion);
        text_spell = (TextView)findViewById(R.id.text_spell);
        text_weapon = (TextView)findViewById(R.id.text_weapon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }}
