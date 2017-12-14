package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;

/**
 * Created by Froozy on 03.12.2017.
 */

public class UserPreferences {

    User userPref;

    public static final String keyUsername = "username";
    public static final String keyEmail = "email";
    public static final String keyRole = "role";
    public static final String keyRank = "rank";
    public static final String keyUid = "uid";
    public static final String keyUpdatedProfile = "updatedProfile";

    public static final String keyBattletag = "battletag";
    public static final String keyFavClass = "favClass";

    public static final String keyFacebook = "facebook";
    public static final String keyTwitter = "twitter";
    public static final String keyTwitch = "twitch";
    public static final String keyYoutube = "youtube";

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor prefsEditor;

//    public UserPreferences(){
//
//    }

    public UserPreferences(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.prefsEditor = sharedPrefs.edit();
    }

    public void setUserPref(User user){


        prefsEditor.putString(keyUsername, user.getUsername());
        prefsEditor.putString(keyEmail, user.getEmail());
        prefsEditor.putLong(keyRank, user.getRank());
        prefsEditor.putString(keyRole, user.getRole());
        prefsEditor.putString(keyUid, user.getUid());
        prefsEditor.putString(keyUpdatedProfile, user.getUpdatedProfile().toString());
        prefsEditor.putString(keyBattletag, user.getBattleTag());
        prefsEditor.putString(keyFavClass, user.getFavClass());
        prefsEditor.putString(keyFacebook, user.getFacebook());
        prefsEditor.putString(keyTwitter, user.getTwitter());
        prefsEditor.putString(keyTwitch, user.getTwitch());
        prefsEditor.putString(keyYoutube, user.getYoutube());

        prefsEditor.commit();
    }

    public void clearUserPref(){
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public void updateUserPref(){



    }

    public void setSinglePref(String key,String value){

        if(value != null){
            prefsEditor.putString(key,value);
        }

    }

    public String getUsernamePref() {
        return sharedPrefs.getString(keyUsername,"username null");
    }

    public String getSingleStringPref(String key){

        return sharedPrefs.getString(key,"error");

    }

    public Boolean getUpdatedProfilePref(){

        return sharedPrefs.getBoolean("updatedProfile", false);
    }

    public Long getRankPref(){
        return sharedPrefs.getLong("rank",0);
    }

    public void setValuePref(String key,String text) {
        prefsEditor.putString(key, text);
        prefsEditor.commit();
    }




}
