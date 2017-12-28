package pl.pjwstk.pgmd.hearthlounge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.pjwstk.pgmd.hearthlounge.model.Card;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Maciek Dembowski on 19.12.2017.
 */

public class CardsAdapter extends ArrayAdapter {

    AppCompatActivity appCompatActivity;
    private List<Card> cardList;
    private int resource;
    private LayoutInflater inflater;

    public MakeImageToast toast;
    private String iconId;

    public CardsAdapter(Context context, int resource, List<Card> object) {
        super(context, resource, object);
        cardList = object;
        this.resource = resource;
        inflater = (LayoutInflater)appCompatActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public void manaFilter(int manaValue) {

//        CardsJSON.JSONTask chosenMana = new CardsJSON.JSONTask();

        List<Card> temp = new ArrayList<>();
        final List<Card> unmodifiable = Collections.unmodifiableList(cardList);

        if (!CardListCache.getInstance().getCardList(null).containsAll(unmodifiable)) {
            CardListCache.getInstance().addList(unmodifiable);
        }

        if (manaValue == 411) {
            temp = CardListCache.getInstance().getCardList(null);
        } else if (manaValue == 8){
            for(Card cards : CardListCache.getInstance().getCardList(null)) {
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
            for(Card cards : CardListCache.getInstance().getCardList(null)) {
                if (cards.getCost() == manaValue) {
                    temp.add(cards);
                }
            }
        }
//        chosenMana.onPostExecute(temp);
    }

    private PopupWindow initiatePopupWindow() {
        PopupWindow mDropdown = null;
        LayoutInflater mInflater;
        LinearLayout pop_up_mana_menu=null;

        try {

            mInflater = (LayoutInflater) appCompatActivity.getApplicationContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            final View layout = mInflater.inflate(R.layout.popup_mana_menu, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            pop_up_mana_menu = (LinearLayout) appCompatActivity.findViewById(R.id.mana_value);

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
//                Drawable background = getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame);
//                mDropdown.setBackgroundDrawable(background);
//                layout.setBackgroundResource(R.drawable.toast_opacity);


            Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();

            mDropdown.showAtLocation(pop_up_mana_menu, Gravity.RIGHT|Gravity.CENTER_VERTICAL, 10,height);
//                mDropdown.showAtLocation(pop_up_mana_menu, Gravity.CENTER, 0,0);

            final ImageView manaIcon = (ImageView) appCompatActivity.findViewById(R.id.image_view_mana_icon);
            final int toastManaIconColor = Color.rgb(0,0,128);
            final PopupWindow finalMDropdown = mDropdown;
            final CardsJSON cards = new CardsJSON();
            final ImageView mana_0 = (ImageView)layout.findViewById(R.id.zero);

            mana_0.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_0));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_0.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_0, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(0);
//                        setIconId("0");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_1 = (ImageView)layout.findViewById(R.id.one);
            mana_1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_1));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_1.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_1, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(1);
//                        setIconId("1");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_2 = (ImageView)layout.findViewById(R.id.two);
            mana_2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_2));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_2.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_2, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
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
            mana_3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_3));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_3.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_3, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(3);
//                        setIconId("3");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_4 = (ImageView)layout.findViewById(R.id.four);
            mana_4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_4));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_4.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_4, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(4);
//                        setIconId("4");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_5 = (ImageView)layout.findViewById(R.id.five);
            mana_5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_5));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_5.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_5, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(5);
//                        setIconId("5");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_6 = (ImageView)layout.findViewById(R.id.six);
            mana_6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_6));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_6.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_6, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(6);
//                        setIconId("6");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_7 = (ImageView)layout.findViewById(R.id.seven);
            mana_7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_7));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_7.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_7, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(7);
//                        setIconId("7");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView mana_7_plus = (ImageView)layout.findViewById(R.id.seven_plus);
            mana_7_plus.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.mana_7_plus));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        mana_7_plus.setColorFilter(Color.rgb(0,169,156));

                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.mana_7_plus, toastManaIconColor, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(8);
//                        setIconId("7+");
                        finalMDropdown.dismiss();
                        changeIcon();
                        return true;
                    }
                    return false;
                }
            });

            final ImageView all = (ImageView) layout.findViewById(R.id.all);
            all.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        manaIcon.setImageDrawable(appCompatActivity.getResources().getDrawable(R.drawable.all_cards));
                        manaIcon.setColorFilter(getContext().getResources().getColor(R.color.sea_color));
                        all.setColorFilter(Color.rgb(0,169,156));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(getContext(), "You Clicked ", R.drawable.all_cards, Color.WHITE, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        manaFilter(411);
//                        setIconId("ALL");
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
        ImageView manaIcon = (ImageView) appCompatActivity.findViewById(R.id.image_view_mana_icon);
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

        text_view_count_cards = (TextView) appCompatActivity.findViewById(R.id.text_view_count_cards);
        String countCards = String.valueOf(cardList.size());
        text_view_count_cards.setText(countCards + " results");

        changeIcon();

        pop_up_mana_menu = (LinearLayout) appCompatActivity.findViewById(R.id.mana_value);
        pop_up_mana_menu.setOnTouchListener(new View.OnTouchListener() {
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

        Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
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