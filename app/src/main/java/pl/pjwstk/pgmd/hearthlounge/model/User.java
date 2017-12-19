package pl.pjwstk.pgmd.hearthlounge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User implements Parcelable {

    private FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    private String username;
    private String email;
    //private Image avatar;   //How to do it???

    private long rank;
    private int role;
    private String uid;
    private Boolean updatedProfile;

    private String battleTag;
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
        this.role = 3;
        this.uid = uid;
        this.updatedProfile = false;
        this.rank = 1;
    }

//    public User(String username, String email, String role, String uid, long rank, Boolean updatedProfile/*, String battleTag, String favClass, String facebook, String twitter, String twitch, String youtube*/){
//
//        this.username = username;
//        this.email = email;
//        this.username = username;
//        this.role = role;
//        this.uid = uid;
//        this.updatedProfile = updatedProfile;
//        this.rank = rank;
//
//    }

    public User(String username, String email, int role, String uid, String rank, String updatedProfile){

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

    public User(String username, String email, int role, String uid, Long rank, Boolean updatedProfile, String battleTag, String favClass, String facebook, String twitter, String twitch, String youtube){

        this.username = username;
        this.email = email;
        this.username = username;
        this.role = role;
        this.uid = uid;
        this.rank = rank;
        this.updatedProfile = updatedProfile;
        this.battleTag = battleTag;
        this.favClass = favClass;
        this.facebook = facebook;
        this.twitter = twitter;
        this.twitch = twitch;
        this.youtube = youtube;




    }

    public User(User user) {
        this.username = user.username;
        this.email = user.email;
        this.role = user.role;
        this.uid = user.uid;
        this.rank = user.rank;
        this.updatedProfile = user.updatedProfile;
        this.battleTag = user.battleTag;
        this.favClass = user.favClass;
        this.facebook = user.facebook;
        this.twitter = user.twitter;
        this.twitch = user.twitch;
        this.youtube = user.youtube;
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        rank = in.readLong();
        role = in.readInt();
        uid = in.readString();
        byte tmpUpdatedProfile = in.readByte();
        updatedProfile = tmpUpdatedProfile == 0 ? null : tmpUpdatedProfile == 1;
        battleTag = in.readString();
        favClass = in.readString();
        facebook = in.readString();
        twitter = in.readString();
        twitch = in.readString();
        youtube = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
//        if(username == null){ username = "";}
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        //if(email == null){ email = "";}
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpdatedProfileS() { return updatedProfile.toString();}

    public Boolean getUpdatedProfile() {
        return updatedProfile;
    }

    public void setUpdatedProfile(Boolean updatedProfile) {
        this.updatedProfile = updatedProfile;
    }

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
    }

    public String getFavClass() {
        //if(favClass == null){ favClass = "";}
        return favClass;
    }

    public void setFavClass(String favClass) {
        this.favClass = favClass;
    }

    public String getFacebook() {
//        if(facebook == null){ facebook = "";}
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
//        if(twitter == null){ twitter = "";}
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTwitch() {
//        if(twitch == null){ twitch = "";}
        return twitch;
    }

    public void setTwitch(String twitch) {
        this.twitch = twitch;
    }

    public String getYoutube() {
//        if(youtube == null){ youtube = "";}
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeLong(rank);
        dest.writeInt(role);
        dest.writeString(uid);
        dest.writeByte((byte) (updatedProfile == null ? 0 : updatedProfile ? 1 : 2));
        dest.writeString(battleTag);
        dest.writeString(favClass);
        dest.writeString(facebook);
        dest.writeString(twitter);
        dest.writeString(twitch);
        dest.writeString(youtube);
    }
}
