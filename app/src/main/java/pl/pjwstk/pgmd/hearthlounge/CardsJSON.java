package pl.pjwstk.pgmd.hearthlounge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

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

/**
 * Created by Maciek Dembowski on 16.10.2017.
 */

public class CardsJSON extends AppCompatActivity{

    private ImageButton buttonCards;
    private ListView listViewCards;
    private Button button;
    private TextView textCards;
    private String obelga = "Magda cię dorwie w sądzie";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);


        new DownloadImageTask((ImageButton)findViewById(R.id.image_button_cards))
                .execute("http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_116.png");

        // TUTAJ WSZYSTKO SPRAWDZIC
        buttonCards = (ImageButton)findViewById(R.id.image_button_cards);
        buttonCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(),CardsAdapter.class); //Do którego ma iść
                startActivity(startIntent);
                new JSONTask().execute("https://omgvamp-hearthstone-v1.p.mashape.com/cards","T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q");
            }
        });

        // listViewCards = (ListView)findViewById(R.id.list_view_cards);
                //new JSONTask().execute("https://omgvamp-hearthstone-v1.p.mashape.com/cards","T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q");

        //button = (Button)findViewById(R.id.button_cards);
        //textCards = (TextView)findViewById(R.id.text_view_cards);
        //listViewCards = (ListView)findViewById(R.id.list_view_cards);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://omgvamp-hearthstone-v1.p.mashape.com/cards",
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    JSONArray jsonArray = response.getJSONArray("T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q");
//
//                                    List<Card> cardList = new ArrayList<>();
//                                    for (int i = 0; i<jsonArray.length(); i++) {
//                                        JSONObject finalObject = jsonArray.getJSONObject(i);
//
//                                        Card cardModel = new Card();
//
//                                        cardModel.setCardId(finalObject.getString("cardId"));
//                                        cardModel.setDbfId(finalObject.getString("dbfId"));
//                                        cardModel.setName(finalObject.getString("name"));
//                                        cardModel.setCardSet(finalObject.getString("cardSet"));
//                                        cardModel.setType(finalObject.getString("type"));
//                                        cardModel.setFaction(finalObject.getString("faction"));
//                                        cardModel.setRarity(finalObject.getString("rarity"));
//                                        cardModel.setCost(finalObject.getInt("cost"));
//                                        cardModel.setAttack(finalObject.getInt("attack"));
//                                        cardModel.setHealth(finalObject.getInt("health"));
//                                        cardModel.setText(finalObject.getString("text"));
//                                        cardModel.setFlavor(finalObject.getString("flavor"));
//                                        cardModel.setArtist(finalObject.getString("artist"));
//                                        cardModel.setCollectible(finalObject.getBoolean("collectible"));
//                                        cardModel.setElite(finalObject.getBoolean("elite"));
//                                        cardModel.setPlayerClass(finalObject.getString("playerClass"));
//                                        cardModel.setImg(finalObject.getString("img"));
//                                        cardModel.setImgGold(finalObject.getString("imgGold"));
//                                        cardModel.setLocale(finalObject.getString("locale"));
//                                        //ArrayList<String> mechanisc = new ArrayList<String>();
//                                        //cardModel.setMechanics(finalObject.getJSONArray("mechanics"));
//
//                                        List<Card.Mechanics> mechanicsList = new ArrayList<>();
//                                        for (int j = 0; j < finalObject.getJSONArray("mechanics").length(); j++) {
//                                            Card.Mechanics mechanics = new Card.Mechanics();
//                                            mechanics.setMechanics(finalObject.getJSONArray("mechanics").getJSONObject(j).getString("name"));
//                                            mechanicsList.add(mechanics);
//                                        }
//                                        cardModel.setMechanics(mechanicsList);
//                                        cardList.add(cardModel);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("VOLLEY", "ERROR");
//                            }
//                        }
//
//
//                        );
//                requestQueue.add(jsonObjectRequest);
//            }
//        });


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageButton bmImage;

        public DownloadImageTask(ImageButton bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    public static class JSONTask extends AsyncTask<String, String, List<Card>> {

        @Override
        protected List<Card> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
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
                JSONArray parentArray = parentObject.getJSONArray("cards");// T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q

                List<Card> cardList = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Card card = new Card();

                    card.setCardId(finalObject.getString("cardId"));
                    card.setDbfId(finalObject.getString("dbfId"));
                    card.setName(finalObject.getString("name"));
                    card.setCardSet(finalObject.getString("cardSet"));
                    card.setType(finalObject.getString("type"));
                    card.setFaction(finalObject.getString("faction"));
                    card.setRarity(finalObject.getString("rarity"));
                    card.setCost(finalObject.getInt("cost"));
                    card.setAttack(finalObject.getInt("attack"));
                    card.setHealth(finalObject.getInt("health"));
                    card.setText(finalObject.getString("text"));
                    card.setFlavor(finalObject.getString("flavor"));
                    card.setArtist(finalObject.getString("artist"));
                    card.setCollectible(finalObject.getBoolean("collectible"));
                    card.setElite(finalObject.getBoolean("elite"));
                    card.setPlayerClass(finalObject.getString("playerClass"));
                    card.setImg(finalObject.getString("img"));
                    card.setImgGold(finalObject.getString("imgGold"));
                    card.setLocale(finalObject.getString("locale"));

                    List<Card.Mechanics> mechanicsList = new ArrayList<>();
                    for (int j = 0; j < finalObject.getJSONArray("mechanics").length(); j++) {
                        Card.Mechanics mechanics = new Card.Mechanics();
                        mechanics.setMechanics(finalObject.getJSONArray("mechanics").getJSONObject(j).getString("name"));
                        mechanicsList.add(mechanics);
                    }
                    card.setMechanicsList(mechanicsList);
                    cardList.add(card);

                }
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
        protected void onPostExecute(List<Card> result) {
            super.onPostExecute(result);

            //textCards.setText(result);
        }
    }

    public class CardsAdapter extends ArrayAdapter {

        private List<Card> cardList;
        private int resource;
        private LayoutInflater inflater;

        public CardsAdapter(Context context, int resource, List<Card> object) {
            super(context, resource, object);
            cardList = object;
            this.resource = resource;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.selected_card, null);
            }

            ImageView image_view_card;
            TextView text_view_cardId;
            TextView text_view_dbfId;
            TextView text_view_name;
            TextView text_view_cardSet;
            TextView text_view_type;
            TextView text_view_faction;
            TextView text_view_rarity;
            TextView text_view_cost;
            TextView text_view_attack;
            TextView text_view_health;
            TextView text_view_text;
            TextView text_view_flavor;
            TextView text_view_artist;
            TextView text_view_collectible;
            TextView text_view_elite;
            TextView text_view_playerClass;
            TextView text_view_locale;

            return convertView;
            //super.getView(position, convertView, parent);
        }
    }

    // MENU U GÓRY NA PASKU TE TRZY KROPKI :D
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new JSONTask().execute("https://omgvamp-hearthstone-v1.p.mashape.com/cards","T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


//    HttpResponse<JsonNode> response = Unirest.get("https://omgvamp-hearthstone-v1.p.mashape.com/cards")
//            .header("X-Mashape-Key", "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q")
//            .asJson();
