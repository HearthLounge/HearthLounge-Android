package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;
import java.util.Map;

import pl.pjwstk.pgmd.hearthlounge.CardsJSON;
import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;
import pl.pjwstk.pgmd.hearthlounge.view.CircleTransform;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;
import pl.pjwstk.pgmd.hearthlounge.view.MakeImageToast;

/**
 * Created by Maciek Dembowski/Froozy on 25.11.2017.
 */

public class UserAccount extends DrawerMenu{

    UserPreferences userPref;
    public SharedPreferences.Editor userPrefs;
    public MakeImageToast toast;
    private static final String urlProfileImg = "https://cdn.pixabay.com/photo/2016/12/13/16/17/dancer-1904467_1280.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.user_account, frameLayout);
        navigationView.getMenu().getItem(0).setChecked(true);

        userPref = new UserPreferences(this.getApplicationContext());

        // NAZEWNICTWO JAK userAvatar I INNE ZMIEN JAK CHCESZ :D

        ImageView userAvatar = (ImageView)findViewById(R.id.user_avatar);
        // Loading profile image /*"user.getAvatar czy cos"*/
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userAvatar);

        TextView userName = (TextView)findViewById(R.id.user_name);
        userName.setText(userPref.getSingleStringPref("username"));

        TextView userRank = (TextView)findViewById(R.id.user_rank);
        userRank.setText("Rank: " + userPref.getRankPref().toString());

        //TODO czy w ogóle potrzebne
        EditText editEmail = (EditText) findViewById(R.id.edit_email_account);
        editEmail.setText(userPref.getSingleStringPref("email"));

        Button buttonUpload = (Button) findViewById(R.id.button_upload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                updateUserData();
                Intent goto_sign_in = new Intent(getApplicationContext(), LogIn.class);
                startActivity(goto_sign_in);
            }
        });

        EditText battletag = (EditText)findViewById(R.id.edit_battletag);
        battletag.setText(userPref.getSingleStringPref("battletag"));

        final ImageView favouriteClassIcon = (ImageView) findViewById(R.id.image_view_playerclass);
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
                }
                return false;
            }
        });

        EditText facebook = (EditText)findViewById(R.id.edit_facebook);
        facebook.setText(userPref.getSingleStringPref("facebook"));

        EditText twitter = (EditText)findViewById(R.id.edit_twitter);
        twitter.setText(userPref.getSingleStringPref("twitter"));

        EditText twitch = (EditText)findViewById(R.id.edit_twitch);
        twitch.setText(userPref.getSingleStringPref("twitch"));

        EditText youtube = (EditText)findViewById(R.id.edit_youtube);
        youtube.setText(userPref.getSingleStringPref("youtube"));

        Button deleteAccount = (Button) findViewById(R.id.button_delete_account);
        // Usuwanie z FIREBASE

        Button saveAccount = (Button) findViewById(R.id.button_save_account);
        saveAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//
//                if(TextUtils.isEmpty(edit_name.getEditableText().toString())){
//                    Toast.makeText(SignUp.this, "Please enter nickname", Toast.LENGTH_SHORT).show();
//                }
//                else if(TextUtils.isEmpty(edit_email.getEditableText().toString())){
//                    Toast.makeText(SignUp.this, "Please enter email", Toast.LENGTH_SHORT).show();
//                }
//                else if(TextUtils.isEmpty(edit_password.getEditableText().toString())){
//                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(SignUp.this, "Test Toast 01", Toast.LENGTH_SHORT).show();
//                    create_user(edit_name.getText().toString(), edit_email.getText().toString(), edit_password.getText().toString());
//                }
            }
        });

        // TODO RESZTA

    }

    private void updateUserData(){


        // TODO add checker of changes and then save them to Shared Pref. because there's not another way to take data in service from there ;)


        Toast.makeText(UserAccount.this,"Updating", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), UserService.class);
        i.putExtra("action", "update");
        i.putExtra("uid", userPref.getSingleStringPref("uid"));
        startService(i);
    }

    private HashMap<String,Object> checkChanges() {

        HashMap<String,Object> changes = new HashMap<>();

        // TODO :/ Boring thing



        return changes;
    }


    private PopupWindow initiatePopupWindow() {
        PopupWindow mDropdown = null;
        LayoutInflater mInflater;
        ImageView favouriteClass;

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

            final PopupWindow finalMDropdown = mDropdown; // Potrzebne, żeby popup menu się zamknęło po wybraniu opcji
            final ImageView favouriteClassIcon = (ImageView) findViewById(R.id.image_view_playerclass);

            final ImageView mage = (ImageView)layout.findViewById(R.id.mage);
            mage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        favouriteClassIcon.setImageDrawable(getResources().getDrawable(R.drawable.mage));
                        favouriteClassIcon.setColorFilter(Color.rgb(105, 204, 240));
                        return true;
                    } else if (action == MotionEvent.ACTION_UP) {
                        toast.makeImageToast(UserAccount.this, "You selected ", R.drawable.mage, Color.rgb(105, 204, 240), Toast.LENGTH_SHORT).show(); // + item.getTitle()
                        finalMDropdown.dismiss();
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
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
        return mDropdown;
    }


    // JAKIEŚ GÓWNA ZE STRONY FIRE BASE JAK WRZUCIĆ Z GALERII ZDJĘCIE CHYBA
//
//    // Create a storage reference from our app
//    StorageReference storageRef = storage.getReference();
//
//    // Create a reference to "mountains.jpg"
//    StorageReference mountainsRef = storageRef.child("mountains.jpg");
//
//    // Create a reference to 'images/mountains.jpg'
//    StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");
//
//// While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
//
//    Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//    StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
//    uploadTask = riversRef.putFile(file);
//
//// Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception exception) {
//            // Handle unsuccessful uploads
//        }
//    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//        }
//    });
//
//    // Create file metadata including the content type
//    StorageMetadata metadata = new StorageMetadata.Builder()
//            .setContentType("image/jpg")
//            .build();
//
//// Upload the file and metadata
//    uploadTask = storageRef.child("images/mountains.jpg").putFile(file, metadata);
//
//    uploadTask = storageRef.child("images/mountains.jpg").putFile(file);
//
//// Pause the upload
//        uploadTask.pause();
//
//// Resume the upload
//        uploadTask.resume();
//
//// Cancel the upload
//        uploadTask.cancel();
//
//
//    // Observe state change events such as progress, pause, and resume
//        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//            System.out.println("Upload is " + progress + "% done");
//        }
//    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
//            System.out.println("Upload is paused");
//        }
//    });
//
//
//    StorageReference mStorageRef;  //mStorageRef was previously used to transfer data.
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        // If there's an upload in progress, save the reference so you can query it later
//        if (mStorageRef != null) {
//            outState.putString("reference", mStorageRef.toString());
//        }
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        // If there was an upload in progress, get its reference and create a new StorageReference
//        final String stringRef = savedInstanceState.getString("reference");
//        if (stringRef == null) {
//            return;
//        }
//        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);
//
//        // Find all UploadTasks under this StorageReference (in this example, there should be one)
//        List<UploadTask> tasks = mStorageRef.getActiveUploadTasks();
//        if (tasks.size() > 0) {
//            // Get the task monitoring the upload
//            UploadTask task = tasks.get(0);
//
//            // Add new listeners to the task using an Activity scope
//            task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot state) {
//                    handleSuccess(state); //call a user defined function to handle the event.
//                }
//            });
//        }
//    }
//
//    uploadTask = mStorageRef.putFile(localFile);
//        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//        @Override
//        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//            Uri sessionUri = taskSnapshot.getUploadSessionUri();
//            if (sessionUri != null && !saved) {
//                saved = true;
//                // A persisted session has begun with the server.
//                // Save this to persistent storage in case the process dies.
//            }
//        }
//    });
//
//
//    //resume the upload task from where it left off when the process died.
//    //to do this, pass the sessionUri as the last parameter
//    uploadTask = mStorageRef.putFile(localFile,
//            new StorageMetadata.Builder().build(), sessionUri);
}
