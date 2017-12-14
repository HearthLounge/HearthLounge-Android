package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.MyApplicationContext;

/**
 * Created by Froozy on 14.12.2017.
 */

public class FirebaseConnector {

    FirebaseDatabase fbDatabase;
    private FirebaseAuth fbAuth;
    private DatabaseReference fbDataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hearthlounge-32197.firebaseio.com");




    //PROTOTYPE !!!1






//    public void logInUser(String email,String password){
//
//        fbAuth.(email, password)
//                .addOnCompleteListener(R.id., new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            //logInUserService(fbAuth.getCurrentUser().getUid());
//                        }
//                        else {
//
//                        }
//
//
//                    }
//                });
//
//    }
//
//
//
//    public void logInUserService(String uid){    //User Data Service
//        Intent i = new Intent(MyApplicationContext.getAppContext(), UserService.class);
//        // potentially add data to the intent
//        i.putExtra("action", "login");
//        i.putExtra("uid", uid);
//        startService(i);
//    }


}
