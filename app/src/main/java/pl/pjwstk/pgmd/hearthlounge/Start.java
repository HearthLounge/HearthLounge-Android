package pl.pjwstk.pgmd.hearthlounge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Maciek Dembowski on 28.12.2017.
 */

public class Start extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiate_app);

        //SPLASH SCREEN
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1);  //Delay of 10000 = 10 seconds
                    DeckListCache.getInstance();
                    RedditCache.getInstance().getRedditList();
                    Intent startApp = new Intent(getApplicationContext(), JSON.class);
                    startActivity(startApp);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        welcomeThread.start();
    }
}
