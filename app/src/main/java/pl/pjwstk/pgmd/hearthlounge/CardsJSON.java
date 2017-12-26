package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.text.Layout;
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
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

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

import pl.pjwstk.pgmd.hearthlounge.authentication.UserService;
import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;

/**
 * Created by Maciek Dembowski on 16.10.2017.
 */

public class CardsJSON extends DrawerMenu {

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";
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

//        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.all_cards, frameLayout);


//        new DownloadImageTask((ImageButton)findViewById(R.id.image_button_cards))
//                .execute("http://media.services.zam.com/v1/media/byName/hs/cards/enus/EX1_116.png");

//        Intent i = new Intent(getApplicationContext(), CardService.class);
//        startService(i);

        Intent intent = getIntent();
        String message = intent.getStringExtra("PlayerClass");
        setSuffixLink(message);

        String iconId = intent.getStringExtra("pl.pjwstk.pgmd.hearthlounge.IconID");
        setIconId("0");//setIconId(iconId);

        dialog = new ProgressDialog(this);
//        View customTitleView = getLayoutInflater().inflate(R.layout.popup_search_menu, null);
//        dialog.setCustomTitle(customTitleView);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
//        dialog.setProgressStyle(1);
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
        new JSONTask().execute(URL);
    }

    public class JSONTask extends AsyncTask<String,String,List<Card>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<Card> doInBackground(String... params) {

            return CardListCache.getInstance().getPrimaryCardList();
        }

//        protected void onProgressUpdate(Integer... progress) {
//            dialog.setProgress(progress[0]);
//        }

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

        public void manaFilter(int manaValue, String value) {

            JSONTask chosenMana = new JSONTask();

            List<Card> temp = new ArrayList<>();
            final List<Card> unmodifiable = Collections.unmodifiableList(cardList);

            if (!CardListCache.getInstance().getList().containsAll(unmodifiable)) {
                CardListCache.getInstance().addList(unmodifiable);
            }

//            value = getSuffixLink();

            if (manaValue == 411) {
                temp = CardListCache.getInstance().getList();
            } else if (manaValue == 8){
                for(Card cards : CardListCache.getInstance().getList()) {
                    if (cards.getCost() >= manaValue-1) {
                        temp.add(cards);
                    }
                }
            }
//            else if (value == "Mage"){
//                for(Card cards : CardListCache.getInstance().getList()) {
//                    if (cards.getPlayerClass().equals(value)) {
//                        Collections.sort(temp, new Comparator<Card>() {
//                            public int compare(Card a, Card b) {
//                                return Integer.compare(a.getCost(), b.getCost()); // return a.cos - b.cos
//                            }
//                        });
//                        temp.add(cards);
//                    }
//                }
//            }
            else {
                for(Card cards : CardListCache.getInstance().getList()) {
                    if (cards.getCost() == manaValue) {
                        temp.add(cards);
                    }
                }
            }

            Collections.sort(temp, new Comparator<Card>() {
                public int compare(Card a, Card b) {
                    return Integer.compare(a.getCost(), b.getCost()); // return a.cos - b.cos
                }
            });
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
                            manaFilter(0, null);
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
                            manaFilter(1, null);
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
                            manaFilter(2, null);
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
                            manaFilter(3, null);
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
                            manaFilter(4, null);
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
                            manaFilter(5, null);
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
                            manaFilter(6, null);
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
                            manaFilter(7, null);
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
                            manaFilter(8, null);
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
                            manaFilter(411, null);
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

//                for(Card card : CardListCache.getInstance().getPrimaryCardList()) {
//                    new DownloadImageTask((ImageView) convertView.findViewById(R.id.image_viewCard))
//                            .execute(card.getImg());
//                }

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

            ImageLoader imageLoader = ImageLoader.getInstance();
//            imageLoader.getDiskCache();
            imageLoader.displayImage(cardList.get(position).getImg(), holder.image_view_card, new ImageLoadingListener() {
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

//            boolean pauseOnScroll = false; // or true
//            boolean pauseOnFling = true; // or false
//            PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
//            listViewCards.setOnScrollListener(listener);

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
            new JSONTask().execute(URL); // JSON.

//            Intent i = new Intent(getApplicationContext(),CardsJSON.class);
//            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView imageView;
//
//        public DownloadImageTask(ImageView imageView) {
//            this.imageView = imageView;
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
//            imageView.setImageBitmap(result);
//        }
//    }
}