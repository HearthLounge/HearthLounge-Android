import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class User extends Application {

    FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
    FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    private String username;
    private String email;
    private int rank;
    private String role;
    private String uid;
    private Boolean updatedProfile;

    public User(){



    }

    public User(String username, String email, String password, String uid){   //Uzupełnić o dodanie reszty ze zmiennych bo auth idzie oddzielnie

        this.username = username;
        this.email = email;
        this.username = username;
        this.role = "user";
        this.uid = uid;
        this.updatedProfile = false;
        this.rank = 1;
    }

    public User(String username, String email, String role, String uid, int rank, Boolean updatedProfile){

        this.username = username;
        this.email = email;
        this.username = username;
        this.role = role;
        this.uid = uid;
        this.updatedProfile = updatedProfile;
        this.rank = rank;

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

    public int getRank() {
        return rank;
    }

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
}
