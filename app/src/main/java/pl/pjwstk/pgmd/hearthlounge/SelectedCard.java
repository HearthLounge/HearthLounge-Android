package pl.pjwstk.pgmd.hearthlounge;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 08.11.2017.
 */

public class SelectedCard extends DrawerMenu {

    private ImageView image_view_card;
    private ImageView image_view_cardGold; //GifImageView
    //private TextView text_view_cardId;
    //private TextView text_view_dbfId;
    private TextView text_view_name;
    private TextView text_view_cardSet;
    private TextView text_view_type;
    private TextView text_view_faction;
    private TextView text_view_rarity;
    private TextView text_view_cost;
    private TextView text_view_attack;
    private TextView text_view_health;
    //private TextView text_view_text;
    private TextView text_view_flavor;
    private TextView text_view_artist;
    //private TextView text_view_collectible;
    //private TextView text_view_elite;
    private TextView text_view_race;
    private TextView text_view_playerClass;
    private TextView text_view_howToGet;
    private TextView text_view_howToGetGold;
    //private TextView text_view_locale;
    private TextView text_view_mechanics;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.selected_card, frameLayout);

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
            String json = bundle.getString("cardModel"); // getting the model from MainActivity send via extras
            Card cardModel = new Gson().fromJson(json, Card.class);

            // Then later, when you want to display image
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth()/2-70;
            int height = display.getHeight()/2-70;

            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,height);
            parms.topMargin = -height/8;
//            parms.bottomMargin = -height/16;
            image_view_card.setLayoutParams(parms);

            ImageLoader.getInstance().displayImage(cardModel.getImg(), image_view_card, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            FrameLayout.LayoutParams parms1 = new FrameLayout.LayoutParams(width,height);
            parms1.topMargin = -height/8;
//            parms1.bottomMargin = -height/16;
            image_view_cardGold.setLayoutParams(parms1);

            Glide.with(SelectedCard.this).load(cardModel.getImgGold()).asGif().into(image_view_cardGold);
            ImageLoader.getInstance().displayImage(cardModel.getImgGold(), image_view_cardGold, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            //text_view_cardId.setText("CARD ID: " + cardModel.getCardId());
            //text_view_dbfId.setText("DBF ID: " + cardModel.getDbfId());
            text_view_name.setText("NAME: " + cardModel.getName());
            text_view_cardSet.setText("CARD SET: " + cardModel.getCardSet());
            text_view_type.setText("TYPE: " + cardModel.getType());

            if (cardModel.getFaction() != null) {
                text_view_faction.setText("FACTION: " + cardModel.getFaction());
            } else text_view_faction.setVisibility(View.GONE);  // ustawi puste pole

            text_view_rarity.setText("RARITY: " + cardModel.getRarity());
            text_view_cost.setText("COST: " + cardModel.getCost()); //tu
            text_view_attack.setText("ATTACK: " + cardModel.getAttack()); //tu
            text_view_health.setText("HEALTH: " + cardModel.getHealth()); //tu

//            if (cardModel.getText() != null) {
//                text_view_text.setText(Html.fromHtml("TEXT: " + cardModel.getText()));
//            } else text_view_text.setText("pusto :( ");

            if (cardModel.getFlavor() != null) {
                text_view_flavor.setText(Html.fromHtml("FLAVOR: " + cardModel.getFlavor()));
            } else text_view_flavor.setVisibility(View.GONE);
            // INNY SPOSOB
//            String flavor = "<body>" + cardModel.getFlavor() + "</body>" + "<style type=\"text/css\">body{color: #00a99c; margin: -5dp;}</style>";
//            text_view_flavor.setBackgroundColor(Color.TRANSPARENT);
//            text_view_flavor.loadData("FLAVOR: " + flavor, "text/html", "UTF-8");

            if (cardModel.getArtist() != null) {
            text_view_artist.setText("ARTIST: " + cardModel.getArtist());
            } else text_view_artist.setVisibility(View.GONE);

            //text_view_collectible.setText("COLLECTIBLE: " + cardModel.getCollectible());
            //text_view_elite.setText("ELITE: " + cardModel.getElite()); //tu setEnabled

            if (cardModel.getRace() != null) {
            text_view_race.setText("RACE: " + cardModel.getRace());
            } else text_view_race.setVisibility(View.GONE);

            text_view_playerClass.setText("PLAYER CLASS: " + cardModel.getPlayerClass());

            if (cardModel.getHowToGet() != null) {
                text_view_howToGet.setText("HOW TO GET: " + cardModel.getHowToGet());
            } else text_view_howToGet.setVisibility(View.GONE);

            if (cardModel.getHowToGetGold() != null) {
                text_view_howToGetGold.setText("HOT TO GET GOLD: " + cardModel.getHowToGetGold());
            } else text_view_howToGetGold.setVisibility(View.GONE);

            //text_view_locale.setText("LOCALE: " + cardModel.getLocale());

            if(cardModel.getMechanicsList() != null) {
                for (Card.Mechanics mechanics : cardModel.getMechanicsList()) {
                        text_view_mechanics.setText("MECHANICS: " + mechanics.getMechanics());
                }
            } else text_view_mechanics.setVisibility(View.GONE);
        }
    }

    private void setUpUIViews() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        image_view_card = (ImageView)findViewById(R.id.image_viewCard);
        image_view_cardGold = (ImageView) findViewById(R.id.image_viewCardGold);
        //text_view_cardId = (TextView)findViewById(R.id.text_view_cardId);
        //text_view_dbfId = (TextView)findViewById(R.id.text_view_dbfId);
        text_view_name = (TextView)findViewById(R.id.text_view_name);
        text_view_cardSet = (TextView)findViewById(R.id.text_view_cardSet);
        text_view_type = (TextView)findViewById(R.id.text_view_type);
        text_view_faction = (TextView)findViewById(R.id.text_view_faction);
        text_view_rarity = (TextView)findViewById(R.id.text_view_rarity);
        text_view_cost = (TextView)findViewById(R.id.text_view_cost);
        text_view_attack = (TextView)findViewById(R.id.text_view_attack);
        text_view_health = (TextView)findViewById(R.id.text_view_health);
        //text_view_text = (TextView) findViewById(R.id.text_view_text);
        text_view_flavor = (TextView) findViewById(R.id.text_view_flavor);
        text_view_artist = (TextView)findViewById(R.id.text_view_artist);
        //text_view_collectible = (TextView)findViewById(R.id.text_view_collectible);
        //text_view_elite = (TextView)findViewById(R.id.text_view_elite);
        text_view_race= (TextView)findViewById(R.id.text_view_race);
        text_view_playerClass = (TextView)findViewById(R.id.text_view_playerClass);
        text_view_howToGet = (TextView)findViewById(R.id.text_view_howToGet);
        text_view_howToGetGold = (TextView)findViewById(R.id.text_view_howToGetGold);
        //text_view_locale = (TextView)findViewById(R.id.text_view_locale);
        text_view_mechanics = (TextView)findViewById(R.id.text_view_mechanics);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
