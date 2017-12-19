package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Display;
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
import android.widget.FrameLayout;
import android.widget.GridView;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;

/**
 * Created by Maciek Dembowski on 16.10.2017.
 */

public class CardsJSON extends JSON {

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards" + getSuffixLink();
    private final String HEADER = "X-Mashape-Key";
    private final String KEY = "T15rGIqg2lmshwDGMsX3mZeWM7vBp1ZmfvVjsnFba6SXP2WK5Q";
    private String suffixLink;
    private String iconId;

    public MakeImageToast toast;

    private GridView listViewCards;
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

//        listViewCards = (ListView)findViewById(R.id.list_view_cards);
        listViewCards = (GridView) findViewById(R.id.list_view_cards);

        // To start fetching the data when app start, uncomment below line to start the async task.
        new JSONTask().execute(URL + HEADER + KEY);
    }

    public class JSONTask extends AsyncTask<String, String, List<Card> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

//        @Override
//        protected List<Card> doInBackground(String... params) {
//            super.doInBackground()
//            return CardListCache.getInstance().getList();
//        }


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

        //final Animation animationScale = AnimationUtils.loadAnimation(CardsJSON.this, R.anim.anim_scale);
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
                        //view.startAnimation(animationScale);

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

        public void manaFilter(int manaValue) {

            JSONTask chosenMana = new JSONTask();

            List<Card> temp = new ArrayList<>();
            final List<Card> unmodifiable = Collections.unmodifiableList(cardList);

            if (!CardListCache.getInstance().getList().containsAll(unmodifiable)) {
                CardListCache.getInstance().addList(unmodifiable);
            }

            if (manaValue == 411) {
                temp = CardListCache.getInstance().getList();
            } else if (manaValue == 8){
                for(Card cards : CardListCache.getInstance().getList()) {
                    if (cards.getCost() >= manaValue-1) {
                        Collections.sort(temp, new Comparator<Card>() {
                            public int compare(Card a, Card b) {
                                return Integer.compare(a.getCost(), b.getCost());
                            }
                        });
                        temp.add(cards);
                    }
                }
            } else {
                for(Card cards : CardListCache.getInstance().getList()) {
                    if (cards.getCost() == manaValue) {
                        temp.add(cards);
                    }
                }
            }
            chosenMana.onPostExecute(temp);
        }

        private PopupWindow initiatePopupWindow() {
            PopupWindow mDropdown = null;
            LayoutInflater mInflater;
            LinearLayout pop_up_mana_menu=null;

            try {

                mInflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = mInflater.inflate(R.layout.popup_mana_menu, null);

                //If you want to add any listeners to your textviews, these are two //textviews.
                pop_up_mana_menu = (LinearLayout) findViewById(R.id.mana_value);

                layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                mDropdown = new PopupWindow(layout,FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,true);
//                Drawable background = getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame);
//                mDropdown.setBackgroundDrawable(background);
//                layout.setBackgroundResource(R.drawable.toast_opacity);


                Display display = getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();

                mDropdown.showAtLocation(pop_up_mana_menu, Gravity.RIGHT|Gravity.CENTER_VERTICAL, 10,height);
//                mDropdown.showAtLocation(pop_up_mana_menu, Gravity.CENTER, 0,0);

                final ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
                final int toastManaIconColor = Color.rgb(0,0,128);
                final PopupWindow finalMDropdown = mDropdown;
                final CardsJSON cards = new CardsJSON();
                final ImageView mana_0 = (ImageView)layout.findViewById(R.id.zero);

                mana_0.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_0));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_0.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_0, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(0);
                            setIconId("0");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_1 = (ImageView)layout.findViewById(R.id.one);
                mana_1.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_1));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_1.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_1, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(1);
                            setIconId("1");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_2 = (ImageView)layout.findViewById(R.id.two);
                mana_2.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_2));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_2.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_2, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(2);
                            setIconId("2");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_3 = (ImageView)layout.findViewById(R.id.three);
                mana_3.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_3));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_3.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_3, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(3);
                            setIconId("3");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_4 = (ImageView)layout.findViewById(R.id.four);
                mana_4.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_4));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_4.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_4, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(4);
                            setIconId("4");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_5 = (ImageView)layout.findViewById(R.id.five);
                mana_5.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_5));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_5.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_5, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(5);
                            setIconId("5");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_6 = (ImageView)layout.findViewById(R.id.six);
                mana_6.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_6));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_6.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_6, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(6);
                            setIconId("6");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_7 = (ImageView)layout.findViewById(R.id.seven);
                mana_7.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_7));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_7.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_7, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(7);
                            setIconId("7");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView mana_7_plus = (ImageView)layout.findViewById(R.id.seven_plus);
                mana_7_plus.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.mana_7_plus));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            mana_7_plus.setColorFilter(Color.rgb(0,169,156));

                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.mana_7_plus, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(8);
                            setIconId("7+");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });

                final ImageView all = (ImageView) layout.findViewById(R.id.all);
                all.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(R.drawable.all_cards));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            all.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(CardsJSON.this, "You Clicked ", R.drawable.all_cards, Color.WHITE, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(411);
                            setIconId("ALL");
                            finalMDropdown.dismiss();
                            changeIcon();
                            return true;
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mDropdown;
        }

        public void changeIcon(){
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
            } else if (getIconId().equals("ALL")) {
                manaIcon.setImageResource(R.drawable.all_cards);
            }
            //manaIcon.setColorFilter(Color.rgb(0, 0, 128), PorterDuff.Mode.SRC_IN);
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

            changeIcon();

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
                        return true;
                    } else if (action == MotionEvent.ACTION_CANCEL) {
                        v.setBackgroundResource(R.drawable.normal);
                        return true;
                    }
                    return false;
                }
            });

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.image_view_card = (ImageView)convertView.findViewById(R.id.image_viewCard);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

            final ViewHolder finalHolder = holder;

            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth()/3;
            int height = display.getHeight()/3;
            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,height);
            parms.gravity=Gravity.CENTER;
//            if (display.getMetrics()) {
//
//            }
            parms.topMargin = -height/8;
            parms.bottomMargin = -height/11;
            holder.image_view_card.setLayoutParams(parms);

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

            return convertView;
        }

        class ViewHolder{
            private ImageView image_view_card;

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