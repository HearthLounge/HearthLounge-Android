package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class CardsJSON extends DrawerMenu{

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards" + getSuffixLink();
    private final String HEADER = "X-Mashape-Key";
    private final String KEY = "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q";
    private String suffixLink;
    private String iconId;

    private ListView listViewCards;
    private ProgressDialog dialog;

    public CardsJSON(){}

    public String getSuffixLink() {
        return suffixLink;
    }

    public void setSuffixLink(String suffixLink){
        this.suffixLink = suffixLink;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.all_cards, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

//        new DownloadImageTask((ImageButton)findViewById(R.id.image_button_cards))
//                .execute("http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_116.png");

        Intent intent = getIntent();
        String message = intent.getStringExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE");
        setSuffixLink(message);

        String iconId = intent.getStringExtra("pl.pjwstk.pgmd.hearthlounge.IconID");
        setIconId(iconId);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
//        dialog.setProgressStyle(R.drawable.card);
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

                URL url = new URL("https://omgvamp-hearthstone-v1.p.mashape.com/cards" + getSuffixLink());
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

                        view.startAnimation(animationScale);

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

        private PopupWindow initiatePopupWindow() {
            PopupWindow mDropdown = null;
            LayoutInflater mInflater;
            LinearLayout pop_up_mana_menu=null;
            try {

                mInflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = mInflater.inflate(R.layout.popup_mana_menu, null);

                //If you want to add any listeners to your textviews, these are two //textviews.
                pop_up_mana_menu = (LinearLayout) findViewById(R.id.mana_value);

                layout.measure(View.MeasureSpec.UNSPECIFIED,
                        View.MeasureSpec.UNSPECIFIED);
                mDropdown = new PopupWindow(layout,FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,true);
//                Drawable background = getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame);
//                mDropdown.setBackgroundDrawable(background);
//                layout.setBackgroundResource(R.drawable.toast_opacity);
                mDropdown.showAtLocation(pop_up_mana_menu, Gravity.RIGHT|Gravity.BOTTOM, 10,10);

                final ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
                ImageView mana_0 = (ImageView)layout.findViewById(R.id.zero);
                mana_0.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {


                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_0, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=0");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "0");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_1 = (ImageView)layout.findViewById(R.id.one);
                mana_1.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_1, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=1");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "1");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_2 = (ImageView)layout.findViewById(R.id.two);
                mana_2.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_2, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=2");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "2");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_3 = (ImageView)layout.findViewById(R.id.three);
                mana_3.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_3, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=3");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "3");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_4 = (ImageView)layout.findViewById(R.id.four);
                mana_4.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_4, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=4");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "4");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_5 = (ImageView)layout.findViewById(R.id.five);
                mana_5.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_5, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=5");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "5");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_6 = (ImageView)layout.findViewById(R.id.six);
                mana_6.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_6, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=6");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "6");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_7 = (ImageView)layout.findViewById(R.id.seven);
                mana_7.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_7, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=7");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "7");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_7_plus = (ImageView)layout.findViewById(R.id.seven_plus);
                mana_7_plus.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_7_plus, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=7-9");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "7+");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_8 = (ImageView)layout.findViewById(R.id.eight);
                mana_8.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_8, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=8");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "8");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_9 = (ImageView)layout.findViewById(R.id.nine);
                mana_9.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_9, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class); //Do którego ma iść
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost=9");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "9");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

                ImageView mana_9_plus = (ImageView) layout.findViewById(R.id.nine_plus);
                mana_9_plus.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_9_plus, Toast.LENGTH_SHORT).show(); // + item.getTitle()

                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class);
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?cost>=9");
                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "9+");
                            startActivity(startIntent);

                            return true;
                        }
                        return false;
                    }
                });

//                ImageView all = (ImageView) layout.findViewById(R.id.cos);
//                all.setOnTouchListener(new OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
//                        manaIcon.setColorFilter(Color.rgb(0,0,128), PorterDuff.Mode.SRC_IN);
//
//                        int action = event.getAction();
//                        if (action == MotionEvent.ACTION_DOWN) {
//                            manaIcon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
//
//                            return true;
//                        } else if (action == MotionEvent.ACTION_UP) {
//                            makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_9_plus, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//
//                            Intent startIntent = new Intent(getApplicationContext(), CardsJSON.class);
//                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.MESSAGE", "?collectible=1");
//                            startIntent.putExtra("pl.pjwstk.pgmd.hearthlounge.IconID", "ALL");
//                            startActivity(startIntent);
//
//                            return true;
//                        }
//                        return false;
//                    }
//                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mDropdown;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            TextView text_view_count_cards = null;
            LinearLayout pop_up_mana_menu = null;

            text_view_count_cards = (TextView)findViewById(R.id.text_view_count_cards);
            String countCards = String.valueOf(cardList.size());
            text_view_count_cards.setText(countCards + " results");

            ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
            if(getIconId().equals("0")) {
                manaIcon.setImageResource(R.drawable.mana_0);
            } else if (getIconId().equals("1")) {
                manaIcon.setImageResource(R.drawable.mana_1);
            } else if (getIconId().equals("2")) {
                manaIcon.setImageResource(R.drawable.mana_2);
            } else if (getIconId().equals("3")) {
                manaIcon.setImageResource(R.drawable.mana_3);
            } else if (getIconId().equals("4")) {
                manaIcon.setImageResource(R.drawable.mana_4);
            } else if (getIconId().equals("5")) {
                manaIcon.setImageResource(R.drawable.mana_5);
            } else if (getIconId().equals("6")) {
                manaIcon.setImageResource(R.drawable.mana_6);
            } else if (getIconId().equals("7")) {
                manaIcon.setImageResource(R.drawable.mana_7);
            } else if (getIconId().equals("7+")) {
                manaIcon.setImageResource(R.drawable.mana_7_plus);
            } else if (getIconId().equals("8")) {
                manaIcon.setImageResource(R.drawable.mana_8);
            } else if (getIconId().equals("9")) {
                manaIcon.setImageResource(R.drawable.mana_9);
            } else if (getIconId().equals("9+")) {
                manaIcon.setImageResource(R.drawable.mana_9_plus);
            } else if (getIconId().equals("ALL")) {
                    manaIcon.setImageResource(R.drawable.hs_logo);
            }
            manaIcon.setColorFilter(Color.rgb(0, 0, 128), PorterDuff.Mode.SRC_IN);

            pop_up_mana_menu = (LinearLayout) findViewById(R.id.mana_value);
            pop_up_mana_menu.setOnTouchListener(new OnTouchListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public boolean onTouch(View v, MotionEvent motionEvent) {

                    int action = motionEvent.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundResource(R.drawable.pressed);
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        v.animate().cancel();
                        v.setBackgroundResource(R.drawable.normal);

                        initiatePopupWindow();

//                        Context wrapper = new ContextThemeWrapper(CardsJSON.this, R.style.PopupMenu);
//                        PopupWindow popupWindow = new PopupWindow();
//                        popupWindow.setWidth(350);
//
//                        PopupMenu popup = new PopupMenu(CardsJSON.this, v);
//                        popup.getMenuInflater().inflate(R.menu.mana_value, popup.getMenu());
//
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            public boolean onMenuItemClick(MenuItem item) {
//                                ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
//                                manaIcon.setColorFilter(Color.rgb(0,0,128), PorterDuff.Mode.SRC_IN);
//
//                                switch (item.getItemId())
//                                {
//                                    case R.id.zero:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_0, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_0);
//
//                                        return true;
//
//                                    case R.id.one:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_1, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_1);
//
//                                        return true;
//
//                                    case R.id.two:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_2, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_2);
//
//                                        return true;
//
//                                    case R.id.three:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_3, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_3);
//
//                                        return true;
//
//                                    case R.id.four:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_4, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_4);
//
//                                        return true;
//
//                                    case R.id.five:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_5, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_5);
//
//                                        return true;
//
//                                    case R.id.six:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_6, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_6);
//
//                                        return true;
//
//                                    case R.id.seven:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_7, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_7);
//
//                                        return true;
//
//                                    case R.id.seven_plus:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_7_plus, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_7_plus);
//
//                                        return true;
//
//                                    case R.id.eight:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_8, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_8);
//
//                                        return true;
//
//                                    case R.id.nine:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_9, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_9);
//
//                                        return true;
//
//                                    case R.id.nine_plus:
//                                        makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_9_plus, Toast.LENGTH_SHORT).show(); // + item.getTitle()
//                                        manaIcon.setImageResource(R.drawable.mana_9_plus);
//
//                                        return true;
//
//                                    default:
//                                    return false;
//                                }
//                            }
//                        });
//                        //Drawable yourdrawable = item.getItem(0).getIcon(); // change 0 with 1,2 ...
//                        MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), v);
//
//                        //yourdrawable.mutate();
//                        //yourdrawable.setColorFilter(getResources().getColor(R.color.mana_navy), PorterDuff.Mode.SRC_IN);
//
//
//                        menuHelper.setForceShowIcon(true);
//                        menuHelper.show(); //showing popup menu
                    }
                    return false;
                }
            });

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

    @SuppressLint("ResourceAsColor")
    public static Toast makeImageToast(Context context, CharSequence text, int imageResId, int length) {
        Toast toast = Toast.makeText(context, text, length);

        View rootView = toast.getView();
        LinearLayout linearLayout = null;
        View messageTextView = null;



        // check (expected) toast layout
        if (rootView instanceof LinearLayout) {
            linearLayout = (LinearLayout) rootView;

            if (linearLayout.getChildCount() == 1) {
                View child = linearLayout.getChildAt(0);

                if (child instanceof TextView) {
                    messageTextView = (TextView) child;
                }
            }
        }

        // cancel modification because toast layout is not what we expected
        if (linearLayout == null || messageTextView == null) {
            return toast;
        }

        ViewGroup.LayoutParams textParams = messageTextView.getLayoutParams();
        ((LinearLayout.LayoutParams) textParams).gravity = Gravity.CENTER_VERTICAL;

        // convert dip dimension
        float density = context.getResources().getDisplayMetrics().density;
        int imageSize = (int) (density * 25 + 1f);
        int imageMargin = (int) (density * 15 + 0.5f);

        // setup image view layout parameters
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageSize, imageSize);
        imageParams.setMargins(0, 0, imageMargin, 0);
        imageParams.gravity = Gravity.CENTER_VERTICAL;

        // setup image view
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageResId);
        imageView.setLayoutParams(imageParams);
        imageView.setColorFilter(Color.rgb(0,0,128), PorterDuff.Mode.SRC_IN);

        // modify root layout ZMIANA LOKOALIZACJI IKONY 0 - PRZED NAPISEM
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(imageView, 1);
        linearLayout.setBackgroundResource(R.drawable.toast_opacity);

        return toast;
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