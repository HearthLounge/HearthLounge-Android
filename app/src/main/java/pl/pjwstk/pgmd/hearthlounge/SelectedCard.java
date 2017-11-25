package pl.pjwstk.pgmd.hearthlounge;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.text.Html;
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

import pl.droidsonroids.gif.GifImageView;
import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 08.11.2017.
 */

public class SelectedCard extends DrawerMenu {

    private ImageView image_view_card;
    private ImageView image_view_cardGold; //GifImageView
    private TextView text_view_cardId;
    private TextView text_view_dbfId;
    private TextView text_view_name;
    private TextView text_view_cardSet;
    private TextView text_view_type;
    private TextView text_view_faction;
    private TextView text_view_rarity;
    private TextView text_view_cost;
    private TextView text_view_attack;
    private TextView text_view_health;
    private TextView text_view_text;
    private TextView text_view_flavor;
    private TextView text_view_artist;
    private TextView text_view_collectible;
    private TextView text_view_elite;
    private TextView text_view_playerClass;
    private TextView text_view_locale;
    private TextView text_view_mechanics;

    private ProgressBar progressBar;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.selected_card, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

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

            text_view_cardId.setText("CARD ID: " + cardModel.getCardId());
            text_view_dbfId.setText("DBF ID: " + cardModel.getDbfId());
            text_view_name.setText("NAME: " + cardModel.getName());
            text_view_cardSet.setText("CARD SET: " + cardModel.getCardSet());
            text_view_type.setText("TYPE: " + cardModel.getType());
            text_view_faction.setText("FACTION: " + cardModel.getFaction());
            text_view_rarity.setText("RARITY: " + cardModel.getRarity());
            text_view_cost.setText("COST: " + cardModel.getCost()); //tu
            text_view_attack.setText("ATTACK: " + cardModel.getAttack()); //tu
            text_view_health.setText("HEALTH: " + cardModel.getHealth()); //tu

            if (cardModel.getText() != null) {
                text_view_text.setText(Html.fromHtml("TEXT: " + cardModel.getText()));
            } else text_view_text.setText("pusto :( ");

            if (cardModel.getFlavor() != null) {
                text_view_flavor.setText(Html.fromHtml("FLAVOR: " + cardModel.getFlavor()));
            } else text_view_flavor.setText("pusto x2 :( ");// ustawi puste pole

            // INNY SPOSOB :D
//            String flavor = "<body>" + cardModel.getFlavor() + "</body>" + "<style type=\"text/css\">body{color: #00a99c; margin: -5dp;}</style>";
//            text_view_flavor.setBackgroundColor(Color.TRANSPARENT);
//            text_view_flavor.loadData("FLAVOR: " + flavor, "text/html", "UTF-8");

            text_view_artist.setText("ARTIST: " + cardModel.getArtist());
            text_view_collectible.setText("COLLECTIBLE: " + cardModel.getCollectible());
            text_view_elite.setText("ELITE: " + cardModel.getElite()); //tu setEnabled
            text_view_playerClass.setText("PLAYER CLASS: " + cardModel.getPlayerClass());
            text_view_locale.setText("LOCALE: " + cardModel.getLocale());

//            StringBuffer stringBuffer = new StringBuffer();

            if(cardModel.getMechanicsList() != null) {
                text_view_mechanics.setText("WESZŁO");
                for (Card.Mechanics mechanics : cardModel.getMechanicsList()) {
                    if (mechanics.getMechanics() != null) {
//                    stringBuffer.append(mechanics.getMechanics() + ", ");
//                    text_view_mechanics.setText("MECHANICS: " + stringBuffer);
                        text_view_mechanics.setText("MECHANICS:: " + mechanics.getMechanics());
                    }
                }
            }else text_view_mechanics.setText("NIC");


        }

    }

    private void setUpUIViews() {
        image_view_card = (ImageView)findViewById(R.id.image_viewCard);
        image_view_cardGold = (ImageView) findViewById(R.id.image_viewCardGold);
        text_view_cardId = (TextView)findViewById(R.id.text_view_cardId);
        text_view_dbfId = (TextView)findViewById(R.id.text_view_dbfId);
        text_view_name = (TextView)findViewById(R.id.text_view_name);
        text_view_cardSet = (TextView)findViewById(R.id.text_view_cardSet);
        text_view_type = (TextView)findViewById(R.id.text_view_type);
        text_view_faction = (TextView)findViewById(R.id.text_view_faction);
        text_view_rarity = (TextView)findViewById(R.id.text_view_rarity);
        text_view_cost = (TextView)findViewById(R.id.text_view_cost);
        text_view_attack = (TextView)findViewById(R.id.text_view_attack);
        text_view_health = (TextView)findViewById(R.id.text_view_health);
        text_view_text = (TextView) findViewById(R.id.text_view_text);
        text_view_flavor = (TextView) findViewById(R.id.text_view_flavor);
        text_view_artist = (TextView)findViewById(R.id.text_view_artist);
        text_view_collectible = (TextView)findViewById(R.id.text_view_collectible);
        text_view_elite = (TextView)findViewById(R.id.text_view_elite);
        text_view_playerClass = (TextView)findViewById(R.id.text_view_playerClass);
        text_view_locale = (TextView)findViewById(R.id.text_view_locale);
        text_view_mechanics = (TextView)findViewById(R.id.text_view_mechanics);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
