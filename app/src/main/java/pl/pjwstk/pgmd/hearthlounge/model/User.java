package pl.pjwstk.pgmd.hearthlounge.model;

import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static java.lang.Math.toIntExact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

//TODO Make one per time!!!
//TODO MAKE EMPTY battletag, favClass itp :|
public class User {

    FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
    FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    private String username;
    private String email;
    private Image avatar;   //How to do it???

    private long rank;
    private String role;
    private String uid;
    private Boolean updatedProfile;

    private String battletag;
    private String favClass;

    private String facebook;
    private String twitter;
    private String twitch;
    private String youtube;

    public User(){



    }

    public User(String username, String email, String uid){   //Uzupełnić o dodanie reszty ze zmiennych bo auth idzie oddzielnie

        this.username = username;
        this.email = email;
        this.username = username;
        this.role = "user";
        this.uid = uid;
        this.updatedProfile = false;
        this.rank = 1;
    }

    public User(String username, String email, String role, String uid, long rank, Boolean updatedProfile/*, String battletag, String favClass, String facebook, String twitter, String twitch, String youtube*/){

        this.username = username;
        this.email = email;
        this.username = username;
        this.role = role;
        this.uid = uid;
        this.updatedProfile = updatedProfile;
        this.rank = rank;

    }

    public User(String username, String email, String role, String uid, String rank, String updatedProfile){

        this.username = username;
        this.email = email;
        this.username = username;
        this.role = role;
        this.uid = uid;
        if(updatedProfile == "false"){
            this.updatedProfile = false;
        }
        else if(updatedProfile == "true"){
            this.updatedProfile = true;
        }
        this.rank = Long.parseLong(rank);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRank() {
        return rank;
    }

    @Exclude
    public void setRank(String rank){
        this.rank = Long.parseLong(rank);
    }

    public void setRank(Long rank){
        this.rank = rank;
    }

    @Exclude
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getUpdatedProfile() {
        return updatedProfile;
    }

    public void setUpdatedProfile(Boolean updatedProfile) {
        this.updatedProfile = updatedProfile;
    }

    @Exclude
    public  void setUpdatedProfile(String updatedProfile){
        if(updatedProfile == "false"){
            this.updatedProfile = false;
        }
        else if(updatedProfile == "true"){
            this.updatedProfile = true;
        }
    }

    public String getBattletag() {
        return battletag;
    }

    public void setBattletag(String battletag) {
        this.battletag = battletag;
    }

    public String getFavClass() {
        return favClass;
    }

    public void setFavClass(String favClass) {
        this.favClass = favClass;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTwitch() {
        return twitch;
    }

    public void setTwitch(String twitch) {
        this.twitch = twitch;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
