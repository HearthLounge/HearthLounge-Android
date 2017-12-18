package pl.pjwstk.pgmd.hearthlounge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;

/**
 * Created by Maciek Dembowski on 17.12.2017.
 */

public class JSON extends DrawerMenu {

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";
    private final String HEADER = "X-Mashape-Key";
    private final String KEY = "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q";

    public MakeImageToast toast;

    private ListView listViewCards;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
//        getLayoutInflater().inflate(R.layout.all_cards, contentFrameLayout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);

//        new DownloadImageTask((ImageButton)findViewById(R.id.image_button_cards))
//                .execute("http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_116.png");

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data
        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        listViewCards = (ListView)findViewById(R.id.list_view_cards);
        // To start fetching the data when app start, uncomment below line to start the async task.
        new JSONTask().execute(URL + HEADER + KEY);
    }

    public class JSONTask extends AsyncTask<String, String, List<Card> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Card> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                //URL url = new URL(params[0] + params[1] + params[2]);
                //URL url = new URL(URL + HEADER + KEY);

                URL url = new URL("https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("X-Mashape-Key", "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q");
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader((new InputStreamReader(stream)));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);//.append("\n");
                }
                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);

                JSONArray basic = parentObject.getJSONArray("Basic");
                JSONArray classic = parentObject.getJSONArray("Classic");
                JSONArray hall_of_fame = parentObject.getJSONArray("Hall of Fame");
                JSONArray naxxramas = parentObject.getJSONArray("Naxxramas");
                JSONArray goblins_vs_gnomes = parentObject.getJSONArray("Goblins vs Gnomes");
                JSONArray blackrock_mountain = parentObject.getJSONArray("Blackrock Mountain");
                JSONArray the_grand_tournament = parentObject.getJSONArray("The Grand Tournament");
                JSONArray the_league_of_explorers = parentObject.getJSONArray("The League of Explorers");
                JSONArray whispers_of_the_old_gods = parentObject.getJSONArray("Whispers of the Old Gods");
                JSONArray one_night_in_karazhan = parentObject.getJSONArray("One Night in Karazhan");
                JSONArray mean_streets_of_gadgetzan = parentObject.getJSONArray("Mean Streets of Gadgetzan");
                JSONArray journey_to_unGoro = parentObject.getJSONArray("Journey to Un'Goro");
                JSONArray knights_of_the_frozen_throne = parentObject.getJSONArray("Knights of the Frozen Throne");
                JSONArray kobolds_and_catacombs = parentObject.getJSONArray("Kobolds & Catacombs");
//                JSONArray tavern_brawl = parentObject.getJSONArray("Tavern Brawl");
                JSONArray hero_skins = parentObject.getJSONArray("Hero Skins");
//                JSONArray missions = parentObject.getJSONArray("Missions");
//                JSONArray credits = parentObject.getJSONArray("Credits");

                List<Card> cardList = new ArrayList<>();
                Gson gson = new Gson();

                // BASIC
                for (int i = 0; i < basic.length(); i++) {
                    JSONObject finalObject = basic.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // CLASSIC
                for (int i = 0; i < classic.length(); i++) {
                    JSONObject finalObject = classic.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // HALL OF FAME
                for (int i = 0; i < hall_of_fame.length(); i++) {
                    JSONObject finalObject = hall_of_fame.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // NAXXRAMAS
                for (int i = 0; i < naxxramas.length(); i++) {
                    JSONObject finalObject = naxxramas.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // GOBLINS VS GNOMES
                for (int i = 0; i < goblins_vs_gnomes.length(); i++) {
                    JSONObject finalObject = goblins_vs_gnomes.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // BLACKROCK MOUNTAIN
                for (int i = 0; i < blackrock_mountain.length(); i++) {
                    JSONObject finalObject = blackrock_mountain.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // THE GRAND TOURNAMENT
                for (int i = 0; i < the_grand_tournament.length(); i++) {
                    JSONObject finalObject = the_grand_tournament.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // THE LEAGUE OF EXPLORERS
                for (int i = 0; i < the_league_of_explorers.length(); i++) {
                    JSONObject finalObject = the_league_of_explorers.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // WHISPERS OF THE OLD GODS
                for (int i = 0; i < whispers_of_the_old_gods.length(); i++) {
                    JSONObject finalObject = whispers_of_the_old_gods.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // ONE NIGHT IN KARAZHAN
                for (int i = 0; i < one_night_in_karazhan.length(); i++) {
                    JSONObject finalObject = one_night_in_karazhan.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // MEAN STREETS OF GADGETZAN
                for (int i = 0; i < mean_streets_of_gadgetzan.length(); i++) {
                    JSONObject finalObject = mean_streets_of_gadgetzan.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // JOURNEY TO UN'GORO
                for (int i = 0; i < journey_to_unGoro.length(); i++) {
                    JSONObject finalObject = journey_to_unGoro.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // KNIGHTS OF THE FROZEN THRONE
                for (int i = 0; i < knights_of_the_frozen_throne.length(); i++) {
                    JSONObject finalObject = knights_of_the_frozen_throne.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

                // KOBOLDS & CATACOMBS
                for (int i = 0; i < kobolds_and_catacombs.length(); i++) {
                    JSONObject finalObject = kobolds_and_catacombs.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

//                // TAVERN BRAWL
//                for (int i = 0; i < tavern_brawl.length(); i++) {
//                    JSONObject finalObject = tavern_brawl.getJSONObject(i);
//                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                    cardList.add(cardModel);
//                }

                // HERO SKINS
                for (int i = 0; i < hero_skins.length(); i++) {
                    JSONObject finalObject = hero_skins.getJSONObject(i);
                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
                }

//                // MISSIONS
//                for (int i = 0; i < missions.length(); i++) {
//                    JSONObject finalObject = missions.getJSONObject(i);
//                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                    cardList.add(cardModel);
//                }
//
//                // CREDITS
//                for (int i = 0; i < credits.length(); i++) {
//                    JSONObject finalObject = credits.getJSONObject(i);
//                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
//                    cardList.add(cardModel);
//                }

//                JSONObject mainObject = new JSONObject(finalJson);
//
//                JSONObject posts = mainObject.getJSONObject("cost");
//
//                Map<String, Card> map = new HashMap<String,Card>();
//                map.put(posts, mainObject);
//
//                ArrayList<String> list = new ArrayList<String>(map.keySet());
//
//                System.out.println(list);



                return cardList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Card> result) {
            super.onPostExecute(result);
            dialog.dismiss();
        }
    }
}