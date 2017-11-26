package pl.pjwstk.pgmd.hearthlounge.authentication;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.widget.FrameLayout;

import java.io.File;

import pl.pjwstk.pgmd.hearthlounge.R;
import pl.pjwstk.pgmd.hearthlounge.model.User;
import pl.pjwstk.pgmd.hearthlounge.view.DrawerMenu;

/**
 * Created by Maciek Dembowski on 25.11.2017.
 */

public class UserAccount extends DrawerMenu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.user_account, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);


    }
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
