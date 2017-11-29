package pl.pjwstk.pgmd.hearthlounge.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import static java.lang.Math.toIntExact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
    FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    private String username;
    private String email;
    private long rank;
    private String role;
    private String uid;
    private Boolean updatedProfile;

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

    public User(String username, String email, String role, String uid, long rank, Boolean updatedProfile){

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
}
