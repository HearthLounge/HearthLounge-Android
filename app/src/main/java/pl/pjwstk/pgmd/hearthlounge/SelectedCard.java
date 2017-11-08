package pl.pjwstk.pgmd.hearthlounge;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import pl.pjwstk.pgmd.hearthlounge.model.Card;

/**
 * Created by Maciek Dembowski on 08.11.2017.
 */

public class SelectedCard extends AppCompatActivity {

    private ImageView image_view_card;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_card);

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

            text_view_cardId.setText(cardModel.getCardId());
            text_view_dbfId.setText(cardModel.getDbfId());
            text_view_name.setText(cardModel.getName());
            text_view_cardSet.setText(cardModel.getCardSet());
            text_view_type.setText(cardModel.getType());
            text_view_faction.setText(cardModel.getFaction());
            text_view_rarity.setText(cardModel.getRarity());
            text_view_cost.setText(cardModel.getCost());
            text_view_attack.setText(cardModel.getAttack());
            text_view_health.setText(cardModel.getHealth());
            text_view_text.setText(cardModel.getText());
            text_view_flavor.setText(cardModel.getFlavor());
            text_view_artist.setText(cardModel.getArtist());
            text_view_collectible.setEnabled(cardModel.getCollectible());
            text_view_elite.setEnabled(cardModel.getElite());
            text_view_playerClass.setText(cardModel.getPlayerClass());
            text_view_locale.setText(cardModel.getLocale());

            StringBuffer stringBuffer = new StringBuffer();
            for(Card.Mechanics mechanics : cardModel.getMechanicsList()) {
                stringBuffer.append(mechanics.getMechanics() + ", ");
            }
            text_view_mechanics.setText("Mechanics: " + stringBuffer);

        }

    }

    private void setUpUIViews() {
        image_view_card = (ImageView)findViewById(R.id.image_view_card);
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
        text_view_text = (TextView)findViewById(R.id.text_view_text);
        text_view_flavor = (TextView)findViewById(R.id.text_view_flavor);
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
}
