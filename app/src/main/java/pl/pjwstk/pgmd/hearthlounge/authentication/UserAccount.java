package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;
import pl.pjwstk.pgmd.hearthlounge.view.CircleTransform;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;

public class UserAccount extends DrawerMenu{

    private UserPreferences userPref;
    private User user;

    public SharedPreferences.Editor userPrefs;
    public MakeImageToast toast;
    private static final String urlProfileImg = "https://cdn.pixabay.com/photo/2016/12/13/16/17/dancer-1904467_1280.png";

    TextView tvUsername;
    TextView tvRank;

    EditText tvEmail; //Change it!!!
    EditText tvAvatar;
    EditText tvBattleTag;
    Spinner regionSpinner;
    ArrayAdapter<CharSequence> adapter;

    EditText tvFacebook;
    EditText tvTwitter;
    EditText tvTwitch;
    EditText tvYoutube;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.user_account, frameLayout);
        navigationView.getMenu().getItem(0).setChecked(true);

        userPref = new UserPreferences(this.getApplicationContext());
        user = new User(userPref.getUserFromUserPref());

        ImageView userAvatar = (ImageView)findViewById(R.id.user_avatar);
        // Loading profile image /*"user.getAvatar czy cos"*/
        String avatarURL;
        if(userPref.getSingleStringPref("avatar") != null){
            avatarURL = userPref.getSingleStringPref("avatar");
        }
        else{
            avatarURL = urlProfileImg;
        }

        Glide.with(this).load(avatarURL)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userAvatar);

        tvUsername = (TextView)findViewById(R.id.user_name);
        tvUsername.setText(user.getUsername());

        TextView userRank = (TextView)findViewById(R.id.user_rank);
        userRank.setText("Rank: " + userPref.getRankPref().toString());

        //TODO czy w ogóle potrzebne
        tvEmail = (EditText) findViewById(R.id.edit_email_account);
        tvEmail.setText(userPref.getSingleStringPref("email"));

        tvAvatar = (EditText) findViewById(R.id.edit_avatar);
        tvAvatar.setText(userPref.getSingleStringPref("avatar"));

//        Button buttonUpload = (Button) findViewById(R.id.button_upload);
//        buttonUpload.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//
//                Intent goto_sign_in = new Intent(getApplicationContext(), LogIn.class);
//                startActivity(goto_sign_in);
//            }
//        });

        tvBattleTag = (EditText)findViewById(R.id.edit_battletag);
        tvBattleTag.setText(userPref.getSingleStringPref("battletag"));

        regionSpinner = (Spinner)findViewById(R.id.region_spinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(userPref.getSingleStringPref(userPref.keyRegion));
        regionSpinner.setSelection(spinnerPosition);
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
                String value = String.valueOf(parent.getItemAtPosition(position));
                if(value != null){
                userPref.setValuePref("newRegion", value);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ImageView favouriteClassIcon = (ImageView) findViewById(R.id.image_view_playerclass);

        if(userPref.getSingleStringPref(userPref.keyFavouriteClass) != null){
            switch (userPref.getSingleStringPref(userPref.keyFavouriteClass)){

                case "druid": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.druid));
                    break;
                }
                case "hunter": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.hunter));
                    break;
                }
                case "mage": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.mage));
                    break;
                }
                case "paladin": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.paladin));
                    break;
                }
                case "priest": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.priest));
                    break;
                }
                case "rogue": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.rogue));
                    break;
                }
                case "shaman": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.shaman));
                    break;
                }
                case "warlock": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.warlock));
                    break;
                }
                case "warrior": {
                    favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.warrior));
                    break;
                }
                default: {
                    break;
                }
            }
        }

        favouriteClassIcon.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    favouriteClassIcon.setColorFilter(Color.rgb(0,169,156));
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    initiatePopupWindow(); // Mała metoda niżej
                    return true;
                } else if (action == MotionEvent.ACTION_CANCEL) {
//                    favouriteClassIcon.setColorFilter(Color.WHITE);
                    return true;
                }
                return false;
            }
        });

        tvFacebook = (EditText)findViewById(R.id.edit_facebook);
        tvFacebook.setText(userPref.getSingleStringPref("facebook"));

        tvTwitter = (EditText)findViewById(R.id.edit_twitter);
        tvTwitter.setText(userPref.getSingleStringPref("twitter"));

        tvTwitch = (EditText)findViewById(R.id.edit_twitch);
        tvTwitch.setText(userPref.getSingleStringPref("twitch"));

        tvYoutube = (EditText)findViewById(R.id.edit_youtube);
        tvYoutube.setText(userPref.getSingleStringPref("youtube"));

        Button deleteAccount = (Button) findViewById(R.id.button_delete_account);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                initiatePopupConfirmWindow();
            }
        });
        Button saveAccount = (Button) findViewById(R.id.button_save_account);
        saveAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateUserData(getUserFromText());
            }
        });
    }

    private void updateUserData(User user){
        Toast.makeText(UserAccount.this,"Updating", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), UserService.class);
        i.putExtra("action", "update");
        i.putExtra("uid", userPref.getSingleStringPref("uid"));
        i.putExtra("updated_user", user);
        startService(i);
    }

    private User getUserFromText(){
        User tempUser = new User();
        if(tvUsername.getText() != null){tempUser.setUsername(tvUsername.getText().toString()); }
        if(tvEmail.getText() != null){tempUser.setEmail(tvEmail.getText().toString()); }
        if(userPref.getSingleStringPref("newFavClass") != null){tempUser.setFavouriteClass(userPref.getSingleStringPref("newFavClass")); }
        if(tvBattleTag.getText() != null || tvBattleTag.getText().toString() != ""){tempUser.setBattletag(tvBattleTag.getText().toString()); }
        if(tvFacebook.getText() != null){tempUser.setFacebook(tvFacebook.getText().toString()); }
        if(tvTwitter.getText() != null){tempUser.setTwitter(tvTwitter.getText().toString()); }
        if(tvTwitch.getText() != null){tempUser.setTwitch(tvTwitch.getText().toString()); }
        if(tvYoutube.getText() != null){tempUser.setYoutube(tvYoutube.getText().toString()); }
        tempUser.setRegion(userPref.getSingleStringPref("newRegion"));
        if(tvAvatar.getText() != null){tempUser.setAvatar(tvAvatar.getText().toString()); }
        return tempUser;
    }

    private PopupWindow initiatePopupWindow() {
        PopupWindow mDropdown = null;
        LayoutInflater mInflater;
        ImageView favouriteClass= null;

        try {

            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = mInflater.inflate(R.layout.favourite_class, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            favouriteClass = (ImageView) findViewById(R.id.image_view_playerclass);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout,FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
            mDropdown.showAtLocation(favouriteClass, Gravity.CENTER, 0,0);

            final PopupWindow finalMDropdown = mDropdown;
            final ImageView favouriteClassIcon = (ImageView) findViewById(R.id.image_view_playerclass);
            final ImageView mage = (ImageView)layout.findViewById(R.id.mage);
            mage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.mage));
                        favouriteClassIcon.setColorFilter(Color.rgb(105, 204, 240));
                        //TODO Add FavClass change
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.mage, Color.rgb(105, 204, 240), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","mage");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView rogue = (ImageView)layout.findViewById(R.id.rogue);
            rogue.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.rogue));
                        favouriteClassIcon.setColorFilter(Color.rgb(255, 245, 105));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.rogue, Color.rgb(255, 245, 105), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","rogue");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView paladin = (ImageView)layout.findViewById(R.id.paladin);
            paladin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.paladin));
                        favouriteClassIcon.setColorFilter(Color.rgb(245, 140, 186));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.paladin, Color.rgb(245, 140, 186), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","paladin");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView druid = (ImageView)layout.findViewById(R.id.druid);
            druid.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.druid));
                        favouriteClassIcon.setColorFilter(Color.rgb(255, 125, 10));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.druid, Color.rgb(255, 125, 10), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","druid");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView shaman = (ImageView)layout.findViewById(R.id.shaman);
            shaman.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.shaman));
                        favouriteClassIcon.setColorFilter(Color.rgb(0, 112, 222));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.shaman, Color.rgb(0, 112, 222), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","shaman");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView warlock = (ImageView)layout.findViewById(R.id.warlock);
            warlock.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.warlock));
                        favouriteClassIcon.setColorFilter(Color.rgb(148, 130, 201));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.warlock, Color.rgb(148, 130, 201), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","warlock");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView priest = (ImageView)layout.findViewById(R.id.priest);
            priest.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.priest));
                        favouriteClassIcon.setColorFilter(Color.WHITE);
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.priest, Color.WHITE, Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","priest");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView warrior = (ImageView)layout.findViewById(R.id.warrior);
            warrior.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.warrior));
                        favouriteClassIcon.setColorFilter(Color.rgb(199, 156, 110));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.warrior, Color.rgb(199, 156, 110),  Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","warrior");
                        return true;
                    }
                    return false;
                }
            });
            final ImageView hunter = (ImageView)layout.findViewById(R.id.hunter);
            hunter.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.hunter));
                        favouriteClassIcon.setColorFilter(Color.rgb(171, 212, 115));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.hunter, Color.rgb(171, 212, 115), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
                        userPref.setValuePref("newFavClass","hunter");
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
        return mDropdown;
    }

    private PopupWindow initiatePopupConfirmWindow() {
        PopupWindow mDropdown = null;
        LayoutInflater mInflater;
        AppCompatButton confirmButton = null;

        try {

            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = mInflater.inflate(R.layout.confirm_delete_account, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            confirmButton = (AppCompatButton) findViewById(R.id.button_delete_account);

            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout,FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
            mDropdown.showAtLocation(confirmButton, Gravity.CENTER, 0,0);

            final PopupWindow finalMDropdown = mDropdown;
            finalMDropdown.setOutsideTouchable(false);

            final TextView textYes = (TextView) layout.findViewById(R.id.text_yes);
            LinearLayout yesButton = (LinearLayout) layout.findViewById(R.id.button_yes);
            yesButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        v.animate().scaleXBy(0.1f).setDuration(5000).start();
                        v.animate().scaleYBy(0.1f).setDuration(5000).start();
                        v.setBackgroundResource(R.drawable.pressed);
                        textYes.animate().scaleXBy(0.1f).setDuration(5000).start();
                        textYes.animate().scaleYBy(0.1f).setDuration(5000).start();
                        textYes.setTextColor(getBaseContext().getResources().getColor(R.color.dark_grey));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        v.animate().cancel();
                        v.animate().scaleX(1f).setDuration(1000).start();
                        v.animate().scaleY(1f).setDuration(1000).start();
                        v.setBackgroundResource(R.drawable.normal);
                        textYes.animate().scaleX(1f).setDuration(1000).start();
                        textYes.animate().scaleY(1f).setDuration(1000).start();
                        textYes.setTextColor(getBaseContext().getResources().getColor(R.color.sea_color));

                        Toast.makeText(getApplicationContext(), "Your account has been removed", Toast.LENGTH_SHORT).show();

//                        Intent i = new Intent(getApplicationContext(), UserService.class);
//                        i.putExtra("action", "delete");
//                        i.putExtra("uid", userPref.getSingleStringPref("uid"));
//                        startService(i);

                        finalMDropdown.dismiss();
                        return true;
                    } else if (action == MotionEvent.ACTION_CANCEL) {
                        v.animate().cancel();
                        v.animate().scaleX(1f).setDuration(1000).start();
                        v.animate().scaleY(1f).setDuration(1000).start();
                        v.setBackgroundResource(R.drawable.normal);
                        textYes.animate().scaleX(1f).setDuration(1000).start();
                        textYes.animate().scaleY(1f).setDuration(1000).start();
                        textYes.setTextColor(getBaseContext().getResources().getColor(R.color.sea_color));
                        return true;
                    }
                    return false;
                }
            });

            final TextView textNo = (TextView) layout.findViewById(R.id.text_no);
            LinearLayout noButton = (LinearLayout) layout.findViewById(R.id.button_no);
            noButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        v.animate().scaleXBy(0.1f).setDuration(5000).start();
                        v.animate().scaleYBy(0.1f).setDuration(5000).start();
                        v.setBackgroundResource(R.drawable.pressed);
                        textNo.animate().scaleXBy(0.1f).setDuration(5000).start();
                        textNo.animate().scaleYBy(0.1f).setDuration(5000).start();
                        textNo.setTextColor(getBaseContext().getResources().getColor(R.color.dark_grey));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        v.animate().cancel();
                        v.animate().scaleX(1f).setDuration(1000).start();
                        v.animate().scaleY(1f).setDuration(1000).start();
                        v.setBackgroundResource(R.drawable.normal);
                        textNo.animate().scaleX(1f).setDuration(1000).start();
                        textNo.animate().scaleY(1f).setDuration(1000).start();
                        textNo.setTextColor(getBaseContext().getResources().getColor(R.color.sea_color));
                        finalMDropdown.dismiss();
                        return true;
                    } else if (action == MotionEvent.ACTION_CANCEL) {
                        v.animate().cancel();
                        v.animate().scaleX(1f).setDuration(1000).start();
                        v.animate().scaleY(1f).setDuration(1000).start();
                        v.setBackgroundResource(R.drawable.normal);
                        textNo.animate().scaleX(1f).setDuration(1000).start();
                        textNo.animate().scaleY(1f).setDuration(1000).start();
                        textNo.setTextColor(getBaseContext().getResources().getColor(R.color.sea_color));
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
        return mDropdown;
    }
}
