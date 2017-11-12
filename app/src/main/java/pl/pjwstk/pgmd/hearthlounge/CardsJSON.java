package pl.pjwstk.pgmd.hearthlounge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";
    private final String HEADER = "X-Mashape-Key";
    private final String KEY = "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q";

    private ListView listViewCards;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);
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
                //JSONArray parentArray = parentObject.getJSONArray("cards");
                JSONArray parentArray = parentObject.getJSONArray("Basic"); //Kobolds & Catacombs

                List<Card> cardList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    Card cardModel = gson.fromJson(finalObject.toString(), Card.class);
                    cardList.add(cardModel);
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

////        buttonDecks = (ImageButton) findViewById(R.id.button_decks);
////        buttonDecks.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent motionEvent) {
//
//                int action = motionEvent.getAction();
//                if (action == MotionEvent.ACTION_DOWN) {
//                    v.animate().scaleXBy(0.2f).setDuration(5000).start();
//                    v.animate().scaleYBy(0.2f).setDuration(5000).start();
//                    v.setBackgroundResource(R.drawable.pressed);
//                    //v.startAnimation(animationScale); // druga metoda
//                    return true;
//                } else if (action == MotionEvent.ACTION_UP) {
//                    v.animate().cancel();
//                    v.animate().scaleX(1f).setDuration(1000).start();
//                    v.animate().scaleY(1f).setDuration(1000).start();
//                    v.setBackgroundResource(R.drawable.normal);
////                    Intent startIntent = new Intent(getApplicationContext(),CardsJSON.class); //Do którego ma iść
////                    startActivity(startIntent);
//                    return true;
//                }
//                return false;
//            }
////        });

        final Animation animationScale = AnimationUtils.loadAnimation(CardsJSON.this, R.anim.anim_scale);
        @Override
        protected void onPostExecute(final List<Card> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result != null) {
                final CardsAdapter adapter = new CardsAdapter(getApplicationContext(), R.layout.row, result);
                listViewCards.setAdapter(adapter);
                listViewCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                        view.animate().scaleXBy(0.2f).setDuration(5000).start();
//                        view.animate().scaleYBy(0.2f).setDuration(5000).start();
//                        view.setBackgroundResource(R.drawable.pressed);
                        view.startAnimation(animationScale); // druga metoda

                        Card cardModel = result.get(position); // getting the model
                        Intent intent = new Intent(CardsJSON.this, SelectedCard.class);
                        intent.putExtra("cardModel", new Gson().toJson(cardModel)); // converting model json into string type and sending it via intent
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
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
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.image_view_card = (ImageView)convertView.findViewById(R.id.image_viewCard);
                //holder.image_view_cardGold = (ImageView)convertView.findViewById(R.id.image_viewCardGold);
//                holder.text_view_cardId = (TextView)convertView.findViewById(R.id.text_view_cardId);
//                holder.text_view_dbfId = (TextView)convertView.findViewById(R.id.text_view_dbfId);
//                holder.text_view_name = (TextView)convertView.findViewById(R.id.text_view_name);
//                holder.text_view_cardSet = (TextView)convertView.findViewById(R.id.text_view_cardSet);
//                holder.text_view_type = (TextView)convertView.findViewById(R.id.text_view_type);
//                holder.text_view_faction = (TextView)convertView.findViewById(R.id.text_view_faction);
//                holder.text_view_rarity = (TextView)convertView.findViewById(R.id.text_view_rarity);
//                holder.text_view_cost = (TextView)convertView.findViewById(R.id.text_view_cost);
//                holder.text_view_attack = (TextView)convertView.findViewById(R.id.text_view_attack);
//                holder.text_view_health = (TextView)convertView.findViewById(R.id.text_view_health);
//                holder.text_view_text = (TextView)convertView.findViewById(R.id.text_view_text);
//                holder.text_view_flavor = (TextView)convertView.findViewById(R.id.text_view_flavor);
//                holder.text_view_artist = (TextView)convertView.findViewById(R.id.text_view_artist);
//                holder.text_view_collectible = (TextView)convertView.findViewById(R.id.text_view_collectible);
//                holder.text_view_elite = (TextView)convertView.findViewById(R.id.text_view_elite);
//                holder.text_view_playerClass = (TextView)convertView.findViewById(R.id.text_view_playerClass);
//                holder.text_view_locale = (TextView)convertView.findViewById(R.id.text_view_locale);
//                holder.text_view_mechanics = (TextView)convertView.findViewById(R.id.text_view_mechanics);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            final ViewHolder finalHolder = holder;
            ImageLoader.getInstance().displayImage(cardList.get(position).getImg(), holder.image_view_card, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    finalHolder.image_view_card.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                    finalHolder.image_view_card.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                    finalHolder.image_view_card.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                    finalHolder.image_view_card.setVisibility(View.INVISIBLE);
                }
            });

//                    holder.text_view_cardId.setText(cardList.get(position).getCardId());
//                    holder.text_view_dbfId.setText(cardList.get(position).getDbfId());
//                    holder.text_view_name.setText(cardList.get(position).getName());
//                    holder.text_view_cardSet.setText(cardList.get(position).getCardSet());
//                    holder.text_view_type.setText(cardList.get(position).getType());
//                    holder.text_view_faction.setText(cardList.get(position).getFaction());
//                    holder.text_view_rarity.setText(cardList.get(position).getRarity());
////                    holder.text_view_cost.setText(cardList.get(position).getCost()); // z tymi jest problem
//                    //holder.text_view_attack.setText(cardList.get(position).getAttack()); // z tymi jest problem
//                    //holder.text_view_health.setText(cardList.get(position).getHealth()); // z tymi jest problem
//                    holder.text_view_text.setText(cardList.get(position).getText());
//                    holder.text_view_flavor.setText(cardList.get(position).getFlavor());
//                    holder.text_view_artist.setText(cardList.get(position).getArtist());
//                    //holder.text_view_collectible.setEnabled(cardList.get(position).getCollectible()); // z tymi jest problem
//                    //holder.text_view_elite.setEnabled(cardList.get(position).getElite()); // z tymi jest problem
//                    holder.text_view_playerClass.setText(cardList.get(position).getPlayerClass());
//                    holder.text_view_locale.setText(cardList.get(position).getLocale());

//            StringBuffer stringBuffer = new StringBuffer();
//            for(Card.Mechanics mechanics : cardList.get(position).getMechanicsList()) {
//                stringBuffer.append(mechanics.getMechanics() + ", ");
//            }
//           holder.text_view_mechanics.setText("Mechanics: " + stringBuffer);

            return convertView;
        }

        class ViewHolder{
            private ImageView image_view_card;
//            private TextView text_view_cardId;
//            private TextView text_view_dbfId;
//            private TextView text_view_name;
//            private TextView text_view_cardSet;
//            private TextView text_view_type;
//            private TextView text_view_faction;
//            private TextView text_view_rarity;
//            private TextView text_view_cost;
//            private TextView text_view_attack;
//            private TextView text_view_health;
//            private TextView text_view_text;
//            private TextView text_view_flavor;
//            private TextView text_view_artist;
//            private TextView text_view_collectible;
//            private TextView text_view_elite;
//            private TextView text_view_playerClass;
//            private TextView text_view_locale;
//            private TextView text_view_mechanics;
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
            new JSONTask().execute(URL + HEADER + KEY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageButton bmImage;
//
//        public DownloadImageTask(ImageButton bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
}