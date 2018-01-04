package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;

/**
 * Created by Froozy on 03.12.2017.
 */

public class UserPreferences {

    public static String keyUsername;
    public static String keyEmail;
    public static String keyRole;
    public static String keyRank;
    public static String keyUid;
    public static String keyUpdatedProfile;

    public static String keyBattletag;
    public static String keyFavouriteClass;

    public static String keyFacebook;
    public static String keyTwitter;
    public static String keyTwitch;
    public static String keyYoutube;

    public static String keyAvatar;
    public static String keyRegion;

    public static String keyLatitude;
    public static String keyLongitude;

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor prefsEditor;

//    public UserPreferences(){
//
//    }

    public UserPreferences(Context context) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.prefsEditor = sharedPrefs.edit();
        keyUsername = context.getString(R.string.keyUsername);
        keyEmail = context.getString(R.string.keyEmail);
        keyRole = context.getString(R.string.keyRole);
        keyRank = context.getString(R.string.keyRank);
        keyUid = context.getString(R.string.keyUid);
        keyUpdatedProfile = context.getString(R.string.keyUpdatedProfile);
        keyBattletag = context.getString(R.string.keyBattleTag);
        keyFavouriteClass = context.getString(R.string.keyFavouriteClass);
        keyFacebook = context.getString(R.string.keyFacebook);
        keyTwitter = context.getString(R.string.keyTwitter);
        keyTwitch = context.getString(R.string.keyTwitch);
        keyYoutube = context.getString(R.string.keyYoutube);
        keyAvatar = context.getString(R.string.keyAvatar);
        keyRegion = context.getString(R.string.keyRegion);
        keyLatitude = context.getString(R.string.keyLatitude);
        keyLongitude = context.getString(R.string.keyLongitude);
    }

    public void setUserPref(User user){

        prefsEditor.putString(keyUsername, user.getUsername());
        prefsEditor.putString(keyEmail, user.getEmail());
        prefsEditor.putLong(keyRank, user.getRank());
        prefsEditor.putInt(keyRole, user.getRole());
        prefsEditor.putString(keyUid, user.getUid());
        prefsEditor.putBoolean(keyUpdatedProfile, user.getUpdatedProfile());
        prefsEditor.putString(keyBattletag, user.getBattletag());
        prefsEditor.putString(keyFavouriteClass, user.getFavouriteClass());
        prefsEditor.putString(keyFacebook, user.getFacebook());
        prefsEditor.putString(keyTwitter, user.getTwitter());
        prefsEditor.putString(keyTwitch, user.getTwitch());
        prefsEditor.putString(keyYoutube, user.getYoutube());
        prefsEditor.putString(keyAvatar, user.getAvatar());
        prefsEditor.putString(keyRegion, user.getRegion());

        prefsEditor.commit();
    }

    public void clearUserPref(){
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public boolean contain(String key){
        return sharedPrefs.contains(key);
    }

    public void updateUserPref(){



    }

    public String getUsernamePref() {
        return sharedPrefs.getString(keyUsername,"username null");
    }

    public String getSingleStringPref(String key){

        if(sharedPrefs.contains(key)){
        return sharedPrefs.getString(key,"DAFUK");}
        return null;
    }

    public Boolean getUpdatedProfilePref(){

        return sharedPrefs.getBoolean("updatedProfile", false);
    }

    public Long getRankPref(){
        return sharedPrefs.getLong("rank",0);
    }

    public int getRolePref(){
        return sharedPrefs.getInt("role",0);
    }


    public void setValuePref(String key,String text) {
        prefsEditor.putString(key, text);
        prefsEditor.commit();
    }

    public User getUserFromUserPref(){
        User user = new User(this.getSingleStringPref(keyUsername), this.getSingleStringPref(keyEmail), this.getRolePref(), this.getSingleStringPref(keyUid),
                this.getRankPref(), this.getUpdatedProfilePref(), this.getSingleStringPref(keyBattletag), this.getSingleStringPref(keyFavouriteClass), this.getSingleStringPref(keyFacebook),
                this.getSingleStringPref(keyTwitter), this.getSingleStringPref(keyTwitch), this.getSingleStringPref(keyYoutube), this.getSingleStringPref("avatar"), this.getSingleStringPref("region"));
        return user;
    }

    public void setLanOrLng(String key ,double value){

        prefsEditor.putString(key, Double.toString(value));
        prefsEditor.commit();

    }

    public double getLanOrLng(String key){

        if(key.equals(this.keyLatitude) || key.equals(this.keyLongitude)) {

            String temp = this.getSingleStringPref(key);
            return Double.parseDouble(temp);
        }
        return 0;
    }
}