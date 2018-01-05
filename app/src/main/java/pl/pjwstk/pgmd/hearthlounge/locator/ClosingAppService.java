package pl.pjwstk.pgmd.hearthlounge.locator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClosingAppService extends Service {

    private static FirebaseFirestore fbCloud = FirebaseFirestore.getInstance();
    private CollectionReference fbLocRef = fbCloud.collection("Localization");
    MapsActivity mapsActivity;

    public ClosingAppService(){

    }

    public ClosingAppService(MapsActivity mapsActivity) {

        this.mapsActivity = mapsActivity;

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        // Burn your allies
        mapsActivity.deleteLocalization();
        mapsActivity.waiter.timer.cancel();
        Log.d("ClosingAppService", "CLEANING OTR");
        // Destroy the service
        stopSelf();
    }

    @Override
    public void onDestroy() {
        mapsActivity.deleteLocalization();
        mapsActivity.waiter.timer.cancel();
        Log.d("ClosingAppService", "CLEANING OD");
        // Destroy the service
        stopSelf();
    }
}
