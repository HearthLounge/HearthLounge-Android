package pl.pjwstk.pgmd.hearthlounge.cards;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;
import pl.pjwstk.pgmd.hearthlounge.view.TypefaceSpan;

/**
 * Created by Maciek Dembowski on 16.10.2017.
 */

public class Cards extends DrawerMenu {

    public MakeImageToast toast;

    private final String URL = "https://omgvamp-hearthstone-v1.p.mashape.com/cards?collectible=1";

    private String stringValue = "";
    private String titleName = "";
    private String iconId = "";
    private int drawable;
    private int color;

    private GridView listViewCards;
    private ProgressDialog dialog;

    public Cards(){}

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
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
        getLayoutInflater().inflate(R.layout.all_cards, frameLayout);

        Intent intent = getIntent();
        String value = intent.getStringExtra("StringValue");
        setStringValue(value);
        String title = intent.getStringExtra("Title");
        setTitleName(title);
        String iconId = intent.getStringExtra("IconID");
        setIconId(iconId);
        setDrawable(getIntent().getExtras().getInt("drawable"));
        setColor(getIntent().getExtras().getInt("color"));
        SpannableString s = new SpannableString(title + " Cards");
        s.setSpan(new TypefaceSpan(this, "belwe_medium.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

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

        listViewCards = (GridView) findViewById(R.id.list_view_cards);

        // To start fetching the data when app start, uncomment below line to start the async task.
        new JSONTask().execute(URL);
    }

    public class JSONTask extends AsyncTask<String,String,List<Card>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog.show();
        }

        @Override
        protected List<Card> doInBackground(String... params) {
            return CardListCache.getInstance().getCardList(getStringValue());
        }

        @Override
        protected void onPostExecute(final List<Card> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result != null) {
                TextView text_view_count_cards = (TextView)findViewById(R.id.text_view_count_cards);
                int countCards = result.size();
                text_view_count_cards.setText(countCards + " results");

                final CardsAdapter adapter = new CardsAdapter(getApplicationContext(), R.layout.row, result);
                listViewCards.setAdapter(adapter);
                listViewCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Card cardModel = result.get(position); // getting the model
                        Intent intent = new Intent(Cards.this, SelectedCard.class);
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

            List<Card> temp = new LinkedList<>();
            final List<Card> unmodifiable = Collections.unmodifiableList(cardList);

            if (!CardListCache.getInstance().getCardList(getStringValue()).containsAll(unmodifiable)) {
                CardListCache.getInstance().addList(unmodifiable);
            }

            if (manaValue == 411) {
                temp = CardListCache.getInstance().getCardList(getStringValue());
            } else if (manaValue == 8){
                for(Card cards : CardListCache.getInstance().getCardList(getStringValue())) {
                    if (cards.getCost() >= manaValue-1) {
                        temp.add(cards);
                    }
                }
            } else {
                for(Card cards : CardListCache.getInstance().getCardList(getStringValue())) {
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

                final ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
                final int toastManaIconColor = Color.rgb(0,0,128);
                final PopupWindow finalMDropdown = mDropdown;

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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_0, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_1, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_2, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_3, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_4, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_5, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_6, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_7, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                            toast.makeImageToast(Cards.this, "You Clicked ", R.drawable.mana_7_plus, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.mana_navy));
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
                all.setImageResource(getDrawable());
                all.setColorFilter(getContext().getResources().getColor(getColor()));
                all.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getAction();
                        if (action == MotionEvent.ACTION_DOWN) {
                            manaIcon.setImageDrawable(getResources().getDrawable(getDrawable()));
                            manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                            all.setColorFilter(Color.rgb(0,169,156));
                            return true;
                        } else if (action == MotionEvent.ACTION_UP) {
                            toast.makeImageToast(Cards.this, "You Clicked ", getDrawable(), getContext().getResources().getColor(getColor()), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                            manaFilter(411, null);
                            if (getStringValue() == null) {
                                setIconId("All");
                            } else {
                                setIconId(getStringValue());
                            }
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

        public void changeIcon() {
            ImageView manaIcon = (ImageView) findViewById(R.id.image_view_mana_icon);
            if (getIconId().equals("0")) {                               // MANA ICON
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
            } else if (getIconId().equals("All")) {
                manaIcon.setImageResource(R.drawable.all_cards);
            } else if (getIconId().equals(getIconId())) {  // CLASS/EXPANSIONS/ADVENTURES ICON/
                manaIcon.setImageResource(getDrawable());
                manaIcon.setColorFilter(getContext().getResources().getColor(getColor()));
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            LinearLayout pop_up_mana_menu = null;

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
            parms.topMargin = -height/8;
            parms.bottomMargin = -height/11;
            holder.image_view_card.setLayoutParams(parms);

            ImageLoader imageLoader = ImageLoader.getInstance();
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
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new JSONTask().execute(URL);
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